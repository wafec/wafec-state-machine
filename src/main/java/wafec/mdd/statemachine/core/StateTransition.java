package wafec.mdd.statemachine.core;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class StateTransition {
    private StateEvent stateEvent;
    private List<StateBase> traversingList;

    public StateTransition() {
        this.initializeComponent();
    }

    private void initializeComponent() {
        traversingList = new ArrayList<>();
    }

    public static StateTransition of(StateEvent stateEvent, StateBase source) {
        StateTransition stateTransition = new StateTransition();
        stateTransition.stateEvent = stateEvent;
        stateTransition.addTarget(source);
        stateTransition.validate();
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

    public void validate() {
        if (!(this.stateEvent instanceof Event)) {
            throw new IllegalStateException();
        }
    }
}
