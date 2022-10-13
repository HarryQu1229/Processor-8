package algorithm;

import io.InputLoader;
import models.TheGraph;
import org.graphstream.graph.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.concurrent.*;

public class ParallelAStar extends AStar{

      ExecutorService executorService;
      CompletionService<PartialSolution> cs;
      private int numofThread;


      public ParallelAStar(int numOfThread){
          super();
          this.numofThread = numOfThread;
      }

      public PartialSolution build(){
          //create the thread pool
          executorService = Executors.newWorkStealingPool(numofThread);
          cs = new ExecutorCompletionService<>(executorService);
          PartialSolution res = findBestPartialSolution();
          executorService.shutdown();
          return res;
      }

      // using parallel method to find the best PartialSolution
      public PartialSolution findBestPartialSolution(){

          PriorityQueue<PartialSolution> solutionQueue = new PriorityQueue<>((x1, x2) -> {
              // compare the cost function between 2 solutions.
              return (int) (x1.getCostFunction() - x2.getCostFunction());
          });


          solutionQueue.offer(new PartialSolution());

          while(solutionQueue.size()<numofThread){
               PartialSolution prev  = solutionQueue.poll();

               //get all next partialSolution of current PartialSolution
               List<PartialSolution> nextAllPartialSolution = prev.getAllNextPartialSolution();

               solutionQueue.addAll(nextAllPartialSolution);
          }

          System.out.println(solutionQueue.size());
          int size = solutionQueue.size();

          while(!solutionQueue.isEmpty()){
              PartialSolution prev = solutionQueue.poll();
              cs.submit(()->buildTree(prev));
          }

          try{

              for(int i=0;i<size;i++){
                  PartialSolution partialSolution = cs.take().get();
                  if(partialSolution != null){
                      return partialSolution;
                  }
              }

          }catch (Exception e){

          }
          return null;
      }
}




