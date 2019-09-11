package photos.brooklyn.effectivejava.annotations.unittest;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * item 39
 */
@Retention(RetentionPolicy.RUNTIME) // makes it available during run time
@Target(ElementType.METHOD) // annotation is only for methods
public @interface Test {
}
