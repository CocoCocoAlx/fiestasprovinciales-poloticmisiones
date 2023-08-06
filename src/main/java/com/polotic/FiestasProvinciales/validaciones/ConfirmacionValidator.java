package com.polotic.FiestasProvinciales.validaciones;

import javax.validation.*;

import com.polotic.FiestasProvinciales.dto.RegistroDto;

public class ConfirmacionValidator implements ConstraintValidator<Confirmacion, Object> {

    @Override
    public void initialize(final Confirmacion constraintAnnotation) {
        //
    }

    @Override
    public boolean isValid(final Object objeto, final ConstraintValidatorContext context) {
        final RegistroDto registro = (RegistroDto) objeto;
        boolean esValido = registro.getClave().equals(registro.getConfirmacion());

        if(! esValido){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                .addPropertyNode( "confirmacion" ).addConstraintViolation();
       }

       return esValido;
    }

}