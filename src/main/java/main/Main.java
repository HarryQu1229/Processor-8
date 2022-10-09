package main;

import JavaFX.Controller;
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

import java.io.IOException;

public class Main extends Application {

    private static boolean visualise = true;
    private static PartialSolution solution = null;

    private static String INPUT_FILE = "input.dot";
    private static String OUTPUT_FILE = "input-output.dot";
    private static String numOfProcessors = "numOfProcessors";
    private static String numOfCore = "1";
    private static String currentBestTime = "UNKNOWN";

    public static void main(String[] args) throws IOException {

        if (args.length < 2) {
            System.err.println("Invalid arguments");
            return;
        }

        String path = args[0];
        INPUT_FILE = path;
        int processAmount = Integer.parseInt(args[1]);
        numOfProcessors = args[1];

        InputLoader.setNumOfProcessors(processAmount);
        InputLoader.loadDotFileFromPath(path);

        if (visualise) {
            launch(args);
        }


        AStar aStar = new AStar();
        solution = aStar.buildTree();

        OutputFormatter outputFormatter = new OutputFormatter();
        outputFormatter.aStar(solution, path.substring(0, path.length() - 4));

//        Digraph inputGraph = InputLoader.loadDotFileFromPath("examples/g7/in.dot");
//        InputLoader.setNumOfProcessors(2);
//
//
////        InputLoader.print(digraph, false);
////        BruteForce solutionTree = BruteForce.getSolutionTree(digraph, 1);
////        System.out.println(solutionTree.getNodeCount());
//
////        System.out.println("\n=== Final print ===");
////        System.out.println(solutionTree.getAllNodes().size());
////        InputLoader.print(solutionTree, false);
//
////        System.out.println(digraph.getAllNodes());
//
////        System.out.println(digraph.getBottomLevel(digraph.getNodeByValue("h")));
//
////        System.out.println(myGraph.getCriticalPath());
//
////
////        BruteForce solutionTree = new BruteForce(2);
////        solutionTree.build(solutionTree.getRoot());
////        solutionTree.print();
//
//
//
//        AStar aStar = new AStar();
//        PartialSolution p = aStar.buildTree();
//        new OutputFormatter().aStar(p, inputGraph);

//        System.out.println(p.calculateEndScheduleTime());
//        PartialSolution p = aStar.getLastPartialSolution();
//        System.out.println(p);
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
        AStar aStar = new AStar();
        solution = aStar.buildTree();
        OutputFormatter outputFormatter = new OutputFormatter();
        outputFormatter.aStar(solution, INPUT_FILE.substring(0, INPUT_FILE.length() - 4));
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader();

        loader.setLocation(getClass().getResource("/Visualization.fxml"));
        Controller controller= loader.getController();
        Parent root = loader.load();

        // run the algorithm on another thread
        new Thread(Main::runAStar).start();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Visualisation");
        stage.show();
    }


    public static String getInputFile() {return INPUT_FILE;}
    public static String getOutputFile() {return OUTPUT_FILE;}
    public static String getNumOfProcessors() {return numOfProcessors;}
    public static String getNumOfCore() {return numOfCore;}
    public static String getCurrentBestTime() {return solution.getInfo();}
//    public PartialSolution getSolution() {return solution;}
}
