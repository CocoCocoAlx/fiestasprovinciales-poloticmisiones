package com.polotic.FiestasProvinciales.controladores;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.polotic.FiestasProvinciales.dto.RegistroDto;
import com.polotic.FiestasProvinciales.entidades.Usuario;
import com.polotic.FiestasProvinciales.repositorios.RolRepositorio;
import com.polotic.FiestasProvinciales.repositorios.UsuarioRepositorio;
import com.polotic.FiestasProvinciales.servicios.RecaptchaServicio;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@Controller
public class AutorizacionControlador {
    
    @Autowired
    private BCryptPasswordEncoder encriptador;

    @Autowired
    private RolRepositorio rolRepositorio;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private RecaptchaServicio recaptchaServicio;

    @GetMapping("/login")
    public ModelAndView showLoginForm(Model model, 
        @RequestParam(name = "error", required = false) String error,
        @RequestParam(name="logout", required = false) String logout) {
            
        ModelAndView maw = new ModelAndView();
        maw.setViewName("fragments/base");
        maw.addObject("titulo", "Iniciar sesión");
        maw.addObject("vista", "ingreso/login");
        model.addAttribute("error", error);
        model.addAttribute("logout", logout);
        return maw;
    }

    @GetMapping({"/loginSuccess"})
    public RedirectView loginCheck(){
        return new RedirectView("/inicio");
    }
    
    @GetMapping("/registro")
	public ModelAndView registro(RegistroDto registroDto)
    {
        ModelAndView maw = new ModelAndView();
        maw.setViewName("fragments/base");
        maw.addObject("titulo", "Registrarse");
        maw.addObject("vista", "ingreso/registro");
        maw.addObject("registroDto", registroDto);
        return maw;
	}

	@PostMapping("/registro")
	public ModelAndView registrar(@RequestParam(name="g-recaptcha-response") String recaptchaResponse, @Valid RegistroDto registroDto, BindingResult br, RedirectAttributes ra, HttpServletRequest request)
    {
        String ip = request.getRemoteAddr();
        String captchaVerifyMessage = recaptchaServicio.verifyRecaptcha(ip, recaptchaResponse);

        if (captchaVerifyMessage != "") {
            br.rejectValue("recaptcha", "recaptcha", captchaVerifyMessage);
        }

        if ( br.hasErrors() ) {
			return this.registro(registroDto);
		}

        Usuario u = new Usuario();
        u.setCorreo(registroDto.getCorreo());
        u.setClave(encriptador.encode(registroDto.getClave()));
        u.setRol(rolRepositorio.findByNombre("Usuario").orElseThrow(() -> new IllegalArgumentException("Error al crear usuario")));

		usuarioRepositorio.save(u);

        try {
            request.login(registroDto.getCorreo(), registroDto.getClave());
        } catch (ServletException e) {
            e.printStackTrace();
        }

        InicioControlador ic = new InicioControlador();
        return ic.inicio();
	}

}