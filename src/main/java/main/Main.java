package main;

import algorithm.BranchAndBoundAlgorithm;
import algorithm.NodeInfo;
import io.InputLoader;
import org.graphstream.graph.Node;
import solution.Digraph;
import solution.SolutionTree;

import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
//        InputLoader.print(graph, true);
        Digraph digraph = InputLoader.loadDotFile("g11");


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

        // for each node initialise its NodeInfo
        Map<Node, NodeInfo> nodeInfo = new HashMap<>();
        int nodeSize = 0;
        int sum = 0;
        for (Node node : digraph) {
            nodeInfo.put(node, new NodeInfo(node.getId(), digraph.getNodeWeight(node.getId()).intValue(), node.getInDegree()));
            nodeSize++;
            sum += digraph.getNodeWeight(node.getId()).intValue();
        }

        BranchAndBoundAlgorithm.solve(digraph, 4,nodeInfo,nodeSize,sum);

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
