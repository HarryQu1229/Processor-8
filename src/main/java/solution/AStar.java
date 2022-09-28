package solution;

import org.graphstream.graph.Node;

import java.util.List;
import java.util.PriorityQueue;

public class AStar {

    PriorityQueue<PartialSolution> solutionQueue = new PriorityQueue<>((x1,x2)->{
          return (int)(x1.getCostFunction() - x2.getCostFunction());
    });

    public PriorityQueue<PartialSolution> test(){
        PartialSolution p1 = new PartialSolution(graph);
        PartialSolution p2 = new PartialSolution(graph);
        PartialSolution p3 = new PartialSolution(graph);
        PartialSolution p4 = new PartialSolution(graph);

        p1.setCostFunction(65);
        p2.setCostFunction(12);
        p3.setCostFunction(44);
        p4.setCostFunction(32);


        solutionQueue.add(p1);
        solutionQueue.add(p2);
        solutionQueue.add(p3);
        solutionQueue.add(p4);

        return solutionQueue;

    }

    Digraph graph;

    PartialSolution root;

    public AStar(Digraph digraph){
        root = new PartialSolution(digraph);
        solutionQueue.add(root);
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
                          solutionQueue.add(current);
                     }
                }else{
                    for(int i=1;i<=numOfProcessor;i++){
                        PartialSolution current = new PartialSolution(prev,graph,node,i,numOfProcessor);
                        if(current.getNodesPath().size() == graph.getNodeCount()){
                            return current.getInfo();
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
