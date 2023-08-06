package com.polotic.FiestasProvinciales.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.polotic.FiestasProvinciales.repositorios.FiestaRepositorio;

@RestController
public class InicioControlador {
    
    @Autowired
    FiestaRepositorio fiestaRepositorio;

    @RequestMapping("/inicio")
    public ModelAndView inicio()
    {
        ModelAndView maw = new ModelAndView();
        maw.setViewName("fragments/base");
        maw.addObject("titulo", "Fiestas Provinciales - Inicio");
        maw.addObject("vista", "inicio/inicio");
        return maw;
    }
}
