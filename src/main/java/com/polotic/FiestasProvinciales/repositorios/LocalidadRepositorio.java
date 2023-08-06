package com.polotic.FiestasProvinciales.repositorios;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.polotic.FiestasProvinciales.entidades.Localidad;

@Repository

public interface LocalidadRepositorio extends CrudRepository<Localidad, Long> {
    
}
