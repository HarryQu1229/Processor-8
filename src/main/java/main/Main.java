package main;

import algorithm.BranchAndBoundAlgorithm;
import algorithm.NodeInfo;
import io.InputLoader;
import io.OutputFormatter;
import models.NodeProperties;
import models.TheGraph;
import org.graphstream.graph.Node;
import org.graphstream.stream.file.FileSinkDOT;
import solution.AStar;
import solution.Digraph;
import solution.PartialSolution;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws IOException {

        if (args.length < 2) {
            System.err.println("Invalid arguments");
            return;
        }

        String path = args[0];
        int processAmount = Integer.parseInt(args[1]);

        InputLoader.setNumOfProcessors(processAmount);
        Digraph baseGraph = InputLoader.loadDotFileFromPath(path);

        AStar aStar = new AStar();
        PartialSolution solution = aStar.buildTree();

        OutputFormatter outputFormatter = new OutputFormatter();
        outputFormatter.aStar(solution, baseGraph);

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


}
