package com.polotic.FiestasProvinciales.repositorios;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.polotic.FiestasProvinciales.entidades.Provincia;

@Repository

public interface ProvinciaRepositorio extends CrudRepository<Provincia, Long>{
    
}
