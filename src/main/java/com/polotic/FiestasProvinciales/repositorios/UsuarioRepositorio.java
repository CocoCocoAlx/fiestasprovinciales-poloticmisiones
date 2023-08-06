package com.polotic.FiestasProvinciales.repositorios;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.polotic.FiestasProvinciales.entidades.Usuario;

@Repository
public interface UsuarioRepositorio extends CrudRepository<Usuario, Long>{
    Usuario findByCorreo(String correo);
    boolean existsByCorreo(String correo);
}
