import io.InputLoader;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

public class Main {

    public static void main(String[] args){
        Graph graph = InputLoader.loadDotFile("g1");
//        InputLoader.print(graph, true);
        MyGraph myGraph = new MyGraph(graph);



    }

}
