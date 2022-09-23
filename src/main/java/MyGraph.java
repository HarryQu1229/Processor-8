import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import java.util.ArrayList;
import java.util.List;

public class MyGraph {

    Graph graph;

    public MyGraph(Graph graph) {
        this.graph = graph;
    }

    /**
     *  get the Node according to its value
     * @param node String   the value of the node
     * @return  Node  return matching Node
     */
    public Node getNodeByValue(String node) {
        return graph.getNode(node);
    }


    /**
     * get weight of specific node
     * @param node String  the value of node
     * @return  Double  the weight of this node
     */
    public Double getNodeWeight(String node) {
        return Double.parseDouble(graph.getNode(node).getAttribute("Weight").toString());
    }


    /**
     * get the edge sourceNode -> destNode
     * @param sourceNode  String: the value of sourceNode
     * @param destNode  String:  the value of destNode
     * @return  Edge:  the edge which is sourceNode -> destNode
     */
    public Edge getEdge(String sourceNode, String destNode) {
        return graph.getEdge("(" + sourceNode + ";" + destNode + ")");
    }

    //get Edge Weight

    /**
     * get Weight of the edge
     * @param sourceNode String: the value of sourceNode
     * @param destNode String:  the value of destNode
     * @return  Double  the weight of the edge which is sourceNode -> destNode
     */
    public Double getEdgeWeight(String sourceNode, String destNode) {
        return Double.parseDouble(getEdge(sourceNode, destNode).getAttribute("Weight").toString());
    }


    /**
     * get list of the node which in-degree is 0
     * @return return all start Node,which its in-degree is 0
     */
    public List<Node> getAllStartNode() {
        List<Node> res = new ArrayList<>();
        for (Node d : graph) {
            if (d.getInDegree() == 0) {
                res.add(d);
            }
        }
        return res;
    }


    /**
     *  get all children node of this input node
     * @param d the input node
     * @return  the list of the children node(input node d point to these nodes) of this input node d
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
     * @param d  the input node d
     * @return the bottom level value of this input node in graph
     */
    public double getBottomLevel(Node d) {
        double ans = getNodeWeight(d.getId());

        for(Node children:getAllChildrenNode(d)){
             ans = Math.max(ans,getNodeWeight(d.getId())+getBottomLevel(children));
        }
        return ans;
    }

    /**
     * get the critical path of the graph
     * @return the length of the critical path of graph
     */
    public double getCriticalPath() {
        double criticalPath = 0;
        for(Node d:getAllStartNode()){
            criticalPath = Math.max(criticalPath, getBottomLevel(d));
        }
        return criticalPath;
    }
}
