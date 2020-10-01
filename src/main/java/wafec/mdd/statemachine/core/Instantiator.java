package wafec.mdd.statemachine.core;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Instantiator {
    private Instantiator() {

    }

    public static <T> T newInstance(Class<T> clazz, Object... args) throws
            ReflectiveOperationException {
        if (args != null && args.length > 0) {
            var ctor = clazz.getConstructor(Stream.of(args).map(Object::getClass).toArray(Class[]::new));
            return ctor.newInstance(args);
        } else {
            return clazz.getConstructor().newInstance();
        }
    }
}
