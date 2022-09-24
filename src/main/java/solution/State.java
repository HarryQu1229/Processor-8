package solution;

/**
 * A state represents the allocation of one single task
 *
 * A schedule can have string format: "(3,1) means that task 3 executes on processor 1
 */
public class State {

    String task;
    int processor;

    public State(String task, int processor) {
        this.task = task;
        this.processor = processor;
    }

    public State(String state) {
        String[] parts = state.split(",");
        this.task = parts[0].replace("(", "");
        this.processor = Integer.parseInt(parts[1].replace(")", ""));
    }

    @Override
    public String toString() {
        return String.format("(%s,%d)", this,task, this.processor);
    }

}
