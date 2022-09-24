package solution;

import org.graphstream.graph.Node;

/**
 * Each node on a solution tree is a schedule string
 */
public class SolutionTree extends Digraph {

    public static SolutionTree getSolutionTree() {
        SolutionTree solutionTree = new SolutionTree();



        return solutionTree;
    }

    public static void appendChildNodes(SolutionTree solutionTree, Node node) {

    }

    public SolutionTree() {
        super("solution tree");
    }

}
