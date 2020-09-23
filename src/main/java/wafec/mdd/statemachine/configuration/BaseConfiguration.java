package wafec.mdd.statemachine.configuration;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class BaseConfiguration {
    protected Map<String, Object> configurationMap;

    public BaseConfiguration() {
        initializeComponent();
    }

    protected void initializeComponent() {
        configurationMap = new HashMap<>();
    }

    public <TConfiguration extends BaseConfiguration> TConfiguration addOrGet(String key, Class<TConfiguration> clazz) {
        if (configurationMap.containsKey(key))
            return (TConfiguration) configurationMap.get(key);
        try {
            TConfiguration configuration = clazz.getConstructor().newInstance();
            configurationMap.put(key, configuration);
            return configuration;
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException exc) {
            throw new IllegalArgumentException();
        }
    }

    public <TConfiguration extends BaseConfiguration> Map<String, TConfiguration> getConfigurationMap() {
        Map<String, TConfiguration> map = new HashMap<>();
        for (var keyPair : configurationMap.entrySet()) {
            map.put(keyPair.getKey(), (TConfiguration) keyPair.getValue());
        }
        return map;
    }

    public <TConfiguration extends BaseConfiguration> Optional<TConfiguration> getConfig(String key) {
        if (configurationMap.containsKey(key))
            return Optional.of((TConfiguration) configurationMap.get(key));
        return Optional.empty();
    }
}
