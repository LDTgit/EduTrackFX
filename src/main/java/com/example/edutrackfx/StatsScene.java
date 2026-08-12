package com.example.edutrackfx;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;


//Clasa pentru generarea raportului vizual al performantei utilizatorului
public class StatsScene {
    private final SceneManager manager;

    public StatsScene(SceneManager manager){
        this.manager = manager;
    }

    //Construim interfata grafica pentru afisarea graficului de performanta
    public Scene getScene(){
        VBox layout = new VBox(20); //layout vertical cu spatiere de 20px intre elemente
        layout.setPadding(new Insets(20)); //spatiu de 20px fata de margini
        layout.setAlignment(Pos.CENTER); // tot continutul va fi pe centrul ecranului

        int corecte = manager.getCorrectCount(); //preluam cifrele din manager
        int gresite = manager.getTotalCount() - corecte; // calculam numarul de raspunsuri gresite

        //Date pentru grafic
        PieChart.Data slice1 = new PieChart.Data("Raspunsuri corecte:", corecte);
        PieChart.Data slice2 = new PieChart.Data("Raspunsuri gresite:", gresite);

        //Instantiem graficul si il populam cu o lista observabila creata pe loc
        PieChart pieChart = new PieChart(FXCollections.observableArrayList(slice1,slice2));
        pieChart.setTitle("Performanta in sesiunea curenta"); // titlul afisat deasupra graficului

        //Daca nu s-a raspuns la nicio intrebare, afisam un mesaj
        if (manager.getTotalCount() == 0){
            layout.getChildren().add(new Label("Nu exista date pentru statistici inca."));
        } else {
            layout.getChildren().add(pieChart); // daca avem date, adaugam graficul in layout
        }

        Button btnBack = new Button("Inapoi");
        btnBack.setOnAction(e-> manager.switchScene(new DashboardScene(manager).getScene(), "Dashboard"));

        layout.getChildren().add(btnBack);
        return new Scene(layout, 800, 600);
    }
}
