package wafec.mdd.statemachine.model;

import wafec.mdd.statemachine.core.Event;
import wafec.mdd.statemachine.core.StateBase;
import wafec.mdd.statemachine.core.StateCallable;

public class Guard extends StateCallable {
    public Guard(String command) {
        super(command);
    }

    public boolean test(StateBase source, Event event) {
        return call(source, event, Boolean.class);
    }
}
