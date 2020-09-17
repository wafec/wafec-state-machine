package wafec.mdd.statemachine.core;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Context {
    protected Object target;
    protected Map<String, List<Method>> methodMap;

    public Context(Object target) {
        this.target = target;
        initializeComponent();
    }

    private void initializeComponent() {
        if (target == null)
            throw new IllegalStateException();
        methodMap = new HashMap<>();
        for (var method : this.target.getClass().getMethods()) {
            List<Method> list = null;
            if (methodMap.containsKey(method.getName()))
                list = methodMap.get(method.getName());
            else
                list = new ArrayList<>();
            list.add(method);
            methodMap.put(method.getName(), list);
        }
    }

    public Object call(String name, Event event) {
        return null;
    }
}
