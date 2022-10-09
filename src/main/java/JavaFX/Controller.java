package JavaFX;

import algorithm.AStar;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.util.Duration;
import main.Main;

public class Controller implements javafx.fxml.Initializable {

    @FXML
    private Label inputFile;
    @FXML
    private Label outputFile;
    @FXML
    private Label numOfProcessors;
    @FXML
    private Label numOfCores;
    @FXML
    private Label currentBestTime;
    private Timeline poller;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setUpDefaultValues();
        update();
    }

    private void update() {
        poller = new Timeline(new KeyFrame(Duration.millis(100), event -> {
            currentBestTime.setText(String.valueOf(Main.getCurrentBestTime()));
        }));
    }

    private void setUpDefaultValues() {
        inputFile.setText(Main.getInputFile());
        outputFile.setText(Main.getOutputFile());
        numOfCores.setText(Main.getNumOfCore());
        numOfProcessors.setText(Main.getNumOfProcessors());
    }

}
