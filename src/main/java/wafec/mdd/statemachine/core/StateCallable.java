package wafec.mdd.statemachine.core;

import lombok.Getter;

public class StateCallable {
    @Getter
    protected String command;

    public StateCallable(String command) {
        this.command = command;
    }

    public Object call(StateBase source, Event event) {
        return source.getContext().call(command, event);
    }

    public <T> T call(StateBase source, Event event, Class<T> clazz) {
        var result = call(source, event);
        if (result == null)
            return null;
        if (result.getClass().equals(clazz))
            return (T) result;
        else
            throw new ClassCastException();
    }
}
