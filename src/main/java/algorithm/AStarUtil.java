package algorithm;

import io.InputLoader;
import models.TheGraph;
import org.graphstream.graph.Node;

import java.util.List;

/**
 * Helper class to help A star algorithm to pre-calculate an upper bound
 * Save memory/ computing time.
 */
public class AStarUtil {

    private int NUM_OF_SOLUTION_ROUTES = 7000;
    private int count;
    private PartialSolution bestPartialSolution;

    public AStarUtil(){
        DFSFindOneSolution(new PartialSolution());
        findMinCost(new PartialSolution());
    }

    public PartialSolution getBestPartialSolution() {
        return bestPartialSolution;
    }

    public void setBestPartialSolution(PartialSolution bestPartialSolution) {
        this.bestPartialSolution = bestPartialSolution;
    }

    /**
     * Find a Guess route of a single solution, following the fashion of the best guess (List algorithm),
     * save the finishing time to Minimum Guess Cost.
     * @param prev Previous iteration of Partial solution.
     * @return boolean  Placeholder for exiting the program.
     */
    public boolean DFSFindOneSolution(PartialSolution prev){

        // if we are at the leaf node of the solution tree.
        if(prev.getNodesPath().size() == TheGraph.get().getNodeCount()){
            // set the minimum guess cost as the result solution cost (Last finish Time among all processors)
            TheGraph.setMinimumGuessCost(prev.calculateEndScheduleTime());
            return true;
        }

        // get the next available nodes onroute.
        List<Node> availableNextNodes = prev.getAvailableNextNodes();

        // placeholders
        PartialSolution minPartialSolution = null;
        double minCostFunction = Integer.MAX_VALUE;

        // find the minimum Cost Function of all possible Partial Solutions of the current Partial Solution's available nodes
        // and on different Processors.
        for(Node node:availableNextNodes){
            for(int i = 1; i<= InputLoader.getNumOfProcessors(); i++){
                PartialSolution current  = new PartialSolution(prev,node,i);
                double v = current.calculateCostFunction(node, i);
                if(v<minCostFunction){
                    minCostFunction= v;
                    minPartialSolution = current;
                }
            }
        }

        // recursive call to find a full-solution.
        if(DFSFindOneSolution(minPartialSolution)){
            return true;
        }
        return false;
    }

    /**
     * Use the `DFSFindOneSolution` method as a benchmark, carry out limited number of DFS search to get
     * an estimate as close as possible to optimal solution cost. This will be used as upper-limit for carrying out
     * A star Algorithm later to reduce Memory/ increase search time.
     *
     * @param prev
     * @return
     */
    public boolean findMinCost(PartialSolution prev){

        // if we are at the leaf node of the solution tree.
        if(prev.getNodesPath().size() == TheGraph.get().getNodeCount()){
            // number of iterations so far.
            count++;
            // branch and bound operation
            if(prev.calculateEndScheduleTime() > TheGraph.getMinimumGuessCost()){
                return false;
            }
            // if we found a lower finish time of a full solution, set the new Minimum Guess Cost.
            TheGraph.setMinimumGuessCost(prev.calculateEndScheduleTime());
            setBestPartialSolution(prev);
            // after a number of attempts for full solutions, terminate the search.
            if(count >= NUM_OF_SOLUTION_ROUTES){
                return true;
            }
            return false;

        // if it's not a leaf node of the solution tree.
        }else{

            // branch and bound operation
            if(prev.getCostFunction() > TheGraph.getMinimumGuessCost()){
                return false;
            }
            // find the minimum Cost Function of all possible Partial Solutions of the current Partial Solution's available nodes
            // and on different Processors.
            List<Node> availableNextNodes = prev.getAvailableNextNodes();
            for(Node node:availableNextNodes){
                for(int i=1;i<=InputLoader.getNumOfProcessors();i++){
                    PartialSolution current = new PartialSolution(prev,node,i);
                    // recursively find next node of the solution tree.
                    if(findMinCost(current)){
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
