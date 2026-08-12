package com.example.edutrackfx; //declaram pachetul in care se afla fisierul
import javafx.collections.FXCollections; //utilizata pentru a crea liste "observabile"
import javafx.collections.ObservableList; //o lista care anunta interfata cand se schimba datele
import javafx.stage.Stage; //fereastra principala a sistemului
import javafx.scene.Scene; //continutul grafic interior al unei ferestre


//Clasa responsabila cu navigarea intre ferestre si stocarea datelor globale
public class SceneManager {
    private final Stage primaryStage; //referinta catre fereastra principala

    //Lista centralizata de date in care stocam toate intrebarile la care s-a raspuns
    //ObservableList permite TableView-ului sa se actualizeze singur cand sunt adaugate date noi
    private final ObservableList<QuizQuestion> history = FXCollections.observableArrayList();
    private int correctCount = 0; //Numarul total de raspunsuri corecte
    private int totalCount = 0; //Numarul total de raspunsuri

    //Constructorul clasei primeste instanta ferestrei de la MainApp
    public SceneManager(Stage stage){
        this.primaryStage = stage;
    }

    //Metoda care realizeaza schimbarea ecranului
    public void switchScene(Scene newScene, String title){
        String STUDENT_INFO = "Jercan Laura - Grupa 310";
        //Setam titlul ferestrei combinand titlul ecranului cu numele si grupa conform cerintei proiectului
        primaryStage.setTitle(title + " " + STUDENT_INFO);
        primaryStage.setScene(newScene); //schimba continutul ferestrei cu noua scena primita ca parametru
        primaryStage.centerOnScreen(); //centreaza automat fereastra pe mijlocul ecranului dupa schimbare
        primaryStage.show(); //face fereastra vizibila
    }

    //Gettere si settere pentru date partajate
    public ObservableList<QuizQuestion> getHistory(){return history;} //Returneaza o lista cu istoricul intrebarilor pentru a o afisa in TableView

    //Salvam rezultatul unei intrebari si actualizam contoarele
    public void addResult(QuizQuestion q, boolean isCorrect){
        history.add(q); //adauga intrebarea in lista de istoric
        totalCount++; //incrementeaza numarul total de incercari
        if (isCorrect) correctCount++; //daca raspunsul e corect, crestem scorul
    }
    public int getCorrectCount(){return  correctCount;} //returneaza numarul de raspusuri corecte pentru PieChart
    public int getTotalCount(){return totalCount++;} //returneaza numarul total de intrebari
}
