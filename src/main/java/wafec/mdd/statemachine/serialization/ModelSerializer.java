package wafec.mdd.statemachine.serialization;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import wafec.mdd.statemachine.core.StateBase;

import java.io.File;
import java.io.IOException;

public class ModelSerializer {
    public StateBase deserialize(String path) throws IOException {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        SerializedDocument document = mapper.readValue(new File(path), SerializedDocument.class);
        return deserialize(new ModelSerializerBuilder(document));
    }

    private StateBase deserialize(ModelSerializerBuilder serializerContext) {

        return null;
    }
}
