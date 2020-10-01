package wafec.mdd.statemachine.control;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import wafec.mdd.statemachine.core.Context;
import wafec.mdd.statemachine.core.Point;
import wafec.mdd.statemachine.core.StateEvent;
import wafec.mdd.statemachine.core.StateEventContext;
import wafec.mdd.statemachine.model.*;

public class XprTest {
    @Test
    public void testHello() {
        var target = new XprTarget();
        var context = new Context(target);
        var stm = new StateMachine(context);
        var initial = new InitialState();
        var state1 = new State();
        var state2 = new State();
        var transition1 = new Transition();
        var transition2 = new Transition();
        initial.setParent(stm);
        state1.setParent(stm);
        state2.setParent(stm);
        transition1.setParent(stm);
        transition2.setParent(stm);
        stm.addInitial(initial);
        initial.addArrow(transition1);
        transition1.addArrow(state1);
        state1.addArrow(transition2);
        transition2.addEvent(new XprTargetEvent());
        transition2.addGuard(new Guard("guardTest"));
        transition2.addArrow(state2);

        var configuration = context.getConfiguration();
        configuration.name("guardTest").parameter("a").atPosition(0);
        configuration.name("guardTest").parameter("b").atPosition(1);

        stm.entry();
        var event = new XprTargetEvent(11, 20);
        stm.accept(event);
        Assertions.assertTrue(state1.isActive());
        event.replaceParam("a", 10);
        stm.accept(event);
        Assertions.assertTrue(state2.isActive());
    }

    private Xpr buildXprBase() {
        var transition1 = new Transition();
        var stateEventContext = new StateEventContext();
        stateEventContext.addPoint(Point.of(transition1, 0d, "TRANSITION_TEST"));
        return new Xpr(stateEventContext);
    }

    @Test
    public void testAnd() {
        var xpr = buildXprBase();
        var result = xpr.and(xpr.of(0), xpr.of(0));
        Assertions.assertTrue(result.isTrue());
        result = xpr.and(xpr.of(1), xpr.of(0));
        Assertions.assertFalse(result.isTrue());
        result = xpr.and(xpr.of(0), xpr.of(1));
        Assertions.assertFalse(result.isTrue());
        result = xpr.and(xpr.of(1), xpr.of(1));
        Assertions.assertFalse(result.isTrue());
        result = xpr.and(xpr.of(10), xpr.of(20));
        Assertions.assertEquals((Double)30d, result.getScore());
    }

    @Test
    public void testOr() {
        var xpr = buildXprBase();
        var result = xpr.or(xpr.of(0), xpr.of(0));
        Assertions.assertTrue(result.isTrue());
        result = xpr.or(xpr.of(1), xpr.of(0));
        Assertions.assertTrue(result.isTrue());
        result = xpr.or(xpr.of(0), xpr.of(1));
        Assertions.assertTrue(result.isTrue());
        result = xpr.or(xpr.of(1), xpr.of(1));
        Assertions.assertFalse(result.isTrue());
        result = xpr.or(xpr.of(10), xpr.of(20));
        Assertions.assertEquals((Double) 10d, result.getScore());
    }

    @Test
    public void testLessThan() {
        var xpr = buildXprBase();
        var result = xpr.lessThan(xpr.of(10), xpr.of(11));
        Assertions.assertTrue(result.isTrue());
        result = xpr.lessThan(xpr.of(10), xpr.of(10));
        Assertions.assertFalse(result.isTrue());
        Assertions.assertEquals((Double)1d, result.getScore());
    }

    @Test
    public void testLessOrEqualThan() {
        var xpr = buildXprBase();
        var result = xpr.lessOrEqualThan(xpr.of(10), xpr.of(11));
        Assertions.assertTrue(result.isTrue());
        result = xpr.lessOrEqualThan(xpr.of(10), xpr.of(10));
        Assertions.assertTrue(result.isTrue());
        result = xpr.lessOrEqualThan(xpr.of(11), xpr.of(10));
        Assertions.assertFalse(result.isTrue());
        Assertions.assertEquals((Double)1d, result.getScore());
    }

    @Test
    public void testGreaterThan() {
        var xpr = buildXprBase();
        var result = xpr.greaterThan(xpr.of(11), xpr.of(10));
        Assertions.assertTrue(result.isTrue());
        result = xpr.greaterThan(xpr.of(11), xpr.of(11));
        Assertions.assertFalse(result.isTrue());
        Assertions.assertEquals((Double) 1d, result.getScore());
    }

    @Test
    public void testGreaterOrEqualThan() {
        var xpr = buildXprBase();
        var result = xpr.greaterOrEqualThan(xpr.of(10), xpr.of(10));
        Assertions.assertTrue(result.isTrue());
        result = xpr.greaterOrEqualThan(xpr.of(11), xpr.of(10));
        Assertions.assertTrue(result.isTrue());
        result = xpr.greaterOrEqualThan(xpr.of(10), xpr.of(11));
        Assertions.assertFalse(result.isTrue());
        Assertions.assertEquals((Double) 1d, result.getScore());
    }

    @Test
    public void testEqual() {
        var xpr = buildXprBase();
        var result = xpr.equal(xpr.of(5), xpr.of(5));
        Assertions.assertTrue(result.isTrue());
        result = xpr.equal(xpr.of(10), xpr.of(5));
        Assertions.assertFalse(result.isTrue());
        Assertions.assertEquals((Double) 5d, result.getScore());
        result = xpr.equal(xpr.of(5), xpr.of(10));
        Assertions.assertFalse(result.isTrue());
        Assertions.assertEquals((Double) 5d, result.getScore());
    }

    @Test
    public void testNotEqual() {
        var xpr = buildXprBase();
        var result = xpr.notEqual(xpr.of(4), xpr.of(5));
        Assertions.assertTrue(result.isTrue());
        result = xpr.notEqual(xpr.of(10), xpr.of(10));
        Assertions.assertTrue(result.isFalse());
    }

    @Test
    public void testNot() {
        var xpr = buildXprBase();
        var result = xpr.not(xpr.and(xpr.of(10), xpr.of(10)));
        Assertions.assertTrue(result.isTrue());
    }
}
