module com.example.chatmulticliente {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.chatmulticliente to javafx.fxml;
    exports com.example.chatmulticliente;
}