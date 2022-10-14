package io;

import org.apache.commons.cli.*;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.stream.file.FileSource;
import org.graphstream.stream.file.FileSourceDOT;
import models.Digraph;

import java.io.IOException;

public class InputLoader {

    private static int numOfProcessors;

    public static void setNumOfProcessors(int inputNumOfProcessors) {
        numOfProcessors = inputNumOfProcessors;
    }

    public static int getNumOfProcessors() {
        return numOfProcessors;
    }

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
     * Load the graph using relative path
     *
     * @param path the relative path of the dot file
     * @return the graph that is loaded from the dot file
     */
    public static Digraph loadDotFileFromPath(String path) {
        Digraph graph = new Digraph("solution." + path);
        FileSource fileSource = new FileSourceDOT();
        fileSource.addSink(graph);
        try {
            fileSource.readAll(path);
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

    /**
     * Handling the command line arguments
     *
     * @param args all the command line arguments stored in a string array
     * @return a CommandLine instance that stores the required setup of command line arguments, or null if the argument
     * is invalid
     */
    public static CommandLine parseArgs(String[] args) {

        // initialise options
        Options options = new Options();

        Option optionP = new Option("p", true, "number of cores for execution in parallel");
        optionP.setRequired(false);

        Option optionV = new Option("v", false, "visualisation");
        optionV.setRequired(false);

        Option optionO = new Option("o", true, "output file name");
        optionO.setRequired(false);

        options.addOption(optionP);
        options.addOption(optionV);
        options.addOption(optionO);

        // if invalid arguments are passed, print help message and exit
        if (args.length < 2) {
            System.err.println("Invalid arguments");
            printHelp(options);
            return null;
        }

        CommandLineParser parser = new DefaultParser();
        try {
            return parser.parse(options, args);
        } catch (ParseException e) {
            printHelp(options);
            return null;
        }
    }

    /**
     * An util method that prints help message
     *
     * @param options the options to print help message for
     */
    public static void printHelp(Options options) {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("java -jar <jar file name>", "Options:", options, "");
    }
}
