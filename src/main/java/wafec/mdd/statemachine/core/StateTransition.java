package wafec.mdd.statemachine.core;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "of")
public class StateTransition {
    private StateEvent stateEvent;
    private StateBase source;
}
