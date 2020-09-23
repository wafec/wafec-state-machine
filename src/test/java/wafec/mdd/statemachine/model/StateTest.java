package wafec.mdd.statemachine.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import wafec.mdd.statemachine.core.Event;
import wafec.mdd.statemachine.core.StateEvent;

import java.util.Arrays;

public class StateTest {
    @Test
    public void testHelloWorld() {
        var state1 = new State();
        var state2 = new State();
        var transition = new Transition();
        state1.addArrow(transition);
        transition.addArrow(state2);
        state1.entry();
        Assertions.assertTrue(state1.isActive());
        Assertions.assertFalse(state2.isActive());
        state1.accept(Event.of(StateEvent.epsilon()));
        Assertions.assertFalse(state1.isActive());
        Assertions.assertTrue(state2.isActive());
    }

    @Test
    public void testHierarchy() {
        var state1 = new State();
        var state1_1 = new State();
        var state1_2 = new State();
        state1_1.setParent(state1);
        state1_2.setParent(state1);
        var state1_1_1 = new State();
        state1_1_1.setParent(state1_1);
        var state1Hierarchy = state1.getHierarchy();
        var state1_1Hierarchy = state1_1.getHierarchy();
        var state1_2Hierarchy = state1_2.getHierarchy();
        Assertions.assertEquals(state1Hierarchy.length, 0);
        Assertions.assertEquals(state1_1Hierarchy.length, 1);
        Assertions.assertEquals(state1_2Hierarchy.length, 1);
        Assertions.assertTrue(state1_1.isHierarchyExactlyEqual(Arrays.asList(state1_2Hierarchy)));
        var state1_1_1Hierarchy = state1_1_1.getHierarchy();
        Assertions.assertEquals(state1_1_1Hierarchy.length, 2);
    }

    @Test
    public void testInitial() {
        var state1 = new State();
        var initial1 = new InitialState();
        var state1_1 = new State();
        var transition1 = new Transition();
        transition1.setParent(state1);
        state1.addInitial(initial1);
        initial1.setParent(state1);
        state1_1.setParent(state1);
        initial1.addArrow(transition1);
        transition1.addArrow(state1_1);
        Assertions.assertFalse(state1.isActive());
        Assertions.assertFalse(state1.isActive());
        Assertions.assertFalse(state1_1.isActive());
        state1.entry();
        Assertions.assertTrue(state1.isActive());
        Assertions.assertFalse(initial1.isActive());
        Assertions.assertTrue(state1_1.isActive());
    }
}
