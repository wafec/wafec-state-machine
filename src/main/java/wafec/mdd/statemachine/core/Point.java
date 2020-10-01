package wafec.mdd.statemachine.core;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "of")
public class Point {
    private StateBase stateBase;
    private Double score;
    private String command;
}
