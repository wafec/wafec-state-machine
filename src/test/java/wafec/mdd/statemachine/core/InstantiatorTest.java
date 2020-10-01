package wafec.mdd.statemachine.core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import wafec.mdd.statemachine.control.Xpr;

public class InstantiatorTest {
    @Test
    public void testNewInstance() throws ReflectiveOperationException {
        var event = new Event();
        var xpr = Instantiator.newInstance(Xpr.class, event.getStateEventContext());
        Assertions.assertTrue(xpr.isTrue());
    }

    @Test
    public void testExceptionThrown() {
        Assertions.assertThrows(ReflectiveOperationException.class, () -> {
            var event = new Event();
            Instantiator.newInstance(Xpr.class, event);
        });
    }
}
