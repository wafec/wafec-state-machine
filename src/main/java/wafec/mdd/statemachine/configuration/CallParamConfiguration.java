package wafec.mdd.statemachine.configuration;

import lombok.Getter;

import java.util.Optional;

public class CallParamConfiguration extends BaseConfiguration {
    @Getter
    private String name;
    @Getter
    private Integer position;

    public CallParamConfiguration hasName(String name) {
        this.name = name;
        return this;
    }

    public CallParamConfiguration atPosition(int position) {
        this.position = position;
        return this;
    }

    public CallParamConfiguration importConfiguration(CallParamConfiguration otherConfig) {
        name = otherConfig.getName();
        position = otherConfig.getPosition();
        return this;
    }

    public Optional<String> getNameOpt() {
        return Optional.ofNullable(name);
    }

    public Optional<Integer> getPositionOpt() {
        return Optional.ofNullable(position);
    }
}
