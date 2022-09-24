package solution;

/**
 * A state represents the allocation of one single task
 * <p>
 * A schedule can have string format: "(3,1) means that task 3 executes on processor 1
 */
public class State {

    private String task;
    private int processor;

    public State(String task, int processor) {
        this.task = task;
        this.processor = processor;
    }

    public State(String state) {
        String[] parts = state.split(",");
        this.task = parts[0].replace("(", "");
        this.processor = Integer.parseInt(parts[1].replace(")", ""));
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public int getProcessor() {
        return processor;
    }

    public void setProcessor(int processor) {
        this.processor = processor;
    }

    @Override
    public String toString() {
        return String.format("(%s,%d)", this.task, this.processor);
    }

}
