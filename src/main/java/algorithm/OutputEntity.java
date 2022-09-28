package algorithm;

public class OutputEntity {
    String source;
    String target;
    int weight;
    int processor;
    int start;

    public OutputEntity(String source, String target, int weight, int processor, int start) {
        this.source = source;
        this.target = target;
        this.weight = weight;
        this.processor = processor;
        this.start = start;
    }

    public String getSource() {
        return source;
    }

    public String getTarget() {
        return target;
    }

    public int getWeight() {
        return weight;
    }

    public int getProcessor() {
        return processor;
    }

    public int getStart() {
        return start;
    }

    @Override
    public String toString() {
        return "OutputEntity{" +
                "source='" + source + '\'' +
                ", target='" + target + '\'' +
                ", weight=" + weight +
                ", processor=" + processor +
                ", start=" + start +
                '}';
    }

    public String toDotString() {
        return source + " -> " + target + " [Weight=" + weight + ", Start=" + start + ", Processor=" + processor + "]";
    }
}
