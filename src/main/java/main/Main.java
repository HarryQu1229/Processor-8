package main;

import io.InputLoader;
import solution.Digraph;
import solution.SolutionTree;
import solution.State;

public class Main {

    public static void main(String[] args){
//        InputLoader.print(graph, true);
        Digraph digraph = InputLoader.loadDotFile("g1");
//        InputLoader.print(digraph, false);

        SolutionTree solutionTree = SolutionTree.getSolutionTree(digraph, 1);

        System.out.println("\n=== Final print ===");
//        System.out.println(solutionTree.getAllNodes().size());
        InputLoader.print(solutionTree, false);

//        System.out.println(digraph.getAllNodes());

//        System.out.println(digraph.getBottomLevel(digraph.getNodeByValue("h")));

//        System.out.println(myGraph.getCriticalPath());


    }

}
