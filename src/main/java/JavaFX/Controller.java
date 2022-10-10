package JavaFX;

import eu.hansolo.tilesfx.Tile;
import eu.hansolo.tilesfx.TileBuilder;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
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
    @FXML
    private ImageView statusImg;
    @FXML
    private ImageView statusImg1;
    @FXML
    private Label statusLabel;
    @FXML
    private Label runtimeCounter;

    private Timeline poller;
    private Timeline runtimePoller;
    private long startTime;
    private long currentTime;
    private Tile memoryTile;
    private int TILE_WIDTH = 200;
    private int TILE_HEIGHT = 200;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setUpDefaultValues();
        setUpRuntimeCounter();
        setUpMemoryTile();
        update();
    }

    private void setUpRuntimeCounter() {
        startTime=System.currentTimeMillis();
        runtimePoller = new Timeline(new KeyFrame(Duration.millis(100), event -> {
            currentTime=System.currentTimeMillis();
            runtimeCounter.setText((currentTime - startTime) / 1000 + "s");
        }));
        runtimePoller.setCycleCount(Animation.INDEFINITE);
        runtimePoller.play();
    }

    private void endRuntimeCounter() {
        runtimePoller.stop();
    }

    private void setUpMemoryTile() {
        memoryTile = TileBuilder.create()
                .skinType(Tile.SkinType.GAUGE)
                .prefSize(TILE_WIDTH, TILE_HEIGHT)
                .maxValue(Runtime.getRuntime().maxMemory() / (1024 * 1024))
                .threshold(Runtime.getRuntime().maxMemory() * 0.8 / (1024 * 1024))
                .title("Memory Usage")
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

            // Updating status
            if (!Main.getIsRunning()) {
                endRuntimeCounter();
                statusLabel.setText("Finished");
                statusImg.setVisible(false);
                statusImg1.setVisible(true);
                currentBestTime.setTextFill(Color.GREEN);
            }
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
        statusImg1.setVisible(false);
    }

}
