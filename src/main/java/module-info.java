module pl.com.harta {
    requires javafx.controls;
    requires javafx.fxml;
    requires commons.csv;
    requires lombok;
    requires commons.collections;
    requires java.xml;
    requires commons.io;

    opens pl.com.harta to javafx.fxml;
    exports pl.com.harta;
}