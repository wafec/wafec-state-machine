package wafec.mdd.statemachine.serialization;

import lombok.Data;
import wafec.mdd.statemachine.core.StateBase;
import wafec.mdd.statemachine.core.StateEvent;
import wafec.mdd.statemachine.model.Action;
import wafec.mdd.statemachine.model.Guard;

import java.util.HashMap;
import java.util.Map;

@Data
public class ModelSerializerBuilder {
    private SerializedDocument document;
    private Map<String, SerializedStateBase> elementMap;
    private Map<String, SerializedContextBase> contextMap;
    private Map<String, StateBase> stateBaseMap;
    private Map<String, StateEvent> stateEventMap;
    private Map<String, Action> actionMap;
    private Map<String, Guard> guardMap;

    public ModelSerializerBuilder(SerializedDocument document) {
        this.document = document;
        this.initializeComponent();
    }

    private void initializeComponent() {
        elementMap = new HashMap<>();
        elementMapDepthSearch(document.getMachine());
        contextMap = new HashMap<>();
        contextMapDepthSearch(document);
    }

    private void elementMapDepthSearch(SerializedStateBase serializedStateBase) {
        if (serializedStateBase != null) {
            this.elementMap.put(serializedStateBase.getUuid(), serializedStateBase);
            for (var child : serializedStateBase.getChildren()) {
                elementMapDepthSearch(child);
            }
        }
    }
    private void contextMapDepthSearch(SerializedDocument serializedDocument) {
        if (serializedDocument != null) {
            for (var function : serializedDocument.getContext()) {
                contextMap.put(function.getUuid(), function);
            }
        }
    }
}
