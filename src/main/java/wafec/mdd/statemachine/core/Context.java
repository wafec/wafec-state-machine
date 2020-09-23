package wafec.mdd.statemachine.core;

import lombok.Getter;
import wafec.mdd.statemachine.configuration.CallParamConfiguration;
import wafec.mdd.statemachine.configuration.CallableConfiguration;
import wafec.mdd.statemachine.configuration.NamedCallConfiguration;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Context {
    protected Object target;
    protected Map<String, List<Method>> methodMap;
    @Getter
    protected CallableConfiguration configuration;

    public Context(Object target) {
        this.target = target;
        initializeComponent();
    }

    private void initializeComponent() {
        if (target == null)
            throw new IllegalStateException();
        methodMapInit();
        configuration = new CallableConfiguration();
    }

    protected void methodMapInit() {
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
        Object result = null;
        var methodName = configuration.getNamedCallConfiguration(name).flatMap(NamedCallConfiguration::getNameOpt).orElse(name);
        if (methodMap.containsKey(methodName)) {
            Method m = methodMap.get(methodName).get(0);
            final Object[] params = new Object[m.getParameterCount()];
            if (params.length > 0) {
                if (m.getParameters()[0].getParameterizedType().equals(Event.class)) {
                    params[0] = event;
                } else {
                    var methodConfigurationOpt = configuration.getNamedCallConfiguration(name);
                    if (methodConfigurationOpt.isPresent()) {
                        for (var paramData : event.getParameterDataList()) {
                            methodConfigurationOpt.orElseThrow()
                                    .getCallParamConfiguration(paramData.getName())
                                    .flatMap(CallParamConfiguration::getPositionOpt)
                                    .ifPresent(position -> params[position] = paramData.getData());
                        }
                    }
                }
            }
            try {
                result = m.invoke(this.target, params);
            } catch (IllegalAccessException | InvocationTargetException exc) {
                throw new RuntimeException(exc);
            }
        }
        return result;
    }
}
