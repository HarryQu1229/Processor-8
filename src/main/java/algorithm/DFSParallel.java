package algorithm;

import io.InputLoader;
import models.TheGraph;
import org.graphstream.graph.Node;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class DFSParallel {


    private int numOfThread;
    private ExecutorService executorService;
    private PartialSolution bestPartialSolution = new PartialSolution();

    public PartialSolution getBestPartialSolution() {
        return bestPartialSolution;
    }

    public void setBestPartialSolution(PartialSolution bestPartialSolution) {
        this.bestPartialSolution = bestPartialSolution;
    }

    public DFSParallel(){}

     public DFSParallel(int numOfthread){
         this.numOfThread = numOfthread;
         DFSFindOneSolution(new PartialSolution());
     }

    public boolean DFSFindOneSolution(PartialSolution prev){

        // if we are at the leaf node of the solution tree.
        if(prev.getNodesPath().size() == TheGraph.get().getNodeCount()){
            // set the minimum guess cost as the result solution cost (Last finish Time among all processors)
            TheGraph.setMinimumGuessCost(prev.calculateEndScheduleTime());
            bestPartialSolution = prev;
            return true;
        }

        // get the next available nodes onroute.
        List<Node> availableNextNodes = prev.getAvailableNextNodes();

        // placeholders
        PartialSolution minPartialSolution = null;
        double minCostFunction = Integer.MAX_VALUE;

        // find the minimum Cost Function of all possible Partial Solutions of the current Partial Solution's available nodes
        // and on different Processors.
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

        // recursive call to find a full-solution.
        if(DFSFindOneSolution(minPartialSolution)){
            return true;
        }
        return false;
    }




    public void findBestPartial(){

         PartialSolution root = new PartialSolution();
         List<PartialSolution> list = new ArrayList<>();

         list.add(root);

         while(list.size() <= numOfThread/2){
              List<PartialSolution> temp = new ArrayList<>();
              for(PartialSolution p:list){
                   temp.addAll(p.getAllNextPartialSolution());
              }

              list.clear();
              list.addAll(temp);
         }

         List<Callable<PartialSolution>> tasks = new ArrayList<>();

         for(PartialSolution p: list){
             tasks.add(()->DFS(p));
         }

         try{
             executorService = Executors.newWorkStealingPool(numOfThread);
             List<Future<PartialSolution>> futures = executorService.invokeAll(tasks);
             for(Future<PartialSolution> f:futures){
                 f.get();
             }
             executorService.shutdown();
         }catch (Exception e){

         }

    }

    public PartialSolution DFS(PartialSolution root){
         if(root.getNodesPath().size()==TheGraph.get().getNodeCount()){
             if(root.calculateEndScheduleTime() >= TheGraph.getMinimumGuessCost()){
                 return getBestPartialSolution();
             }

             synchronized (DFSParallel.class){
                 TheGraph.setMinimumGuessCost(Math.min(TheGraph.getMinimumGuessCost(),root.calculateEndScheduleTime()));
                 bestPartialSolution = root;
             }
             return bestPartialSolution;
         }

        if(root.getCostFunction() > TheGraph.getMinimumGuessCost()){
             return bestPartialSolution;
        }

        List<PartialSolution> allNextPartialSolution = root.getAllNextPartialSolution();
        for(PartialSolution p:allNextPartialSolution){
             DFS(p);
        }

        return bestPartialSolution;
    }
}


