package wafec.mdd.statemachine.core;


import java.util.ArrayList;
import java.util.List;

public class StateEventContext {
    private List<Point> pointList;

    public StateEventContext() {
        this.initializeComponent();
    }

    private void initializeComponent() {
        pointList = new ArrayList<>();
    }

    public void addPoint(Point point) {
        this.pointList.add(point);
    }

    public Point getLastPoint() {
        if (this.pointList.size() > 0)
            return this.pointList.get(this.pointList.size() - 1);
        return null;
    }
}
