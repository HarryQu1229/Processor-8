package solution;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A schedule represents the allocation of a series of tasks, ie, a list of State instance
 *
 * A schedule can have string representation, for example, "(1,1);(2,1);(3,1)"
 */
public class Schedule {

    private List<State> states;
    private List<String> waitingTasks;

    public Schedule(Digraph digraph) {
        this.states = new ArrayList<>();
        this.waitingTasks = new ArrayList<>();
        digraph.getAllNodes().forEach(node -> this.waitingTasks.add(node.getId()));
    }

    public Schedule(Digraph digraph, Schedule schedule) {
        this(digraph);
        this.states.addAll(schedule.states);
        this.states.forEach(state -> this.waitingTasks.remove(state.getTask()));
    }

    public Schedule(Digraph digraph, String schedule) {
        this(digraph);
        for (String stateString: schedule.split(";")) {
            State state = new State(stateString);
            this.states.add(state);
            this.waitingTasks.remove(state.getTask());
        }
    }

    public void addState(String state) {
        State state1 = new State(state);
        this.waitingTasks.remove(state1.getTask());
        this.states.add(state1);
    }

    public void addState(State state) {
        this.waitingTasks.remove(state.getTask());
        this.states.add(state);
    }

    public boolean hasExecuted(String task) {
        return this.getScheduledTasks().contains(task);
    }

    public List<String> getScheduledTasks() {
        return this.states.stream().map(State::getTask).collect(Collectors.toList());
    }

    public List<String> getWaitingTasks() {
        return this.waitingTasks;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        this.states.forEach(state -> builder.append(state.toString()).append(";"));
        builder.deleteCharAt(builder.length() - 1);
        return builder.toString();
    }

}
