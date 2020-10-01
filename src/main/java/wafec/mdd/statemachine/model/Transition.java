package wafec.mdd.statemachine.model;

import wafec.mdd.statemachine.core.*;

import java.util.ArrayList;
import java.util.List;

public class Transition extends PseudoStateBase {
    public static final String COMMAND_TRANSITION_ACCEPT_BEGIN = "TRANSITION_ACCEPT_BEGIN";
    public static final String COMMAND_TRANSITION_ACCEPT_END = "TRANSITION_ACCEPT_END";
    public static final String COMMAND_TRANSITION_EVENT_MISMATCH = "TRANSITION_EVENT_MISMATCH";
    public static final String COMMAND_TRANSITION_EVENT_MATCH = "TRANSITION_EVENT_MATCH";
    public static final String COMMAND_TRANSITION_GUARD_TRUE = "TRANSITION_GUARD_TRUE";
    public static final String COMMAND_TRANSITION_GUARD_FALSE = "TRANSITION_GUARD_FALSE";

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
        stateTransition.getStateEvent().getStateEventContext()
                .addPoint(Point.of(this, 0d, COMMAND_TRANSITION_ACCEPT_BEGIN));
        if (eventSet.isEmpty() || eventSet.stream().anyMatch(stateEvent -> stateEvent.match(stateTransition.getStateEvent()))) {
            stateTransition.getStateEvent().getStateEventContext()
                    .addPoint(Point.of(this, 0d, COMMAND_TRANSITION_EVENT_MATCH));
            if (guardSet.isEmpty() || guardSet.stream().anyMatch( guard -> guard.test(this, (Event) stateTransition.getStateEvent()))) {
                stateTransition.getStateEvent().getStateEventContext()
                        .addPoint(Point.of(this, 0d, COMMAND_TRANSITION_GUARD_TRUE));
                for (var action : actionSequence) {
                    action.run(this, (Event) stateTransition.getStateEvent());
                }
                stateTransition.getStateEvent().getStateEventContext()
                        .addPoint(Point.of(this, 0d, COMMAND_TRANSITION_ACCEPT_END));
                for (var arrow : arrowSet) {
                    arrow.accept(stateTransition);
                }
                return;
            } else {
                stateTransition.getStateEvent().getStateEventContext()
                        .addPoint(Point.of(this, 0d, COMMAND_TRANSITION_GUARD_FALSE));
            }
        } else {
            stateTransition.getStateEvent().getStateEventContext()
                    .addPoint(Point.of(this, 0d, COMMAND_TRANSITION_EVENT_MISMATCH));
        }
        stateTransition.getStateEvent().getStateEventContext()
                .addPoint(Point.of(this, 0d, COMMAND_TRANSITION_ACCEPT_END));
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
