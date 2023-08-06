package com.polotic.FiestasProvinciales.controladores;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.polotic.FiestasProvinciales.entidades.Provincia;
import com.polotic.FiestasProvinciales.servicios.ProvinciaServicio;

@RestController
@RequestMapping("/provincias")

public class ProvinciaControlador implements WebMvcConfigurer {

    @Autowired
    ProvinciaServicio provinciaServicio;

    @GetMapping
    private ModelAndView inicio()
    {
        ModelAndView maw = new ModelAndView();
        maw.setViewName("fragments/base");
        maw.addObject("titulo", "Listado de provincias");
        maw.addObject("vista", "provincias/inicio");
        maw.addObject("provincias", provinciaServicio.mostrarTodos());
        
        return maw;

    }

    @GetMapping("/agregar")
    private ModelAndView agregar(Provincia provincia)
    {
        ModelAndView maw = new ModelAndView();
        maw.setViewName("fragments/base");
        maw.addObject("titulo", "Agregar provincia");
        maw.addObject("vista", "provincias/agregar");
        maw.addObject("localidad", provinciaServicio.mostrarTodos());
        maw.addObject("fiesta", provinciaServicio.mostrarTodos());
        return maw;

    }

    @PostMapping("/agregar")
    public ModelAndView guardar(@Valid Provincia provincia, BindingResult br, RedirectAttributes ra)
    {
        
        if (br.hasErrors()) {
            return this.agregar(provincia);
        }

       provinciaServicio.guardar(provincia);
      
        ModelAndView maw = this.inicio();   

        provinciaServicio.guardar(provincia);
        maw.addObject("correcto", "El archivo fue cargado exitosamente");
        return maw;
    }

    @GetMapping("/editar/{id}")
    private ModelAndView editar(@PathVariable("id") Long id, Provincia provincia, boolean estaGuardado) {
        ModelAndView maw = new ModelAndView();
        maw.setViewName("fragments/base");
        maw.addObject("titulo", "Editar provincia");
        maw.addObject("vista", "provincia/editar");
        

        if (estaGuardado)
            maw.addObject("provincia", provinciaServicio.seleccionarPorId(id));

        return maw;
    }

    @PutMapping("/editar/{id}")
    private ModelAndView actualizar(@PathVariable("id") Long id,
    @Valid Provincia provincia, BindingResult br, RedirectAttributes ra)
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       
    {
        if (br.hasErrors())
        {
        ModelAndView maw = new ModelAndView();
        maw.setViewName("fragments/base");
        maw.addObject("titulo", "Editar provincia");
        maw.addObject("vista", "provincia/editar");
        return maw;

        }

        Provincia registro = provinciaServicio.seleccionarPorId(id);
        registro.setNombre(provincia.getNombre());
        registro.setInformacion(provincia.getInformacion());
        ModelAndView maw = this.inicio();

        
        provinciaServicio.guardar(provincia);
        maw.addObject("correcto","Provincia editada correctamente.");
        return maw;
    }

    @DeleteMapping("/id")
    private ModelAndView borrar(@PathVariable("id") Long id)
    {
        provinciaServicio.borrar(id);
        ModelAndView maw = this.inicio();
        maw.addObject("correcto", "Provincia eliminada correctamente.");
        return maw;
    }
    
}
