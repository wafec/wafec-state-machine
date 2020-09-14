package wafec.mdd.statemachine.core;

import com.google.common.collect.Lists;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StateBase {
    @Getter @Setter
    private String uuid;
    @Setter @Getter
    private String name;
    private StateBase parent;
    private boolean active;
    private int activeCount;
    private List<StateBase> parentList;
    private ParentChangeListenerCollection parentChangeListenerCollection;
    @Setter(AccessLevel.PRIVATE)
    @Getter
    private StateContext stateContext;
    private List<StateBase> arrowList;
    @Getter
    protected boolean stateTransitionConsumer;
    @Getter
    protected boolean countable;
    private StateTransition currentStateTransition;
    @Getter @Setter
    private boolean initial;

    public StateBase() {
        this(null);
    }

    public StateBase(StateContext stateContext) {
        this.stateContext = Optional.ofNullable(stateContext).orElseGet(StateContext::new);
        this.initializeComponent();
    }

    private void initializeComponent() {
        uuid = UUID.randomUUID().toString();
        activeCount = 0;
        parentList = new ArrayList<>();
        this.parentChangeListenerCollection = new ParentChangeListenerCollection();
        this.arrowList = new ArrayList<>();
        this.stateTransitionConsumer = false;
        this.countable = true;
    }

    public void setActive(boolean active) {
        if (active == this.active)
            return;
        this.active = active;
        if (this.countable) {
            var value = active ? 1 : -1;
            for (var parent : parentList) {
                parent.setActiveCount(parent.activeCount + value);
            }
        }
        this.updateStateContextActive();
    }

    private void setActiveCount(int activeCount) {
        if (activeCount == this.activeCount || !this.countable)
            return;
        this.activeCount = activeCount;
        this.updateStateContextActive();
    }

    private void updateStateContextActive() {
        if (this.countable) {
            if (this.isActive()) {
                this.stateContext.addActive(this);
            } else {
                this.stateContext.removeActive(this);
            }
        }
    }

    public boolean isActive() {
        return this.active || this.activeCount > 0;
    }

    public boolean isNotActive() {
        return !this.isActive();
    }

    public void setParent(StateBase parent) {
        if (parent == this.parent)
            return;
        if (this.parent != null)
            this.parent.parentChangeListenerCollection.removeListener(this::handleParentChange);
        if (this.isActive()) {
            Lists.reverse(this.parentList).forEach(p -> p.setActiveCount(p.activeCount - 1));
        }
        this.parent = parent;
        this.stateContext = this.parent.stateContext;
        parent.parentChangeListenerCollection.addListener(this::handleParentChange);
        this.parentList = Stream.concat(parent.parentList.stream(), Stream.of(parent)).collect(Collectors.toList());
        this.parentChangeListenerCollection.fireChange(this);
        if (this.isActive()) {
            Lists.reverse(this.parentList).forEach(p -> p.setActiveCount(p.activeCount + 1));
        }
    }

    private void handleParentChange(StateBase target) {
        int index = 0;
        for (; index < parentList.size() && parentList.get(index) != target; index++)
            ;
        if (index < parentList.size()) {
            var subList = parentList.subList(index, parentList.size());
            parentList = Stream.concat(target.parentList.stream(), subList.stream()).collect(Collectors.toList());
            this.parentChangeListenerCollection.fireChange(this);
        }
    }

    public void accept(StateEvent stateEvent) {
        stateEvent.historyPush(this);
        if (this.stateTransitionConsumer) {
            for (var arrow : this.arrowList) {
                arrow.accept(StateTransition.of(stateEvent, this));
            }
        }
    }

    public void accept(StateTransition stateTransition) {
        stateTransition.getStateEvent().historyPush(this);
        this.currentStateTransition = stateTransition;
        if (this.stateTransitionConsumer) {
            this.stateContext.beginTransaction();
            if (!this.isInitial())
                stateTransition.getSource().setActive(false);
            this.setActive(true);
            this.stateContext.endTransaction();
            this.accept(stateTransition.getStateEvent());
        } else {
            for (var arrow : arrowList) {
                arrow.accept(stateTransition);
            }
        }
        this.currentStateTransition = null;
    }

    public void addArrow(StateBase arrow) {
        var index = this.arrowList.indexOf(arrow);
        if (index < 0) {
            this.arrowList.add(arrow);
        }
    }

    public void removeArrow(StateBase arrow) {
        var index = this.arrowList.indexOf(arrow);
        if (index >= 0) {
            this.arrowList.remove(index);
        }
    }

    public final void handleEntry() {
        this.handleEntry(this.currentStateTransition);
    }

    public void handleEntry(StateTransition stateTransition) {

    }

    public final void handleExit() {
        this.handleExit(this.currentStateTransition);
    }

    public void handleExit(StateTransition stateTransition) {

    }

    public String getQualifiedName() {
        return Optional.ofNullable(this.name).orElse(this.uuid);
    }

    @Override
    public String toString() {
        return String.format("%s@%s", this.getClass().getCanonicalName(), this.getQualifiedName());
    }
}