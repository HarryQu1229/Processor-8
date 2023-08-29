package algorithm;

import models.InputGraph;

import java.util.List;
import java.util.PriorityQueue;
import java.util.concurrent.*;

/**
 * The multi thread version of A star algorithm
 * This class is made to be a derived class of AStar
 */
public class ParallelAStar extends AStar {

    ExecutorService executorService;
    CompletionService<PartialSolution> completionService;
    private int numOfThread;

    public ParallelAStar(int numOfThread) {
        super();
        this.numOfThread = numOfThread;
    }

    /**
     * Use findBestPartialSolution method to find the best solution and return
     * This method also initialise required fields and handling closing instance
     *
     * @return the optimal solution returned by findBestPartialSolution method
     */
    public PartialSolution build() {
        //create the thread pool
        executorService = Executors.newWorkStealingPool(numOfThread);
        completionService = new ExecutorCompletionService<>(executorService);
        PartialSolution res = findBestPartialSolution();
        executorService.shutdown();
        return res;
    }

    /**
     * using parallel method to find the best PartialSolution
     *
     * @return a partial solution instance that represents the best scheduling
     */
    public PartialSolution findBestPartialSolution() {

        PriorityQueue<PartialSolution> solutionQueue = new PriorityQueue<>((x1, x2) -> {
            // compare the cost function between 2 solutions.
            return (int) (x1.getCostFunction() - x2.getCostFunction());
        });

        solutionQueue.offer(new PartialSolution());

        while (solutionQueue.size() < numOfThread * 100) {
            PartialSolution prev = solutionQueue.poll();

            //get all next partialSolution of current PartialSolution
            List<PartialSolution> nextAllPartialSolution = prev.getAllNextPartialSolution();

            for (PartialSolution p : nextAllPartialSolution) {
                if (p.getNodesPath().size() == InputGraph.get().getNodeCount()) {
                    return p;
                }
            }

            solutionQueue.addAll(nextAllPartialSolution);
        }

        int size = solutionQueue.size();

        while (!solutionQueue.isEmpty()) {
            PartialSolution prev = solutionQueue.poll();
            completionService.submit(() -> buildTree(prev));
        }

        try {
            for (int i = 0; i < size; i++) {

                // get the results of the first-finished thread
                // if the result is not null, then the result must be an optimal solution
                PartialSolution partialSolution = completionService.take().get();
                if (partialSolution != null) {
                    return partialSolution;
                }
            }
        } catch (Exception ignored) {}
        return null;
    }
}
