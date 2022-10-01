package io;

import models.NodeProperties;
import org.graphstream.graph.Node;
import org.graphstream.stream.file.FileSinkDOT;
import solution.Digraph;
import solution.PartialSolution;

import java.io.IOException;

public class OutputFormatter {

    public Digraph aStar(PartialSolution partialSolution, Digraph baseGraph) {
        partialSolution.getNodesPath().forEach(n -> {
            Node node = baseGraph.getNodeById(n.getId());
            NodeProperties properties = partialSolution.getNodeStates().get(n);
            node.setAttribute("Start", properties.getStartingTime());
            node.setAttribute("Processor", properties.getProcessorId());
        });
        writeToFile(baseGraph, "Solution.dot");
        return baseGraph;
    }

    private void writeToFile(Digraph digraph, String outputFile) {
        FileSinkDOT fs = new FileSinkDOT(true);
        try {
            fs.writeAll(digraph, outputFile);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

}
