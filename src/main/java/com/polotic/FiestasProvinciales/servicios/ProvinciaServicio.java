package com.polotic.FiestasProvinciales.servicios;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.polotic.FiestasProvinciales.entidades.Provincia;
import com.polotic.FiestasProvinciales.repositorios.ProvinciaRepositorio;

@Service


public class ProvinciaServicio {

    @Autowired
   
    ProvinciaRepositorio provinciaRepositorio;
   
    public List<Provincia> mostrarTodos()
    {
    List<Provincia> lista = new ArrayList<Provincia>();
    provinciaRepositorio.findAll().forEach(registro -> lista.add(registro));
    return lista;
    }

    public Provincia seleccionarPorId(Long id)
    {
        return provinciaRepositorio.findById(id).get();
    }

    public void guardar(Provincia provincia)
    {
        provinciaRepositorio.save(provincia);
    }

    public void borrar(Long id)
    {
        provinciaRepositorio.deleteById(id);
    }
    
}
