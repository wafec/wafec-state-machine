package wafec.mdd.statemachine.serialization;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class SerializedStateBase {
    @JsonProperty("class")
    private SerializedStateBaseType stateBaseClassType;
    private String uuid;
    private String name;
    private List<SerializedStateBase> children;
}
