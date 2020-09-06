package wafec.mdd.statemachine.core;

public interface StateChangeListener<Target> {
    void onChange(Target target);
}
