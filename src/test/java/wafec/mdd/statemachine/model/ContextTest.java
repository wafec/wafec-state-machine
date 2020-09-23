package wafec.mdd.statemachine.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import wafec.mdd.statemachine.core.Context;
import wafec.mdd.statemachine.core.StateEvent;

public class ContextTest {
    @Test
    public void testHelloWorld() {
        var stackImpl = new StackImpl();
        var context = new Context(stackImpl);
        var stm = new StateMachine(context);
        var initial1 = new InitialState();
        initial1.setParent(stm);
        var state1 = new State();
        state1.setParent(stm);
        var transition1 = new Transition();
        transition1.setParent(stm);
        stm.addInitial(initial1);
        initial1.addArrow(transition1);
        transition1.addArrow(state1);

        Assertions.assertEquals(state1.getContext(), context);
        stm.entry();
        Assertions.assertEquals(state1.getContext(), context);
    }

    @Test
    public void testTwoStateMachines() {
        var stackImpl = new StackImpl();
        var context1 = new Context(stackImpl);
        var stm1 = new StateMachine(context1);
        var context2 = new Context(stackImpl);
        var stm2 = new StateMachine(context2);
        var initial1 = new InitialState();
        initial1.setParent(stm1);
        stm1.addInitial(initial1);
        var state1 = new State();
        state1.setParent(stm1);
        var transition1 = new Transition();
        transition1.setParent(stm1);
        transition1.addArrow(state1);
        initial1.addArrow(transition1);
        var initial2 = new InitialState();
        initial2.setParent(stm2);
        stm2.addInitial(initial2);
        var state2 = new State();
        state2.setParent(stm2);
        var transition2 = new Transition();
        transition2.setParent(stm2);
        transition2.addArrow(state2);
        initial2.addArrow(transition2);

        Assertions.assertEquals(state2.getContext(), context2);
        Assertions.assertEquals(state1.getContext(), context1);
        var initial3 = new InitialState();
        initial3.setParent(state1);
        var transition3 = new Transition();
        transition3.setParent(state1);
        state1.addInitial(initial3);
        initial3.addArrow(transition3);
        transition3.addArrow(stm2);
        Assertions.assertEquals(state2.getContext(), context2);
        Assertions.assertEquals(state1.getContext(), context1);
        stm1.entry();
        Assertions.assertEquals(state2.getContext(), context2);
        Assertions.assertEquals(state1.getContext(), context1);
    }

    @Test
    public void testAction() {
        var stackImpl = new StackImpl();
        var context = new Context(stackImpl);
        var stm = new StateMachine(context);
        var initial = new InitialState();
        initial.setParent(stm);
        stm.addInitial(initial);
        var state1 = new State();
        state1.setParent(stm);
        var state2 = new State();
        state2.setParent(stm);
        var transition1 = new Transition();
        transition1.setParent(stm);
        transition1.addArrow(state1);
        initial.addArrow(transition1);
        var transition2 = new Transition();
        transition2.setParent(stm);
        transition2.addEvent(new Push());
        transition2.addArrow(state2);
        state1.addArrow(transition2);
        transition2.addAction(new Action("push"));

        stm.entry();
        stm.accept(new Push().setElement(10));
        Assertions.assertEquals(1, stackImpl.getStack().size());
        stm.accept(new Pop());
        Assertions.assertEquals(1, stackImpl.getStack().size());
    }

    @Test
    public void testGuard() {
        var stackImpl = new StackImpl(3);
        var context = new Context(stackImpl);
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
        transition2.addArrow(state2);
        transition2.addGuard(new Guard("guardIfEmpty"));
        transition2.addAction(new Action("push"));
        transition2.addEvent(new Push());
        context.getConfiguration().name("push")
                .parameter("element").atPosition(0);
        stm.entry();
        stm.accept(new Push().setElement(10));
        Assertions.assertTrue(state2.isActive());
        var transition3 = new Transition();
        transition3.setParent(stm);
        state2.addArrow(transition3);
        transition3.addGuard(new Guard("guardIfNotFull"));
        transition3.addArrow(state2);
        transition3.addAction(new Action("push"));
        stm.accept(new Push().setElement(11));
        Assertions.assertEquals(2, stackImpl.getStack().size());
        var state3 = new State();
        state3.setParent(stm);
        var transition4 = new Transition();
        transition4.setParent(stm);
        state2.addArrow(transition4);
        transition4.addArrow(state3);
        transition4.addEvent(new Push());
        transition4.addGuard(new Guard("guardIfFull"));
        transition4.addAction(new Action("push"));
        stm.accept(new Push().setElement(12));
        Assertions.assertEquals(3, stackImpl.getStack().size());
        stm.accept(new Push().setElement(13));
        Assertions.assertEquals(3, stackImpl.getStack().size());
        Assertions.assertFalse(state2.isActive());
        Assertions.assertTrue(state3.isActive());
    }
}
