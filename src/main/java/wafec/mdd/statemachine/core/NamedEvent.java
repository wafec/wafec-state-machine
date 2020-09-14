package wafec.mdd.statemachine.core;

public class NamedEvent extends StateEvent {
    private String uuid;

    public NamedEvent(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public String getUUID() {
        return uuid;
    }
}
