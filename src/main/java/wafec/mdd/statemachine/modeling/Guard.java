package wafec.mdd.statemachine.modeling;

import wafec.mdd.statemachine.core.StateTransition;

public interface Guard {
    boolean test(StateTransition stateTransition);
}
