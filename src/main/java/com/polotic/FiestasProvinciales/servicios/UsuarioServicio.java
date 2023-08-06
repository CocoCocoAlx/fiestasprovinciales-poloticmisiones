package com.polotic.FiestasProvinciales.servicios;

import java.util.*;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.polotic.FiestasProvinciales.entidades.Rol;
import com.polotic.FiestasProvinciales.entidades.Usuario;
import com.polotic.FiestasProvinciales.repositorios.RolRepositorio;
import com.polotic.FiestasProvinciales.repositorios.UsuarioRepositorio;

@Service
public class UsuarioServicio implements UserDetailsService {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private RolRepositorio rolRepositorio;

    @Autowired
    private BCryptPasswordEncoder encriptador;

    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepositorio.findByCorreo(correo);
        List<GrantedAuthority> ga = buildAuthorities(usuario.getRol());
        return armarUsuario(usuario, ga);
    }

    public User armarUsuario(Usuario usuario, List<GrantedAuthority> ga) {
        return new User(usuario.getCorreo(), usuario.getClave(), ga);
    }

    public List<GrantedAuthority> buildAuthorities(Rol rol) {
        List<GrantedAuthority> ga = new ArrayList<>();
        ga.add(new SimpleGrantedAuthority("ROLE_" + rol.getNombre()));
        return ga;
    }

    @Transactional
    public void registro(Usuario usuario) {
        if (usuarioRepositorio.existsByCorreo(usuario.getCorreo()))
            throw new IllegalArgumentException("Ya existe un usuario con este email");

        usuario.setClave(encriptador.encode(usuario.getClave()));
        usuario.setRol(rolRepositorio.findByNombre("Administrador")
                .orElseThrow(() -> new IllegalArgumentException("Error al crear usuario")));
        usuarioRepositorio.save(usuario);
    }

}
