package wafec.mdd.statemachine.model;

import wafec.mdd.statemachine.core.Context;
import wafec.mdd.statemachine.core.StateBase;

public class StateMachine extends StateBase {
    public StateMachine(Context context) {
        super();
        setContext(context);
    }
}
