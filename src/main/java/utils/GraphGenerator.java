package utils;

import models.Digraph;
import org.graphstream.graph.Node;
import org.graphstream.stream.file.FileSinkDOT;

import java.io.IOException;
import java.util.Random;

public class GraphGenerator {

    /**
     * Generate a number of DAGs
     *
     * @param count the number of DAGs to generate
     */
    public static void generateRandomGraphs(int count) {
        for (int i = 0; i < count; i++) {
            generateDAG(10, 20, "g" + i, true);
        }
    }

    /**
     * Randomly generated a DAG and return
     * @param nodeCount number of nodes in the DAG
     * @param edgeCount number of edges in the DAG
     * @param name the number of DAG
     * @param saveToFile whether to save the DAG as a dot file or not, if true, the file will be stored in
     *                   src/main/resources/examples/random/[name].dot
     * @return the randomly generated DAG
     */
    public static Digraph generateDAG(int nodeCount, int edgeCount, String name, boolean saveToFile) {
        Digraph graph = new Digraph("dag");
        graph.setAttribute("Name", name);

        Random random = new Random();

        for (int i = 0; i < nodeCount; i++) {
            // add nodes with random weights
            graph.addNode(String.valueOf(i), random.nextInt(10));
        }

        boolean isTargetValid;

        // add edges with communication cost
        for (int i = 0; i < edgeCount; i++) {
            int source = random.nextInt(nodeCount);
            int target = random.nextInt(nodeCount);
            while (source == target && graph.getNodeById(String.valueOf(source)).hasEdgeBetween(String.valueOf(target))) {
                target = random.nextInt(nodeCount);
            }

            isTargetValid = true;

            // make sure that the target does not have an edge to the source and any of the parents of the source
            for (Node node : graph.getParentsOfNode(graph.getNodeById(String.valueOf(source)))) {
                if (graph.getNodeById(String.valueOf(target)).hasEdgeBetween(node.getId())) {
                    isTargetValid = false;
                    break;
                }
            }

            if (isTargetValid && source != target && !graph.getNodeById(String.valueOf(target)).hasEdgeBetween(String.valueOf(source)) && !graph.getParentsOfNode(graph.getNodeById(String.valueOf(source))).contains(graph.getNodeById(String.valueOf(target)))) {
                try {
                    graph.addEdge(String.valueOf(i), String.valueOf(source), String.valueOf(target), true);
                    graph.getEdge(String.valueOf(i)).setAttribute("Weight", (int) (Math.random() * 10));
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
        }

        if (saveToFile) {
            // write graph to file at src/main/resources/examples
            FileSinkDOT fs = new FileSinkDOT(true);
            try {
                // create directory if not exist
                java.nio.file.Files.createDirectories(java.nio.file.Paths.get("examples/random/" + name));

                fs.writeAll(graph, "examples/random/" + name + "/in.dot");
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }

        return graph;
    }

}
