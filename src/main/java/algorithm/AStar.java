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

        PriorityQueue<PartialSolution> leafNodeQueue = new PriorityQueue<>((x1, x2) -> {
            // compare the last node's starting time on the Node Path between 2 solutions.
            return x1.getNodeStates().get(x1.getNodesPath().get(x1.getNodesPath().size() - 1)).getStartingTime()
                    - x2.getNodeStates().get(x1.getNodesPath().get(x1.getNodesPath().size() - 1)).getStartingTime();
        });

        solutionQueue.offer(root);

        while (!solutionQueue.isEmpty()) {
//            System.out.println("first solution: " + solutionQueue);
            // poll the first element from the Priority queue.
            PartialSolution prev = solutionQueue.poll();
//            System.out.println("prev: " + prev);
//            System.out.println(prev);

            // get available next nodes from the current partial solution.
            List<Node> availableNextNodes = prev.getAvailableNextNodes();
//            System.out.println(availableNextNodes);

            for (Node node : availableNextNodes) {
                // when scheduling the starting nodes of the solution tree, remove the trivial
                // situations since schedule these tasks on any processor is the same, keep one of the processor
                // is sufficient for the starting node. Effectively pruning by removing the symmetric part of the
                // solution tree.
                if (prev.getNodesPath().size() == 0) {
                    PartialSolution current = new PartialSolution(prev, node, 1);
                    // if we have reached leaf node of the solution tree, then return the
                    // current partial solution as optimal solution.
                    if (current.getNodesPath().size() == TheGraph.get().getNodeCount()) {
                        return current;
                    } else {
                        // add the current partial solution to the solutionQueue.
                        solutionQueue.offer(current);
                    }
                    // if the node is not the first node on solution tree.
                } else {
                    List<Integer> emptyProcessorIds = new ArrayList<>();
                    List<Integer> notEmptyProcessorIds = new ArrayList<>();
                    // check for duplicate(homogeneous) empty processors
                    for (int i = 1; i <= InputLoader.getNumOfProcessors(); i++) {
                        if (prev.findLastFinishTime(i) == 0) {
                            emptyProcessorIds.add(i);
                        } else {
                            notEmptyProcessorIds.add(i);
                        }
                    }

                    // if empty processor count is less or equal to 1, it means that there are no homogeneous
                    // empty processors, carry on with normal operations.
                    if (emptyProcessorIds.size() <= 1) {

                        for (int i = 1; i <= InputLoader.getNumOfProcessors(); i++) {
                            PartialSolution current = new PartialSolution(prev, node, i);
                            // if we have reached leaf node of the solution tree, then return the
                            // current partial solution as optimal solution, by determining the optimal processorId
                            // that the leaf task node is being scheduled.
                            if (current.getNodesPath().size() == TheGraph.get().getNodeCount()) {
                                leafNodeQueue.offer(current);
                                if (i == InputLoader.getNumOfProcessors()) {
                                    return leafNodeQueue.poll();
                                }
                            } else {
                                // add to the solutionQueue, if the projected underestimate cost from the current node on the
                                // solution tree is greater than the minimum guess cost we found from the `AStarUtil` methods,
                                // effectively it means the minimum cost to reach the leaf node of the solution tree from the
                                // current node is greater than the Projected Upper Limit of the cost. Therefore, we will discard
                                // the current node and all of its children nodes on the solution tree. Otherwise, we will add
                                // the current partial solution into the solution Priority queue.
                                if (current.calculateCostFunction(node, i) <= TheGraph.getMinimumGuessCost()) {
                                    solutionQueue.offer(current);
                                }
                            }
                        }

                        // if 2 or more empty processor before the current task being scheduled, we can arbitrarily choose
                        // which empty processor should the task be scheduled on, by convention, we choose the empty processor
                        // with the smallest lexicographically ordered processorID. As this would have the same effect if
                        // any other empty processor was chosen to schedule the current task on. By having this step, a lot of computation
                        // time would be saved if the task is being scheduled on a lot of processors.
                    } else {

                        notEmptyProcessorIds.add(emptyProcessorIds.get(0));
                        for (int i = 0; i < notEmptyProcessorIds.size(); i++) {
                            PartialSolution current = new PartialSolution(prev, node, notEmptyProcessorIds.get(i));
                            // if we have reached leaf node of the solution tree, then return the
                            // current partial solution as optimal solution, by determining the optimal processorId
                            // that the leaf task node is being scheduled.
                            if (current.getNodesPath().size() == TheGraph.get().getNodeCount()) {
                                leafNodeQueue.offer(current);
                                if (i == notEmptyProcessorIds.size() - 1) {
                                    return leafNodeQueue.poll();
                                }
                            } else {
                                if (current.calculateCostFunction(node, notEmptyProcessorIds.get(i)) <= TheGraph.getMinimumGuessCost()) {
                                    solutionQueue.offer(current);
                                }
                            }
                        }
                    }
                }
            }
//            System.out.println("last solution queue: " + solutionQueue);
        }
        return null;
    }

}

