package io;

import models.NodeProperties;
import models.TheGraph;
import org.graphstream.graph.Node;
import org.graphstream.stream.file.FileSinkDOT;
import models.Digraph;
import algorithm.PartialSolution;

import java.io.IOException;

public class OutputFormatter {

    public Digraph aStar(PartialSolution partialSolution, String inputGraphName) {
        Digraph baseGraph = TheGraph.get();
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

    private void writeToFile(Digraph digraph, String outputFile) {
        FileSinkDOT fs = new FileSinkDOT(true);
        try {
            fs.writeAll(digraph, outputFile);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

}
