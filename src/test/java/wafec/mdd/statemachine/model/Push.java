package wafec.mdd.statemachine.model;

import wafec.mdd.statemachine.core.Event;

public class Push extends Event {
    @Override
    public void initializeComponent() {
        super.initializeComponent();
        id = "push";
        addParam("element", 0);
    }

    public Push setElement(int element) {
        this.replaceParam("element", element);
        return this;
    }
}
