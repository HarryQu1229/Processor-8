package solution;

import io.InputLoader;
import org.graphstream.graph.Node;

import java.util.List;

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
            appendChildNodes(solutionTree, digraph, child, schedule);
        });

        return solutionTree;
    }

    public static void appendChildNodes(SolutionTree solutionTree, final Digraph digraph, Node node, Schedule currentSchedule) {
        List<Node> childNodes = digraph.getAllChildrenNode(node);
        Schedule schedule = new Schedule(currentSchedule.toString());
        childNodes.forEach(childNode -> {
            List<Node> prerequisites = digraph.getAllParentNode(childNode);
            boolean canExecute = prerequisites.stream().allMatch(
                    x -> schedule.hasExecuted(x.getId())
            ) && !currentSchedule.hasExecuted(node.getId());
            if (canExecute) {
                Schedule newSchedule = new Schedule(schedule);
                newSchedule.addState(new State(childNode.getId(), 0));
                solutionTree.addNode(newSchedule.toString());
                solutionTree.addEdge(schedule.toString(), newSchedule.toString(), 0);
                appendChildNodes(solutionTree, digraph, childNode, newSchedule);
            }
        });
    }

    public SolutionTree() {
        super("solution tree");
    }

}
