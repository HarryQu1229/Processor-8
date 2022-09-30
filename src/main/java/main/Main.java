package main;

import io.InputLoader;
import models.TheGraph;
import org.graphstream.graph.Node;
import solution.AStar;
import solution.Digraph;
import solution.PartialSolution;

public class Main {

    public static void main(String[] args) {
//      InputLoader.print(graph, true);
        InputLoader.loadDotFile("g11");
        InputLoader.setNumOfProcessors(2);
        TheGraph.DFSFindOneSolution(new PartialSolution());
        TheGraph.findMinCost(new PartialSolution());

        System.out.println(TheGraph.getMinCost());

//        TheGraph.setMinCost(350);



//        InputLoader.print(digraph, false);
//        BruteForce solutionTree = BruteForce.getSolutionTree(digraph, 1);
//        System.out.println(solutionTree.getNodeCount());

//        System.out.println("\n=== Final print ===");
//        System.out.println(solutionTree.getAllNodes().size());
//        InputLoader.print(solutionTree, false);




//
//        BruteForce solutionTree = new BruteForce(2);
//        solutionTree.build(solutionTree.getRoot());
//        solutionTree.print();



        AStar aStar = new AStar();
//        aStar.DFSFindOneSolution(aStar.getRoot());
//        System.out.println(aStar.minCost);
        String s = aStar.buildTree();
        System.out.println(s);


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
