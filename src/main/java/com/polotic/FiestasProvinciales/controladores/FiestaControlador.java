package com.polotic.FiestasProvinciales.controladores;

import java.io.File;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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

import com.polotic.FiestasProvinciales.entidades.Fiesta;
import com.polotic.FiestasProvinciales.repositorios.UsuarioRepositorio;
import com.polotic.FiestasProvinciales.servicios.CorreoServicio;
import com.polotic.FiestasProvinciales.servicios.FiestaServicio;
import com.polotic.FiestasProvinciales.servicios.LocalidadServicio;
import com.polotic.FiestasProvinciales.servicios.PredioServicio;
import com.polotic.FiestasProvinciales.servicios.UsuarioServicio;

@RestController
@RequestMapping("fiestas")
public class FiestaControlador implements WebMvcConfigurer {

    @Autowired
    FiestaServicio fiestaServicio;

    @Autowired
    PredioServicio predioServicio;

    @Autowired
    LocalidadServicio localidadServicio;

    @Autowired
    CorreoServicio correoServicio;

    @Autowired
    UsuarioServicio usuarioServicio;

    @Autowired
    UsuarioRepositorio usuarioRepositorio;
  
    @GetMapping
    private ModelAndView inicio() {
        ModelAndView maw = new ModelAndView();
        maw.setViewName("fragments/base");
        maw.addObject("titulo", "Listado de festividades");
        maw.addObject("vista", "fiestas/inicio");
        maw.addObject("fiestas", fiestaServicio.mostrarTodos());
        return maw;

    }

    @GetMapping("/{id}")
    private ModelAndView uno(@PathVariable("id") Long id) {
        ModelAndView maw = new ModelAndView();
        maw.setViewName("fragments/base");
        maw.addObject("titulo", "Detalle de la festividad #" + id);
        maw.addObject("vista", "fiestas/ver");
        maw.addObject("fiesta", fiestaServicio.seleccionarPorId(id));
        return maw;
    }

    @PostMapping("/enviarcorreo/{id}")
    public ModelAndView enviarCorreo(@PathVariable("id") Long id, Fiesta fiesta) {
        ModelAndView maw = new ModelAndView();
        maw.addObject("titulo", "Detalle de la festividad #" + id);
        maw.addObject("vista", "fiestas/ver");
        String correo = SecurityContextHolder.getContext().getAuthentication().getName();

        // Otros métodos para enviar correos
        // correoServicio.enviarCorreoSimple(correo, "Información sobre el evento "+fiestaServicio.seleccionarPorId(id).getNombre(), "Aquí tenés toda la información de la festividad "+fiestaServicio.seleccionarPorId(id).getDescripcion());
        
        // String[] adjuntos={"classpath:static/images/fiestas/32.jpeg"};
        // correoServicio.enviarCorreoConAdjunto(correo, "Información sobre el evento "+fiestaServicio.seleccionarPorId(id).getNombre(), "Aquí tenés toda la información de la festividad "+fiestaServicio.seleccionarPorId(id).getDescripcion(), adjuntos);

        Map<String, Object> propiedades = new HashMap<>();
        propiedades.put("fiesta", fiestaServicio.seleccionarPorId(id).getNombre());
        propiedades.put("fechaInicio", fiestaServicio.seleccionarPorId(id).getFechaInicio());
        propiedades.put("fechaFin", fiestaServicio.seleccionarPorId(id).getFechaFin());
        propiedades.put("predio", fiestaServicio.seleccionarPorId(id).getPredio().getNombre());
        propiedades.put("predioUbicacion", fiestaServicio.seleccionarPorId(id).getPredio().getUbicacion());
        propiedades.put("localidad", fiestaServicio.seleccionarPorId(id).getLocalidad().getNombre());
        propiedades.put("distancia", fiestaServicio.seleccionarPorId(id).getLocalidad().getDistancia());
        propiedades.put("fiestaEnlace", fiestaServicio.seleccionarPorId(id).getEnlace());
        propiedades.put("predioEnlace", fiestaServicio.seleccionarPorId(id).getPredio().getUbicacionUrl());
        try {
            correoServicio.enviarMailHtml(correo, fiestaServicio.seleccionarPorId(id).getNombre(), "correo/mensaje.html", propiedades);
        } catch (MessagingException err) {
            err.printStackTrace();
        }
        return uno(id);
    }

    @GetMapping("/agregar")
    private ModelAndView agregar(Fiesta fiesta) {
        ModelAndView maw = new ModelAndView();
        maw.setViewName("fragments/base");
        maw.addObject("titulo", "Agregar festividad");
        maw.addObject("vista", "fiestas/agregar");
        maw.addObject("predios", predioServicio.mostrarTodos());
        maw.addObject("localidad", localidadServicio.mostrarTodos());
        return maw;

    }

    @PostMapping("/agregar")
    public ModelAndView guardar(@RequestParam("archivo") MultipartFile archivo,
            @Valid Fiesta fiesta, BindingResult br, RedirectAttributes ra) {
        if (archivo.isEmpty())
            br.reject("archivo", "Por favor, cargar un archivo válido");

        if (br.hasErrors()) {
            return this.agregar(fiesta);
        }

        fiestaServicio.guardar(fiesta);

        String tipo = archivo.getContentType();
        String extension = "." + tipo.substring(tipo.indexOf('/') + 1, tipo.length());
        String foto = fiesta.getId() + extension;
        String ruta = Paths.get("src/main/resources/static/images/fiestas", foto).toAbsolutePath().toString();
        ModelAndView maw = this.inicio();

        try {
            archivo.transferTo(new File(ruta));
        } catch (Exception e) {
            maw.addObject("error", "No se pudo guardar la imagen");
            return maw;
        }

        fiesta.setFoto(foto);
        fiestaServicio.guardar(fiesta);
        maw.addObject("exito", "Fiesta agregada exitosamente");
        return maw;
    }

    @GetMapping("/editar/{id}")
    public ModelAndView editar(@PathVariable("id") Long id, Fiesta fiesta) {
        return this.editar(id, fiesta, true);
    }

    public ModelAndView editar(@PathVariable("id") Long id, Fiesta fiesta, boolean estaGuardado) {
        ModelAndView maw = new ModelAndView();
        maw.setViewName("fragments/base");
        maw.addObject("titulo", "Editar festividad");
        maw.addObject("vista", "fiestas/editar");
        maw.addObject("predios", predioServicio.mostrarTodos());
        maw.addObject("localidad", localidadServicio.mostrarTodos());

        if (estaGuardado)
            maw.addObject("fiesta", fiestaServicio.seleccionarPorId(id));
        else
            fiesta.setFoto(fiestaServicio.seleccionarPorId(id).getFoto());

        return maw;
    }

    @PutMapping("/editar/{id}")
    private ModelAndView actualizar(@PathVariable("id") Long id,
            @RequestParam(value = "archivo", required = false) MultipartFile archivo,
            @Valid Fiesta fiesta, BindingResult br, RedirectAttributes ra) {
        if (br.hasErrors()) {
            return this.editar(id, fiesta, false);
        }

        Fiesta registro = fiestaServicio.seleccionarPorId(id);
        registro.setNombre(fiesta.getNombre());
        registro.setDescripcion(fiesta.getDescripcion());
        registro.setFechaInicio(fiesta.getFechaInicio());
        registro.setFechaFin(fiesta.getFechaFin());
        registro.setEnlace(fiesta.getEnlace());
        registro.setPredio(fiesta.getPredio());
        ModelAndView maw = this.inicio();

        if (!archivo.isEmpty()) {
            String tipo = archivo.getContentType();
            String extension = "." + tipo.substring(tipo.indexOf('/') + 1, tipo.length());
            String foto = fiesta.getId() + extension;
            String ruta = Paths.get("scr/main/resources/static/images/fiestas", foto).toAbsolutePath().toString();

            try {
                archivo.transferTo(new File(ruta));
            } catch (Exception error) {
                maw.addObject("error", "No se pudo guardar el archivo.");
                return maw;
            }

            registro.setFoto(foto);
        }

        fiestaServicio.guardar(fiesta);
        maw.addObject("exito", "Festividad editada correctamente.");
        return maw;
    }

    @DeleteMapping("/{id}")
    private ModelAndView borrar(@PathVariable("id") Long id) {
        fiestaServicio.borrar(id);
        ModelAndView maw = this.inicio();
        maw.addObject("exito", "Festividad eliminada correctamente.");
        return maw;
    }
}