package utils;

import models.Digraph;
import org.graphstream.graph.Node;
import org.graphstream.stream.file.FileSinkDOT;

import java.io.IOException;
import java.util.Random;

public class GraphGenerator {

        // generate n number of random DAG
        public static void generateRandomGraphs(int n) {
            for (int i = 0; i < n; i++) {
                generateDAG(10, 20, "g"+i);
            }
        }

        // generate a directed acyclic graph with specific with n nodes and m edges
        public static void generateDAG(int n, int m, String name) {
            Digraph graph = new Digraph("dag");
            graph.setAttribute("Name", name);

            Random random = new Random();

            for (int i = 0; i < n; i++) {
                // add nodes with random weights
                graph.addNode(String.valueOf(i), random.nextInt(10));
            }

            boolean isTargetValid;

            // add edges with communication cost
            for (int i = 0; i < m; i++) {
                int source = random.nextInt(n);
                int target = random.nextInt(n);
                while (source == target && graph.getNodeById(String.valueOf(source)).hasEdgeBetween(String.valueOf(target))) {
                    target = random.nextInt(n);
                }

                isTargetValid = true;

                // make sure that the target does not have an edge to the source and any of the parents of the source
                for(Node node : graph.getParentsOfNode(graph.getNodeById(String.valueOf(source)))){
                    if(graph.getNodeById(String.valueOf(target)).hasEdgeBetween(node.getId())){
                        isTargetValid = false;
                        break;
                    }
                }

                if(isTargetValid && source != target && !graph.getNodeById(String.valueOf(target)).hasEdgeBetween(String.valueOf(source)) && !graph.getParentsOfNode(graph.getNodeById(String.valueOf(source))).contains(graph.getNodeById(String.valueOf(target)))) {
                    try {
                        graph.addEdge(String.valueOf(i), String.valueOf(source), String.valueOf(target), true);
                        graph.getEdge(String.valueOf(i)).setAttribute("Weight", (int) (Math.random() * 10));
                    }catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                }
            }


            // write graph to file at src/main/resources/examples
            FileSinkDOT fs = new FileSinkDOT(true);
            try {
                // create directory if not exist
                java.nio.file.Files.createDirectories(java.nio.file.Paths.get("examples/random/"+name));

                fs.writeAll(graph, "examples/random/" + name + "/" + name + ".dot");
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }

}
