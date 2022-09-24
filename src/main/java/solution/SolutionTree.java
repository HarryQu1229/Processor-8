package solution;

import io.InputLoader;
import org.graphstream.graph.Node;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Each node on a solution tree is a schedule string
 */
public class SolutionTree extends Digraph {

    public static SolutionTree getSolutionTree(Digraph digraph, int processCount) {

        List<Node> startingNodes = digraph.getAllStartNode();
        digraph.addNode("Empty");
        digraph.getNode("Empty").setAttribute("Weight", 0);
        startingNodes.forEach(startingNode -> {
            digraph.addEdge("Empty", startingNode.getId(), 0);
        });

        SolutionTree solutionTree = new SolutionTree();
        Schedule schedule = new Schedule(digraph);
        schedule.addState(new State("Empty", 0));
        solutionTree.clear();
        solutionTree.addNode(schedule.toString(), 0);
        appendChildNodes(solutionTree, digraph, digraph.getNode("Empty"), schedule);
        return solutionTree;
    }

    public static void appendChildNodes(SolutionTree solutionTree, final Digraph digraph, Node node, Schedule currentSchedule) {
        List<Node> waitingTasks = currentSchedule.getWaitingTasks().stream().map(digraph::getNodeByValue).collect(Collectors.toList());
        Schedule schedule = new Schedule(digraph, currentSchedule.toString());
        waitingTasks.forEach(childNode -> {
            List<Node> prerequisites = digraph.getAllParentNode(childNode);
            boolean canExecute = prerequisites.stream().allMatch(
                    x -> schedule.hasExecuted(x.getId())
            );
            if (canExecute) {
                Schedule newSchedule = new Schedule(digraph, schedule);
                newSchedule.addState(new State(childNode.getId(), 0));
                solutionTree.addNode(newSchedule.toString(), 0);
                solutionTree.addEdge(schedule.toString(), newSchedule.toString(), 0);
                appendChildNodes(solutionTree, digraph, childNode, newSchedule);
            }
        });
    }

    public SolutionTree() {
        super("solution tree");
    }

}
