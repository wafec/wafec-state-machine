package wafec.mdd.statemachine.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import wafec.mdd.statemachine.core.NamedEvent;
import wafec.mdd.statemachine.core.StateContext;
import wafec.mdd.statemachine.core.VoidEvent;
import wafec.mdd.statemachine.serialization.SerializedDocument;

import java.io.IOException;
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

    @Test
    public void testTwoRegions() {
        var event1 = new NamedEvent("event1");
        var event2 = new NamedEvent("event2");
        var stateContext = new StateContext();
        var stateMachine = new StateMachine(stateContext);
        stateMachine.setName("machine");
        var initialState1 = new InitialState();
        initialState1.setName("initial1");
        initialState1.setParent(stateMachine);
        var state1 = new State();
        state1.setParent(stateMachine);
        state1.setName("state1");
        stateMachine.addArrow(initialState1);
        var transition1 = new Transition();
        transition1.setParent(stateMachine);
        transition1.setName("transition1");
        transition1.addArrow(state1);
        initialState1.addArrow(transition1);
        var initialState1_1 = new InitialState();
        initialState1_1.setName("initial1_1");
        initialState1_1.setParent(state1);
        var state1_1 = new State();
        state1_1.setName("state1_1");
        state1_1.setParent(state1);
        state1.addArrow(initialState1_1);
        var transition1_1 = new Transition();
        transition1_1.setName("transition1_1");
        transition1_1.setParent(state1);
        initialState1_1.addArrow(transition1_1);
        transition1_1.addArrow(state1_1);
        var state1_2 = new State();
        state1_2.setName("state1_2");
        state1_2.setParent(state1);
        var transition1_2 = new Transition();
        transition1_2.setName("transition1_2");
        transition1_2.setParent(state1);
        transition1_2.addEvent(event1);
        state1_1.addArrow(transition1_2);
        transition1_2.addArrow(state1_2);
        var state2 = new State();
        state2.setName("state2");
        state2.setParent(stateMachine);
        var transition2 = new Transition();
        transition2.setName("transition2");
        transition2.setParent(stateMachine);
        state1.addArrow(transition2);
        transition2.addEvent(event2);
        transition2.addArrow(state2);

        stateContext.setRoot(stateMachine);
        stateContext.start();
        stateContext.fireEvent(event1);
        stateContext.fireEvent(event2);
        System.out.println(stateContext.toString());
    }

    @Test
    public void testDeserialization() throws IOException {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        var result = mapper.readValue(StateMachineTest.class.getResource("/examples/serialization/stm-basic.yaml"), SerializedDocument.class);
        Assertions.assertNotNull(result);
    }
}
