package wafec.mdd.statemachine.core;

import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@ToString
public class StateContext {
    @Getter
    private List<StateBase> activeList;
    @Getter
    private boolean transaction;
    private List<StateContextChange> contextChangeList;
    private StateBase root;

    public StateContext() {
        root = null;
        this.initializeComponent();
    }

    private void initializeComponent() {
        this.activeList = new ArrayList<>();
        this.transaction = false;
        this.contextChangeList = new ArrayList<>();
    }

    public void addActive(StateBase stateBase) {
        var index = this.activeList.indexOf(stateBase);
        if (index < 0) {
            this.activeList.add(stateBase);
            if (this.isTransaction()) {
                this.contextChangeList.add(StateContextChange.of(StateContextChangeType.ADD, stateBase));
            } else {
                stateBase.handleEntry();
            }
        }
    }

    public void removeActive(StateBase stateBase) {
        var index = this.activeList.indexOf(stateBase);
        if (index >= 0) {
            this.activeList.remove(index);
            if (this.isTransaction()) {
                this.contextChangeList.add(StateContextChange.of(StateContextChangeType.REMOVE, stateBase));
            } else {
                stateBase.handleExit();
            }
        }
    }

    public void fireEvent(StateEvent stateEvent) {
        for (var active : Lists.reverse(this.activeList)) {
            if (active.isActive()) {
                active.accept(stateEvent);
            }
        }
    }

    public void beginTransaction() {
        this.transaction = true;
    }

    public void endTransaction() {
        this.handleTransactionEnd();
        this.transaction = false;
    }

    private void handleTransactionEnd() {
        for (var change : this.contextChangeList) {
            var changeType = change.getChangeType();
            var target = change.getTarget();
            if (changeType.equals(StateContextChangeType.ADD) && target.isActive()) {
                target.handleEntry();
            } else if (changeType.equals(StateContextChangeType.REMOVE) && target.isNotActive()) {
                target.handleExit();
            }
        }
        this.contextChangeList = new ArrayList<>();
    }

    public void setRoot(StateBase stateBase) {
        if (this.root != stateBase) {
            this.root = stateBase;
        }
    }

    public void start() {
        if (this.root != null) {
            this.root.accept(new VoidEvent());
        }
    }
}
