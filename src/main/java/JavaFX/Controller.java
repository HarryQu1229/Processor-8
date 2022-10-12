package JavaFX;

import algorithm.PartialSolution;
import eu.hansolo.tilesfx.Tile;
import eu.hansolo.tilesfx.TileBuilder;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BubbleChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import main.Main;
import models.NodeProperties;
import models.TheGraph;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

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
    @FXML
    private VBox graphContainer;

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
        setUpGanttChart();
        update();
    }

    private void setUpGanttChart() {
        // update gantt chart x axis
//        xAxis.setUpperBound(Double.parseDouble(Main.getNumOfProcessors()));
//        xAxis.setLowerBound(1);



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
        poller.stop();
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
            if (Main.getIsRunning()) {
                double memoryUsage = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory())/(1000000d);
                memoryTile.setValue(memoryUsage);
            }

            // Updating best time
            currentBestTime.setText((Main.getCurrentBestTime()));

            // Updating status
            if (!Main.getIsRunning()) {
                endRuntimeCounter();
                statusLabel.setText("Finished");
                statusImg.setVisible(false);
                statusImg1.setVisible(true);
                currentBestTime.setTextFill(Color.GREEN);

                final NumberAxis xAxis = new NumberAxis();
                final CategoryAxis yAxis = new CategoryAxis();

                xAxis.setLabel("Time (Weight)");
                xAxis.setMinorTickCount(4);
                xAxis.setAnimated(false);

                yAxis.setLabel("Processor");
                yAxis.setTickLabelGap(10);
                yAxis.setAnimated(false);

                List<String> processorsList = new ArrayList<>();
                for (int i = 0; i < Integer.parseInt(Main.getNumOfProcessors()); i++) {
                    processorsList.add(i + 1 + "");
                }

                yAxis.setCategories(FXCollections.<String>observableArrayList(processorsList));

                final GanttChart<Number,String> chart = new GanttChart<Number,String>(xAxis,yAxis);

                chart.setTitle("Current Best Schedule");
                chart.setBlockHeight(200 / Integer.parseInt(Main.getNumOfProcessors()));

                PartialSolution solution = Main.getCurrentPartialSolution();

                XYChart.Series[] seriesArr = new XYChart.Series[Integer.parseInt(Main.getNumOfProcessors())];
                for (int i = 0; i < Integer.parseInt(Main.getNumOfProcessors()); i++) {
                    seriesArr[i] = new XYChart.Series();
                }

                NodeProperties nodeProperties;
                for (Node node : solution.getNodesPath()){
                    nodeProperties = solution.getNodeStates().get(node);
                    int taskProcessor = nodeProperties.getProcessorId();
                    int taskStartTime = nodeProperties.getStartingTime();
                    XYChart.Data data = new XYChart.Data(taskStartTime, taskProcessor+"", new GanttChart.ExtraData((int)TheGraph.get().getNodeWeightById(node.getId()),"task" ,node.getId()));
                    seriesArr[taskProcessor - 1].getData().add(data);
                }


                for (int i = 0; i < Integer.parseInt(Main.getNumOfProcessors()); i++) {
                    chart.getData().add(seriesArr[i]);
                }

                graphContainer.getChildren().add(chart);



//                solution.getNodesPath().forEach((node) -> {
//                    NodeProperties properties = solution.getNodeStates().get(node);
//                    XYChart.Data<Number,String> data = new XYChart.Data<Number,String>(properties.getStartingTime(), properties.getProcessorId()+"");
//
//                    XYChart.Series<Number,String> series = new XYChart.Series<Number,String>();
//                    series.getData().add(data);
//                });

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
