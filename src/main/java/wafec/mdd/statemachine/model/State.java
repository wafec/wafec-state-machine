package wafec.mdd.statemachine.model;

import wafec.mdd.statemachine.core.StateBase;
import wafec.mdd.statemachine.core.StateContext;
import wafec.mdd.statemachine.core.StateTransition;

import java.util.ArrayList;
import java.util.List;

public class State extends StateBase {
    private List<Action> entryList;
    private List<Action> exitList;

    public State() {
        this(null);
    }

    public State(StateContext stateContext) {
        super(stateContext);
        this.initializeComponent();
    }

    private void initializeComponent() {
        this.stateTransitionConsumer = true;
        this.entryList = new ArrayList<>();
        this.exitList = new ArrayList<>();
    }

    public void addEntry(Action entry) {
        if (!this.entryList.contains(entry))
            this.entryList.add(entry);
    }

    public void addExit(Action exit) {
        if (!this.exitList.contains(exit))
            this.exitList.add(exit);
    }

    @Override
    public void handleEntry(final StateTransition stateTransition) {
        super.handleEntry(stateTransition);
        this.entryList.forEach(entry -> entry.execute(stateTransition));
    }

    @Override
    public void handleExit(final StateTransition stateTransition) {
        super.handleExit(stateTransition);
        this.exitList.forEach(exit -> exit.execute(stateTransition));
    }
}
