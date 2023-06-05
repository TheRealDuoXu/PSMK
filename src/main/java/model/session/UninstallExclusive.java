package model.session;

import java.lang.annotation.*;

/**
 * Marks methods for exclusive uninstalling use
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface UninstallExclusive {
}
