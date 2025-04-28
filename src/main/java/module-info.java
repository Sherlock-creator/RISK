module com.example.risk {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.ikonli.javafx;
    requires com.almasb.fxgl.all;
	requires org.junit.jupiter.api;
	requires java.desktop;
    requires java.sql;

    opens com.example.risk to javafx.fxml;
    exports com.example.risk;
}