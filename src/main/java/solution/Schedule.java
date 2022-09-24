package solution;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        this.states.forEach(state -> builder.append(state.toString()));
        return builder.toString();
    }

}
