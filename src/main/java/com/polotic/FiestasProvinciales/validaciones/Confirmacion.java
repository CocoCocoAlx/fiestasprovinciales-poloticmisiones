package com.polotic.FiestasProvinciales.validaciones;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.*;
import javax.validation.*;

@Target({TYPE, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = ConfirmacionValidator.class)
@Documented
public @interface Confirmacion {
    String message() default "Error: las contraseñas no coinciden.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}