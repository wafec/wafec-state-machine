package wafec.mdd.statemachine.serialization;

import lombok.AllArgsConstructor;
import lombok.Data;
import wafec.mdd.statemachine.core.StateBase;

@Data
@AllArgsConstructor(staticName = "of")
public class SerializedStateBaseKeyPair {
    private SerializedStateBase serializedStateBase;
    private StateBase stateBase;
}
