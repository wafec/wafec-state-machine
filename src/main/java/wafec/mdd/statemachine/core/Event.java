package wafec.mdd.statemachine.core;

import lombok.Setter;
import wafec.mdd.statemachine.control.ParamData;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class Event extends StateEvent {
    protected List<String> parameterList;
    protected List<Object> dataList;

    @Override
    public void initializeComponent() {
        super.initializeComponent();
        parameterList = new ArrayList<>();
        dataList = new ArrayList<>();
    }

    public void addParam(String param, Object data) {
        addParam(param, data, parameterList.size());
    }

    public void addParam(String param, Object data, Integer position) {
        if (!parameterList.contains(param)) {
            parameterList.add(position, param);
            dataList.add(position, data);
        }
    }

    public void replaceParam(String param, Object data) {
        if (parameterList.contains(param)) {
            var index = parameterList.indexOf(param);
            parameterList.remove(index);
            dataList.remove(index);
            addParam(param, data, index);
        }
    }

    public ParamData[] getParameterDataList() {
        ParamData[] arr = new ParamData[parameterList.size()];
        Iterator<String> parameterIt = parameterList.iterator();
        Iterator<Object> dataIt = dataList.iterator();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = ParamData.of(parameterIt.next(), dataIt.next());
        }
        return arr;
    }

    public static Event of(StateEvent stateEvent) {
        var event = new Event();
        event.id = stateEvent.id;
        return event;
    }

    public static Event epsilon() {
        return of(StateEvent.epsilon());
    }
}
