package main;

import io.InputLoader;
import org.graphstream.graph.Node;
import solution.AStar;
import solution.Digraph;
import solution.PartialSolution;
import solution.SolutionTree;

import java.util.PriorityQueue;

public class Main {

    public static void main(String[] args) {
//        InputLoader.print(graph, true);
        Digraph digraph = InputLoader.loadDotFile("g9");


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

        SolutionTree solutionTree = new SolutionTree(digraph,2);
        solutionTree.build(solutionTree.getRoot());
        solutionTree.print();



        AStar aStar = new AStar(digraph);
        String s = aStar.buildTree(2);

        System.out.println(s);

//        PriorityQueue<PartialSolution> test = aStar.test();
//        while(!test.isEmpty()){
//            System.out.println(test.poll());
//        }


//        System.out.println(digraph.getAllNodeWeight());


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
