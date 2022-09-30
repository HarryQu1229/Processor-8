package solution;

import io.InputLoader;
import models.TheGraph;
import org.graphstream.graph.Node;

import java.util.List;
import java.util.PriorityQueue;

public class AStar {

    PriorityQueue<PartialSolution> solutionQueue = new PriorityQueue<>((x1,x2)->{
          return  (int) (x1.getCostFunction() -  x2.getCostFunction());
    });

    PriorityQueue<PartialSolution> leafNodeQueue = new PriorityQueue<>((x1,x2)->{
        return (int)(x1.getNodeStates().get(x1.getNodesPath().get(x1.getNodesPath().size()-1)).getStartingTime()
                - x2.getNodeStates().get(x1.getNodesPath().get(x1.getNodesPath().size()-1)).getStartingTime());
    });


    PartialSolution root;

    public AStar(){
        root = new PartialSolution();
        solutionQueue.offer(root);
    }

    public String buildTree(){

        while(!solutionQueue.isEmpty()){
            PartialSolution prev = solutionQueue.poll();

            List<Node> availableNextNodes = prev.getAvailableNextNodes();

            for(Node node:availableNextNodes){
                if(prev.getNodesPath().size()==0){
                     PartialSolution current = new PartialSolution(prev,node,1);
                     if(current.getNodesPath().size() == TheGraph.get().getNodeCount()){
                          return current.getInfo();
                     }else{
                          solutionQueue.offer(current);
                     }
                }else{
                    for(int i = 1; i<= InputLoader.getNumOfProcessors(); i++){
                        PartialSolution current = new PartialSolution(prev,node,i);
                        if(current.getNodesPath().size() == TheGraph.get().getNodeCount()){
                            leafNodeQueue.offer(current);
                            if (i == InputLoader.getNumOfProcessors()){
                                return leafNodeQueue.poll().getInfo();
                            }
                        }else{
                            solutionQueue.offer(current);
                        }
                    }
                }
            }
        }

        return "";
    }

}

