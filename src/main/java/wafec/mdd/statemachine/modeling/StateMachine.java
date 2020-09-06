package wafec.mdd.statemachine.modeling;

import wafec.mdd.statemachine.core.StateContext;

public class StateMachine extends State {
    public StateMachine(StateContext stateContext) {
        super(stateContext);
    }

    public StateMachine() {
        this(null);
    }
}
