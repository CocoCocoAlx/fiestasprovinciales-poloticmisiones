package com.polotic.FiestasProvinciales.validaciones;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.*;
import javax.validation.*;

@Target({TYPE, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = EmailUnicoValidator.class)
@Documented
public @interface EmailUnico {
    String message() default "Error: Ya existe un usuario con esta dirección de correo.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}