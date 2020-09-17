package wafec.mdd.statemachine.core;

import java.util.ArrayList;
import java.util.List;

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
        if (!parameterList.contains(param)) {
            parameterList.add(param);
            dataList.add(data);
        }
    }

    public void replaceParam(String param, Object data) {
        if (parameterList.contains(param)) {
            var index = parameterList.indexOf(param);
            parameterList.remove(index);
            dataList.remove(index);
            addParam(param, data);
        }
    }
}
