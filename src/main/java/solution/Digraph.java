package solution;

import models.TheGraph;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

import java.util.*;

public class Digraph extends SingleGraph{

    private Map<Node, Double> bottomLevels;

    public Digraph(String digraphId) {
        super(digraphId);
    }

    /**
     * helper method to initialises Digraph upon DOT file parsed.
     */
    public void initialize(){
        bottomLevels = new HashMap<>();
        initializeBottomLevels();
        TheGraph.set(this);

    }

    /**
     * Get the `Node` object according to its value
     *
     * @param nodeId String       the value of the node
     * @return Node             return matching Node
     */
    public Node getNodeById(String nodeId) {
        return super.getNode(nodeId);
    }

    /**
     * get all nodes from a graph
     *
     * @return list of all nodes from the graph
     */
    public List<Node> getAllNodes() {
        List<Node> result = new ArrayList<>();
        Iterator<Node> nodeIterator = super.iterator();
        while (nodeIterator.hasNext()) {
            result.add(nodeIterator.next());
        }
        return result;
    }

    /**
     * Add a Node to the digraph.
     *
     * @param nodeId
     * @param weight
     * @return `Node`  being added to the digraph.
     */

    public Node addNode(String nodeId, int weight) {
        super.addNode(nodeId);
        Node node = getNodeById(nodeId);
        node.setAttribute("Weight", weight);
        return node;
    }

    /**
     * Get weight of a specific node
     *
     * @param nodeId String   the Id of a node
     * @return (double)     the weight of the node specified
     */
    public double getNodeWeightById(String nodeId) {
        String nodeWeight = getNodeById(nodeId).getAttribute("Weight").toString();
        return Double.parseDouble(nodeWeight);
    }

    /**
     * get list of the node which in-degree is 0
     *
     * @return return all start Node,which its in-degree is 0
     */
    public List<Node> getStartingNodes() {
        List<Node> result = new ArrayList<>();
        for (Node node : getAllNodes()) {
            // if node's in-degree is 0, then it is a starting node
            if (node.getInDegree() == 0) {
                result.add(node);
            }
        }
        return result;
    }


    /**
     * Get the in degree of a node in the graph.
     * Graph stream api does provide getIndegree method but this method is unstable and does not meet our needs
     *
     * @param node the node that we are getting the in-degree of
     */
    public int getInDegreeOfNode(Node node) {
        int count = 0;
        for (Edge edge : node) {
            if (edge.getNode1().equals(node)) count++;
        }
        return count;
    }

    /**
     * Get the out degree of a node in the graph.
     * Graph stream api does provide getOutdegree method but this method is unstable and does not meet our needs
     *
     * @param node the node that we are getting the out-degree of
     */
    public int getOutDegreeOfNode(Node node) {
        int count = 0;
        for (Edge edge : node) {
            if (edge.getNode0().equals(node)) count++;
        }
        return count;
    }

    /**
     * get sum of Weight of all Nodes of the graph.
     *
     * @return double      sum of Weight of all Nodes of the graph.
     */
    public double getSumWeightOfNodes() {
        double sumNodeWeight = 0;
        for (Node node : getAllNodes()) {
            sumNodeWeight += getNodeWeightById(node.getId());
        }
        return sumNodeWeight;
    }


    /**
     * get the critical path of the graph
     *
     * @return the length of the critical path of graph
     */
    public double getCriticalPath() {
        double criticalPath = 0;
        for (Node node : getStartingNodes()) {
            criticalPath = Math.max(criticalPath, getBottomLevel(node));
        }
        return criticalPath;
    }


    /**
     * get all children node of this input node
     *
     * @param node the input node
     * @return the list of the children node(input node d point to these nodes) of this input node d
     */
    public List<Node> getChildrenOfNode(Node node) {
        List<Node> result = new ArrayList<>();
        for (Edge edge : node) {
            if (edge.getNode0() == node) {
                result.add(edge.getNode1());
            }
        }
        return result;
    }

    /**
     * get all Parent Node of this input node
     *
     * @param node the input node
     * @return the list of the parent node(these nodes will point to input node d)of this input node d
     */
    public List<Node> getParentsOfNode(Node node) {
        List<Node> result = new ArrayList<>();
        for (Edge edge : node) {
            if (edge.getNode1() == node) {
                result.add(edge.getNode0());
            }
        }
        return result;
    }

    /**
     * helper method for initializing bottom levels.
     */
    private void initializeBottomLevels() {
        for(Node node : getAllNodes()){
            bottomLevels.put(node, calculateBottomLevel(node));
        }
    }

    /**
     * helper methods for calculating bottom levels.
     * @param node
     * @return double       bottom level of selected node.
     */
    private double calculateBottomLevel(Node node){
        double result = getNodeWeightById(node.getId());
        // recursively calculate bottom level.
        for (Node children : getChildrenOfNode(node)) {
            result = Math.max(result, getNodeWeightById(node.getId()) + calculateBottomLevel(children));
        }
        return result;
    }

    /**
     * get the bottom level of the node
     *
     * @param node the input node
     * @return the bottom level value of this input node in graph
     */
    public double getBottomLevel(Node node) {
        return bottomLevels.get(node);
    }

    /**
     * Add an Edge to the digraph.
     *
     * @param sourceNodeId
     * @param targetNodeId
     * @param weight
     * @return `Edge`  being added to the digraph.
     */
    public Edge addEdge(String sourceNodeId, String targetNodeId, int weight) {
        String id = String.format("(%s;%s)", sourceNodeId, targetNodeId);
        super.addEdge(id, sourceNodeId, targetNodeId);
        Edge edge = super.getEdge(id);
        edge.setAttribute("Weight", weight);
        return edge;
    }


    /**
     * get the edge sourceNode -> destNode
     *
     * @param sourceNodeId String: the value of sourceNode
     * @param destNodeId   String:  the value of destNode
     * @return Edge:  the edge which is sourceNode -> destNode
     */
    public Edge getEdge(String sourceNodeId, String destNodeId) {
        return super.getEdge("(" + sourceNodeId + ";" + destNodeId + ")");
    }

    /**
     * get Weight of the edge
     *
     * @param sourceNodeId String: the value of sourceNode
     * @param destNodeId   String:  the value of destNode
     * @return Double  the weight of the edge which is sourceNode -> destNode
     */
    public Double getEdgeWeight(String sourceNodeId, String destNodeId) {
        return Double.parseDouble(getEdge(sourceNodeId, destNodeId).getAttribute("Weight").toString());
    }



}
