package algorithm;

import io.InputLoader;
import models.InputGraph;
import org.graphstream.graph.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * This class uses multi thread version of DFS to calculate the best solution
 * The class is not used in milestone 2 release, as this was implemented as a comparison of A star
 */
public class ParallelDFS {


    private int numOfThread;
    private ExecutorService executorService;
    private PartialSolution bestPartialSolution = new PartialSolution();

    public PartialSolution getBestPartialSolution() {
        return bestPartialSolution;
    }

    public void setBestPartialSolution(PartialSolution bestPartialSolution) {
        this.bestPartialSolution = bestPartialSolution;
    }

    public ParallelDFS() {
    }

    public ParallelDFS(int numOfThread) {
        this.numOfThread = numOfThread;
        DFSFindOneSolution(new PartialSolution());
    }

    public boolean DFSFindOneSolution(PartialSolution prev) {

        // if we are at the leaf node of the solution tree.
        if (prev.getNodesPath().size() == InputGraph.get().getNodeCount()) {
            // set the minimum guess cost as the result solution cost (Last finish Time among all processors)
            InputGraph.setMinimumGuessCost(prev.calculateEndScheduleTime());
            bestPartialSolution = prev;
            return true;
        }

        // get the next available nodes onroute.
        List<Node> availableNextNodes = prev.getAvailableNextNodes();

        // placeholders
        PartialSolution minPartialSolution = null;
        double minCostFunction = Integer.MAX_VALUE;

        // find the minimum Cost Function of all possible Partial Solutions of the current Partial Solution's available nodes
        // and on different Processors.
        for (Node node : availableNextNodes) {
            for (int i = 1; i <= InputLoader.getNumOfProcessors(); i++) {
                PartialSolution current = new PartialSolution(prev, node, i);
                double v = current.calculateCostFunction(node, i);
                if (v < minCostFunction) {
                    minCostFunction = v;
                    minPartialSolution = current;
                }
            }
        }

        // recursive call to find a full-solution.
        return DFSFindOneSolution(minPartialSolution);
    }

    public void findBestPartial() {

        PartialSolution root = new PartialSolution();
        List<PartialSolution> list = new ArrayList<>();

        list.add(root);

        while (list.size() <= numOfThread / 2) {
            List<PartialSolution> temp = new ArrayList<>();
            for (PartialSolution p : list) {
                temp.addAll(p.getAllNextPartialSolution());
            }

            list.clear();
            list.addAll(temp);
        }

        List<Callable<PartialSolution>> tasks = new ArrayList<>();

        for (PartialSolution p : list) {
            tasks.add(() -> DFS(p));
        }

        try {
            executorService = Executors.newWorkStealingPool(numOfThread);
            List<Future<PartialSolution>> futures = executorService.invokeAll(tasks);
            for (Future<PartialSolution> f : futures) {
                f.get();
            }
            executorService.shutdown();
        } catch (Exception e) {

        }

    }

    public PartialSolution DFS(PartialSolution root) {
        if (root.getNodesPath().size() == InputGraph.get().getNodeCount()) {
            if (root.calculateEndScheduleTime() >= InputGraph.getMinimumGuessCost()) {
                return getBestPartialSolution();
            }

            synchronized (ParallelDFS.class) {
                InputGraph.setMinimumGuessCost(Math.min(InputGraph.getMinimumGuessCost(), root.calculateEndScheduleTime()));
                bestPartialSolution = root;
            }
            return bestPartialSolution;
        }

        if (root.getCostFunction() > InputGraph.getMinimumGuessCost()) {
            return bestPartialSolution;
        }

        List<PartialSolution> allNextPartialSolution = root.getAllNextPartialSolution();
        for (PartialSolution p : allNextPartialSolution) {
            DFS(p);
        }

        return bestPartialSolution;
    }
}


