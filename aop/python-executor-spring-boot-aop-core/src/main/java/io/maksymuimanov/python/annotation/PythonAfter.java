package io.maksymuimanov.python.annotation;

import org.springframework.core.env.Environment;

import java.lang.annotation.*;

/**
 * Annotation to execute a Python script after the annotated method completes.
 * <p>
 * When a method is annotated with {@code @PythonAfter}, the specified Python script
 * or file will be executed immediately after the method finishes execution.
 * </p>
 * <p>
 * This annotation supports specifying a Python script as inline code or as a file path.
 * Additionally, execution can be restricted to certain Spring profiles using
 * {@link #activeProfiles()}.
 * </p>
 * <p>
 * Example usage:
 * <pre>{@code
 * @PythonAfter("my_script.py")
 * public void myMethod() {
 *     // method logic
 * }
 * }</pre>
 * </p>
 *
 * @see Environment
 * @author w4t3rcs
 * @since 1.0.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface PythonAfter {
    String name();

    boolean isFile() default false;

    String script() default "";

    /**
     * Spring profiles under which this Python script should be executed.
     * <p>
     * If empty, the script executes regardless of active profiles.
     * If one or more profiles are specified, the script will execute only if
     * at least one of these profiles is active in the current
     * {@link Environment}.
     * </p>
     * <p>
     * Implementations should verify active profiles at runtime before executing the script.
     * </p>
     *
     * @return array of profile names, never {@code null}, may be empty
     */
    String[] activeProfiles() default {};
}