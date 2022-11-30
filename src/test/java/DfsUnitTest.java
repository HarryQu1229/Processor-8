import algorithm.BranchAndBound;
import algorithm.SolutionTreeNode;
import io.InputLoader;
import org.graphstream.graph.Node;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import models.Digraph;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DfsUnitTest {

    @Nested
    class Graph8 {

        private Digraph digraph;

        @BeforeEach
        public void loadInputGraph() {
            digraph = InputLoader.loadDotFile("g8");
        }

        @Test
        public void TwoProcessors() {
            InputLoader.setNumOfProcessors(2);
            Map<Node, SolutionTreeNode> nodeInfo = new HashMap<>();
            int nodeSize = 0;
            int sum = 0;
            for (Node node: digraph) {
                int weight = (int) digraph.getNodeWeightById(node.getId());
                nodeInfo.put(
                        node,
                        new SolutionTreeNode(node.getId(), weight, node.getInDegree())
                );
                nodeSize++;
                sum += weight;
            }
            int ans = BranchAndBound.solve(nodeInfo, nodeSize, sum);
            assertEquals(28, ans);
        }

        @Test
        public void FourProcessors() {
            InputLoader.setNumOfProcessors(4);
            Map<Node, SolutionTreeNode> nodeInfo = new HashMap<>();
            int nodeSize = 0;
            int sum = 0;
            for (Node node: digraph) {
                int weight = (int) digraph.getNodeWeightById(node.getId());
                nodeInfo.put(
                        node,
                        new SolutionTreeNode(node.getId(), weight, node.getInDegree())
                );
                nodeSize++;
                sum += weight;
            }
            int ans = BranchAndBound.solve(nodeInfo, nodeSize, sum);
            assertEquals(22, ans);
        }

    }

    @Nested
    class Graph3 {

        private Digraph digraph;

        @BeforeEach
        public void loadInputGraph() {
            digraph = InputLoader.loadDotFile("g3");
        }

        @Test
        public void TwoProcessors() {
            InputLoader.setNumOfProcessors(2);
            Map<Node, SolutionTreeNode> nodeInfo = new HashMap<>();
            int nodeSize = 0;
            int sum = 0;
            for (Node node: digraph) {
                int weight = (int) digraph.getNodeWeightById(node.getId());
                nodeInfo.put(
                        node,
                        new SolutionTreeNode(node.getId(), weight, node.getInDegree())
                );
                nodeSize++;
                sum += weight;
            }
            int ans = BranchAndBound.solve(nodeInfo, nodeSize, sum);
            assertEquals(581, ans);
        }

        @Test
        public void FourProcessors() {
            InputLoader.setNumOfProcessors(4);
            Map<Node, SolutionTreeNode> nodeInfo = new HashMap<>();
            int nodeSize = 0;
            int sum = 0;
            for (Node node: digraph) {
                int weight = (int) digraph.getNodeWeightById(node.getId());
                nodeInfo.put(
                        node,
                        new SolutionTreeNode(node.getId(), weight, node.getInDegree())
                );
                nodeSize++;
                sum += weight;
            }
            int ans = BranchAndBound.solve(nodeInfo, nodeSize, sum);
            assertEquals(581, ans);
        }

    }

    @Nested
    class Graph9 {

        private Digraph digraph;

        @BeforeEach
        public void loadInputGraph() {
            digraph = InputLoader.loadDotFile("g9");
        }

        @Test
        public void TwoProcessors() {
            InputLoader.setNumOfProcessors(2);
            Map<Node, SolutionTreeNode> nodeInfo = new HashMap<>();
            int nodeSize = 0;
            int sum = 0;
            for (Node node: digraph) {
                int weight = (int) digraph.getNodeWeightById(node.getId());
                nodeInfo.put(
                        node,
                        new SolutionTreeNode(node.getId(), weight, node.getInDegree())
                );
                nodeSize++;
                sum += weight;
            }
            int ans = BranchAndBound.solve(nodeInfo, nodeSize, sum);
            assertEquals(55, ans);
        }

        @Test
        public void FourProcessors() {
            InputLoader.setNumOfProcessors(4);
            Map<Node, SolutionTreeNode> nodeInfo = new HashMap<>();
            int nodeSize = 0;
            int sum = 0;
            for (Node node: digraph) {
                int weight = (int) digraph.getNodeWeightById(node.getId());
                nodeInfo.put(
                        node,
                        new SolutionTreeNode(node.getId(), weight, node.getInDegree())
                );
                nodeSize++;
                sum += weight;
            }
            int ans = BranchAndBound.solve(nodeInfo, nodeSize, sum);
            assertEquals(55, ans);
        }

    }

    @Nested
    class Graph10 {

        private Digraph digraph;

        @BeforeEach
        public void loadInputGraph() {
            digraph = InputLoader.loadDotFile("g10");
        }

        @Test
        public void TwoProcessors() {
            InputLoader.setNumOfProcessors(2);
            Map<Node, SolutionTreeNode> nodeInfo = new HashMap<>();
            int nodeSize = 0;
            int sum = 0;
            for (Node node: digraph) {
                int weight = (int) digraph.getNodeWeightById(node.getId());
                nodeInfo.put(
                        node,
                        new SolutionTreeNode(node.getId(), weight, node.getInDegree())
                );
                nodeSize++;
                sum += weight;
            }
            int ans = BranchAndBound.solve(nodeInfo, nodeSize, sum);
            assertEquals(50, ans);
        }

        @Test
        public void FourProcessors() {
            InputLoader.setNumOfProcessors(4);
            Map<Node, SolutionTreeNode> nodeInfo = new HashMap<>();
            int nodeSize = 0;
            int sum = 0;
            for (Node node: digraph) {
                int weight = (int) digraph.getNodeWeightById(node.getId());
                nodeInfo.put(
                        node,
                        new SolutionTreeNode(node.getId(), weight, node.getInDegree())
                );
                nodeSize++;
                sum += weight;
            }
            int ans = BranchAndBound.solve(nodeInfo, nodeSize, sum);
            assertEquals(50, ans);
        }

    }

    @Nested
    class Graph11 {

        private Digraph digraph;

        @BeforeEach
        public void loadInputGraph() {
            digraph = InputLoader.loadDotFile("g11");
        }

        @Test
        public void TwoProcessors() {
            InputLoader.setNumOfProcessors(2);
            Map<Node, SolutionTreeNode> nodeInfo = new HashMap<>();
            int nodeSize = 0;
            int sum = 0;
            for (Node node: digraph) {
                int weight = (int) digraph.getNodeWeightById(node.getId());
                nodeInfo.put(
                        node,
                        new SolutionTreeNode(node.getId(), weight, node.getInDegree())
                );
                nodeSize++;
                sum += weight;
            }
            int ans = BranchAndBound.solve(nodeInfo, nodeSize, sum);
            assertEquals(350, ans);
        }

        @Test
        public void FourProcessors() {
            InputLoader.setNumOfProcessors(4);
            Map<Node, SolutionTreeNode> nodeInfo = new HashMap<>();
            int nodeSize = 0;
            int sum = 0;
            for (Node node: digraph) {
                int weight = (int) digraph.getNodeWeightById(node.getId());
                nodeInfo.put(
                        node,
                        new SolutionTreeNode(node.getId(), weight, node.getInDegree())
                );
                nodeSize++;
                sum += weight;
            }
            int ans = BranchAndBound.solve(nodeInfo, nodeSize, sum);
            assertEquals(227, ans);
        }

    }

}
