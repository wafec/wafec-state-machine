package wafec.mdd.statemachine.core;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class StateTransition {
    private StateEvent stateEvent;
    private List<StateBase> traversingList;

    public static StateTransition of(StateEvent stateEvent, StateBase source) {
        StateTransition stateTransition = new StateTransition();
        stateTransition.stateEvent = stateEvent;
        stateTransition.traversingList = new ArrayList<>();
        stateTransition.traversingList.add(source);
        return stateTransition;
    }

    public void addTarget(StateBase target) {
        this.traversingList.add(target);
    }

    public StateBase getSource() {
        return traversingList.get(0);
    }

    public StateBase getTarget() {
        return traversingList.get(traversingList.size() - 1);
    }
}
