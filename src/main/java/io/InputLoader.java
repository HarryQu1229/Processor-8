package io;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.stream.file.FileSource;
import org.graphstream.stream.file.FileSourceDOT;
import solution.Digraph;

import java.io.IOException;

public class InputLoader {


    /**
     * To load graph from .dot file using graph stream api
     *
     * @param graphName The name of the graph, this name must be a folder name in the "examples" folder at root level,
     *                  the file that the loader will load the data from is "examples/<graphName>/in.dot
     * @return a Graph instance that represents the data read from input file
     */
    public static Digraph loadDotFile(String graphName) {
        String relativePath = "examples/" + graphName + "/in.dot";
        Digraph graph = new Digraph("solution." + graphName);
        FileSource fileSource = new FileSourceDOT();
        fileSource.addSink(graph);
        try {
            fileSource.readAll(relativePath);
            graph.initialize();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return graph;
    }

    /**
     * A util method that prints the graph
     *
     * @param graph            The graph object to print
     * @param showEnteringEdge if true, then for each node, edges coming into the node will also be printed.
     */
    public static void print(Graph graph, boolean showEnteringEdge) {
        for (Node node : graph) {
            System.out.printf(
                    "Node <%s>, weight = %d\n",
                    node,
                    (int) Double.parseDouble(node.getAttribute("Weight").toString())
            );
            for (Edge edge : node) {
                if (edge.getNode0().equals(node) || showEnteringEdge) {
                    System.out.printf(
                            "    Edge %s -> %s, weight = %s\n",
                            edge.getNode0().toString(),
                            edge.getNode1().toString(),
                            (int) Double.parseDouble(edge.getAttribute("Weight").toString())
                    );
                }
            }
        }
    }
}
