package com.polotic.FiestasProvinciales.controladores;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.*;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.polotic.FiestasProvinciales.entidades.Rol;
import com.polotic.FiestasProvinciales.servicios.RolServicio;


@RestController
@RequestMapping("roles")
public class RolControlador implements WebMvcConfigurer {

	@Autowired
    RolServicio rolServicio;

	@GetMapping
    private ModelAndView inicio()
    {
        ModelAndView maw = new ModelAndView();
        maw.setViewName("fragments/base");
        maw.addObject("titulo", "Listado de roles");
        maw.addObject("vista", "roles/inicio");
        maw.addObject("roles", rolServicio.mostrarTodos());
        return maw;
    }

	@GetMapping("/{id}")
    private ModelAndView ver(@PathVariable("id") Long id)
    {
        ModelAndView maw = new ModelAndView();
        maw.setViewName("fragments/base");
        maw.addObject("titulo", "Detalle del rol #" + id);
        maw.addObject("vista", "roles/ver");
        maw.addObject("rol", rolServicio.seleccionarPorId(id));
        return maw;
    }

	@GetMapping("/crear")
	public ModelAndView crear(Rol rol)
    {
        ModelAndView maw = new ModelAndView();
        maw.setViewName("fragments/base");
        maw.addObject("titulo", "Crear rol");
        maw.addObject("vista", "roles/crear");
        return maw;
	}

	@PostMapping("/crear")
	public ModelAndView guardar(@Valid Rol rol, BindingResult br, RedirectAttributes ra)
    {
		if ( br.hasErrors() ) {
			return this.crear(rol);
		}

		rolServicio.guardar(rol);
        
        ModelAndView maw = this.inicio();
        maw.addObject("exito", "Rol guardado exitosamente");
		return maw;
	}

    @DeleteMapping("/{id}")
    private ModelAndView borrar(@PathVariable("id") Long id)
    {
        rolServicio.borrar(id);
        ModelAndView maw = this.inicio();
        maw.addObject("exito", "Rol borrado exitosamente");
		return maw;
    }
    
}