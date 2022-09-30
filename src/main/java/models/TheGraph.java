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

    private static int minimumGuessCost;

    public static int getMinimumGuessCost() {
        return minimumGuessCost;
    }

    public static void setMinimumGuessCost(int minCost) {
        TheGraph.minimumGuessCost = minCost;
    }



}
