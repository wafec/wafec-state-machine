package wafec.mdd.statemachine.core;

import lombok.Data;

import java.util.UUID;

@Data
public class StateEvent {
    private static final StateEvent _epsilon = StateEvent.of("__EPSILON__");

    private String id;

    public StateEvent() {
        initializeComponent();
    }

    protected void initializeComponent() {
        id = UUID.randomUUID().toString();
    }

    public boolean match(StateEvent other) {
        return id.equals(other.id);
    }

    public static StateEvent epsilon() {
        return _epsilon;
    }

    public static StateEvent of(String id) {
        StateEvent stateEvent = new StateEvent();
        stateEvent.id = id;
        return stateEvent;
    }
}
