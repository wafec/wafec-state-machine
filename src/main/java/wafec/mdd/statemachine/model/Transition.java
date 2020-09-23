package wafec.mdd.statemachine.model;

import wafec.mdd.statemachine.core.Event;
import wafec.mdd.statemachine.core.PseudoStateBase;
import wafec.mdd.statemachine.core.StateEvent;
import wafec.mdd.statemachine.core.StateTransition;

import java.util.ArrayList;
import java.util.List;

public class Transition extends PseudoStateBase {
    private List<StateEvent> eventSet;
    private List<Guard> guardSet;
    private List<Action> actionSequence;

    @Override
    public void initializeComponent() {
        super.initializeComponent();
        eventSet = new ArrayList<>();
        guardSet = new ArrayList<>();
        actionSequence = new ArrayList<>();
    }

    @Override
    public void accept(final StateEvent stateEvent) {
        throw new IllegalStateException();
    }

    @Override
    public void accept(final StateTransition stateTransition) {
        if (eventSet.isEmpty() || eventSet.stream().anyMatch(stateEvent -> stateEvent.match(stateTransition.getStateEvent()))) {
            if (guardSet.isEmpty() || guardSet.stream().anyMatch( guard -> guard.test(this, (Event) stateTransition.getStateEvent()))) {
                for (var action : actionSequence) {
                    action.run(this, (Event) stateTransition.getStateEvent());
                }
                for (var arrow : arrowSet) {
                    arrow.accept(stateTransition);
                }
            }
        }
    }

    public void addEvent(final StateEvent stateEvent) {
        if (eventSet.stream().map(StateEvent::getId).noneMatch(id -> id.equals(stateEvent.getId())))
            this.eventSet.add(stateEvent);
    }

    public void addGuard(final Guard guard) {
        if (this.guardSet.stream().map(Guard::getCommand).noneMatch(g -> g.equals(guard.getCommand()))) {
            this.guardSet.add(guard);
        }
    }

    public void addAction(Action action) {
        this.actionSequence.add(action);
    }
}
