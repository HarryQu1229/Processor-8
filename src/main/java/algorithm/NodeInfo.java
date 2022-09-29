package algorithm;

public class NodeInfo {
    private String nodeId;
    private int weight;
    private int indegrees;
    private int scheduledProcessor;
    private int scheduledStartTime;

    public NodeInfo(String nodeId, int weight, int indegree) {
        this.nodeId = nodeId;
        this.weight = weight;
        this.indegrees = indegree;
        this.scheduledProcessor = -1;
        this.scheduledStartTime = -1;
    }

    public NodeInfo clone() {
        NodeInfo nodeInfo = new NodeInfo(this.nodeId, this.weight, this.indegrees);
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

    public int getIndegrees() {
        return indegrees;
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
        this.indegrees--;
    }

    public void increaseIndegrees() {
        this.indegrees++;
    }

    @Override
    public String toString() {
        return "NodeInfo{" +
                "nodeId='" + nodeId + '\'' +
                ", weight=" + weight +
                ", indegrees=" + indegrees +
                ", scheduledProcessor=" + scheduledProcessor +
                ", scheduledStartTime=" + scheduledStartTime +
                '}';
    }

    public String toDotString() {
        return nodeId + " [Weight=" + weight + ", Start=" + scheduledStartTime + ", Processor=" + scheduledProcessor + "]";
    }
}
