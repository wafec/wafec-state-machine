package wafec.mdd.statemachine.core;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class StateBase {
    protected String id;
    @Getter @Setter
    protected String name;
    protected List<StateBase> hierarchy;
    protected List<StateBase> entrySet;
    protected List<StateBase> arrowSet;
    protected StateBase parent;
    protected List<StateBase> children;
    @Getter
    protected boolean active;
    protected boolean pseudo;
    protected List<StateBase> subElementList;
    protected Context outerContext;
    protected Context context;

    public StateBase() {
        initializeComponent();
    }

    public void initializeComponent() {
        id = UUID.randomUUID().toString();
        entrySet = new ArrayList<>();
        arrowSet = new ArrayList<>();
        children = new ArrayList<>();
        hierarchy = new ArrayList<>();
        subElementList = new ArrayList<>();
    }

    public StateBase[] getHierarchy() {
        var result = new StateBase[hierarchy.size()];
         hierarchy.toArray(result);
         return result;
    }

    public void accept(StateEvent stateEvent) {
        if (!isActive())
            return;
        for (var child : subElementList) {
            child.accept(stateEvent);
        }
        StateTransition stateTransition = StateTransition.of(stateEvent, this);
        for (var arrow : arrowSet) {
            arrow.accept(stateTransition);
        }
    }

    public void accept(StateTransition stateTransition) {
        stateTransition.addTarget(this);
        transit(stateTransition);
    }

    public void transit(StateTransition stateTransition) {
        var source = stateTransition.getSource();
        var target = stateTransition.getTarget();
        var sourceHierarchyRootDifferent = source.findHierarchyRootDifferent(target);
        var targetHierarchyRootDifferent = target.findHierarchyRootDifferent(source);
        sourceHierarchyRootDifferent.exit();
        targetHierarchyRootDifferent.entry();
    }

    public StateBase findHierarchyRootDifferent(StateBase other) {
        int l = Math.min(hierarchy.size(), other.hierarchy.size());
        for (int i = 0; i < l; i++) {
            if (!hierarchy.get(i).id.equals(other.hierarchy.get(i).id)) {
                return hierarchy.get(i);
            }
        }
        return this;
    }

    public void exit() {
        for (var child : children) {
            child.exit();
        }
        if (active) {
            handleExit();
            active = false;
        }
    }

    public void handleExit() {

    }

    public void entry() {
        if (!active) {
            active = true;
            handleEntry();
        }
        for (var child : entrySet) {
            child.entry();
        }
    }

    public void handleEntry() {

    }

    public void setParent(StateBase parent) {
        var prevParent = this.parent;
        this.parent = parent;
        updateParentChild(prevParent);
        updateParent();
        updateContext();
    }

    public void updateContext() {
        if (this.parent != null) {
            if (this.context == null && this.outerContext != this.parent.getContext()) {
                this.outerContext = this.parent.getContext();
                this.updateChildrenContext();
            }
        }
    }

    public void updateParent() {
        var newHierarchy = new ArrayList<StateBase>();
        if (parent != null) {
            newHierarchy.addAll(parent.hierarchy);
            newHierarchy.add(parent);
        }
        if (!isHierarchyExactlyEqual(newHierarchy)) {
            hierarchy = newHierarchy;
            for (var child : children) {
                child.updateParent();
            }
            for (var child : entrySet) {
                child.updateParent();
            }
            for (var arrow : arrowSet) {
                arrow.updateParent();
            }
        }
    }

    public boolean isHierarchyExactlyEqual(List<StateBase> other) {
        var thisHierarchy = hierarchy;
        if (thisHierarchy == null)
            thisHierarchy = new ArrayList<>();
        if (other.size() != this.hierarchy.size())
            return false;
        for (int i = 0; i < thisHierarchy.size(); i++) {
            if (!thisHierarchy.get(i).id.equals(other.get(i).id))
                return false;
        }
        return true;
    }

    public void updateParentChild(StateBase prevParent) {
        if (prevParent != null) {
            prevParent.children = prevParent.children.stream().filter(c -> !c.id.equals(this.id))
                    .collect(Collectors.toList());
            prevParent.updateSubElementList();
        }
        if (this.parent != null && this.parent.children.stream().noneMatch(c -> c.id.equals(this.id))) {
            this.parent.children.add(this);
            this.parent.updateSubElementList();
        }
    }

    public void updateSubElementList() {
        subElementList = this.children.stream().filter(c -> !c.pseudo).collect(Collectors.toList());
    }

    public void addArrow(StateBase arrow) {
        this.arrowSet.add(arrow);
    }

    public void addInitial(StateBase initial) {
        this.entrySet.add(initial);
    }

    public void setContext(Context context) {
        this.context = context;
        updateChildrenContext();
    }

    public void updateChildrenContext() {
        for (var child : children) {
            child.updateChildContext();
        }
        for (var child : arrowSet) {
            child.updateChildContext();
        }
        for (var child : entrySet) {
            child.updateChildContext();
        }
    }

    public void updateChildContext() {
        if (parent == null)
            throw new IllegalStateException();
        if (this.outerContext != parent.getContext()) {
            this.outerContext = parent.getContext();
            updateChildrenContext();
        }
    }

    public Context getContext() {
        return Optional.ofNullable(context).orElse(outerContext);
    }
}
