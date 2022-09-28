package main;

import algorithm.BranchAndBoundAlgorithm;
import io.InputLoader;
import org.graphstream.graph.Node;
import solution.Digraph;
import solution.SolutionTree;

public class Main {

    public static void main(String[] args) {
//        InputLoader.print(graph, true);
        Digraph digraph = InputLoader.loadDotFile("temp");


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
//
//        SolutionTree solutionTree = new SolutionTree(digraph,2);
//        solutionTree.build(solutionTree.getRoot());
//
//        solutionTree.print();

//        InputLoader.print(solutionTree, true);
        long startTime = System.nanoTime();
        BranchAndBoundAlgorithm.solve(digraph, 4);  

        long endTime = System.nanoTime();
        long duration = (endTime - startTime)/1000000;
        System.out.println(duration);
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
