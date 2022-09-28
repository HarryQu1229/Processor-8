package algorithm;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import solution.Digraph;
import solution.NodeProperties;

import java.util.*;

public class BranchAndBoundAlgorithm {
    public static double minCost = Double.MAX_VALUE;
    public static int numOfNodes = 0;
    public static List<OutputEntity> solution;
    public static List<OutputEntity> tempSolution = new ArrayList<>();
    public static List<Node> visitedNodes = new ArrayList<>();
    public static Map<Node,Integer> indegress = new HashMap<>();

    public static double[] processorsCurrentTimes;

    public static Map<Node, NodeProperties> nodeToProcessor = new HashMap<>();

    public static void solve(Digraph graph, int numOfProcessors) {
        // populate all nodes' indegress and calculate number of nodes in graph
        for (Node node : graph) {
            indegress.put(node, node.getInDegree());
            numOfNodes++;
        }

        processorsCurrentTimes = new double[numOfProcessors];

        // start calculation for start node
        calculate(graph, numOfProcessors, 0);


        // print out the solution
        for (OutputEntity entity : solution) {
            System.out.println(entity.toDotString());
        }

    }

    public static void calculate (Digraph graph, int numOfProcessors, double currentCost) {

        // if the currentCost is already larger than the current minCost, return
        if (currentCost > minCost) {
            return;
        }

        // if all nodes have been processed, check if it can give us the final answer
        if (tempSolution.size() == numOfNodes) {
            if (currentCost < minCost) {
                minCost = currentCost;
                solution = new ArrayList<>(tempSolution);
            }
            return;
        }

        for (Node node : graph) {
            // if the currentnode should not start (indegree is 0), return
            if (indegress.get(node) != 0) {
                continue;
            }

            if (visitedNodes.contains(node)) {
                continue;
            }

            // update all indegress of the nodes that are connected to the current node
            for (Node child : graph.getAllChildrenNode(node)) {
                indegress.put(child, indegress.get(child) - 1);
            }

            visitedNodes.add(node);

            for (int i = 1; i<= numOfProcessors; i++) {

                double startTimeForCurrentChild = 0;

                for(Node eachProcessedNode: visitedNodes) {
                    if (eachProcessedNode.hasEdgeToward(node)) {
                        // if the current node is on the same processor with the selected its parent node.
                        if (i == nodeToProcessor.get(eachProcessedNode).getProcessorId()) {
                            // find the finishing time of the parent node of the current node and let it be the starting
                            // time of the current node.
                            startTimeForCurrentChild = Math.max(startTimeForCurrentChild, nodeToProcessor.get(eachProcessedNode).getStartingTime() + graph.getNodeWeight(eachProcessedNode.getId()));
                            // if the current node is on the different processor with the selected its parent node.
                        } else {
                            // find the finishing time of the parent node of the current node and add communication cost,
                            // then let it be the starting time of the current node.
                            startTimeForCurrentChild = Math.max(startTimeForCurrentChild, nodeToProcessor.get(eachProcessedNode).getStartingTime() + graph.getNodeWeight(eachProcessedNode.getId()) + graph.getEdgeWeight(eachProcessedNode.getId(), node.getId()));
                        }
                    }
                }

                startTimeForCurrentChild = Math.max(processorsCurrentTimes[i-1], startTimeForCurrentChild);

                double temp = processorsCurrentTimes[i-1];
                processorsCurrentTimes[i-1] = startTimeForCurrentChild + graph.getNodeWeight(node.getId());

                nodeToProcessor.put(node, new NodeProperties(i, (int) startTimeForCurrentChild));

                double temp2 = currentCost;
                currentCost = Math.max(currentCost, startTimeForCurrentChild + graph.getNodeWeight(node.getId()));

                tempSolution.add(new OutputEntity(node, graph.getNodeWeight(node.getId()), i, startTimeForCurrentChild));

                calculate(graph, numOfProcessors, currentCost);

                tempSolution.remove(tempSolution.size()-1);

                processorsCurrentTimes[i-1] = temp;

                nodeToProcessor.remove(node);

                currentCost = temp2;
            }

            visitedNodes.remove(node);

            // update all indegress of the nodes that are connected to the current node
            for (Node child : graph.getAllChildrenNode(node)) {
                indegress.put(child, indegress.get(child) + 1);
            }
        }



    }


}
