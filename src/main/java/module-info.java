module project{

    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.web;
    requires javafx.base;
    requires gs.core;
    requires eu.hansolo.tilesfx;


    opens main;
    opens JavaFX;
    exports main;
    exports JavaFX;
        }