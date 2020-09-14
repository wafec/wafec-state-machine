package wafec.mdd.statemachine.model;

import wafec.mdd.statemachine.core.StateTransition;

public interface Guard {
    boolean test(StateTransition stateTransition);
}
