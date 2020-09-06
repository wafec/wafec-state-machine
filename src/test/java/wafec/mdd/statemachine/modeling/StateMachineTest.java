package wafec.mdd.statemachine.modeling;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import wafec.mdd.statemachine.core.StateContext;
import wafec.mdd.statemachine.core.VoidEvent;

import java.util.Arrays;

public class StateMachineTest {
    @Tag("normal")
    @Test
    public void testHelloWorld() {
        var stateContext = new StateContext();
        var stateMachine = new StateMachine(stateContext);
        var state1 = new State();
        var state2 = new State();
        var transition1 = new Transition();
        transition1.setName("transition1");
        state1.setName("state1");
        state2.setName("state2");
        state1.setParent(stateMachine);
        state2.setParent(stateMachine);
        transition1.setParent(stateMachine);
        stateMachine.addArrow(state1);
        state1.addArrow(transition1);
        transition1.addArrow(state2);
        var event = new VoidEvent();
        stateContext.setRoot(stateMachine);
        stateContext.start();
        stateContext.fireEvent(event);
        stateContext.fireEvent(event);
        Assertions.assertIterableEquals(stateContext.getActiveList(), Arrays.asList(stateMachine, state2));
    }
}
