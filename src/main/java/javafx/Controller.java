package javafx;

import algorithm.AStar;
import algorithm.PartialSolution;
import eu.hansolo.tilesfx.Tile;
import eu.hansolo.tilesfx.TileBuilder;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import java.lang.management.ManagementFactory;

import com.sun.management.OperatingSystemMXBean;

import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import main.FxMain;
import models.NodeProperties;
import models.InputGraph;
import org.graphstream.graph.Node;

public class Controller implements javafx.fxml.Initializable {

    private static final int BLOCKHEIGHTDENOMINATOR = 100;

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
    private VBox cpuBox;
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

    private Timeline graphTimer;
    private long startTime;
    private long currentTime;
    private Tile memoryTile;
    private Tile cpuTile;
    private int TILE_WIDTH = 200;
    private int TILE_HEIGHT = 200;

    private GanttChart<Number, String> chart;
    private int numberOfProcessor;

    private final OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setUpDefaultValues();
        setUpRuntimeCounter();
        setUpMemoryTile();
        setUpCpuTile();
        setUpGanttChart();
        setUpGraphTimer();
        update();
    }

    /**
     * Set up the chart that displays the detailed scheduling of all tasks
     */
    private void setUpGanttChart() {

        // Set up the base chart
        NumberAxis xAxis = new NumberAxis();
        CategoryAxis yAxis = new CategoryAxis();

        xAxis.setLabel("Time (Weight)");
        xAxis.setMinorTickCount(4);
        xAxis.setAnimated(false);

        yAxis.setLabel("Processor");
        yAxis.setTickLabelGap(10);
        yAxis.setAnimated(false);

        // Append the blocks that represent the tasks
        List<String> processorsList = new ArrayList<>();
        for (int i = 0; i < numberOfProcessor; i++) {
            processorsList.add(i + 1 + "");
        }

        yAxis.setCategories(FXCollections.<String>observableArrayList(processorsList));

        chart = new GanttChart<>(xAxis, yAxis);

        chart.setTitle("Current Partial Schedule");

        // set chart title to bold
        chart.setStyle("-fx-font-weight: " + 700);
        chart.setBlockHeight(BLOCKHEIGHTDENOMINATOR / numberOfProcessor);

        chart.setLegendVisible(false);

        graphContainer.getChildren().add(chart);
    }

    /**
     * Display how long the program has been running on the UI
     */
    private void setUpRuntimeCounter() {
        startTime = System.currentTimeMillis();
        runtimePoller = new Timeline(new KeyFrame(Duration.millis(100), event -> {
            currentTime = System.currentTimeMillis();
            double time = (currentTime - startTime * 1.0) / 1000;
            runtimeCounter.setText(time + "");

        }));
        runtimePoller.setCycleCount(Animation.INDEFINITE);
        runtimePoller.play();
    }

    private void endRuntimeCounter() {
        runtimePoller.stop();
    }

    /**
     * Set up the tile that displays the RAM usage
     */
    private void setUpMemoryTile() {
        memoryTile = TileBuilder.create()
                .skinType(Tile.SkinType.GAUGE)
                .prefSize(TILE_WIDTH, TILE_HEIGHT)
                .maxValue(Runtime.getRuntime().maxMemory() / (1024 * 1024))
                .threshold(Runtime.getRuntime().maxMemory() * 0.8 / (1024 * 1024))
                .title("Memory Usage")
                .unit("MB")
                .build();

        // set initial memory tile
        memoryTile.setValue((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / (1000000d));
        memBox.getChildren().addAll(this.memoryTile);

    }

    /**
     * Set up the tile that displays the CPU usage
     */
    private void setUpCpuTile() {
        cpuTile = TileBuilder.create()
                .skinType(Tile.SkinType.FLUID)
                .prefSize(TILE_WIDTH, TILE_HEIGHT)
                .title("CPU Usage")
                .maxValue(100)
                .threshold(80)
                .unit("\u0025")
                .decimals(0)
                .barColor(Tile.BLUE)
                .animated(true)
                .build();

        // set initial value
        cpuTile.setValue(osBean.getProcessCpuLoad() * 100);
        cpuBox.getChildren().addAll(cpuTile);
    }

    private void update() {
        poller = new Timeline(new KeyFrame(Duration.millis(100), event -> {

            if (FxMain.getIsRunning()) {
                // Updating memory tile
                memoryTile.setValue((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / (1000000d));

                // Updating CPU tile
                cpuTile.setValue(osBean.getProcessCpuLoad() * 100);
            }

            // Updating best time
            currentBestTime.setText((FxMain.getCurrentBestTime()));

            // Updating status
            if (!FxMain.getIsRunning()) {
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

    /**
     * Whenever a new complete schedule has been obtained, update the gantt chart to show user the scheduler
     *
     * @param solution the new complete schedule that needs to be displayed
     */
    private void updateGanttChart(PartialSolution solution) {

        if (solution != null) {
            // clear current chart
            chart.getData().clear();

            XYChart.Series[] seriesArr = new XYChart.Series[numberOfProcessor];

            for (int i = 0; i < numberOfProcessor; i++) {
                seriesArr[i] = new XYChart.Series();
            }

            NodeProperties nodeProperties;
            for (Node node : solution.getNodesPath()) {
                nodeProperties = solution.getNodeStates().get(node);
                int taskProcessor = nodeProperties.getProcessorId();
                int taskStartTime = nodeProperties.getStartingTime();
                XYChart.Data data = new XYChart.Data(taskStartTime, taskProcessor + "", new GanttChart.ExtraData((int) InputGraph.get().getNodeWeightById(node.getId()), "task", node.getId()));
                seriesArr[taskProcessor - 1].getData().add(data);
            }

            for (int i = 0; i < Integer.parseInt(FxMain.getNumOfProcessors()); i++) {
                chart.getData().add(seriesArr[i]);
            }

            // add tooltip to each task scheduled
            for (XYChart.Series series : chart.getData()) {
                for (XYChart.Data data : (ObservableList<XYChart.Data>) series.getData()) {
                    GanttChart.ExtraData extraData = (GanttChart.ExtraData) data.getExtraValue();
                    // show tooltip with task id and start time and processor id
                    Tooltip t = new Tooltip("Task: " + extraData.getTaskName() + "\nStart Time: " + data.getXValue() + "\nFinish Time: " + ((int) data.getXValue() + (int) InputGraph.get().getNodeWeightById(extraData.getTaskName())));
                    t.setShowDelay(Duration.ZERO);
                    Tooltip.install(data.getNode(), t);
                }
            }
        }
    }

    private void setUpDefaultValues() {
        inputFile.setText(FxMain.getInputFile());
        outputFile.setText(FxMain.getOutputFile());
        numOfCores.setText(FxMain.getNumOfCore());
        numOfProcessors.setText(FxMain.getNumOfProcessors());
        numOfTasks.setText(FxMain.getNumOfTasks());
        numberOfProcessor = Integer.parseInt(FxMain.getNumOfProcessors());
        statusImg1.setVisible(false);
        currentBestTime.setTextFill(Color.RED);
    }

    private void setUpGraphTimer() {
        graphTimer = new Timeline(new KeyFrame(Duration.seconds(0.5), event -> {
            updateGanttChart(AStar.getCurrentSolution());
            if (!FxMain.getIsRunning()) {
                chart.setTitle("Optimal Schedule");
                updateGanttChart(FxMain.getSolution());
                graphTimer.stop();
            }
        }));
        graphTimer.setCycleCount(Animation.INDEFINITE);
        graphTimer.play();
    }

}
