package models;

import io.InputLoader;
import org.graphstream.graph.Node;
import solution.Digraph;
import solution.PartialSolution;

import java.util.List;

public class TheGraph {

    private static Digraph graph;

    public static void set(Digraph theGraph){
        graph = theGraph;
    }

    public static Digraph get(){
        return graph;
    }

    private static int minCost;

    public static int getMinCost() {
        return minCost;
    }

    public static void setMinCost(int minCost) {
        TheGraph.minCost = minCost;
    }

    public static boolean DFSFindOneSolution(PartialSolution prev){


        if(prev.getNodesPath().size() == TheGraph.get().getNodeCount()){
            TheGraph.setMinCost(prev.calculateEndScheduleTime());
            return true;
        }

        List<Node> availableNextNodes = prev.getAvailableNextNodes();

        PartialSolution minPartialSolution = null;
        double minCostFunction = Integer.MAX_VALUE;

        for(Node node:availableNextNodes){
            for(int i = 1; i<= InputLoader.getNumOfProcessors(); i++){
                PartialSolution current  = new PartialSolution(prev,node,i);
                double v = current.calculateCostFunction(node, i);
                if(v<minCostFunction){
                    minCostFunction= v;
                    minPartialSolution = current;
                }
            }
        }

        if(DFSFindOneSolution(minPartialSolution)){
            return true;
        }
        return false;
    }

    public static  int count;
    public static boolean findMinCost(PartialSolution prev){

         if(prev.getNodesPath().size() == TheGraph.get().getNodeCount()){
              count++;
              if(prev.calculateEndScheduleTime() > minCost){
                  return false;
              }
              minCost = prev.calculateEndScheduleTime();
              if(count >= 10000){
                  return true;
              }

              return false;

         }else{

             if(prev.getCostFunction() > minCost){
                 return false;
             }
             List<Node> availableNextNodes = prev.getAvailableNextNodes();
             for(Node node:availableNextNodes){
                 for(int i=1;i<=InputLoader.getNumOfProcessors();i++){
                     PartialSolution current = new PartialSolution(prev,node,i);
                     if(findMinCost(current)){
                         return true;
                     }
                 }
             }
         }
         return false;
    }

}
