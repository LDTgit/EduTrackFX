package com.example.edutrackfx;

//Importuri pentru geometrie si alinierea elementelor
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
//Importuri pentru controalele de interfata - buton, eticheta, panou de scroll
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;


// Clasa pentru afisarea instructiunilor de utilizare
public class HelpScene {
    //Referinta catre managerul de scene
    private final SceneManager manager;

    // Constructorul clasei care primeste managerul pentru navigare
    public HelpScene(SceneManager manager){
        this.manager = manager;
    }

    //Construieste si returneaza scena pentru ecranul de Ajutor
    public Scene getScene(){
        VBox root = new VBox(20); // layout vertical cu un spatiu de 20px intre elemente
        root.setPadding(new Insets(30)); // spatiu interior de 30px
        root.setAlignment(Pos.TOP_CENTER); // aliniere in partea de centru-sus
        root.setStyle("-fx-background-color: #f4f4f4;"); //culoare de fundal gri deschis

        //Creare titlu pagina si stilizarea acestuia
        Label title = new Label("Ajutor");
        title.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #2c4e50;");

        //Apelam metoda privata pentru a obtine zona de text cu scroll
        ScrollPane helpScrollPane = createHelpContent();

        //Configurare buton de intoarcere la Dashboard
        Button btnBack = new Button("Inapoi la Dashboard");
        btnBack.setPrefWidth(250); //latime fixa pentru buton
        btnBack.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-cursor: hand;");

        // La actionarea butonului se revine la Dashboard
        btnBack.setOnAction(e->manager.switchScene(new DashboardScene(manager).getScene(), "Dashboard"));

        //Adaugam toate elementele in container
        root.getChildren().addAll(title,helpScrollPane,btnBack);

        //Returneaza scena finala
        return new Scene(root, 800, 600);
    }

// Metoda privata pentru a crea zona de continut cu scroll
    private ScrollPane createHelpContent(){
        //Definim textul de ajutor
        String heptText = "Bine ai venit! \n\n" +
                "1. Autentificare: Introdu orice date pentru a accesa sistemul. \n\n" +
                "2. Dashboard: Este punctul central de navigare intre module. \n\n" +
                "3. Test Grila: Incarca intrebari din baza de date OpenTrivia. \n\n" +
                "4. Gestiune Date: Vizualizeaza istoricul sesiunii tale intr-un format tabelar. \n\n" +
                "5. Statistici: Urmareste performanta grafica a raspunsurilor tale. \n\n";

        //Creare eticheta care va contine textul
        Label lblHelp = new Label(heptText);
        lblHelp.setWrapText(true); //activeaza trecerea automata la randul urmator daca textul este prea lung
        lblHelp.setStyle("-fx-font-size: 14px; -fx-line-spacing: 5;");

        // Creare panou de scroll care contine eticheta text
        ScrollPane scroll = new ScrollPane(lblHelp);
        scroll.setFitToWidth(true); // ajusteaza continutul automat la latimea panoului
        scroll.setPrefHeight(350); //inaltimea fixa a zonei de text
        scroll.setStyle("-fx-background-color: transparent;"); //fundal transparent

        return scroll;
    }
}
