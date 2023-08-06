package com.polotic.FiestasProvinciales.controladores;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.*;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.polotic.FiestasProvinciales.entidades.Predio;
import com.polotic.FiestasProvinciales.servicios.FiestaServicio;
import com.polotic.FiestasProvinciales.servicios.PredioServicio;


@RestController
@RequestMapping("predios")
public class PredioControlador implements WebMvcConfigurer {

    @Autowired
    FiestaServicio fiestaServicio;

    @Autowired
    PredioServicio predioServicio;

	@GetMapping
    public ModelAndView inicio()
    {
        ModelAndView maw = new ModelAndView();
        maw.setViewName("fragments/base");
        maw.addObject("titulo", "Listado de predios");
        maw.addObject("vista", "predios/inicio");
        maw.addObject("predios", predioServicio.mostrarTodos());
        return maw;
    }

	@GetMapping("/agregar")
	public ModelAndView agregar(Predio predio)
    {
        ModelAndView maw = new ModelAndView();
        maw.setViewName("fragments/base");
        maw.addObject("titulo", "Agregar predio");
        maw.addObject("vista", "predios/agregar");
        maw.addObject("predio", predio);
        return maw;
	}

	@PostMapping("/agregar")
	public ModelAndView guardar(@Valid Predio predio, BindingResult br, RedirectAttributes ra)
    {
		if ( br.hasErrors() ) {
			return this.agregar(predio);
		}

        predioServicio.guardar(predio);

        ModelAndView maw = this.inicio();
        maw.addObject("exito", "Predio agregado correctamente.");
		return maw;
	}

	@GetMapping("/editar/{id}")
    public ModelAndView editar(@PathVariable("id") Long id, Predio predio)
    {
        ModelAndView maw = new ModelAndView();
        maw.setViewName("fragments/base");
        maw.addObject("titulo", "Editar predio");
        maw.addObject("vista", "predios/editar");
        maw.addObject("predio", predioServicio.seleccionarPorId(id));

        return maw;
    }

    @PutMapping("/editar/{id}")
    public ModelAndView update(@PathVariable("id") Long id, @Valid Predio predio, BindingResult br, RedirectAttributes ra)
    {
        if ( br.hasErrors() ) {
            ModelAndView maw = new ModelAndView();
            maw.setViewName("fragments/base");
            maw.addObject("titulo", "Editar predio");
            maw.addObject("vista", "predios/editar");
            maw.addObject("predio", predio);
			return maw;
		}

        Predio registro = predioServicio.seleccionarPorId(id);
        registro.setNombre(predio.getNombre());
        registro.setCapacidad(predio.getCapacidad());
        registro.setUbicacion(predio.getUbicacion());
        registro.setUbicacionUrl(predio.getUbicacionUrl());
        ModelAndView maw = this.inicio();

        predioServicio.guardar(predio);
        maw.addObject("exito", "Predio editado correctamente.");
        return maw;
    }

    @DeleteMapping("/{id}")
    public ModelAndView delete(@PathVariable("id") Long id)
    {
        predioServicio.borrar(id);
        ModelAndView maw = this.inicio();
        maw.addObject("exito", "Predio borrado.");
		return maw;
    }
    
}