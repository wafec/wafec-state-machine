package wafec.mdd.statemachine.configuration;

import lombok.Getter;

import java.util.Optional;
import java.util.function.Consumer;

public class NamedCallConfiguration extends BaseConfiguration {
    @Getter
    private String name;

    public NamedCallConfiguration hasName(String name) {
        this.name = name;
        return this;
    }

    public CallParamConfiguration parameter(String name) {
        return addOrGet(name, CallParamConfiguration.class)
                .hasName(name);
    }

    public NamedCallConfiguration parameter(String name, Consumer<CallParamConfiguration> consumer) {
        consumer.accept(addOrGet(name, CallParamConfiguration.class).hasName(name));
        return this;
    }

    public NamedCallConfiguration importConfiguration(NamedCallConfiguration otherConfig) {
        this.name = otherConfig.getName();
        for (var keyPair : this.<CallParamConfiguration>getConfigurationMap().entrySet()) {
            addOrGet(keyPair.getKey(), CallParamConfiguration.class)
                .importConfiguration(keyPair.getValue());
        }
        return this;
    }

    public Optional<CallParamConfiguration> getCallParamConfiguration(String name) {
        return this.<CallParamConfiguration>getConfig(name);
    }

    public Optional<String> getNameOpt() {
        return Optional.ofNullable(name);
    }
}
