package wafec.mdd.statemachine.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public abstract class StateEvent {
    private Object[] variableList;
    private String[] variableNameList;
    private Map<String, Integer> variableNameMap;
    private List<StateBase> history;

    public StateEvent() {
        this(null, null);
    }

    public StateEvent(String[] variableNameList, Object[] variableList) {
        this.variableList = variableList;
        this.variableNameList = variableNameList;

        this.initializeComponent();
    }

    private void initializeComponent() {
        history = new ArrayList<>();

        if (variableNameList != null) {
            variableNameMap = new HashMap<>();
            for (int i = 0; i < variableNameList.length; i++) {
                variableNameMap.put(variableNameList[i], i);
            }
        }
    }

    public boolean match(StateEvent otherStateEvent) {
        if (otherStateEvent == null)
            return false;
        return this.getUUID().equals(otherStateEvent.getUUID());
    }

    public abstract String getUUID();

    public Object get(String name) {
        if (!hasVariable(name))
            return null;
        var index = variableNameMap.get(name);
        return variableList[index];
    }

    public Object get(int index) {
        if (!hasVariables())
            return null;
        validateVariableListIndex(index);
        return variableList[index];
    }

    public void update(String name, Object value) {
        if (!hasVariable(name))
            return;
        var index = variableNameMap.get(name);
        variableList[index] = value;
    }

    public void update(int index, Object value) {
        if (!hasVariables())
            return;
        validateVariableListIndex(index);
        variableList[index] = value;
    }

    public boolean hasVariables() {
        return variableNameList != null && variableNameList.length > 0;
    }

    public boolean hasVariable(String name) {
        return hasVariables() && variableNameMap.containsKey(name);
    }

    private void validateVariableListIndex(int index) {
        if (index < 0 || index >= variableList.length) {
            throw new IndexOutOfBoundsException();
        }
    }

    public Object[] getVariableList() {
        return variableList;
    }

    public String[] getVariableNameList() {
        return variableNameList;
    }

    public Class[] getVariableClassList() {
        return Stream.of(variableList).map(Object::getClass).toArray(Class[]::new);
    }

    public void historyPush(StateBase stateBase) {
        history.add(stateBase);
    }

    public List<StateBase> getHistory() {
        return history;
    }
}
