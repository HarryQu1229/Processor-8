package solutiontree;

import org.graphstream.graph.Node;
import org.graphstream.stream.file.FileSource;
import org.graphstream.stream.file.FileSourceDOT;
import solution.Digraph;
import solution.SolutionTree;

import org.junit.jupiter.api.*;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class SolutionTreeUnitTest {

    private Digraph loadGraph(String graphName) {
        String relativePath = "examples/" + graphName + "/in.dot";
        Digraph graph = new Digraph("solution." + graphName);
        FileSource fileSource = new FileSourceDOT();
        fileSource.addSink(graph);
        try {
            fileSource.readAll(relativePath);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return graph;
    }

    private int countSolutions(SolutionTree solutionTree) {
        int count = 0;
        for (Node node : solutionTree.getAllNodes()) {
            if (solutionTree.getOutDegree(node) == 0) {
                count++;
            }
        }
        return count;
    }

    @Nested
    class Graph1UnitTesting {

        Digraph inputGraph;

        @BeforeEach
        public void loadInputGraph() {
            inputGraph = loadGraph("g1");
        }

        @Test
        public void graph1ProcessorAmount1() {
            SolutionTree solutionTree = new SolutionTree(inputGraph,1);
            solutionTree.build(solutionTree.getRoot());
            assertEquals(2, countSolutions(solutionTree));
        }

        @Test
        public void graph1ProcessorAmount2() {
            SolutionTree solutionTree = new SolutionTree(inputGraph,2);
            solutionTree.build(solutionTree.getRoot());
            assertEquals(32, countSolutions(solutionTree));
        }
    }

    /**
     * This test suite is based on the assumption that the implementation of solution tree on 26/09/2022 was correct, (
     * commit: 5ac66763cd632cdcb7968f5ffe59bbedc4e5c3f6). The actual answer was not calculated by hand.
     *
     * This test should only be used to compare the new implementation with the implementation on 26/09/2022
     */
    @Nested
    class Graph2UnitTesting {

        Digraph inputGraph;

        @BeforeEach
        public void loadInputGraph() {
            inputGraph = loadGraph("g2");
        }

        @Test
        public void graph2ProcessorAmount1() {
            SolutionTree solutionTree = new SolutionTree(inputGraph,1);
            solutionTree.build(solutionTree.getRoot());
            assertEquals(211588, countSolutions(solutionTree));
        }

    }

    @Nested
    class Graph6UnitTesting {

        Digraph inputGraph;

        @BeforeEach
        public void loadInputGraph() {
            inputGraph = loadGraph("g6");
        }

        @Test
        public void graph6ProcessorAmount1() {
            SolutionTree solutionTree = new SolutionTree(inputGraph,1);
            solutionTree.build(solutionTree.getRoot());
            assertEquals(1, countSolutions(solutionTree));
        }

        @Test
        public void graph6ProcessorAmount2() {
            SolutionTree solutionTree = new SolutionTree(inputGraph,2);
            solutionTree.build(solutionTree.getRoot());
            assertEquals(8, countSolutions(solutionTree));
        }
    }

    @Nested
    class Graph7UnitTesting {

        Digraph inputGraph;

        @BeforeEach
        public void loadInputGraph() {
            inputGraph = loadGraph("g7");
        }

        @Test
        public void graph7ProcessorAmount1() {
            SolutionTree solutionTree = new SolutionTree(inputGraph,1);
            solutionTree.build(solutionTree.getRoot());
            assertEquals(3, countSolutions(solutionTree));
        }

        @Test
        public void graph7ProcessorAmount2() {
            SolutionTree solutionTree = new SolutionTree(inputGraph,2);
            solutionTree.build(solutionTree.getRoot());
            assertEquals(192, countSolutions(solutionTree));
        }
    }
}
