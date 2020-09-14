package wafec.mdd.statemachine.serialization;

import wafec.mdd.statemachine.core.StateBase;
import wafec.mdd.statemachine.core.StateEvent;
import wafec.mdd.statemachine.model.*;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ModelSerializerFactory {
    private Map<SerializedStateBaseType, Class<? extends StateBase>> stateBaseClassMap;

    public ModelSerializerFactory() {
        this.initializeComponent();
    }

    private void initializeComponent() {
        stateBaseClassMap = Map.of(
                SerializedStateBaseType.STATE_MACHINE, StateMachine.class,
                SerializedStateBaseType.STATE, State.class,
                SerializedStateBaseType.TRANSITION, Transition.class
        );
    }

    public List<SerializedStateBaseKeyPair> buildStateBaseList(List<SerializedStateBase> serializedStateBaseList) throws
            ModelSerializationException {
        try {
            List<SerializedStateBaseKeyPair> result = new ArrayList<>();

            for (var serializedStateBase : serializedStateBaseList.stream().filter(this::isStateBase).collect(Collectors.toList())) {
                var clazz = stateBaseClassMap.get(serializedStateBase.getStateBaseClassType());
                var ctor = clazz.getConstructor();
                StateBase stateBase = ctor.newInstance();
                stateBase.setUuid(serializedStateBase.getUuid());
                result.add(SerializedStateBaseKeyPair.of(serializedStateBase, stateBase));
            }

            return result;
        }
        catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException exc) {
            throw new ModelSerializationException();
        }
    }

    private boolean isStateBase(SerializedStateBase serializedStateBase) {
        return stateBaseClassMap.containsKey(serializedStateBase.getStateBaseClassType());
    }

    public List<StateEvent> buildStateEventList(List<SerializedContextBase> serializedStateBaseList) {
        return null;
    }

    public List<Action> buildActionList(List<SerializedContextBase> serializedContextBaseList) {
        return null;
    }

    public List<Guard> buildGuardList(List<SerializedContextBase> serializedContextBaseList) {
        return null;
    }
}
