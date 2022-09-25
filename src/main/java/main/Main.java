package main;

import io.InputLoader;
import org.graphstream.graph.Node;
import solution.Digraph;
import solution.PartialSolution;
import solution.SolutionTree;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
//        InputLoader.print(graph, true);
        Digraph digraph = InputLoader.loadDotFile("g1");


//        InputLoader.print(digraph, false);
//        SolutionTree solutionTree = SolutionTree.getSolutionTree(digraph, 1);
//        System.out.println(solutionTree.getNodeCount());

//        System.out.println("\n=== Final print ===");
//        System.out.println(solutionTree.getAllNodes().size());
//        InputLoader.print(solutionTree, false);

//        System.out.println(digraph.getAllNodes());

//        System.out.println(digraph.getBottomLevel(digraph.getNodeByValue("h")));

//        System.out.println(myGraph.getCriticalPath());

//        int numOfProcessors = 2;

        SolutionTree solutionTree = new SolutionTree(digraph,1);

        solutionTree.buildTree(solutionTree.getRoot());

//        solutionTree.printSolutionTree();

        System.out.println(countLeaves(solutionTree));

//        InputLoader.print(solutionTree, true);

    }

    private static int countLeaves(Digraph digraph) {
        int count = 0;
        for (Node node : digraph.getAllNodes()) {
            if (digraph.getOutDegree(node) == 0) {
                count++;
            }
        }
        return count;
    }



}
