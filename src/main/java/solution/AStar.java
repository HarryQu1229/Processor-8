package solution;

import io.InputLoader;
import models.TheGraph;
import org.graphstream.graph.Node;

import java.util.List;
import java.util.PriorityQueue;

public class AStar {

    PriorityQueue<PartialSolution> solutionQueue = new PriorityQueue<>((x1,x2)->{
        // compare the cost function between 2 solutions.
          return  (int) (x1.getCostFunction() -  x2.getCostFunction());
    });

    PriorityQueue<PartialSolution> leafNodeQueue = new PriorityQueue<>((x1,x2)->{
        // compare the last node's starting time on the Node Path between 2 solutions.
        return (int)(x1.getNodeStates().get(x1.getNodesPath().get(x1.getNodesPath().size()-1)).getStartingTime()
                - x2.getNodeStates().get(x1.getNodesPath().get(x1.getNodesPath().size()-1)).getStartingTime());
    });


    PartialSolution root;

    public AStar(){
        root = new PartialSolution();
        // add the root element of the solution tree - i.e. the empty schedule
        solutionQueue.offer(root);
    }

    /**
     * using A star algorithm to build tree and calculate solution,
     * @return String   the shortest solution path.
     */
    public String buildTree(){

        while(!solutionQueue.isEmpty()){
            // poll the first element from the Priority queue.
            PartialSolution prev = solutionQueue.poll();

            // get available next nodes from the current partial solution.
            List<Node> availableNextNodes = prev.getAvailableNextNodes();


            for(Node node:availableNextNodes){
                // when scheduling the starting nodes of the solution tree, remove the trivial
                // situations since schedule these tasks on any processor is the same, keep one of the processor
                // is sufficient for the starting node. Effectively pruning by removing the symmetric part of the
                // solution tree.
                if(prev.getNodesPath().size()==0){
                     PartialSolution current = new PartialSolution(prev,node,1);
                     // if we have reached leaf node of the solution tree, then return the
                    // current partial solution as optimal solution.
                     if(current.getNodesPath().size() == TheGraph.get().getNodeCount()){
                          return current.getInfo();
                     }else{
                         // add the current partial solution to the solutionQueue.
                          solutionQueue.offer(current);
                     }
                     // if the node is not the first node on solution tree.
                }else{
                    for(int i = 1; i<= InputLoader.getNumOfProcessors(); i++){
                        PartialSolution current = new PartialSolution(prev,node,i);
                        // if we have reached leaf node of the solution tree, then return the
                        // current partial solution as optimal solution, by determining the optimal processorId
                        // that the leaf task node is being scheduled.
                        if(current.getNodesPath().size() == TheGraph.get().getNodeCount()){
                            leafNodeQueue.offer(current);
                            if (i == InputLoader.getNumOfProcessors()){
                                return leafNodeQueue.poll().getInfo();
                            }
                        }else{
                            // add to the solutionQueue
                            solutionQueue.offer(current);
                        }
                    }
                }
            }
        }

        return "";
    }

}

