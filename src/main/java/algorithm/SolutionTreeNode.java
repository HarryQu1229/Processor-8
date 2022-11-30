package algorithm;

/**
 * A model class stores the information about each node in the solution tree
 */
public class SolutionTreeNode {
    private String nodeId;
    private int weight;
    private int inDegrees;
    private int scheduledProcessor;
    private int scheduledStartTime;

    public SolutionTreeNode(String nodeId, int weight, int inDegree) {
        this.nodeId = nodeId;
        this.weight = weight;
        this.inDegrees = inDegree;
        this.scheduledProcessor = -1;
        this.scheduledStartTime = -1;
    }

    public SolutionTreeNode clone() {
        SolutionTreeNode nodeInfo = new SolutionTreeNode(this.nodeId, this.weight, this.inDegrees);
        nodeInfo.setScheduledProcessor(this.scheduledProcessor);
        nodeInfo.setScheduledStartTime(this.scheduledStartTime);
        return nodeInfo;
    }

    public String getNodeId() {
        return nodeId;
    }

    public int getWeight() {
        return weight;
    }

    public int getInDegrees() {
        return inDegrees;
    }

    public int getScheduledProcessor() {
        return scheduledProcessor;
    }

    public int getScheduledStartTime() {
        return scheduledStartTime;
    }

    public void setScheduledProcessor(int scheduledProcessor) {
        this.scheduledProcessor = scheduledProcessor;
    }

    public void setScheduledStartTime(int scheduledStartTime) {
        this.scheduledStartTime = scheduledStartTime;
    }

    public void decreaseIndegrees() {
        this.inDegrees--;
    }

    public void increaseIndegrees() {
        this.inDegrees++;
    }

    @Override
    public String toString() {
        return "NodeInfo{" +
                "nodeId='" + nodeId + '\'' +
                ", weight=" + weight +
                ", indegrees=" + inDegrees +
                ", scheduledProcessor=" + scheduledProcessor +
                ", scheduledStartTime=" + scheduledStartTime +
                '}';
    }

    public String toDotString() {
        return nodeId + " [Weight=" + weight + ", Start=" + scheduledStartTime + ", Processor=" + scheduledProcessor + "]";
    }
}
