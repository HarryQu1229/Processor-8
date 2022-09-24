package solution;

import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleNode;

/**
 * Each node on a solution tree is a schedule string
 */
public class SolutionTree extends Digraph {

    public static SolutionTree getSolutionTree(Digraph digraph, int processCount) {

        digraph.addNode("Empty");
        digraph.getNode("Empty").setAttribute("Weight", 0);
        digraph.getAllStartNode().forEach(startingNode -> {
            digraph.addEdge("Empty", startingNode.getId(), 0);
        });

        SolutionTree solutionTree = new SolutionTree();

        Schedule schedule = new Schedule();
        schedule.addState(new State("Empty", 0));
        solutionTree.clear();
        solutionTree.addNode(schedule.toString());
        digraph.getAllChildrenNode(digraph.getNode("Empty")).forEach(child -> {
            appendChildNodes(solutionTree, child);
        });

        return solutionTree;
    }

    public static void appendChildNodes(SolutionTree solutionTree, Node node) {

    }

    public SolutionTree() {
        super("solution tree");
    }

}
