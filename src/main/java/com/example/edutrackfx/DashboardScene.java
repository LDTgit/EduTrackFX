package com.example.edutrackfx;

import javafx.geometry.Insets; //pentru margini interioare(padding)
import javafx.geometry.Pos; //pentru alinierea elementelor (centru, stanga, etc)
import javafx.scene.Scene; //obiectul care reprezinta continutul ferestrei
import javafx.scene.control.Button; //componenta buton
import javafx.scene.control.ContentDisplay; //enumerare pentru pozitia iconitei fata de text
import javafx.scene.control.Label; //componenta pentru afisarea textului static
import javafx.scene.layout.GridPane; //layout tip tabel (linii si coloane)
import javafx.scene.layout.VBox; //layout vertical (elemente asezate unul sub altul)
import org.kordamp.ikonli.javafx.FontIcon; //import pentru utilizarea iconitelor vectoriale din libraria Ikonli


// Clasa care defineste interfata si logica panoului principal (Dashboard)
public class DashboardScene {
    private final SceneManager manager; //referinta catre managerul de scene pentru a permite navigarea

    //Constructor care primeste managerul pentru schimbarea scenelor
    public DashboardScene(SceneManager manager){
        this.manager = manager;
    }

    //Construim scena Dashboardului
    public Scene getScene(){
        VBox root = new VBox(20); //VBox este radacina ecranului, elementele sunt asezate vertica cu spatiu de 20px
        root.setPadding(new Insets(40));//adaugam spatiu de 40px padding
        root.setAlignment(Pos.CENTER); //centram tot continutul
        root.setStyle("-fx-background-color: #ecf0f1;"); //setam culoarea de fundal gri deschis

        //Eticheta Panou de control
        Label lblWelcome = new Label("Panou de control");
        lblWelcome.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        //Folosim un GridPane pentru a aseza butoanele pe 2 coloane
        GridPane grid = new GridPane(); //initializam grila pentru butoane
        grid.setHgap(15); //spatiu orizontal intre butoane
        grid.setVgap(15); //spatiu vertical intre butoane
        grid.setAlignment(Pos.CENTER);

        //Crearea butoanelor pentru cele 6 ecrane
        // Primul paramatru este textul, al doilea este codul de iconita FontAwesome
        Button btnQuiz = createIconButton("Test Grila", "fas-question-circle");
        Button btnGestiune = createIconButton("Gestiune Date", "fas-database");
        Button btnStats = createIconButton("Statistici", "fas-chart-pie");
        Button btnHelp = createIconButton("Ajutor", "fas-info-circle");
        Button btnLogout = createIconButton("Deconectare", "fas-sign-out-alt");

        //Logica de navigare
        // Definim actiunile pentru click pentru fiecare buton. SceneManager schimba scena curenta cu una noua
        btnQuiz.setOnAction(e->manager.switchScene(new QuizScene(manager).getScene(), "Test Grila"));
        btnGestiune.setOnAction(e->manager.switchScene(new GestiuneScene(manager).getScene(), "Baza de date"));
        btnStats.setOnAction(e->manager.switchScene(new StatsScene(manager).getScene(), "Performanta"));
        btnHelp.setOnAction(e->manager.switchScene(new HelpScene(manager).getScene(), "Ajutor"));
        btnLogout.setOnAction(e->manager.switchScene(new LoginScene(manager).getScene(), "Autentificare"));

        //Adaugare butoane in Grid grid.add(element, coloana, rand)
        grid.add(btnQuiz, 0, 0);
        grid.add(btnGestiune, 1, 0);
        grid.add(btnStats, 0, 1);
        grid.add(btnHelp, 1, 1);
        grid.add(btnLogout, 0, 2, 2, 1); // Intins pe 2 coloane pentru a fi centrat cub celelalte

        //Adaugam titlul si grila in containerul vertical principal
        root.getChildren().addAll(lblWelcome, grid);

        //Returneaza o scena noua cu dimensiunile de 800x600 pixeli
        return new Scene(root, 800, 600);
    }

// Metoda care creeaza un buton stilizat cu iconita si efect de hover
    private Button createIconButton(String text, String iconCode){
        //Instantiem iconita vectoriala din libraria Ikonli
        FontIcon icon = new FontIcon(iconCode);
        icon.setIconSize(30); //dimensiunea iconitei
        icon.setIconColor(javafx.scene.paint.Color.web("#2c3e50")); //culoare albastru inchis

        Button btn = new Button(text);
        btn.setGraphic(icon); //Adaugam iconita in buton
        btn.setContentDisplay(ContentDisplay.TOP);//Iconita deasupra textului
        btn.setPrefSize(200,100); //dimensiunea preferata a butonului

        //Stilul de baza
        String baseStyle = "-fx-background-color: #ffffff; " +
                "-fx-border-color: #dcdde1; " +
                "-fx-border-radius: 10; " +
                "-fx-background-radius: 10; " +
                "-fx-font-weight: bold; " +
                "-fx-font-size: 14px; " +
                "-fx-cursor: hand; ";

        //Stilul pentru mouse-over (Hover)
        // folosim dropshadow pentru a adauga o umbra fina care da impresia ca butonul se ridica de pe ecran (elevation)
        String hoverStyle = "-fx-background-color: #f1f2f6; " +
                "-fx-border-color: #3498db; " +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 0);";

        btn.setStyle(baseStyle);

        //Eveniment: Mouse enter
        btn.setOnMouseEntered(_ -> {
            btn.setStyle(baseStyle + hoverStyle); //combina stilul de baza cu cel de hover
            btn.setScaleX((1.03)); //Marim usor butonul cu 3% pe axa X
            btn.setScaleY(1.03); // marim butonul cu 3% pe axa Y
            icon.setIconColor(javafx.scene.paint.Color.web("#3498db")); //Schimbam si culoarea iconitei in albastru deschis
        });

        //Eveniment: Mouse exit
        btn.setOnMouseExited(_ -> {
            btn.setStyle(baseStyle); //Revine la stilul de baza
            btn.setScaleX(1.0); // Revine la dimensiunea initiala
            btn.setScaleY(1.0);
            icon.setIconColor(javafx.scene.paint.Color.web("#2c3e50")); //Iconita revine la albastru inchis
        });
        return btn;
    }
}
