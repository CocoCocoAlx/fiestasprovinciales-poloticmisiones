package com.polotic.FiestasProvinciales.repositorios;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.polotic.FiestasProvinciales.entidades.Predio;

@Repository
public interface PredioRepositorio extends CrudRepository<Predio, Long> {
    
}

    

