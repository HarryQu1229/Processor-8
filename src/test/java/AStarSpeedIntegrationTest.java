import algorithm.AStar;
import algorithm.PartialSolution;
import io.InputLoader;
import models.Digraph;
import org.junit.jupiter.api.*;
import utils.GraphGenerator;
import java.util.concurrent.TimeUnit ;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AStarSpeedIntegrationTest {

    AStar aStar;

    @BeforeAll
    public static void clearTestingFolder() throws IOException {
        File file = new File("examples/random");
        if (file.exists())
            FileUtils.deleteDirectory(file);
    }

    @Nested
    class fiveNodeTenEdge {

        @BeforeEach
        public void loadGraph() {
            String name = "testing-node5-edge10";
            GraphGenerator.generateDAG(5, 10, name, true);
            String path = "examples/random/" + name + "/in.dot";
            InputLoader.loadDotFileFromPath(path);
        }

        @Test
        @Timeout(value = 10, unit = TimeUnit.MINUTES)
        public void twoProcessor() {
            InputLoader.setNumOfProcessors(2);
            aStar = new AStar();
            aStar.buildTree(new PartialSolution());
        }

        @Test
        @Timeout(value = 10, unit = TimeUnit.MINUTES)
        public void fourProcessor() {
            InputLoader.setNumOfProcessors(4);
            aStar = new AStar();
            aStar.buildTree(new PartialSolution());
        }
    }

    @Nested
    class tenNodeFifteenEdge {

        @BeforeEach
        public void loadGraph() {
            String name = "testing-node10-edge15";
            GraphGenerator.generateDAG(10, 15, name, true);
            String path = "examples/random/" + name + "/in.dot";
            InputLoader.loadDotFileFromPath(path);
        }

        @Test
        @Timeout(value = 10, unit = TimeUnit.MINUTES)
        public void twoProcessor() {
            InputLoader.setNumOfProcessors(2);
            aStar = new AStar();
            aStar.buildTree(new PartialSolution());
        }

        @Test
        @Timeout(value = 10, unit = TimeUnit.MINUTES)
        public void fourProcessor() {
            InputLoader.setNumOfProcessors(4);
            aStar = new AStar();
            aStar.buildTree(new PartialSolution());
        }
    }

    @Nested
    class fifteenNode25Edge {

        @BeforeEach
        public void loadGraph() {
            String name = "testing-node15-edge25";
            GraphGenerator.generateDAG(15, 25, name, true);
            String path = "examples/random/" + name + "/in.dot";
            InputLoader.loadDotFileFromPath(path);
        }

        @Test
        @Timeout(value = 10, unit = TimeUnit.MINUTES)
        public void twoProcessor() {
            InputLoader.setNumOfProcessors(2);
            aStar = new AStar();
            aStar.buildTree(new PartialSolution());
        }

        @Test
        @Timeout(value = 10, unit = TimeUnit.MINUTES)
        public void fourProcessor() {
            InputLoader.setNumOfProcessors(4);
            aStar = new AStar();
            aStar.buildTree(new PartialSolution());
        }
    }

}
