package model.database.containers;

import java.lang.annotation.*;

/**
 * Marks a container class object as inmutable, all fields must be final
 */
@Documented
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface Inmutable {
}
