package com.polotic.FiestasProvinciales.servicios;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.polotic.FiestasProvinciales.entidades.Rol;
import com.polotic.FiestasProvinciales.repositorios.RolRepositorio;

@Service
public class RolServicio {
    @Autowired
    RolRepositorio rolRepositorio;

    public List<Rol> mostrarTodos()
    {
        List<Rol> lista = new ArrayList<Rol>();
        rolRepositorio.findAll().forEach(registro -> lista.add(registro));
        return lista;
    }

    public Rol seleccionarPorId(Long id)
    {
        return rolRepositorio.findById(id).get();
    }

    public void guardar(Rol rol)
    {
        rolRepositorio.save(rol);
    }

    public void borrar(Long id)
    {
        rolRepositorio.deleteById(id);
    }
}
