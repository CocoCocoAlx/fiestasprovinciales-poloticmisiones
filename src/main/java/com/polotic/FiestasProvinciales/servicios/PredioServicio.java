package com.polotic.FiestasProvinciales.servicios;


import com.polotic.FiestasProvinciales.entidades.*;
import com.polotic.FiestasProvinciales.repositorios.*;
import java.util.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

@Service
public class PredioServicio {

    @Autowired
    PredioRepositorio predioRepositorio;


    public List<Predio> mostrarTodos()
    {
    List<Predio> lista = new ArrayList<Predio>();
    predioRepositorio.findAll().forEach(registro -> lista.add(registro));

    return lista;
    }

    public Predio seleccionarPorId(Long id)
    {
        return predioRepositorio.findById(id).get();
    }

    public void guardar(Predio predio)
    {
        predioRepositorio.save(predio);
    }

    public void borrar(Long id)
    {
        predioRepositorio.deleteById(id);
    }
    
}






    

