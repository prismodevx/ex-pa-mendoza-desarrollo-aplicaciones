package com.mendoza.examen01.validator;

import com.mendoza.examen01.entity.Evento;
import com.mendoza.examen01.exception.ValidateException;

public class EventoValidator {
    public static void save(Evento registro) {
        if(registro.getNombre() == null || registro.getNombre().trim().isEmpty()) {
            throw new ValidateException("El nombre es requerido");
        }
        if(registro.getNombre().length() > 255) {
            throw new ValidateException("El nombre no debe exceder los 255 caracteres");
        }
        if(registro.getFechaInicio() == null || registro.getFechaInicio().equals("")) {
            throw new ValidateException("La fecha de inicio es requerida");
        }
        if(registro.getFechaFin() == null || registro.getFechaFin().equals("")) {
            throw new ValidateException("La fecha de fin es requerida");
        }

//        if(registro.getCosto().toString()) {
//            throw new ValidateException("El costo es requerido");
//        }
    }
}
