import io.InputLoader;
import org.graphstream.graph.Graph;

public class Main {

    public static void main(String[] args){
        Graph graph = InputLoader.loadDotFile("g1");
        InputLoader.print(graph, true);
    }

}
