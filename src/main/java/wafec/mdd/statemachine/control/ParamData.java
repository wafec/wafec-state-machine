package wafec.mdd.statemachine.control;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "of")
public class ParamData {
    private String name;
    private Object data;
}
