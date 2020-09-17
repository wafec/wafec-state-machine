package wafec.mdd.statemachine.core;

public class PseudoStateBase extends StateBase {
    @Override
    public void initializeComponent() {
        super.initializeComponent();
        pseudo = true;
    }
}
