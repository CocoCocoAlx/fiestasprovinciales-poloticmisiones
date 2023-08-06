package com.polotic.FiestasProvinciales.servicios;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.polotic.FiestasProvinciales.entidades.Fiesta;
import com.polotic.FiestasProvinciales.repositorios.FiestaRepositorio;

@Service
public class FiestaServicio {
    @Autowired
    FiestaRepositorio fiestaRepositorio;

    public List<Fiesta> mostrarTodos()
    {
    List<Fiesta> lista = new ArrayList<Fiesta>();
    fiestaRepositorio.findAll().forEach(registro -> lista.add(registro));
    return lista;
    }

    public Fiesta seleccionarPorId(Long id)
    {
        return fiestaRepositorio.findById(id).get();
    }

    public void guardar(Fiesta fiesta)
    {
        fiestaRepositorio.save(fiesta);
    }

    public void borrar(Long id)
    {
        fiestaRepositorio.deleteById(id);
    }

}
