module pl.com.harta {
    requires javafx.controls;
    requires javafx.fxml;
    requires commons.csv;
    requires commons.collections;
    requires java.xml;
    requires commons.io;
    requires org.apache.commons.lang3;

    opens pl.com.harta.controller to javafx.fxml;
    opens pl.com.harta.model to javafx.base;
    exports pl.com.harta;
}