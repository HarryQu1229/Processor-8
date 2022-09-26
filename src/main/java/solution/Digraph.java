package solution;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

public class Digraph extends SingleGraph {

    public Digraph(String id) {
        super(id);
    }

    /**
     * get the Node according to its value
     *
     * @param node String   the value of the node
     * @return Node  return matching Node
     */
    public Node getNodeByValue(String node) {
        return super.getNode(node);
    }

    /**
     * get weight of specific node
     *
     * @param node String  the value of node
     * @return Double  the weight of this node
     */
    public Double getNodeWeight(String node) {
        return Double.parseDouble(super.getNode(node).getAttribute("Weight").toString());
    }

    public Edge addEdge(String sourceNode, String targetNode, int weight) {
        String id = String.format("(%s;%s)", sourceNode, targetNode);
        super.addEdge(id, sourceNode, targetNode);
        Edge edge = super.getEdge(id);
        edge.setAttribute("Weight", weight);
        return edge;
    }

    public Node addNode(String nodeId, int weight) {
        super.addNode(nodeId);
        Node node = super.getNode(nodeId);
        node.setAttribute("Weight", weight);
        return node;
    }

    /**
     * get the edge sourceNode -> destNode
     *
     * @param sourceNode String: the value of sourceNode
     * @param destNode   String:  the value of destNode
     * @return Edge:  the edge which is sourceNode -> destNode
     */
    public Edge getEdge(String sourceNode, String destNode) {
        return super.getEdge("(" + sourceNode + ";" + destNode + ")");
    }

    /**
     * get Weight of the edge
     *
     * @param sourceNode String: the value of sourceNode
     * @param destNode   String:  the value of destNode
     * @return Double  the weight of the edge which is sourceNode -> destNode
     */
    public Double getEdgeWeight(String sourceNode, String destNode) {
        return Double.parseDouble(getEdge(sourceNode, destNode).getAttribute("Weight").toString());
    }

    /**
     * get list of the node which in-degree is 0
     *
     * @return return all start Node,which its in-degree is 0
     */
    public List<Node> getAllStartNode() {
        List<Node> res = new ArrayList<>();
        Iterator<Node> nodeIterator = super.iterator();
        while (nodeIterator.hasNext()) {
            Node node = nodeIterator.next();
            if (node.getInDegree() == 0) {
                res.add(node);
            }
        }
        return res;
    }

    /**
     * get all children node of this input node
     *
     * @param d the input node
     * @return the list of the children node(input node d point to these nodes) of this input node d
     */
    public List<Node> getAllChildrenNode(Node d) {
        List<Node> res = new ArrayList<>();
        for (Edge e : d) {
            if (e.getNode0() == d) {
                res.add(e.getNode1());
            }
        }
        return res;
    }

    /**
     * get all Parent Node of this input node
     *
     * @param d the input node
     * @return the list of the parent node(these nodes will point to input node d)of this input node d
     */
    public List<Node> getAllParentNode(Node d) {
        List<Node> res = new ArrayList<>();
        for (Edge e : d) {
            if (e.getNode1() == d) {
                res.add(e.getNode0());
            }
        }
        return res;
    }

    /**
     * get the bottom level of the node
     *
     * @param node the input node
     * @return the bottom level value of this input node in graph
     */
    public double getBottomLevel(Node node) {
        double ans = getNodeWeight(node.getId());

        for (Node children : getAllChildrenNode(node)) {
            ans = Math.max(ans, getNodeWeight(node.getId()) + getBottomLevel(children));
        }
        return ans;
    }

    /**
     * get the critical path of the graph
     *
     * @return the length of the critical path of graph
     */
    public double getCriticalPath() {
        double criticalPath = 0;
        for (Node d : getAllStartNode()) {
            criticalPath = Math.max(criticalPath, getBottomLevel(d));
        }
        return criticalPath;
    }

    /**
     * get all nodes from a graph
     *
     * @return list of all nodes from the graph
     */
    public List<Node> getAllNodes() {
        List<Node> res = new ArrayList<>();
        Iterator<Node> nodeIterator = super.iterator();
        while (nodeIterator.hasNext()) {
            res.add(nodeIterator.next());
        }
        return res;
    }

    /**
     * Get the in degree of a node in the graph.
     * Graph stream api does provide getIndegree method but this method is unstable and does not meet our needs
     *
     * @param node the node that we are getting the in-degree of
     */
    public int getInDegree(Node node) {
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
    public int getOutDegree(Node node) {
        int count = 0;
        for (Edge edge : node) {
            if (edge.getNode0().equals(node)) count++;
        }
        return count;
    }
}
