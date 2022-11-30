package algorithm;

import io.InputLoader;
import models.NodeProperties;
import models.InputGraph;
import org.graphstream.graph.Node;

import java.util.*;

public class PartialSolution {

    private LinkedHashMap<Node, NodeProperties> nodeStates;
    private List<Node> nodesPath;
    private double costFunction;
    private int idleTime;

    /**
     * Constructor for any subsequent partial solution on the solution tree except the root of the solution tree.
     *
     * @param prevPartial previous PartialSolution immediately prior to the current PartialSolution
     * @param currentNode The current Task(node) that is going to be scheduled
     * @param processorId Which Processor is the current Task(node) is going to be scheduled on
     */
    public PartialSolution(PartialSolution prevPartial, Node currentNode, int processorId) {

        initializeFromPrevPartialSolution(prevPartial);
        scheduleTask(currentNode, processorId);
        updateCurrentPartialSolutionStatus(currentNode, processorId);
        //calculate Cost Function for this PartialSolution
        costFunction = calculateCostFunction(currentNode, processorId);
    }

    /**
     * Constructor for the root of the solution tree.
     */
    public PartialSolution() {
        initializeRootPartialSolution();
    }

    /**
     * @return `List<Node>` a list of next available Tasks(nodes) that can be scheduled immediately.
     */
    public List<Node> getAvailableNextNodes() {
        List<Node> availableNextNodes = new ArrayList<>();
        for (Node node : nodeStates.keySet()) {
            if (nodeStates.get(node).getInDegree() == 0) {
                availableNextNodes.add(node);
            }
        }
        return availableNextNodes;
    }

    public List<Node> getNodesPath() {
        return nodesPath;
    }

    public int getIdleTime() {
        return idleTime;
    }

    public LinkedHashMap<Node, NodeProperties> getNodeStates() {
        return nodeStates;
    }

    public double getCostFunction() {
        return costFunction;
    }


    /**
     * @return a String representation of the current partial solution status on the solution tree.
     * i.e.  "(nodeId,processorId,startTime);"
     */
    public String getInfo() {

        StringBuffer sb = new StringBuffer();
        for (Node node : nodesPath) {
            sb.append("(").append(node.getId()).append(",");
            sb.append(nodeStates.get(node).getProcessorId()).append(",");
            sb.append(nodeStates.get(node).getStartingTime()).append(")");
            sb.append(";");
        }
        sb.append(" Finishing Time: ");
        sb.append(calculateEndScheduleTime());

        return sb.toString();
    }

    /**
     * initialize fields from the previous partial solution.
     *
     * @param prevPartial previous PartialSolution immediately prior to the current PartialSolution
     */
    private void initializeFromPrevPartialSolution(PartialSolution prevPartial) {
        // copy nodePath from the previous solution
        this.nodesPath = new ArrayList<>();
        nodesPath.addAll(prevPartial.getNodesPath());

        // copy nodeStates from the previous solution
        this.nodeStates = new LinkedHashMap<>();
        for (Node node : prevPartial.getNodeStates().keySet()) {
            NodeProperties nodeProperties = new NodeProperties();

            NodeProperties prevNodeProperties = prevPartial.getNodeStates().get(node);
            nodeProperties.setInDegree(prevNodeProperties.getInDegree());
            nodeProperties.setProcessorId(prevNodeProperties.getProcessorId());
            nodeProperties.setStartingTime(prevNodeProperties.getStartingTime());

            nodeStates.put(node, nodeProperties);
        }

        idleTime = prevPartial.getIdleTime();
    }

    /**
     * initialize fields for root partial solution of the solution tree.
     */
    private void initializeRootPartialSolution() {
        this.nodesPath = new ArrayList<>();
        this.nodeStates = new LinkedHashMap<>();
        idleTime = 0;

        // initialize status for all nodes.
        for (Node node : InputGraph.get().getAllNodes()) {
            NodeProperties nodeProperties = new NodeProperties();
            nodeProperties.setProcessorId(0);//processorId
            nodeProperties.setInDegree(node.getInDegree());// inDegree
            nodeProperties.setStartingTime(0);//startingTime
            nodeStates.put(node, nodeProperties);
        }

    }


    /**
     * logic for schedule task according to supplied task(Node) and processorId.
     *
     * @param currentNode The current Task(node) that is going to be scheduled
     * @param processorId Which Processor is the current Task(node) is going to be scheduled on
     */
    private void scheduleTask(Node currentNode, int processorId) {
        // set the starting time of the current node.
        int earliestStartTime = calculateStartingTime(currentNode, processorId);
        nodeStates.get(currentNode).setStartingTime(earliestStartTime);
    }

    /**
     * logic for calculating next available starting time for the task.
     *
     * @param currentNode The current Task(node) that is going to be scheduled
     * @param processorId Which Processor is the current Task(node) is going to be scheduled on
     * @return int      starting time
     */
    public int calculateStartingTime(Node currentNode, int processorId) {
        // make sure it's not the root partial solution of the solution tree before going to the next step.
        if (!nodesPath.isEmpty()) {
            // find the latest finishing time of previously direct parent(s) of the current node(Task) iteratively.
            int startTime = 0;
            for (Node node : nodesPath) {
                if (node.hasEdgeToward(currentNode)) {
                    // if the current node is on the same processor with the selected its parent node.
                    if (nodeStates.get(node).getProcessorId() == processorId) {
                        // find the finishing time of the parent node of the current node and let it be the starting
                        // time of the current node.
                        startTime = (int) Math.max(startTime, nodeStates.get(node).getStartingTime() + InputGraph.get().getNodeWeightById(node.getId()));
                        // if the current node is on the different processor with the selected its parent node.
                    } else {
                        // find the finishing time of the parent node of the current node and add communication cost,
                        // then let it be the starting time of the current node.
                        int communicationCost = InputGraph.get().getEdgeWeight(node.getId(), currentNode.getId()).intValue();
                        startTime = (int) Math.max(startTime, nodeStates.get(node).getStartingTime() + InputGraph.get().getNodeWeightById(node.getId()) + communicationCost);
                    }
                }
            }
            // check if the current scheduled processor that may have any Tasks may span over the finishing time of the parents of the
            // current node being calculated above. Select the max value between the two values as the starting time of the current node.
            int finishTimePrev = findLastFinishTime(processorId);
            return Math.max(startTime, finishTimePrev);
        }
        return 0;
    }

    /**
     * calculate the minimum bottom level among all possible future tasks.
     *
     * @return int minimum bottom level.
     */
    private int futureMinBottomLevel() {
        int MinStartingTime = Integer.MAX_VALUE;
        int bottomLevel = 0;
        for (Node node : getAvailableNextNodes()) {
            for (int p = 1; p <= InputLoader.getNumOfProcessors(); p++) {
                int startingTime = calculateStartingTime(node, p);
                if (startingTime < MinStartingTime) {
                    MinStartingTime = startingTime;
                    bottomLevel = (int) (InputGraph.get().getBottomLevel(node) + startingTime);
                }
            }
        }

        return bottomLevel;
    }

    /**
     * Get the last finish time of this processor
     *
     * @param processorId processor Id
     * @return last finish time of this processor
     */
    public int findLastFinishTime(int processorId) {

        int lastTime = 0;
        for (Node node : nodesPath) {
            if (nodeStates.get(node).getProcessorId() == processorId) {
                String nodeId = node.getId();
                lastTime = (int) Math.max(lastTime,
                        nodeStates.get(node).getStartingTime() + InputGraph.get().getNodeWeightById(nodeId));
            }
        }
        return lastTime;
    }

    /**
     * updating the current partial solution status and save it into fields.
     *
     * @param currentNode The current Task(node) that is going to be scheduled
     * @param processorId Which Processor is the current Task(node) is going to be scheduled on
     */
    private void updateCurrentPartialSolutionStatus(Node currentNode, int processorId) {
        // add this node to the solution path
        nodesPath.add(currentNode);
        // set current node's processor number.
        nodeStates.get(currentNode).setProcessorId(processorId);
        // set current node's indegree to -1 (all done.)
        nodeStates.get(currentNode).setInDegree(-1);

        // decrease all direct children indegree by 1 (effectively get rid of the in edges from the current node to
        // all its children nodes).
        List<Node> childrenNodes = InputGraph.get().getChildrenOfNode(currentNode);
        for (Node childNode : childrenNodes) {
            int currentInDegree = nodeStates.get(childNode).getInDegree();
            nodeStates.get(childNode).setInDegree(currentInDegree - 1);
        }
    }

    /**
     * calculate cost function value for the current partial solution.
     *
     * @param currentNode The current Task(node) that is going to be scheduled
     * @param processorId Which Processor is the current Task(node) is going to be scheduled on
     * @return double       costFunction value of the current partial solution.
     */
    public double calculateCostFunction(Node currentNode, int processorId) {

        // find idle time
        idleTime += nodeStates.get(currentNode).getStartingTime() - findLastFinishTime(processorId);

        // find the max bottomLevel + startingTime of scheduled node as of this partial solution.
        double bottomLevel = 0;
        for (Node node : nodesPath) {
            bottomLevel = Math.max(bottomLevel,
                    nodeStates.get(node).getStartingTime() + InputGraph.get().getBottomLevel(node));
        }

        // calculate the load balance of the current partial solution.
        double loadBalance = (InputGraph.get().getSumWeightOfNodes() + idleTime) / (double) InputLoader.getNumOfProcessors();

        // Since the calculation of the futureMinBottomLevel is computer intensive, we pre-check that if the current
        // bottom level and loadBalance is already greater than the Minimum Guess Cost, if so, there is no need to calculate
        // the futureMinBottomLevel since we will be dropping this partial solution and all combination of its children
        // from the solution tree.
        if (InputGraph.getMinimumGuessCost() != 0 && Math.max(bottomLevel, loadBalance) > InputGraph.getMinimumGuessCost()) {
            return Math.max(bottomLevel, loadBalance);
        }
        // return the MAX value from maxCurrentBottomLevel, currentLoadBalance, and futureMinBottomLevel
        // as the cost function value for the current partial solution.
        return Math.max(Math.max(bottomLevel, loadBalance), futureMinBottomLevel());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Node node : nodesPath) {
            sb.append(node.getId()).append(": ");
            sb.append(nodeStates.get(node).getProcessorId()).append(" ");
        }
        return "PartialSolution{" +
                "nodesPath=" + nodesPath + " (" + sb + " )" +
                "endTime=" + this.calculateEndScheduleTime() +
                '}';
    }

    /**
     * calculate the end schedule time,  i.e. the last task finishing time among all the processors.
     *
     * @return int      finishing time.
     */
    public int calculateEndScheduleTime() {
        int finishingTime = 0;
        for (Node node : nodesPath) {
            int weight = (int) InputGraph.get().getNodeWeightById(node.getId());
            int startingTime = nodeStates.get(node).getStartingTime();
            finishingTime = Math.max(finishingTime, weight + startingTime);
        }
        return finishingTime;
    }


    /**
     * According to current node to find its all possible PartialSolution(not include pruning PartialSolution).
     *
     * @param node current Node
     * @return List  contains all possible PartialSolution
     */
    public List<PartialSolution> getNextPartialSolution(Node node) {

        List<PartialSolution> nextPartialSolution = new ArrayList<>();

        PriorityQueue<PartialSolution> leafNodeQueue = new PriorityQueue<>((x1, x2) -> {
            // compare the last node's starting time on the Node Path between 2 solutions.
            return x1.getNodeStates().get(x1.getNodesPath().get(x1.getNodesPath().size() - 1)).getStartingTime()
                    - x2.getNodeStates().get(x1.getNodesPath().get(x1.getNodesPath().size() - 1)).getStartingTime();
        });

        // if this node is the first node, then we just assign it to the first processor
        if (this.getNodesPath().size() == 0) {
            PartialSolution current = new PartialSolution(this, node, 1);
            nextPartialSolution.add(current);
            return nextPartialSolution;
        } else {
            List<Integer> emptyProcessorIds = new ArrayList<>();
            List<Integer> notEmptyProcessorIds = new ArrayList<>();
            // check for duplicate(homogeneous) empty processors
            for (int i = 1; i <= InputLoader.getNumOfProcessors(); i++) {
                if (this.findLastFinishTime(i) == 0) {
                    emptyProcessorIds.add(i);
                } else {
                    notEmptyProcessorIds.add(i);
                }
            }

            // if empty processor count is less or equal to 1, it means that there are no homogeneous
            // empty processors, carry on with normal operations.
            if (emptyProcessorIds.size() <= 1) {

                for (int i = 1; i <= InputLoader.getNumOfProcessors(); i++) {
                    PartialSolution current = new PartialSolution(this, node, i);
                    // if we have reached leaf node of the solution tree, then return the
                    // current partial solution as optimal solution, by determining the optimal processorId
                    // that the leaf task node is being scheduled.
                    if (current.getNodesPath().size() == InputGraph.get().getNodeCount()) {
                        leafNodeQueue.offer(current);
                        if (i == InputLoader.getNumOfProcessors()) {
                            nextPartialSolution.add(leafNodeQueue.peek());
                            return nextPartialSolution;
                        }
                    } else {
                        // add to the solutionQueue, if the projected underestimate cost from the current node on the
                        // solution tree is greater than the minimum guess cost we found from the `AStarUtil` methods,
                        // effectively it means the minimum cost to reach the leaf node of the solution tree from the
                        // current node is greater than the Projected Upper Limit of the cost. Therefore, we will discard
                        // the current node and all of its children nodes on the solution tree. Otherwise, we will add
                        // the current partial solution into the solution Priority queue.
                        if (current.calculateCostFunction(node, i) <= InputGraph.getMinimumGuessCost()) {
                            nextPartialSolution.add(current);
                        }
                    }
                }

                // if 2 or more empty processor before the current task being scheduled, we can arbitrarily choose
                // which empty processor should the task be scheduled on, by convention, we choose the empty processor
                // with the smallest lexicographically ordered processorID. As this would have the same effect if
                // any other empty processor was chosen to schedule the current task on. By having this step, a lot of computation
                // time would be saved if the task is being scheduled on a lot of processors.
            } else {

                notEmptyProcessorIds.add(emptyProcessorIds.get(0));
                for (int i = 0; i < notEmptyProcessorIds.size(); i++) {
                    PartialSolution current = new PartialSolution(this, node, notEmptyProcessorIds.get(i));
                    // if we have reached leaf node of the solution tree, then return the
                    // current partial solution as optimal solution, by determining the optimal processorId
                    // that the leaf task node is being scheduled.
                    if (current.getNodesPath().size() == InputGraph.get().getNodeCount()) {
                        leafNodeQueue.offer(current);
                        if (i == notEmptyProcessorIds.size() - 1) {
                            nextPartialSolution.add(leafNodeQueue.peek());
                            return nextPartialSolution;
                        }
                    } else {
                        if (current.calculateCostFunction(node, notEmptyProcessorIds.get(i)) <= InputGraph.getMinimumGuessCost()) {
                            nextPartialSolution.add(current);
                        }
                    }
                }
            }
        }
        return nextPartialSolution;
    }

    /**
     * get all possible next PartialSolution(not including the duplicate) according to current PartialSolution
     *
     * @return List of next PartialSolution(not including the duplicate)
     */
    public List<PartialSolution> getAllNextPartialSolution() {

        List<PartialSolution> res = new ArrayList<>();

        for (Node node : getAvailableNextNodes()) {
            res.addAll(getNextPartialSolution(node));
        }
        return res;
    }


}
