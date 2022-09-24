package main;

import io.InputLoader;
import solution.Digraph;

public class Main {

    public static void main(String[] args){
//        InputLoader.print(graph, true);
        Digraph digraph = InputLoader.loadDotFile("g5");
        InputLoader.print(digraph, false);

        System.out.println(digraph.getAllNodes());

//        System.out.println(digraph.getBottomLevel(digraph.getNodeByValue("h")));

//        System.out.println(myGraph.getCriticalPath());


    }

}
