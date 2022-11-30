package models;

/**
 * The class that stores the information about the input graph
 */
public class InputGraph {

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
        InputGraph.minimumGuessCost = minCost;
    }



}
