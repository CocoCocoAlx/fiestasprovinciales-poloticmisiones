package com.polotic.FiestasProvinciales.repositorios;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.polotic.FiestasProvinciales.entidades.Fiesta;

@Repository
public interface FiestaRepositorio extends CrudRepository<Fiesta, Long> {
    
}
