import io.InputLoader;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import java.util.Iterator;

public class Main {

    public static void main(String[] args){
        Graph graph = InputLoader.loadDotFile("g5");
//        InputLoader.print(graph, true);
        MyGraph myGraph = new MyGraph(graph);

        System.out.println(myGraph.getBottomLevel(myGraph.getNodeByValue("h")));


//        System.out.println(myGraph.getCriticalPath());


    }

}
