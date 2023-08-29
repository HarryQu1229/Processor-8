package algorithm;

import org.graphstream.graph.Node;

/**
 * The model class the stores the information about how a single task is scheduled
 * Including which processor the task executes on, when the task starts, and the cost of the task itself
 */
public class OutputEntity {
    private Node task;
    private double weight;
    private int processor;
    private double start;

    public OutputEntity(Node source, double weight, int processor, double start) {
        this.task = source;
        this.weight = weight;
        this.processor = processor;
        this.start = start;
    }

    public Node getTask() {
        return task;
    }

    public double getWeight() {
        return weight;
    }

    public int getProcessor() {
        return processor;
    }

    public double getStart() {
        return start;
    }

    public void setProcessor(int processor) {
        this.processor = processor;
    }

    public void setStart(int start) {
        this.start = start;
    }

    @Override
    public String toString() {
        return "OutputEntity{" +
                "task='" + task + '\'' +
                ", weight=" + weight +
                ", processor=" + processor +
                ", start=" + start +
                '}';
    }

    public String toDotString() {
        return task + " [Weight=" + weight + ", Start=" + start + ", Processor=" + processor + "]";
    }
}
