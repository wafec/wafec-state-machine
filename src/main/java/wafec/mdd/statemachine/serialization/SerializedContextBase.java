package wafec.mdd.statemachine.serialization;

import lombok.Data;

@Data
public class SerializedContextBase {
    private SerializedContextBaseType function;
    private String uuid;
    private String name;
}
