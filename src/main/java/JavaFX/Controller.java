package JavaFX;

import algorithm.AStar;
import eu.hansolo.tilesfx.Tile;
import eu.hansolo.tilesfx.TileBuilder;
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
    private VBox memBox;
    private Timeline poller;

    private Tile memoryTile;
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
        memoryTile = TileBuilder.create()
                .skinType(Tile.SkinType.GAUGE)
                .prefSize(TILE_WIDTH, TILE_HEIGHT)
                .maxValue(Runtime.getRuntime().maxMemory() / (1024 * 1024))
                .threshold(Runtime.getRuntime().maxMemory() * 0.8 / (1024 * 1024))
                .title("Memory")
                .unit("MB")
                .build();
        memBox.getChildren().addAll(this.memoryTile);

    }

    private void update() {
        poller = new Timeline(new KeyFrame(Duration.millis(100), event -> {

            // Updating memory tile
            double memoryUsage = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory())/(1000000d);
            memoryTile.setValue(memoryUsage);

            // Updating best time
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
