package wafec.mdd.statemachine.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import wafec.mdd.statemachine.core.StateEvent;

public class StateMachineTest {
    @Test
    public void testComplexHierarchy() {
        var state1 = new State();
        var state1_1 = new State();
        var state1_2 = new State();
        var state1_1_1 = new State();
        var state1_2_1 = new State();
        var state1_1_1_1 = new State();
        var state1_2_1_1 = new State();
        var initial1_1 = new InitialState();
        var initial1_2 = new InitialState();
        var initial1_1_1 = new InitialState();
        var initial1_2_1 = new InitialState();
        var initial1_1_1_1 = new InitialState();
        var initial1_2_1_1 = new InitialState();
        var transition1_1 = new Transition();
        var transition1_2 = new Transition();
        var transition1_1_1 = new Transition();
        var transition1_2_1 = new Transition();
        var transition1_1_1_1 = new Transition();
        var transition1_2_1_1 = new Transition();
        state1_1.setParent(state1);
        state1_2.setParent(state1);
        state1_1_1.setParent(state1_1);
        state1_2_1.setParent(state1_2);
        state1_1_1_1.setParent(state1_1_1);
        state1_2_1_1.setParent(state1_2_1);
        initial1_1.setParent(state1);
        state1.addInitial(initial1_1);
        transition1_1.setParent(state1);
        transition1_1.addArrow(state1_1);
        initial1_1.addArrow(transition1_1);
        initial1_2.setParent(state1);
        transition1_2.setParent(state1);
        initial1_2.addArrow(transition1_2);
        transition1_2.addArrow(state1_2);
        state1.addInitial(initial1_2);
        initial1_1_1.setParent(state1_1);
        state1_1.addInitial(initial1_1_1);
        transition1_1_1.setParent(state1_1);
        initial1_1_1.addArrow(transition1_1_1);
        transition1_1_1.addArrow(state1_1_1);
        initial1_2_1.setParent(state1_2);
        state1_2.addInitial(initial1_2_1);
        transition1_2_1.setParent(state1_2);
        initial1_2_1.addArrow(transition1_2_1);
        transition1_2_1.addArrow(state1_2_1);
        initial1_1_1_1.setParent(state1_1_1);
        state1_1_1.addInitial(initial1_1_1_1);
        transition1_1_1_1.setParent(state1_1_1);
        transition1_1_1_1.addArrow(state1_1_1_1);
        initial1_1_1_1.addArrow(transition1_1_1_1);
        initial1_2_1_1.setParent(state1_2_1);
        state1_2_1.addInitial(initial1_2_1_1);
        transition1_2_1_1.setParent(state1_2_1);
        initial1_2_1_1.addArrow(transition1_2_1_1);
        transition1_2_1_1.addArrow(state1_2_1_1);

        state1.entry();
        Assertions.assertTrue(state1_1_1_1.isActive());
        Assertions.assertTrue(state1_2_1_1.isActive());
        var event = new StateEvent();
        event.setId("event");
        var transition = new Transition();
        transition.setParent(state1_1_1);
        transition.addEvent(event);
        transition.addArrow(state1_2_1_1);
        state1_1_1_1.addArrow(transition);
        state1.accept(event);
        Assertions.assertFalse(state1_1_1_1.isActive());
        Assertions.assertTrue(state1_2_1_1.isActive());
        Assertions.assertFalse(state1_1_1.isActive());
        Assertions.assertFalse(state1_1.isActive());
        Assertions.assertTrue(state1.isActive());
    }
}
