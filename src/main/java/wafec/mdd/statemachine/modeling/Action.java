package wafec.mdd.statemachine.modeling;

import wafec.mdd.statemachine.core.StateTransition;

public interface Action {
    void execute(StateTransition stateTransition);
}
