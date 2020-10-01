package wafec.mdd.statemachine.control;

public class XprCalc {
    public Double and(Double x1, Double x2) {
        return x1 + x2;
    }

    public Double or(Double x1, Double x2) {
        return Math.min(x1, x2);
    }

    public Double equal(Double x1, Double x2) {
        return Math.abs(x1 - x2);
    }

    public Double notEqual(Double x1, Double x2) {
        return !x1.equals(x2) ? 0d : 1d;
    }

    public Double lessThan(Double x1, Double x2) {
        return (x1 - x2) + 1;
    }

    public Double lessOrEqualThan(Double x1, Double x2) {
        return x1 - x2;
    }

    public Double zeroOrPositive(Double x1) {
        return x1 > 0 ? x1 : 0;
    }

    public Double greaterThan(Double x1, Double x2) {
        return lessThan(x2, x1);
    }

    public Double greaterOrEqualThan(Double x1, Double x2) {
        return lessOrEqualThan(x2, x1);
    }
}
