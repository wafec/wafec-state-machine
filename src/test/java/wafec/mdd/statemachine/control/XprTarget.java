package wafec.mdd.statemachine.control;

import wafec.mdd.statemachine.core.StateInject;

public class XprTarget {
    @StateInject
    public Xpr xpr;

    public boolean guardTest(int a, int b) {
        return xpr.and(xpr.equal(xpr.of(a), xpr.of(10)), xpr.equal(xpr.of(b), xpr.of(20))).isTrue();
    }
}
