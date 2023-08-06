package com.polotic.FiestasProvinciales.controladores;

import java.io.File;
import java.nio.file.Paths;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.polotic.FiestasProvinciales.entidades.Artista;
import com.polotic.FiestasProvinciales.servicios.FiestaServicio;
import com.polotic.FiestasProvinciales.servicios.ArtistaServicio;

@RestController
@RequestMapping("artistas")
public class ArtistaControlador implements WebMvcConfigurer {

    @Autowired
    ArtistaServicio artistaServicio;

    @Autowired
    FiestaServicio fiestaServicio;

    @GetMapping
    private ModelAndView inicio() {
        ModelAndView maw = new ModelAndView();
        maw.setViewName("fragments/base");
        maw.addObject("titulo", "Listado de festividades");
        maw.addObject("vista", "artista/inicio");
        maw.addObject("artistas", artistaServicio.mostrarTodos());
        return maw;

    }
   @GetMapping("/{id}")
    private ModelAndView uno(@PathVariable("id") Long id) {
        ModelAndView maw = new ModelAndView();
        maw.setViewName("fragments/base");
        maw.addObject("titulo", "Detalle de la festividad #" + id);
        maw.addObject("vista", "artista/ver");
        maw.addObject("artistas", artistaServicio.seleccionarPorId(id));
        return maw;
    }

    @GetMapping("/agregar")
    private ModelAndView agregar(Artista artista) {
        ModelAndView maw = new ModelAndView();
        maw.setViewName("fragments/base");
        maw.addObject("titulo", "Agregar festividad");
        maw.addObject("vista", "artista/agregar");
        return maw;

    }

    @PostMapping("/agregar")
    public ModelAndView guardar(@RequestParam("archivo") MultipartFile archivo,
            @Valid Artista artista, BindingResult br, RedirectAttributes ra) {
        if (archivo.isEmpty())
            br.reject("archivo", "Por favor, cargar un archivo válido");

        if (br.hasErrors()) {
            return this.agregar(artista);
        }

        artistaServicio.guardar(artista);

        String tipo = archivo.getContentType();
        String extension = "." + tipo.substring(tipo.indexOf('/') + 1, tipo.length());
        String foto = artista.getId() + extension;
        String ruta = Paths.get("src/main/resources/static/images/artista", foto).toAbsolutePath().toString();
        ModelAndView maw = this.inicio();

        try {
            archivo.transferTo(new File(ruta));
        } catch (Exception e) {
            maw.addObject("error", "No se pudo guardar la imagen");
            return maw;
        }

        artista.setFoto(foto);
        artistaServicio.guardar(artista);
        maw.addObject("exito", "Predio agregada exitosamente");
        return maw;
    }

    @GetMapping("/editar/{id}")
    public ModelAndView editar(@PathVariable("id") Long id, Artista artista) {
        return this.editar(id, artista, true);
    }

    public ModelAndView editar(@PathVariable("id") Long id, Artista artista, boolean estaGuardado) {
        ModelAndView maw = new ModelAndView();
        maw.setViewName("fragments/base");
        maw.addObject("titulo", "Editar festividad");
        maw.addObject("vista", "artista/editar");
        maw.addObject("artistas", artistaServicio.mostrarTodos());

        if (estaGuardado)
            maw.addObject("artistas", artistaServicio.seleccionarPorId(id));
        else
           artista.setFoto(artistaServicio.seleccionarPorId(id).getFoto());

        return maw;
    }

    @PutMapping("/editar/{id}")
    private ModelAndView actualizar(@PathVariable("id") Long id,
            @RequestParam(value = "archivo", required = false) MultipartFile archivo,
            @Valid Artista artista, BindingResult br, RedirectAttributes ra) {
        if (br.hasErrors()) {
            return this.editar(id, artista, false);
        }

        Artista registro = artistaServicio.seleccionarPorId(id);
        registro.setNombre(artista.getNombre());
        registro.setDescripcion(artista.getDescripcion());
        registro.setFechaPresentación(artista.getFechaPresentación());
        //registro.setFechaFin(artista.getFechaFin());
        registro.setEnlace(artista.getEnlace());
        registro.setHoraPresentacion(artista.getHoraPresentacion());
        ModelAndView maw = this.inicio();

        if (!archivo.isEmpty()) {
            String tipo = archivo.getContentType();
            String extension = "." + tipo.substring(tipo.indexOf('/') + 1, tipo.length());
            String foto = artista.getId() + extension;
            String ruta = Paths.get("scr/main/resources/static/images/artistas", foto).toAbsolutePath().toString();

            try {
                archivo.transferTo(new File(ruta));
            } catch (Exception error) {
                maw.addObject("error", "No se pudo guardar el archivo.");
                return maw;
            }

            registro.setFoto(foto);
        }

        artistaServicio.guardar(artista);
        maw.addObject("correcto", "Festividad editada correctamente.");
        return maw;
    }

    @DeleteMapping("/{id}")
    private ModelAndView borrar(@PathVariable("id") Long id) {
        artistaServicio.borrar(id);
        ModelAndView maw = this.inicio();
        maw.addObject("correcto", "Festividad eliminada correctamente.");
        return maw;
    }
}

