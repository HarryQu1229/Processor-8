package main;

import io.InputLoader;
import org.graphstream.graph.Graph;
import solution.Digraph;

public class Main {

    public static void main(String[] args){
        Graph graph = InputLoader.loadDotFile("g5");
//        InputLoader.print(graph, true);
        Digraph digraph = new Digraph(graph);

        System.out.println(digraph.getBottomLevel(digraph.getNodeByValue("h")));

//        System.out.println(myGraph.getCriticalPath());


    }

}
