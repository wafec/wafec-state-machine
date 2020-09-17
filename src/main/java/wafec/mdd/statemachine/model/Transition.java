package wafec.mdd.statemachine.model;

import wafec.mdd.statemachine.core.PseudoStateBase;
import wafec.mdd.statemachine.core.StateEvent;
import wafec.mdd.statemachine.core.StateTransition;

import java.util.ArrayList;
import java.util.List;

public class Transition extends PseudoStateBase {
    private List<StateEvent> eventSet;

    @Override
    public void initializeComponent() {
        super.initializeComponent();
        eventSet = new ArrayList<>();
    }

    @Override
    public void accept(final StateEvent stateEvent) {
        throw new IllegalStateException();
    }

    @Override
    public void accept(final StateTransition stateTransition) {
        if (eventSet.isEmpty() || eventSet.stream().anyMatch(stateEvent -> stateEvent.match(stateTransition.getStateEvent()))) {
            for (var arrow : arrowSet) {
                arrow.accept(stateTransition);
            }
        }
    }

    public void addEvent(final StateEvent stateEvent) {
        if (eventSet.stream().map(StateEvent::getId).noneMatch(id -> id.equals(stateEvent.getId())))
            this.eventSet.add(stateEvent);
    }
}
