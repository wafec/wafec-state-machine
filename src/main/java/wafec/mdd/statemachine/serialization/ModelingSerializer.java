package wafec.mdd.statemachine.serialization;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import wafec.mdd.statemachine.core.StateBase;
import wafec.mdd.statemachine.modeling.State;

import java.io.File;
import java.io.IOException;

public class ModelingSerializer {
    public StateBase deserialize(String path) throws IOException {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        SerializedStateBase serializedStateBase = mapper.readValue(new File(path), SerializedStateBase.class);
        return deserialize(new ModelingSerializerContext(serializedStateBase));
    }

    private StateBase deserialize(ModelingSerializerContext serializerContext) {

        return null;
    }
}
