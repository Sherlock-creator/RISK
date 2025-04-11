module com.example.risk {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.ikonli.javafx;
    requires com.almasb.fxgl.all;

    opens com.example.risk to javafx.fxml;
    exports com.example.risk;
}