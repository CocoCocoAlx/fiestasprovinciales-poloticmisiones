package com.polotic.FiestasProvinciales.validaciones;

import javax.validation.*;
import org.springframework.beans.factory.annotation.Autowired;

import com.polotic.FiestasProvinciales.dto.RegistroDto;
import com.polotic.FiestasProvinciales.repositorios.UsuarioRepositorio;

public class EmailUnicoValidator implements ConstraintValidator<EmailUnico, Object> {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Override
    public void initialize(final EmailUnico constraintAnnotation) {
        //
    }

    @Override
    public boolean isValid(final Object objeto, final ConstraintValidatorContext context) {
        final RegistroDto registro = (RegistroDto) objeto;
        boolean esValido = ! usuarioRepositorio.existsByCorreo(registro.getCorreo());

        if (! esValido) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                .addPropertyNode( "correo" ).addConstraintViolation();
       }

       return esValido;
    }

}