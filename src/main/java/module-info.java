module pl.com.harta {
    requires javafx.controls;
    requires javafx.fxml;
    requires commons.csv;
    requires lombok;
    requires commons.collections;

    opens pl.com.harta to javafx.fxml;
    exports pl.com.harta;
}