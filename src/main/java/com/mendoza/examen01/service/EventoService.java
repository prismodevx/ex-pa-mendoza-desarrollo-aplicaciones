package com.mendoza.examen01.service;

import com.mendoza.examen01.entity.Evento;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EventoService {
    public List<Evento> findAll(Pageable page);
    public List<Evento> findAll();
    public List<Evento> findByNombre(String nombre, Pageable page);
    public Evento findById(int id);
    public Evento save(Evento categoria);
    public void delete(int id);
}
