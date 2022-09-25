package solution;

import org.graphstream.graph.Node;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Each node on a solution tree is a schedule string
 */
public class SolutionTree extends Digraph {

    private  Digraph digraph;
    private  int numOfProcessor;
    private  PartialSolution root;


    public PartialSolution getRoot(){
        return root;
    }


    public SolutionTree(Digraph digraph,int numOfProcessor){
        super("SolutionTree");
        this.digraph = digraph;
        this.numOfProcessor = numOfProcessor;
        //create the root node of solution tree(empty)
        root = new PartialSolution(digraph);
        addNode(root.getInfo(),0);
    }

    public void printSolutionTree() {

        Queue<String> queue = new LinkedList<>();
        int count =0;

        queue.add(root.getInfo());

        while(!queue.isEmpty()){
            String poll = queue.poll();
            System.out.println(poll);
            count++;


            List<Node> children = getAllChildrenNode(getNodeByValue(poll));

            for(int i=0;i<children.size();i++){
                queue.add(children.get(i).getId());
            }
        }

        System.out.println(count);
        System.out.println(small);
    }


    // build the solutionTree From this PartialSolution
    int small = Integer.MAX_VALUE;
    public void buildTree(PartialSolution prevPartialSolution){
        if (prevPartialSolution.getNodesPath().size() == digraph.getNodeCount()) {
            Node node =  prevPartialSolution.getNodesPath().get(prevPartialSolution.getNodesPath().size()-1);
            small = (int)Math.min(small,prevPartialSolution.getNodeStates().get(node).getStartingTime());
            return;
        }

        List<Node> availableNextNodes = prevPartialSolution.getAvailableNextNodes();


        for (int i = 0; i < availableNextNodes.size(); i++) {
            for (int j = 1; j <= numOfProcessor; j++) {
                PartialSolution currentPartialSolution = new PartialSolution(prevPartialSolution,digraph,
                        availableNextNodes.get(i),j);
                appendChildNodes(prevPartialSolution,currentPartialSolution);
                buildTree(currentPartialSolution);
            }
        }
    }

    public void appendChildNodes(PartialSolution prev, PartialSolution current) {
        addNode(current.getInfo(),0);
        this.addEdge(prev.getInfo(),current.getInfo(),0);
    }

}
