package wafec.mdd.statemachine.modeling;

import wafec.mdd.statemachine.core.StateBase;
import wafec.mdd.statemachine.core.StateEvent;
import wafec.mdd.statemachine.core.StateTransition;

import java.util.ArrayList;
import java.util.List;

public class Transition extends StateBase {
    private List<StateEvent> stateEventList;
    private List<Guard> guardList;
    private List<Action> actionList;

    public Transition() {
        super();
        this.initializeComponent();
    }

    private void initializeComponent() {
        this.countable = false;
        this.stateEventList = new ArrayList<>();
        this.guardList = new ArrayList<>();
        this.actionList = new ArrayList<>();
    }

    @Override
    public void accept(final StateTransition stateTransition) {
        var matches = stateEventList.size() == 0 ? 1 : (int) stateEventList.stream().filter(stateEvent -> stateEvent.match(stateTransition.getStateEvent())).count();
        if (matches > 0) {
            if (guardList.size() == 0 || guardList.stream().anyMatch(guard -> guard.test(stateTransition))) {
                actionList.forEach(action -> action.execute(stateTransition));
                super.accept(stateTransition);
            }
        }
    }
}
