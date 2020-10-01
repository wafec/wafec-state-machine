package wafec.mdd.statemachine.control;

import lombok.Data;
import wafec.mdd.statemachine.core.Event;

public class XprTargetEvent extends Event {
    public XprTargetEvent() {
        this(0, 0);
    }

    public XprTargetEvent(int a, int b) {
        super();
        id = "XPR_TARGET_EVENT";
        addParam("a", a);
        addParam("b", b);
    }
}
