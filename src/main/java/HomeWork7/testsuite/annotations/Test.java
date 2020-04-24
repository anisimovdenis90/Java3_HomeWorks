package HomeWork7.testsuite.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Test {

    MethodPriority priority() default Test.MethodPriority.FIVE;

    enum MethodPriority {
        ONE(1),
        TWO(2),
        THREE(3),
        FOUR(4),
        FIVE(5),
        SIX(6),
        SEVEN(7),
        EIGHT(8),
        NINE(9),
        TEN(10);

        private final int priority;

        MethodPriority(int priority) {
            this.priority = priority;
        }

        public int getIntPriority() {
            return priority;
        }
    }
}