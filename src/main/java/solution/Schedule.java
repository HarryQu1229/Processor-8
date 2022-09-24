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

    public Schedule() {
        this.states = new ArrayList<>();
    }

    public Schedule(Schedule schedule) {
        this();
        this.states.addAll(schedule.states);
    }

    public Schedule(String schedule) {
        this();
        for (String state: schedule.split(";")) {
            this.states.add(new State(state));
        }
    }

    public void addState(String state) {
        this.states.add(new State(state));
    }

    public void addState(State state) {
        this.states.add(state);
    }

    public boolean hasExecuted(String task) {
        return this.getTasks().contains(task);
    }

    public List<String> getTasks() {
        return this.states.stream().map(State::getTask).collect(Collectors.toList());
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        this.states.forEach(state -> builder.append(state.toString()).append(";"));
        builder.deleteCharAt(builder.length() - 1);
        return builder.toString();
    }

}
