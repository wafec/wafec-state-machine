package wafec.mdd.statemachine.model;

import wafec.mdd.statemachine.core.PseudoStateBase;
import wafec.mdd.statemachine.core.StateEvent;
import wafec.mdd.statemachine.core.StateTransition;

public class InitialState extends PseudoStateBase {
    @Override
    public void entry() {
        for (var arrow : arrowSet) {
            var epsilonStateTransition = StateTransition.of(StateEvent.epsilon(), this);
            arrow.accept(epsilonStateTransition);
        }
    }

    @Override
    public void accept(StateTransition stateTransition) {
        for (var arrow : arrowSet) {
            arrow.accept(stateTransition);
        }
    }
}
