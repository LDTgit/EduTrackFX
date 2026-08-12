package com.example.edutrackfx;

import javafx.geometry.Insets; //pentru margini interioare(padding)
import javafx.scene.Scene; //pentru geometria ferestrei
import javafx.scene.control.*; // pentru controale UI - buton, eticheta, tabel
// Importuri pentru legarea coloanelor de variabilele din clasa QuizQuestion
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

//Clasa pentru afisarea istoricului intr-un tabel
public class GestiuneScene {
    // Referinta catre managerul de scene pentru navigare si acces la lista de date
    private final SceneManager manager;

    // Constructor care primeste instanta de management
    public GestiuneScene(SceneManager manager){
        this.manager = manager;
    }

    // Construim interfata grafica pentru ecranul de gestiune de date
    public Scene getScene(){
        VBox layout = new VBox(10); //layout vertical cu spatiere de 10 pixeli intre elemente
        layout.setPadding(new Insets(20)); // margine de 20px de jur imprejur

        Label title = new Label("Istoric intrebari descarcate"); // Titlul sectiunii
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;"); // Stilizare titlu sectiune

        //Configurare tabel
        //Declarare tabel care va contine obiecte de tip QuizQuestion
        TableView<QuizQuestion> table = new TableView<>();

        //Concectam tabelul la lista history din SceneManager
        // Deoarece lista este de tip ObservableList, tabelul se va actualiza singur la fiecare intrebare noua
        table.setItems(manager.getHistory());

        //Coloana 1: Intrebarea
        //Definim prima coloana pentru textul intrebarii
        TableColumn<QuizQuestion,String> colQuestion = new  TableColumn<>("Intrebare:");
        // PropertyValueFactory cauta automat metoda getQuestion() in clasa QuizQuestion
        colQuestion.setCellValueFactory(new PropertyValueFactory<>("question"));
        colQuestion.setPrefWidth(400); // latime fixa

        //Coloana 2: Raspuns corect
        //Definim a doua coloana pentru raspunsul corect
        TableColumn<QuizQuestion,String> colCorect = new TableColumn<>("Raspuns corect: ");
        // PropertyValueFactory cauta automat metoda getCorrectAnswer() in clasa QuizQuestion
        colCorect.setCellValueFactory(new PropertyValueFactory<>("correctAnswer"));
        colCorect.setPrefWidth(200);

        //Adaugam coloanele in tabel
        table.getColumns().add(colQuestion);
        table.getColumns().add(colCorect);

        //Buton inapoi
        Button btnBack = new Button("Inapoi la Dashboard");
        // Cand apasam butonul, managerul schimba scena actuala cu cea de Dashboard
        btnBack.setOnAction(e->manager.switchScene(new DashboardScene(manager).getScene(), "Dashboard"));

        //Adaugam componentele in layout-ul vertical
        layout.getChildren().addAll(title,table,btnBack);

        // Returneaza scena finala cu dimensiunile 800x600px
        return  new Scene(layout, 800, 600);

    }
}
