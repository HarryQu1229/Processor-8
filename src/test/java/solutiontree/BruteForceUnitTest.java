package solutiontree;

import io.InputLoader;
import org.graphstream.graph.Node;
import org.graphstream.stream.file.FileSource;
import org.graphstream.stream.file.FileSourceDOT;
import solution.BruteForce;
import solution.Digraph;

import org.junit.jupiter.api.*;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class BruteForceUnitTest {

    private Digraph loadGraph(String graphName) {
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

    private int countSolutions(BruteForce bruteForce) {
        int count = 0;
        for (Node node : bruteForce.getAllNodes()) {
            if (bruteForce.getOutDegreeOfNode(node) == 0) {
                count++;
            }
        }
        return count;
    }

    @Nested
    class Graph1UnitTesting {


        @BeforeEach
        public void loadInputGraph() {
            loadGraph("g1");
        }

        @Test
        public void graph1ProcessorAmount1() {
            InputLoader.setNumOfProcessors(1);
            BruteForce bruteForce = new BruteForce();
            bruteForce.build(bruteForce.getRoot());
            assertEquals(2, countSolutions(bruteForce));
        }

        @Test
        public void graph1ProcessorAmount2() {
            InputLoader.setNumOfProcessors(2);
            BruteForce bruteForce = new BruteForce();
            bruteForce.build(bruteForce.getRoot());
            assertEquals(32/2, countSolutions(bruteForce));
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
            InputLoader.setNumOfProcessors(1);
            BruteForce bruteForce = new BruteForce();
            bruteForce.build(bruteForce.getRoot());
            assertEquals(211588, countSolutions(bruteForce));
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
            InputLoader.setNumOfProcessors(1);
            BruteForce bruteForce = new BruteForce();
            bruteForce.build(bruteForce.getRoot());
            assertEquals(1, countSolutions(bruteForce));
        }

        @Test
        public void graph6ProcessorAmount2() {
            InputLoader.setNumOfProcessors(2);
            BruteForce bruteForce = new BruteForce();
            bruteForce.build(bruteForce.getRoot());
            assertEquals(8/2, countSolutions(bruteForce));
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
            InputLoader.setNumOfProcessors(1);
            BruteForce bruteForce = new BruteForce();
            bruteForce.build(bruteForce.getRoot());
            assertEquals(3, countSolutions(bruteForce));
        }

        @Test
        public void graph7ProcessorAmount2() {
            InputLoader.setNumOfProcessors(2);
            BruteForce bruteForce = new BruteForce();
            bruteForce.build(bruteForce.getRoot());
            assertEquals(192/2, countSolutions(bruteForce));
        }
    }
}
