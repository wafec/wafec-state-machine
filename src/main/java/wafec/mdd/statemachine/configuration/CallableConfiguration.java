package wafec.mdd.statemachine.configuration;

import java.util.Optional;
import java.util.function.Consumer;

public class CallableConfiguration extends BaseConfiguration {
    public NamedCallConfiguration name(String name) {
        return addOrGet(name, NamedCallConfiguration.class)
                .hasName(name);
    }

    public CallableConfiguration name(String name, Consumer<NamedCallConfiguration> consumer) {
        consumer.accept(addOrGet(name, NamedCallConfiguration.class).hasName(name));
        return this;
    }

    public CallableConfiguration importConfiguration(CallableConfiguration otherConfig) {
        for (var keyPair : this.<NamedCallConfiguration>getConfigurationMap().entrySet()) {
            addOrGet(keyPair.getKey(), NamedCallConfiguration.class)
                    .importConfiguration(keyPair.getValue());
        }
        return this;
    }

    public Optional<NamedCallConfiguration> getNamedCallConfiguration(String name) {
        return this.<NamedCallConfiguration>getConfig(name);
    }
}
