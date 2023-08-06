package com.polotic.FiestasProvinciales.servicios;



import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.polotic.FiestasProvinciales.entidades.Artista;
import com.polotic.FiestasProvinciales.repositorios.ArtistaRepositorio;

@Service
public class ArtistaServicio {
    @Autowired
    ArtistaRepositorio artistaRepositorio;

    public List<Artista> mostrarTodos()
    {
    List<Artista> lista = new ArrayList<Artista>();
    artistaRepositorio.findAll().forEach(registro -> lista.add(registro));
    return lista;
    }

    public Artista seleccionarPorId(Long id)
    {
        return artistaRepositorio.findById(id).get();
    }

    public void guardar(Artista artista)
    {
        artistaRepositorio.save(artista);
    }

    public void borrar(Long id)
    {
        artistaRepositorio.deleteById(id);
    }

}

