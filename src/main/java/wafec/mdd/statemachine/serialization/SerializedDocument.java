package wafec.mdd.statemachine.serialization;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class SerializedDocument {
    private List<SerializedContextBase> context;
    private SerializedStateBase machine;
}
