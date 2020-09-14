package wafec.mdd.statemachine.model;

import wafec.mdd.statemachine.core.StateBase;
import wafec.mdd.statemachine.core.StateContext;

public class InitialState extends StateBase {
    public InitialState() {
        this(null);
    }

    public InitialState(StateContext stateContext) {
        super(stateContext);
        initializeComponent();
    }

    private void initializeComponent() {
        setInitial(true);
    }
}
