package algorithm;

import io.InputLoader;
import models.TheGraph;
import org.graphstream.graph.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class AStar {
    public AStar() {
        // add the root element of the solution tree - i.e. the empty schedule
        new AStarUtil();
    }

    /**
     * using A star algorithm to build tree and calculate solution,
     *
     * @return String   the shortest solution path.
     */
    public PartialSolution buildTree(PartialSolution root) {

        PriorityQueue<PartialSolution> solutionQueue = new PriorityQueue<>((x1, x2) -> {
            // compare the cost function between 2 solutions.
            return (int) (x1.getCostFunction() - x2.getCostFunction());
        });

        solutionQueue.offer(root);

        while (!solutionQueue.isEmpty()) {
            // poll the first element from the Priority queue.
            PartialSolution prev = solutionQueue.poll();

            // get available next nodes from the current partial solution.
            List<Node> availableNextNodes = prev.getAvailableNextNodes();

            for (Node node : availableNextNodes) {
                // when scheduling the starting nodes of the solution tree, remove the trivial
                // situations since schedule these tasks on any processor is the same, keep one of the processor
                // is sufficient for the starting node. Effectively pruning by removing the symmetric part of the
                // solution tree.
                List<PartialSolution> nextPartialSolution = prev.getNextPartialSolution(node);
                for(PartialSolution partialSolution:nextPartialSolution){
                    if(partialSolution.getNodesPath().size()==TheGraph.get().getNodeCount()){
                        return partialSolution;
                    }else{
                        solutionQueue.offer(partialSolution);
                    }
                }
            }
        }
        return null;
    }
}

