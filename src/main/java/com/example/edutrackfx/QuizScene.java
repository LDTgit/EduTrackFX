package com.example.edutrackfx;

//Importuri pentru layout, controale si culori JavaFX
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
//Import pentru iconite externe Ikonli
import org.kordamp.ikonli.javafx.FontIcon;

//Importuri pentru comunicarea prin Internet (HTTP)
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

//Importuri pentru colectii si algoritmi (amestecare raspunsuri)
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Clasa care gestioneaza logica testului grila cu date descarcate prin API
public class QuizScene {
    private final SceneManager manager; //referinta catre managerul de scene
    private final HttpClient client = HttpClient.newHttpClient(); // Client HTTP creat o singura data, reutilizabil

    private VBox optionsContainer; //container pentru butoanele radio - variante de raspuns
    private Label lblQuestion; // eticheta pentru textul intrebarii
    private ToggleGroup group; // grup care permite slectarea unui singur raspuns
    private Button btnCheck; // buton de verificare raspuns
    private QuizQuestion currentQuestion; // Retinem intrebarea curenta

    //Constructorul clasei
    public QuizScene(SceneManager manager){
        this.manager= manager;
    }

    //Construim interfata grafica a ecranului de Quiz
    public Scene getScene(){
        VBox layout = new VBox(20); //VBox principal cu spatiere de 20px
        layout.setPadding(new Insets(30));
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: #fdfefe"); //fundal alb

        //Configurare eticheta pentru intrebare
        lblQuestion = new Label("Apasa pe buton pentru a incarca o intrebare");
        lblQuestion.setWrapText(true); //permitem textului sa treaca pe randul urmator
        lblQuestion.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-alignment: center;");

        //Container pentru variantele de raspuns
        optionsContainer = new VBox(10);
        optionsContainer.setAlignment(Pos.CENTER_LEFT);
        optionsContainer.setPadding(new Insets(0,50,0,50));
        group = new ToggleGroup(); //initializare grup de selectie unica

        //Buton verificare
        btnCheck = new Button("Verifica");
        FontIcon checkIcon = new FontIcon("fas-check-double"); // Iconita din FontAwesome
        checkIcon.setIconColor(Color.WHITE);
        btnCheck.setGraphic(checkIcon); // adauga iconita langa text
        btnCheck.setDisable(true); //Dezactivat pana la incarcarea unei intrebari
        btnCheck.setPrefWidth(200);
        btnCheck.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white;");

        //Actiunea butonului de verificare
        btnCheck.setOnAction(_->handleCheckAnswer());

        //Buton Incarcare
        Button btnLoad = new Button("Incarca intrebare noua");
        btnLoad.setPrefWidth(200);
        btnLoad.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; -fx-font-size: 14px;");
        // Apelare metoda care face cererea catre API
        btnLoad.setOnAction(_->loadNewQuestion());

        //Buton inapoi
        Button btnBack = new Button("Inapoi la Dashboard");
        btnBack.setPrefWidth(200);
        btnBack.setOnAction(e-> manager.switchScene(new DashboardScene(manager).getScene(), "Dashboard"));

        //Adaugare elemente in layout
        layout.getChildren().addAll(lblQuestion, optionsContainer, btnCheck, btnLoad, btnBack);
        return new Scene(layout, 800, 600);
    }

    // Metoda care comunica cu API-ul extern pentru a prelua o intrebare
    private void loadNewQuestion(){
        try{
            // Crearea cererii HTTP catre URL-ul OpenTriviaDB
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create("https://opentdb.com/api.php?amount=1&type=multiple")).build();
           // Trimiterea cererii si asteptarea raspunsului sub forma de text JSON
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String body = response.body();

            //Extractie manuala din JSON
            // Extragem valorile cautand pozitia cheilor in textul brut
            String q = extractValue(body, "question");
            String correct = extractValue(body, "correct_answer");
            // Separam raspunsurile incorecte din array-ul JSON
            String incorrectPart = body.split("\"incorrect_answers\":\\[")[1].split("]")[0];
            String[] incorrects = incorrectPart.replace("\"", "").split(",");

            // Cream lista de raspunsuri
            List<String> answers = new ArrayList<>();
            for (String s : incorrects) answers.add(decodeHtml(s)); // decondare caractere speciale
            answers.add(decodeHtml(correct));
            Collections.shuffle(answers); // amestecam lista pentru ca raspunsul corect sa nu fie mereu ultimul

            // Salvam intrebarea curenta in obiectul currentQuestion
            currentQuestion = new QuizQuestion(decodeHtml(q),decodeHtml(correct), answers);

            //Actualizare interfata
            lblQuestion.setText(decodeHtml(q));
            optionsContainer.getChildren().clear(); //stergem intrebarile vechi
            for (String ans : answers){
                RadioButton rb = new RadioButton(ans);
                rb.setToggleGroup(group); //atasam la grup pentru selectie unica
                rb.setStyle("-fx-font-size: 14px");
                optionsContainer.getChildren().add(rb);
            }
            btnCheck.setDisable(false); //activam butonul de verificare

        } catch (Exception e){
            lblQuestion.setText("Eroare la conectarea cu API"); //gestionare erori
        }
    }

    // Metoda de verificare daca raspunsul selectat este corect
    private  void handleCheckAnswer(){
        RadioButton selected = (RadioButton) group.getSelectedToggle(); //verificam ce RadioButton a fost selectat
        if (selected==null){
            showAlert("Atentie", "Selecteaza un raspuns!");
            return;
        }

        String userAnswer = selected.getText();
        // Comparam raspunsul utilizatorului cu cel salvat din API
        boolean isCorrect = userAnswer.equals(currentQuestion.getCorrectAnswer());

        // Afisare feedback prin fereastra de pop-up
        if (isCorrect){
            showAlert("Rezultat", "Corect!");
        } else {
            showAlert("Rezultat", "Gresit! Raspunsul corect era: " + currentQuestion.getCorrectAnswer());
        }

        //Trimitem rezultatul catre SceneManager pentru Tabel si Grafic
        manager.addResult(currentQuestion,isCorrect);
        btnCheck.setDisable(true); //Nu lasam utilizatorul sa raspunda de mai multe ori la aceeasi intrebare
    }

    // Metoda utilitara pentru a extrage valori dintr-un String de tip JSON
    private String extractValue(String json, String key){
        String pattern = "\"" + key + "\":\"";
        int start = json.indexOf(pattern) + pattern.length();
        int end = json.indexOf("\"", start);
        return json.substring(start,end);
    }

    // Metoda care transforma entitatile HTML in caractere normale
    private String decodeHtml(String input){
        return input.replace("&quot;","\"").replace("&#039;","'").replace("&amp;", "&");
    }

    // creare si afisare fereastra de alerta de tip informatie
    private void showAlert(String title, String content){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait(); // Blocheaza interactiunea pana cand userul apasa OK
    }
}
