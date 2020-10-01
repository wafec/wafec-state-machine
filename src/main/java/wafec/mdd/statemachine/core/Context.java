package wafec.mdd.statemachine.core;

import lombok.Getter;
import wafec.mdd.statemachine.configuration.CallParamConfiguration;
import wafec.mdd.statemachine.configuration.CallableConfiguration;
import wafec.mdd.statemachine.configuration.NamedCallConfiguration;
import wafec.mdd.statemachine.control.Xpr;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Context {
    protected Object target;
    protected Map<String, List<Method>> methodMap;
    protected Map<Class, List<Field>> fieldStateInjectMap;
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
        fieldStateInjectMapInit();
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

    protected void fieldStateInjectMapInit() {
        fieldStateInjectMap = new HashMap<>();
        for (var field : this.target.getClass().getFields()) {
            if (field.getAnnotation(StateInject.class) != null) {
                List<Field> list = null;
                if (fieldStateInjectMap.containsKey(field.getType())) {
                    list = fieldStateInjectMap.get(field.getType());
                } else {
                    list = new ArrayList<>();
                }
                list.add(field);
                fieldStateInjectMap.put(field.getType(), list);
            }
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
                handlePreInvocation(event);
                result = m.invoke(this.target, params);
            } catch (ReflectiveOperationException exc) {
                throw new RuntimeException(exc);
            }
        }
        return result;
    }

    protected void handlePreInvocation(final Event event) throws ReflectiveOperationException {
        if (fieldStateInjectMap.containsKey(Xpr.class)) {
            for (var field : fieldStateInjectMap.get(Xpr.class))
                field.set(target, Instantiator.newInstance(Xpr.class, event.getStateEventContext()));
        }
    }
}
