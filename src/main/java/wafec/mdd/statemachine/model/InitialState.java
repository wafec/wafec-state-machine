package wafec.mdd.statemachine.model;

import wafec.mdd.statemachine.core.Event;
import wafec.mdd.statemachine.core.PseudoStateBase;
import wafec.mdd.statemachine.core.StateTransition;

public class InitialState extends PseudoStateBase {
    @Override
    public void entry() {
        for (var arrow : arrowSet) {
            arrow.accept(StateTransition.of(Event.epsilon(), this));
        }
    }

    @Override
    public void accept(StateTransition stateTransition) {
        for (var arrow : arrowSet) {
            arrow.accept(stateTransition);
        }
    }
}
