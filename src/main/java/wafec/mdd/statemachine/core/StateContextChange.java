package wafec.mdd.statemachine.core;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "of")
public class StateContextChange {
    private StateContextChangeType changeType;
    private StateBase target;
}
