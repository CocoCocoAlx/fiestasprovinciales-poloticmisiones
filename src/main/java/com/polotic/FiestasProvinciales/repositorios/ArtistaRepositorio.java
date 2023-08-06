package com.polotic.FiestasProvinciales.repositorios;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.polotic.FiestasProvinciales.entidades.Artista;

@Repository
public interface ArtistaRepositorio extends CrudRepository<Artista, Long> {
    
}
