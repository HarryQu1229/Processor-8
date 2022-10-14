package io;

import models.NodeProperties;
import models.InputGraph;
import org.graphstream.graph.Node;
import org.graphstream.stream.file.FileSinkDOT;
import models.Digraph;
import algorithm.PartialSolution;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Scanner;
import java.io.IOException;

/**
 * The class that handles output the graph to a dot file
 */
public class OutputFormatter {

    /**
     * Append the necessary attributes to the graph and output the solution from A Star algorithm to a dot file
     * @param partialSolution the solution comes out from A Star algorithm
     * @param inputGraphName the name of the graph, which will be written to the dot file as well
     * @return the graph instance that is written to the dot file, this graph contains the information about how each
     * task is scheduled
     */
    public Digraph aStar(PartialSolution partialSolution, String inputGraphName) {
        Digraph baseGraph = InputGraph.get();
        partialSolution.getNodesPath().forEach(n -> {
            Node node = baseGraph.getNodeById(n.getId());
            NodeProperties properties = partialSolution.getNodeStates().get(n);
            node.setAttribute("Start", properties.getStartingTime());
            node.setAttribute("Processor", properties.getProcessorId());
            node.setAttribute("Weight", (int) baseGraph.getNodeWeightById(node.getId()));
        });
        writeToFile(baseGraph, inputGraphName + "-output.dot");
        return baseGraph;
    }

    /**
     * Write the graph to a dot file
     * @param digraph the graph to write, all needed attributes should have been added
     * @param outputFile the name of the file to write to
     */
    private void writeToFile(Digraph digraph, String outputFile)  {

        // use graph stream to write first
        FileSinkDOT fs = new FileSinkDOT(true);
        try {
            fs.writeAll(digraph, outputFile);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        // graph stream does not write the graph name, so use our own method to write the graph name
        StringBuilder sb = new StringBuilder();
        try (Scanner scanner = new Scanner(new File(outputFile))) {
            sb.append(String.format("digraph \"%s\" {\n", digraph.getId()));
            scanner.nextLine();
            while (scanner.hasNext()) {
                sb.append(scanner.nextLine()).append("\n");
            }
        } catch (FileNotFoundException ignored) {}

        try (FileWriter writer = new FileWriter(outputFile)) {
            writer.write(sb.toString());
        } catch (IOException ignored) {}
    }

}
