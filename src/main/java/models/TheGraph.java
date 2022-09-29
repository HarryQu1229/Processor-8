package models;

import solution.Digraph;

public class TheGraph {

    private static Digraph graph;

    public static void set(Digraph theGraph){
        graph = theGraph;
    }

    public static Digraph get(){
        return graph;
    }


}
