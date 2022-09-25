package solution;

import org.graphstream.graph.Node;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class PartialSolution {

    private static int PROCESSOR_NO = 0;
    private static int STARTING_TIME = 1;
    private static int INDEGREE_NO = 2;

    // nodeIndex :  [Processor Num, starting Time, Indegree Number]
    private LinkedHashMap<Node, List<Integer>> nodeStates;
    private double costFunction;
    private List<Node> nodesPath;


    public PartialSolution(PartialSolution prevPartial, Digraph graph, Node currentNode, int processorNo) {

        this.nodesPath = new ArrayList<>();
        nodesPath.addAll(prevPartial.getNodesPath());
        this.nodeStates = new LinkedHashMap<>();
        for (Node node : prevPartial.getNodeStates().keySet()) {
            List<Integer> states = new ArrayList<>();
            states.add(prevPartial.getNodeStates().get(node).get(PROCESSOR_NO)); //processor number
            states.add(prevPartial.getNodeStates().get(node).get(STARTING_TIME)); //starting time
            states.add(prevPartial.getNodeStates().get(node).get(INDEGREE_NO));
            nodeStates.put(node, states);
        }

        if (!nodesPath.isEmpty()) {
            // get the previous node
            Node prevNode = nodesPath.get(nodesPath.size() - 1);

            // previous processor number
            int prevProcessorNo = nodeStates.get(prevNode).get(PROCESSOR_NO);
            int prevStartingTime = nodeStates.get(prevNode).get(STARTING_TIME);
            int prevNodeWeight = graph.getNodeWeight(prevNode.getId()).intValue();


            int startTime = 0;
            for (Node node : nodesPath) {
                if (node.hasEdgeToward(currentNode)) {
                    if (nodeStates.get(node).get(PROCESSOR_NO) == processorNo) {
                        startTime = (int) Math.max(startTime, nodeStates.get(node).get(STARTING_TIME) + graph.getNodeWeight(node.getId()));
                    } else {
                        int communicationCost = graph.getEdgeWeight(node.getId(), currentNode.getId()).intValue();
                        startTime = (int) Math.max(startTime, nodeStates.get(node).get(STARTING_TIME) + graph.getNodeWeight(node.getId()) + communicationCost);
                    }
                }
            }

            int finishTimePrev = 0;
            for (Node node: nodesPath){
                if (nodeStates.get(node).get(PROCESSOR_NO) == processorNo){
                    finishTimePrev = (int)Math.max(finishTimePrev, nodeStates.get(node).get(STARTING_TIME)+graph.getNodeWeight(node.getId()));
                }
            }
            nodeStates.get(currentNode).set(STARTING_TIME, Math.max(startTime, finishTimePrev));
        }

        // add this node to the solution path
        nodesPath.add(currentNode);
        // set current node's processor number.
        nodeStates.get(currentNode).set(PROCESSOR_NO, processorNo);
        // set current node's indegree to -1 (all done.)
        nodeStates.get(currentNode).set(INDEGREE_NO, -1);
        // decrease all direct children indegree by 1 (effectively get rid of the in edges from the current node to
        // all its children nodes).
        List<Node> childrenNodes = graph.getAllChildrenNode(currentNode);
        for (Node childNode : childrenNodes) {
            int currentIndegree = nodeStates.get(childNode).get(INDEGREE_NO);
            nodeStates.get(childNode).set(INDEGREE_NO, currentIndegree - 1);
        }

    }

    // initial empty state, set up all nodes.
    public PartialSolution(Digraph graph) {
        this.nodesPath = new ArrayList<>();
        this.nodeStates = new LinkedHashMap<>();

        for (Node node : graph.getAllNodes()) {
            List<Integer> states = new ArrayList<>();
            states.add(0); //processor number
            states.add(0); //starting time
            states.add(node.getInDegree()); // indegree
            nodeStates.put(node, states);
        }
    }

    public List<Node> getAvailableNextNodes() {
        List<Node> availableNextNodes = new ArrayList<>();
        for (Node node : nodeStates.keySet()) {
            if (nodeStates.get(node).get(INDEGREE_NO) == 0) {
                availableNextNodes.add(node);
            }
        }
        return availableNextNodes;
    }

    public List<Node> getNodesPath() {
        return nodesPath;
    }

    public LinkedHashMap<Node, List<Integer>> getNodeStates() {
        return nodeStates;
    }

    public double getCostFunction() {
        return costFunction;
    }
}
