package wafec.mdd.statemachine.model;

import wafec.mdd.statemachine.core.StateContext;

public class StateMachine extends State {
    public StateMachine(StateContext stateContext) {
        super(stateContext);
    }

    public StateMachine() {
        this(null);
    }
}
