package solution;

import org.graphstream.graph.Node;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class PartialSolution {


//    private static int PROCESSOR_NO = 0;
//    private static int STARTING_TIME = 1;
//    private static int INDEGREE_NO = 2;

    // nodeIndex :  [Processor Num, starting Time, Indegree Number]
    private LinkedHashMap<Node, NodeProperties> nodeStates;
    private double costFunction;
    private List<Node> nodesPath;


    public PartialSolution(PartialSolution prevPartial, Digraph graph, Node currentNode, int processorNo) {

        this.nodesPath = new ArrayList<>();
        nodesPath.addAll(prevPartial.getNodesPath());
        this.nodeStates = new LinkedHashMap<>();
        for (Node node : prevPartial.getNodeStates().keySet()) {
            NodeProperties nodeProperties = new NodeProperties();
            NodeProperties prevNodeProperties = prevPartial.getNodeStates().get(node);
            nodeProperties.setInDegree_no(prevNodeProperties.getInDegree_no());
            nodeProperties.setProcess_no(prevNodeProperties.getProcess_no());
            nodeProperties.setStart_time(prevNodeProperties.getStart_time());
            nodeStates.put(node, nodeProperties);
        }

        if (!nodesPath.isEmpty()) {

            int startTime = 0;
            for (Node node : nodesPath) {
                if (node.hasEdgeToward(currentNode)) {
                    if (nodeStates.get(node).getProcess_no() == processorNo) {
                        startTime = (int) Math.max(startTime, nodeStates.get(node).getStart_time() + graph.getNodeWeight(node.getId()));
                    } else {
                        int communicationCost = graph.getEdgeWeight(node.getId(), currentNode.getId()).intValue();
                        startTime = (int) Math.max(startTime, nodeStates.get(node).getStart_time() + graph.getNodeWeight(node.getId()) + communicationCost);
                    }
                }
            }

            int finishTimePrev = 0;
            for (Node node: nodesPath){
                if (nodeStates.get(node).getProcess_no() == processorNo){
                    finishTimePrev = (int)Math.max(finishTimePrev, nodeStates.get(node).getStart_time()+graph.getNodeWeight(node.getId()));
                }
            }
            nodeStates.get(currentNode).setStart_time(Math.max(startTime, finishTimePrev));
        }

        // add this node to the solution path
        nodesPath.add(currentNode);
        // set current node's processor number.
        nodeStates.get(currentNode).setProcess_no(processorNo);
        // set current node's indegree to -1 (all done.)
        nodeStates.get(currentNode).setInDegree_no(-1);
        // decrease all direct children indegree by 1 (effectively get rid of the in edges from the current node to
        // all its children nodes).
        List<Node> childrenNodes = graph.getAllChildrenNode(currentNode);
        for (Node childNode : childrenNodes) {
            int currentInDegree = nodeStates.get(childNode).getInDegree_no();
            nodeStates.get(childNode).setInDegree_no(currentInDegree - 1);
        }

    }

    // initial empty state, set up all nodes.
    public PartialSolution(Digraph graph) {
        this.nodesPath = new ArrayList<>();
        this.nodeStates = new LinkedHashMap<>();

        for (Node node : graph.getAllNodes()) {
            NodeProperties nodeProperties = new NodeProperties();
            nodeProperties.setProcess_no(0);//processor number
            nodeProperties.setInDegree_no(node.getInDegree());// indegree
            nodeProperties.setStart_time(0);//starting time
            nodeStates.put(node, nodeProperties);
        }
    }

    public List<Node> getAvailableNextNodes() {
        List<Node> availableNextNodes = new ArrayList<>();
        for (Node node : nodeStates.keySet()) {
            if (nodeStates.get(node).getInDegree_no() == 0) {
                availableNextNodes.add(node);
            }
        }
        return availableNextNodes;
    }

    public List<Node> getNodesPath() {
        return nodesPath;
    }

    public LinkedHashMap<Node, NodeProperties> getNodeStates() {
        return nodeStates;
    }

    public double getCostFunction() {
        return costFunction;
    }

    public String getInfo(){

        StringBuffer sb = new StringBuffer();

        for(int i=0;i<nodesPath.size();i++){
            Node node = nodesPath.get(i);
            sb.append("(" + node.getId()+",");
            sb.append(nodeStates.get(node).getProcess_no()+",");
            sb.append(nodeStates.get(node).getStart_time()+")");
            sb.append(";");
        }

        return sb.toString();

    }
}
