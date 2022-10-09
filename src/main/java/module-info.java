module project{

    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.base;
    requires gs.core;


    opens main;
    opens JavaFX;
    exports main;
    exports JavaFX;
        }