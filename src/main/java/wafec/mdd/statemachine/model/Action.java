package wafec.mdd.statemachine.model;

import wafec.mdd.statemachine.core.Event;
import wafec.mdd.statemachine.core.StateBase;
import wafec.mdd.statemachine.core.StateCallable;

public class Action extends StateCallable {
    public Action(String command) {
        super(command);
    }

    public void run(StateBase source, Event event) {
        call(source, event, Void.class);
    }
}
