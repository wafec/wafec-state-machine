package wafec.mdd.statemachine.control;


import wafec.mdd.statemachine.core.Point;
import wafec.mdd.statemachine.core.StateEventContext;

public class Xpr {
    public static final String COMMAND_XPR_SCORE = "XPR_SCORE";

    private StateEventContext stateEventContext;
    private Point current;
    private Double result = 0d;
    private Double negativeResult = 0d;
    private boolean copied;

    private XprCalc xprCalc;

    public Xpr(StateEventContext stateEventContext) {
        this.stateEventContext = stateEventContext;
        this.initializeComponent();
    }

    private void initializeComponent() {
        xprCalc = new XprCalc();
    }

    private Point getCurrent() {
        if (current == null) {
            current = Point.of(stateEventContext.getLastPoint().getStateBase(), 0d, COMMAND_XPR_SCORE);
            stateEventContext.addPoint(current);
        }
        return current;
    }

    public Xpr setCommand(String command) {
        if (this.copied)
            throw new IllegalStateException();
        this.getCurrent().setCommand(command);
        return this;
    }

    public Xpr and(Xpr x1, Xpr x2) {
        updateScore(xprCalc.and(x1.result, x2.result), xprCalc.or(x1.negativeResult, x2.negativeResult));
        return copy();
    }

    public Xpr or(Xpr x1, Xpr x2) {
        updateScore(xprCalc.or(x1.result, x2.result), xprCalc.and(x1.negativeResult, x2.negativeResult));
        return copy();
    }

    public Xpr equal(Xpr x1, Xpr x2) {
        updateScore(xprCalc.equal(x1.result, x2.result), xprCalc.notEqual(x1.result, x2.result));
        return copy();
    }

    public Xpr notEqual(Xpr x1, Xpr x2) {
        updateScore(xprCalc.notEqual(x1.result, x2.result), xprCalc.equal(x1.result, x2.result));
        return copy();
    }

    public Xpr lessThan(Xpr x1, Xpr x2) {
        updateScore(xprCalc.lessThan(x1.result, x2.result), xprCalc.greaterOrEqualThan(x1.result, x2.result));
        return copy();
    }

    public Xpr lessOrEqualThan(Xpr x1, Xpr x2) {
        updateScore(xprCalc.lessOrEqualThan(x1.result, x2.result), xprCalc.greaterThan(x1.result, x2.result));
        return copy();
    }

    public Xpr greaterThan(Xpr x1, Xpr x2) {
        return lessThan(x2, x1);
    }

    public Xpr greaterOrEqualThan(Xpr x1, Xpr x2) {
        return lessOrEqualThan(x2, x1);
    }

    public Xpr not(Xpr x1) {
        updateScore(x1.negativeResult, x1.result);
        return copy();
    }

    private void updateScore(Double score, Double negativeScore) {
        result = xprCalc.zeroOrPositive(score);
        negativeResult = xprCalc.zeroOrPositive(negativeScore);
        this.getCurrent().setScore(result);
    }

    public Xpr copy() {
        Xpr cl = new Xpr(stateEventContext);
        cl.result = result;
        cl.current = current;
        cl.copied = true;
        return cl;
    }

    public Xpr of(Integer value) {
        updateScore(value.doubleValue(), value.doubleValue());
        return copy();
    }

    public Xpr of(Double value) {
        updateScore(value, value);
        return copy();
    }

    public boolean isTrue() {
        return result == 0d;
    }

    public boolean isFalse() {
        return result > 0d;
    }

    public Double getScore() {
        return result;
    }

    public Double getNegativeScore() {
        return negativeResult;
    }
}
