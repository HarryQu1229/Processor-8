module project {


    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires gs.core;

    opens main;
    opens JavaFX;

    exports JavaFX;
}