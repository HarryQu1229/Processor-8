package main;

import io.InputLoader;
import org.graphstream.graph.Node;
import solution.AStar;
import solution.Digraph;
import solution.SolutionTree;

public class Main {

    public static void main(String[] args) {
//      InputLoader.print(graph, true);
        InputLoader.loadDotFile("g1");



//        InputLoader.print(digraph, false);
//        SolutionTree solutionTree = SolutionTree.getSolutionTree(digraph, 1);
//        System.out.println(solutionTree.getNodeCount());

//        System.out.println("\n=== Final print ===");
//        System.out.println(solutionTree.getAllNodes().size());
//        InputLoader.print(solutionTree, false);




//
//        SolutionTree solutionTree = new SolutionTree(2);
//        solutionTree.build(solutionTree.getRoot());
//        solutionTree.print();



        AStar aStar = new AStar();
        String s = aStar.buildTree(2);
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
