package com.example.edutrackfx;

//Importuri pentru gestionare margini si alinierea elementelor
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
//Import componente de control - eticheta, camp text, camp parola, buton
import javafx.scene.control.*;
// Import pentru layout vertical
import javafx.scene.layout.VBox;
//Import pentru criptarea parolei
import org.mindrot.jbcrypt.BCrypt;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

//Clasa pentru crearea ecranului de Login
public class LoginScene {
    //Referinta catre obiectul SceneManager pentru a permite trecerea la Dashboard
    private final SceneManager manager;

    //Constructorul primeste managerul de scene pentru navigarea ulterioara
    public LoginScene(SceneManager manager){
        this.manager = manager;
    }

    // Construim interfata grafica pentru procesul de autentificare
    public Scene getScene(){
        VBox layout = new VBox(15); //VBox cu spatiere de 15px intre componente
        layout.setPadding(new Insets(50)); //Margine interioara de 50px
        layout.setAlignment(Pos.CENTER); //aliniere elemente in centrul ferestrei
        layout.setStyle("-fx-background-color: #ffffff;");//fundal alb

        Label title = new Label("Autentificare"); //titlul ecranului
        title.setStyle("-fx-font-size: 26px; -fx-font-weight: bold;");

        // mesaj de eroare ascuns initial
        Label lblError = new Label("utilizator sa parola incorecta!");
        lblError.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
        lblError.setVisible(false);

        TextField username = new TextField(); //campul pentru introducerea numelui de utilizator
        username.setPromptText("Utilizator"); // text care dispare cand utilizatorul incepe sa scrie
        username.setMaxWidth(250); //limitare latime maxima

        PasswordField password = new PasswordField(); //camp special pentru parola care ascunde caracterele tastate
        password.setPromptText("Parola");
        password.setMaxWidth(250);

        // Creare buton Login
        Button btnLogin = new Button("Login");
        btnLogin.setPrefWidth(250); //latime egala cu campurile de text
        btnLogin.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-weight: bold;"); //fundal albasgtru

        // Creare buton inregistrare utilizator nou
        Button btnRegister = new Button("Inregistrare");
        btnRegister.setPrefWidth(250);
        btnRegister.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white; -fx-font-weight: bold;");

        // Logica butonului de login
        btnLogin.setOnAction(_ -> {
            String userStr = username.getText();
            String passStr = password.getText();

            // verificam datele statice
            if (validateLogin(userStr, passStr)){
                manager.switchScene(new DashboardScene(manager).getScene(), "Dashboard");
            } else {
                lblError.setVisible(true); // afisam eroarea daca datele sunt gresite
                password.clear();
            }
        });

        // Logica butonului de inregistrare utilizator
        btnRegister.setOnAction(_ -> {
            String u = username.getText();
            String p = password.getText();
            if (u.isEmpty() || p.isEmpty()){
                lblError.setText("Introduceti datele pentru inregistrare!");
                lblError.setVisible(true);
            } else {
                registerUser(u, p);
                username.clear();
                password.clear();
            }
        });

        //Adaugam toate elementele create in containerul VBox
        layout.getChildren().addAll(title, lblError, username, password, btnLogin, btnRegister);

        //Returneaza scena noua
        return new Scene(layout, 800, 600);
    }

    //Validare logare
    private boolean validateLogin(String username, String password){
        //Citim linie cu linie fisierul users.db unde am salvat userii
        try (BufferedReader br = new BufferedReader(new FileReader("users.db"))){
            String line;
            while ((line = br.readLine()) != null){
                String[] parts = line.split(":");
                if (parts.length == 2){
                    String storedUser = parts[0];
                    String storedHash = parts[1];

                    //Daca am gasit userul, verificam parola cu BCrypt
                    if (storedUser.equals(username) && BCrypt.checkpw(password,storedHash)){
                        return true;
                    }
                }
            }
        } catch (IOException e){
            System.err.println("Eroare la citirea bazei de date: " + e.getMessage());
        }
        return false;
    }

    //Inregistrare utilizator
    private void registerUser(String username, String password){
        // Verificam intai daca utilizatorul exista deja
        boolean userExists = false;
        try (BufferedReader br = new BufferedReader(new FileReader("users.db"))){
            String line;
            while ((line = br.readLine()) != null){
                String[] parts = line.split((":"));
                if (parts.length>0 && parts[0].equals(username)){
                    userExists = true;
                    break;
                }
            }
        } catch (IOException e){
            // Daca fisierul nu exista inca, ignoram eroarea
        }

        // Daca utilizatorul a fost creat anterior, afisam un mesaj de eroare si oprim executia
        if (userExists){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Atentie!");
            alert.setHeaderText(null);
            alert.setContentText("Acest nume de utilizator este deja folosit!");
            alert.showAndWait();
            return; // Oprim executia
        }

        //Daca utilizatorul este unic, generam hash-ul securizat pentru parola introdusa
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(12));

        //Scriem in fisier
        try (java.io.FileWriter fw = new java.io.FileWriter("users.db", true);
        java.io.BufferedWriter bw = new java.io.BufferedWriter(fw);
        java.io.PrintWriter out = new java.io.PrintWriter(bw)) {
            out.println(username + ":" + hashedPassword);

            //Afisam o alerta de succes
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succes");
            alert.setHeaderText(null);
            alert.setContentText("Utilizatorul a fost inregistrat cu succes pentru: " + username);
            alert.showAndWait();
        } catch (java.io.IOException e){
            System.err.println("Eroare la salvarea utilizatorului: " + e.getMessage());
        }
    }
}
