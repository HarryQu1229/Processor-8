package main;

import models.InputGraph;
import org.apache.commons.cli.*;
import javafx.Controller;
import algorithm.ParallelAStar;
import io.InputLoader;
import io.OutputFormatter;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.graphstream.graph.Node;
import algorithm.AStar;
import models.Digraph;
import algorithm.PartialSolution;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;

public class FxMain extends Application {

    private static boolean visualise = true;
    private static PartialSolution solution = null;
    private static String INPUT_FILE = "input.dot";
    private static String OUTPUT_FILE = "input-output.dot";
    private static String numOfProcessors = "numOfProcessors";
    private static String numOfTasks;
    private static String numOfCore = "1";
    private static String currentBestTime = "UNKNOWN";
    private static Boolean isRunning = true;

    public static void start(String[] args)  {

        CommandLine cmd = InputLoader.parseArgs(args);

        if (cmd == null) return;

        String path = args[0];
        INPUT_FILE = path;
        int processAmount = Integer.parseInt(args[1]);
        numOfProcessors = args[1];

        InputLoader.setNumOfProcessors(processAmount);
        InputLoader.loadDotFileFromPath(path);

        numOfTasks = Integer.toString(InputGraph.get().getAllNodes().size());

        if (cmd.hasOption("o")) {
            OUTPUT_FILE = cmd.getOptionValue("o");
            if (!OUTPUT_FILE.endsWith(".dot")) {
                OUTPUT_FILE += ".dot";
            }
        } else {
            OUTPUT_FILE = path.substring(0, path.length() - 4) + "-output.dot";
        }

        if (cmd.hasOption("p")) {
            numOfCore = cmd.getOptionValue("p");
        }

        visualise = cmd.hasOption("v");

        if (visualise) {
            launch(args);
        } else {
            System.out.println("\n------------------- Processor 8 -------------------");
            System.out.println("Start Computing: " + INPUT_FILE);
            System.out.println("Number of processors: " + numOfProcessors);
            System.out.println("Number of cores: " + numOfCore);
            System.out.println("Running...");
            long start = System.currentTimeMillis();
            runAStar();
            long timeUsed = System.currentTimeMillis() - start;
            System.out.println();
            System.out.println("Completed!");
            System.out.println("Solution saved to " + OUTPUT_FILE);
            System.out.println("Cost of optimal schedule: " + solution.calculateEndScheduleTime());
            String time = String.format("Time used: %.2fs", (double) timeUsed / 1000);
            System.out.println(time);
            System.out.println("-----------------------------------------------------\n");
        }

        System.exit(0);

    }


    private static int countLeaves(Digraph digraph) {
        int count = 0;
        for (Node node : digraph.getAllNodes()) {
            if (digraph.getOutDegreeOfNode(node) == 0) {
                count++;
            }
        }
        return count;
    }

    private static void runAStar() {
        ParallelAStar parallelAStar = new ParallelAStar(Integer.parseInt(getNumOfCore()));
        solution = parallelAStar.build();
        OutputFormatter outputFormatter = new OutputFormatter();
        outputFormatter.aStar(solution, OUTPUT_FILE);
    }

    public static PartialSolution getCurrentPartialSolution() {
        return solution;
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader();

        loader.setLocation(getClass().getResource("/Visualization.fxml"));
        Controller controller = loader.getController();
        Parent root = loader.load();

        // run the algorithm on another thread
        new Thread(FxMain::runAStar).start();

        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/css/ganttChart.css").toExternalForm());
        stage.setScene(scene);
        stage.setOnCloseRequest(windowEvent -> System.exit(0));
        stage.setResizable(false);
        stage.setTitle("Visualisation");
        stage.show();
    }


    public static String getInputFile() {
        return INPUT_FILE;
    }

    public static String getOutputFile() {
        return OUTPUT_FILE;
    }

    public static String getNumOfProcessors() {
        return numOfProcessors;
    }

    public static String getNumOfCore() {
        return numOfCore;
    }

    public static String getNumOfTasks() {
        return numOfTasks;
    }

    public static Boolean getIsRunning() {
        return isRunning;
    }

    public static String getCurrentBestTime() {
        if (solution == null) {
            return "CALCULATING";
        } else {
            isRunning = false;
            return String.valueOf(solution.calculateEndScheduleTime());
        }
    }

    public static PartialSolution getSolution() {
        return solution;
    }
}
