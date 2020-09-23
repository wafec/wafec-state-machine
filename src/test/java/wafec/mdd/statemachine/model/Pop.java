package wafec.mdd.statemachine.model;

import wafec.mdd.statemachine.core.Event;

public class Pop extends Event {
    @Override
    public void initializeComponent() {
        super.initializeComponent();
        id = "pop";
    }
}
