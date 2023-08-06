package com.polotic.FiestasProvinciales.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.polotic.FiestasProvinciales.validaciones.Confirmacion;
import com.polotic.FiestasProvinciales.validaciones.EmailUnico;

import lombok.Data;

@Data
@Confirmacion
@EmailUnico
public class RegistroDto {

    @NotNull
    @NotEmpty(message = "Ingrese una dirección de correo electrónico")
    @Email(message = "Ingrese una dirección de correo electrónico válida")
    private String correo;

    @NotNull
    @NotEmpty(message = "Ingrese una contraseña")
    private String clave;

    private String confirmacion;

    private String recaptcha;
}
