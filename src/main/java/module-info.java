module project {


    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires gs.core;

    opens main;

    exports JavaFX;
}