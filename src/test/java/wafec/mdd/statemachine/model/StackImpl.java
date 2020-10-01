package wafec.mdd.statemachine.model;

import lombok.Data;
import wafec.mdd.statemachine.control.Xpr;
import wafec.mdd.statemachine.core.StateInject;

import java.util.ArrayList;
import java.util.List;

@Data
public class StackImpl {
    private int size;
    private int limit;
    private List<Integer> stack;

    @StateInject
    public Xpr xpr;

    public StackImpl() {
        this(5);
    }

    public StackImpl(int limit) {
        size = 0;
        this.limit = limit;
        stack = new ArrayList<>();
    }

    public void push(int element) {
        size++;
        stack.add((element));
    }

    public void pop() {
        size--;
    }

    public boolean guardIfFull() {
        return xpr.equal(xpr.of(size), xpr.of(limit - 1)).isTrue();
    }

    public boolean guardIfEmpty() {
        return size == 0;
    }

    public boolean guardIfNotFull() {
        return !guardIfFull();
    }
}
