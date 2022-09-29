package solution;

import org.graphstream.graph.Node;

import java.io.PrintWriter;
import java.util.*;

/**
 * Bruteforce solution to build a solution tree, Each node on a solution tree is represented as a schedule string
 */
public class SolutionTree extends Digraph {

    private Digraph graph;
    private int numOfProcessor;
    private PartialSolution root;


    public PartialSolution getRoot() {
        return root;
    }


    /**
     * Constructor of Solution Tree.
     * @param graph diGraph of the Solution Tree.
     * @param numOfProcessor The number of processors that Tasks to be scheduled on.
     */
    public SolutionTree(Digraph graph, int numOfProcessor) {
        // create new digraph
        super("SolutionTree");
        this.graph = graph;
        this.numOfProcessor = numOfProcessor;
        //create the root node of solution tree(empty)
        root = new PartialSolution(graph);
        // initialise the root node of the solution tree graph.
        addNode(root.getInfo(), 0);
    }

    /**
     * method to print task schedule string of the solution tree for testing purpose.
     */
    public void print() {

        try(PrintWriter out = new PrintWriter("texts.txt")) {
            Queue<String> queue = new LinkedList<>();
            int count = 0;

            queue.add(root.getInfo());

            while (!queue.isEmpty()) {
                String poll = queue.poll();
                out.println(poll);
                count++;
                List<Node> children = getChildrenOfNode(getNodeById(poll));
                for (int i = 0; i < children.size(); i++) {
                    queue.add(children.get(i).getId());
                }
            }

            out.println(count);
            out.println(minStartingTime);
        }catch (Exception e){

        }

    }


    // build the solutionTree From this PartialSolution
    int minStartingTime = Integer.MAX_VALUE;

    /**
     * method to recursively build the solution tree
     * @param prevPartialSolution previous PartialSolution immediately prior to the current PartialSolution
     */
    public void build(PartialSolution prevPartialSolution) {


            if (prevPartialSolution.getNodesPath().size() == graph.getNodeCount()) {
                int FinalFinishTime  = 0;
                List<Node> nodes = prevPartialSolution.getNodesPath();
                for(int i=0;i<nodes.size();i++){
                    FinalFinishTime = (int) Math.max(FinalFinishTime,prevPartialSolution.getNodeStates().get(nodes.get(i)).getStartingTime()+graph.getNodeWeightById(nodes.get(i).getId()));
                }

                minStartingTime = Math.min(minStartingTime,FinalFinishTime);
                return;
            }

            List<Node> availableNextNodes = prevPartialSolution.getAvailableNextNodes();
            // for every available next Tasks.
            for (Node availableNextNode : availableNextNodes) {
                // for every processor
                if(prevPartialSolution.getNodesPath().size()==0){
                    PartialSolution currentPartialSolution = new PartialSolution(prevPartialSolution, graph,
                            availableNextNode, 1,numOfProcessor);
                    appendChildNodes(prevPartialSolution, currentPartialSolution);
                    build(currentPartialSolution);
                }else{
                    for (int j = 1; j <= numOfProcessor; j++) {
                        // create a new PartialSolution and recursively expand the next level of the solution graph
                        PartialSolution currentPartialSolution = new PartialSolution(prevPartialSolution, graph,
                                availableNextNode, j,numOfProcessor);

                        appendChildNodes(prevPartialSolution, currentPartialSolution);
                        build(currentPartialSolution);
                    }

                }
            }
    }

    /**
     * helper methods to append the children to the current solution tree digraph
     * @param prev previous Partial solution
     * @param current current Partial solution
     */
    private void appendChildNodes(PartialSolution prev, PartialSolution current) {
        addNode(current.getInfo(), 0);
        addEdge(prev.getInfo(), current.getInfo(), 0);
    }

}
