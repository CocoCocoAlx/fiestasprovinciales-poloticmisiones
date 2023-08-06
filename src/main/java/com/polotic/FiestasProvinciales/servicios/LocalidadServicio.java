package com.polotic.FiestasProvinciales.servicios;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.polotic.FiestasProvinciales.entidades.Localidad;
import com.polotic.FiestasProvinciales.repositorios.LocalidadRepositorio;

@Service

public class LocalidadServicio {
   
    @Autowired
   
    LocalidadRepositorio localidadRepositorio;
   
    public List<Localidad> mostrarTodos()
    {
    List<Localidad> lista = new ArrayList<Localidad>();
    localidadRepositorio.findAll().forEach(registro -> lista.add(registro));
    return lista;
    }

    public Localidad seleccionarPorId(Long id)
    {
        return localidadRepositorio.findById(id).get();
    }

    public void guardar(Localidad localidad)
    {
        localidadRepositorio.save(localidad);
    }

    public void borrar(Long id)
    {
        localidadRepositorio.deleteById(id);
    }


    
}
