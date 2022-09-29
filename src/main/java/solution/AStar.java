package solution;

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


    Digraph graph;

    PartialSolution root;

    public AStar(Digraph digraph){
        root = new PartialSolution(digraph);
        solutionQueue.offer(root);
        graph = digraph;
    }

    public String buildTree(int numOfProcessor){

        while(!solutionQueue.isEmpty()){
            PartialSolution prev = solutionQueue.poll();

            List<Node> availableNextNodes = prev.getAvailableNextNodes();

            for(Node node:availableNextNodes){
                if(prev.getNodesPath().size()==0){
                     PartialSolution current = new PartialSolution(prev,graph,node,1,numOfProcessor);
                     if(current.getNodesPath().size() == graph.getNodeCount()){
                          return current.getInfo();
                     }else{
                          solutionQueue.offer(current);
                     }
                }else{
                    for(int i=1;i<=numOfProcessor;i++){
                        PartialSolution current = new PartialSolution(prev,graph,node,i,numOfProcessor);
                        if(current.getNodesPath().size() == graph.getNodeCount()){
                            leafNodeQueue.offer(current);
                            if (i == numOfProcessor){
                                return leafNodeQueue.poll().getInfo();
                            } else {
                                continue;
                            }
//                           return current.getInfo();
                        }else{
                            solutionQueue.add(current);
                        }
                    }
                }
            }
        }

        return "";
    }

}
