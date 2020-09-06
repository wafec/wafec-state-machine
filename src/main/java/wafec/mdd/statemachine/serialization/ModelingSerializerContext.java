package wafec.mdd.statemachine.serialization;

import lombok.Data;
import wafec.mdd.statemachine.core.StateBase;

import java.util.HashMap;
import java.util.Map;

@Data
public class ModelingSerializerContext {
    private SerializedStateBase rootSerializedStateBase;
    private Map<String, SerializedStateBase> elementMap;

    public ModelingSerializerContext(SerializedStateBase rootSerializedStateBase) {
        this.rootSerializedStateBase = rootSerializedStateBase;
        this.initializeComponent();
    }

    private void initializeComponent() {
        elementMap = new HashMap<>();
        elementMapDeepSearch(rootSerializedStateBase);
    }

    private void elementMapDeepSearch(SerializedStateBase serializedStateBase) {
        if (serializedStateBase != null) {
            this.elementMap.put(serializedStateBase.getUuid(), serializedStateBase);
            for (var child : serializedStateBase.getChildren()) {
                elementMapDeepSearch(child);
            }
        }
    }
}
