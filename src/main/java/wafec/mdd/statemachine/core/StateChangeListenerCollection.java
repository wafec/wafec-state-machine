package wafec.mdd.statemachine.core;

import java.util.ArrayList;
import java.util.List;

public abstract class StateChangeListenerCollection<Target extends StateBase, Listener extends StateChangeListener<Target>> {
    private List<Listener> listenerList;

    public StateChangeListenerCollection() {
        this.listenerList = new ArrayList<>();
    }

    public void addListener(Listener listener) {
        this.listenerList.add(listener);
    }

    public void removeListener(Listener listener) {
        var index = this.listenerList.indexOf(listener);
        if (index >= 0) {
            this.listenerList.remove(index);
        }
    }

    public void fireChange(Target target) {
        for (var listener : this.listenerList) {
            listener.onChange(target);
        }
    }
}
