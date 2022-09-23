import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import java.util.ArrayList;
import java.util.List;

public class MyGraph {

    Graph graph;

     public MyGraph(Graph graph){
         this.graph = graph;
     }

     public Node getNodeByValue(String node){
         return graph.getNode(node);
     }

     //get Node weight
     public Double getNodeWeight(String node){
         return Double.parseDouble(graph.getNode(node).getAttribute("Weight").toString());
     }

     //get the edge sourceNode -> destNode
     public Edge getEdge(String sourceNode, String destNode){
          return graph.getEdge("("+sourceNode+";"+destNode+")");
     }

     //get Edge Weight
    public Double getEdgeWeight(String sourceNode,String destNode){
         return Double.parseDouble(getEdge(sourceNode,destNode).getAttribute("Weight").toString());
    }

    //get list of the node which in-degree is 0
    public List<Node> getAllStartNode(){
         List<Node> res = new ArrayList<>();
         for(Node d:graph){
             if(d.getInDegree()==0){
                 res.add(d);
             }
         }
         return res;
    }

    //get all children node of this input node
    public List<Node> getAllChildrenNode(Node d){
         List<Node> res = new ArrayList<>();
         for(Edge e:d){
             if(e.getNode0() == d){
                 res.add(e.getNode1());
             }
         }
         return res;
    }

    //get all Parent Node of this input node
    public List<Node> getAllParentNode(Node d){
         List<Node> res = new ArrayList<>();
         for(Edge e:d){
             if(e.getNode1() == d){
                 res.add(e.getNode0());
             }
         }
         return res;
    }



}
