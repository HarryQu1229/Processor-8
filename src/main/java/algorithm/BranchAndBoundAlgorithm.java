package algorithm;

import io.InputLoader;
import models.TheGraph;
import org.graphstream.graph.Node;

import java.util.*;

public class BranchAndBoundAlgorithm {
    public static int minCost = Integer.MAX_VALUE;
    public static int numOfNodes;
    public static int sumOfWeights;
    public static List<NodeInfo> solution;
    public static List<Node> visitedNodes;
    public static Map<Node, NodeInfo> nodeInfo;
    public static int[] processorsCurrentTimes;

    public static int solve(Map<Node, NodeInfo> nodeInfo, int nodeSize, int sum) {
        BranchAndBoundAlgorithm.nodeInfo = nodeInfo;
        numOfNodes = nodeSize;
        sumOfWeights = sum;
        visitedNodes = new ArrayList<>();
        processorsCurrentTimes = new int[InputLoader.getNumOfProcessors()];
        // start calculation for start node
        calculate(0);

        // print out the solution
        int ans = 0;
        for (NodeInfo eachNodeInfo : solution) {
            ans = Math.max(ans, eachNodeInfo.getScheduledStartTime() + eachNodeInfo.getWeight());
            System.out.println(eachNodeInfo.toDotString());
        }

        System.out.println("Total time: " + ans);
        return ans;

    }

    public static void calculate(int currentCost) {

        // if the currentCost is already larger than the current minCost, return
        if (currentCost >= minCost) {
            return;
        }

        // if all nodes have been processed, check if it can give us the final answer
        if (visitedNodes.size() == numOfNodes) {
            if (minCost > currentCost) {
                minCost = currentCost;
                solution = new ArrayList<>();
                for (Node node : visitedNodes) {
                    solution.add(nodeInfo.get(node).clone());
                }
                return;
            }
        }

        int startTimeForCurrentChild;
        int previousProcessorCurrentTime;
        int previousCurrentCost;
        int previousScheduledProcessor;
        int previousScheduledStartTime;
        // get the next node to be processed
        for (Node node : TheGraph.get()) {
            // if the current node should not start (indegree is 0), return
            if (nodeInfo.get(node).getIndegrees() != 0) {
                continue;
            }

            // if node has already been visited, return
            if (visitedNodes.contains(node)) {
                continue;
            }

            // update all indegress of the nodes that are connected to the current node
            for (Node child : TheGraph.get().getChildrenOfNode(node)) {
                nodeInfo.get(child).decreaseIndegrees();
            }

            // update the node to visited
            visitedNodes.add(node);

            sumOfWeights -= nodeInfo.get(node).getWeight();

            // try schedule each node to each processor
            for (int i = 1; i <= InputLoader.getNumOfProcessors(); i++) {

                startTimeForCurrentChild = 0;

                for (Node eachProcessedNode : visitedNodes) {
                    if (eachProcessedNode.hasEdgeToward(node)) {
                        // if the current node is on the same processor with the selected its parent node.
                        if (i == nodeInfo.get(eachProcessedNode).getScheduledProcessor()) {
                            // find the finishing time of the parent node of the current node and let it be the starting
                            // time of the current node.
                            startTimeForCurrentChild = Math.max(startTimeForCurrentChild, +nodeInfo.get(eachProcessedNode).getScheduledStartTime() + nodeInfo.get(eachProcessedNode).getWeight());
                            // if the current node is on the different processor with the selected its parent node.
                        } else {
                            // find the finishing time of the parent node of the current node and add communication cost,
                            // then let it be the starting time of the current node.
                            startTimeForCurrentChild = Math.max(startTimeForCurrentChild, nodeInfo.get(eachProcessedNode).getScheduledStartTime() + nodeInfo.get(eachProcessedNode).getWeight() + TheGraph.get().getEdgeWeight(eachProcessedNode.getId(), node.getId()).intValue());
                        }
                    }
                }

                startTimeForCurrentChild = Math.max(processorsCurrentTimes[i - 1], startTimeForCurrentChild);

                previousProcessorCurrentTime = processorsCurrentTimes[i - 1];
                processorsCurrentTimes[i - 1] = startTimeForCurrentChild + nodeInfo.get(node).getWeight();

                previousScheduledProcessor = nodeInfo.get(node).getScheduledProcessor();
                previousScheduledStartTime = nodeInfo.get(node).getScheduledStartTime();

                previousCurrentCost = currentCost;
                currentCost = Math.max(currentCost, startTimeForCurrentChild + nodeInfo.get(node).getWeight());

                nodeInfo.get(node).setScheduledProcessor(i);
                nodeInfo.get(node).setScheduledStartTime(startTimeForCurrentChild);

                calculate(currentCost);

                nodeInfo.get(node).setScheduledProcessor(previousScheduledProcessor);
                nodeInfo.get(node).setScheduledStartTime(previousScheduledStartTime);

                processorsCurrentTimes[i - 1] = previousProcessorCurrentTime;

                currentCost = previousCurrentCost;
            }

            // backtrack
            visitedNodes.remove(node);

            sumOfWeights += nodeInfo.get(node).getWeight();

            // update all indegress of the nodes that are connected to the current node
            for (Node child : TheGraph.get().getChildrenOfNode(node)) {
                nodeInfo.get(child).increaseIndegrees();
            }
        }
    }


}
