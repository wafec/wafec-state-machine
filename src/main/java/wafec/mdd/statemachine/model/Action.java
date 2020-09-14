package wafec.mdd.statemachine.model;

import wafec.mdd.statemachine.core.StateTransition;

public interface Action {
    void execute(StateTransition stateTransition);
}
