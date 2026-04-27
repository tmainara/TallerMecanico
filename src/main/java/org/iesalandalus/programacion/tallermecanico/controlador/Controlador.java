package org.iesalandalus.programacion.tallermecanico.controlador;

import org.iesalandalus.programacion.tallermecanico.modelo.Modelo;
import org.iesalandalus.programacion.tallermecanico.vista.Vista;
import org.iesalandalus.programacion.tallermecanico.vista.eventos.Evento;


import java.util.Objects;

public class Controlador implements IControlador {
    private final Modelo modelo;
    private final Vista vista;

    public Controlador(Modelo modelo, Vista vista) {
        this.modelo = Objects.requireNonNull(modelo, "ERROR: El modelo no puede ser nulo.");
        this.vista = Objects.requireNonNull(vista, "ERROR: La vista no puede ser nula.");
    }

    @Override
    public void comenzar() {
        modelo.comenzar();
        vista.comenzar();
    }

    @Override
    public void terminar() {
        vista.terminar();
        modelo.terminar();
    }
    @Override
    public void actualizar(Evento evento){
        vista.getGestorEventos().notificar(evento);
    }
}


