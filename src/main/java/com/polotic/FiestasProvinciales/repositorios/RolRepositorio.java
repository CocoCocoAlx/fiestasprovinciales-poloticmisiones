package com.polotic.FiestasProvinciales.repositorios;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.polotic.FiestasProvinciales.entidades.Rol;

@Repository
public interface RolRepositorio extends CrudRepository<Rol, Long> {

    Optional<Rol> findByNombre(String string);
}
