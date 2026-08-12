package com.example.edutrackfx;

import javafx.application.Application; //necesara pentru a crea o aplicatie grafica
import javafx.stage.Stage; //fereastra principala a aplicatiei (rama ferestrei)
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;//pentru gestionarea erorilor de intrare/iesire


//Clasa principala a proiectului
//Extinde Application, ceea ce inseamna ca mosteneste toate functionalitatile unei aplicatii JavaFX
public class MainApp extends Application {
    // metoda start este apelata automat de JavaFX dupa ce aplicatia este lansata
    @Override
    public void start(Stage stage) throws IOException {
        try{
            //Incarcam iconita aplicatiei
            stage.getIcons().add(new javafx.scene.image.Image(getClass().getResourceAsStream("/icon.png")));
        } catch (Exception e){
            System.out.println("Iconita nu a fost gasita, se foloseste cea default.");
        }
        //Cream o instanta a clasei SceneManager, transmitand fereastra (stage) ca parametru
        //SceneManager ne ajuta sa schimbam ecranele in interiorul acestei ferestre
        SceneManager manager = new SceneManager(stage);

        //Pornim primul ecran (Login)
        LoginScene login = new LoginScene(manager); //transmitem parametrul manager pentru ca ecranul de Login sa poata cere ulterior trecerea la Dashboard
        //Apelam metoda switchScene din manager pentru a pune continutul ecranului de Login in fereastra
        //Primul parametru este continutul grafic (scene), al doilea este textul care va aparea in bara de titlu
        manager.switchScene(login.getScene(), "Autentificare");
    }

    //metoda lauch face parte din clasa Application
    public static void main(String[] args) {
        launch();
    }
}

