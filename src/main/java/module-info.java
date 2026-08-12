module com.example.edutrackfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.fontawesome5;
    requires jbcrypt;


    opens com.example.edutrackfx to javafx.fxml;
    exports com.example.edutrackfx;
}