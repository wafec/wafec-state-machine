package wafec.mdd.statemachine.core;

public abstract class StateEvent {
    public boolean match(StateEvent otherStateEvent) {
        if (otherStateEvent == null)
            return false;
        return this.getUUID().equals(otherStateEvent.getUUID());
    }

    public abstract String getUUID();
}
