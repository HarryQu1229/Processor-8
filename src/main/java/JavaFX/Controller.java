package JavaFX;

import algorithm.AStar;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.layout.VBox;
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
    private Label numOfTasks;
    @FXML
    private Label currentBestTime;
    @FXML
    private VBox memTile;
    private Timeline poller;

//    private Tile memoryTile;
    private int TILE_WIDTH = 200;
    private int TILE_HEIGHT = 200;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setUpDefaultValues();
        setUpMemoryTile();
        update();
    }

    private void setUpMemoryTile() {

    }

    private void update() {
        poller = new Timeline(new KeyFrame(Duration.millis(100), event -> {
            currentBestTime.setText((Main.getCurrentBestTime()));
        }));
        poller.setCycleCount(Animation.INDEFINITE);
        poller.play();
    }

    private void setUpDefaultValues() {
        inputFile.setText(Main.getInputFile());
        outputFile.setText(Main.getOutputFile());
        numOfCores.setText(Main.getNumOfCore());
        numOfProcessors.setText(Main.getNumOfProcessors());
        numOfTasks.setText(Main.getNumOfTasks());
    }

}
