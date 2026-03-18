package org.iesalandalus.programacion.tallermecanico.modelo.negocio;

import org.iesalandalus.programacion.tallermecanico.modelo.TallerMecanicoExcepcion;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Revision;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Vehiculo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Revisiones {
    private final List<Revision> coleccionRevisiones;

    public Revisiones (){
        coleccionRevisiones = new ArrayList<>();
    }
    public List<Revision> get(){
        return new ArrayList<>(coleccionRevisiones);
    }
    public List<Revision> get(Cliente cliente) {
        List<Revision> resultado = new ArrayList<>();
        for (Revision revision : coleccionRevisiones) {
            if (revision.getCliente().equals(cliente)) {
                resultado.add(revision);
            }
        }
        return resultado;
    }

    public List<Revision> get(Vehiculo vehiculo) {
        List<Revision> resultado = new ArrayList<>();
        for (Revision revision : coleccionRevisiones) {
            if (revision.getVehiculo().equals(vehiculo)) {
                resultado.add(revision);
            }
        }
        return resultado;
    }


    public void insertar(Revision revision) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(revision,"No se puede insertar una revisión nula.");
        comprobarRevision(revision.getCliente(), revision.getVehiculo(), revision.getFechaInicio());
        coleccionRevisiones.add(revision);
    }


    private void comprobarRevision(Cliente cliente, Vehiculo vehiculo, LocalDate fechaRevision) throws TallerMecanicoExcepcion {
        for (Revision revision : coleccionRevisiones) {
            if (!revision.estaCerrada()) {
                if (revision.getCliente().equals(cliente)) {
                    throw new TallerMecanicoExcepcion("El cliente tiene otra revisión en curso.");
                }
                if (revision.getVehiculo().equals(vehiculo)) {
                    throw new TallerMecanicoExcepcion("El vehículo está actualmente en revisión.");
                }
            } else {
                if (revision.getCliente().equals(cliente) && !fechaRevision.isAfter(revision.getFechaFin())) {
                    throw new TallerMecanicoExcepcion("El cliente tiene una revisión posterior.");
                }
                if (revision.getVehiculo().equals(vehiculo) && !fechaRevision.isAfter(revision.getFechaFin())) {
                    throw new TallerMecanicoExcepcion("El vehículo tiene una revisión posterior.");
                }
            }
        }
    }


    private Revision getRevision(Revision revision)  {
        Objects.requireNonNull(revision,"No puedo operar sobre una revisión nula.");
        int indice = coleccionRevisiones.indexOf(revision);
        return (indice == -1) ? null : coleccionRevisiones.get(indice);
    }

    public Revision anadirHoras(Revision revision, int horas) throws TallerMecanicoExcepcion {
        Revision revisionEncontrada = getRevision(revision);
        if (revisionEncontrada == null) {
            throw new TallerMecanicoExcepcion("No existe ninguna revisión igual.");
        }
        revisionEncontrada.anadirHoras(horas);
        return revisionEncontrada;
    }
    public Revision anadirPrecioMaterial(Revision revision, float precioMaterial) throws TallerMecanicoExcepcion {
        Revision revisionEncontrada = getRevision(revision);
        if (revisionEncontrada == null) {
            throw new TallerMecanicoExcepcion("No existe ninguna revisión igual.");
        }
        revisionEncontrada.anadirPrecioMaterial(precioMaterial);
        return revisionEncontrada;

    }
    public Revision cerrar(Revision revision, LocalDate fechaFin) throws TallerMecanicoExcepcion {
        Revision revisionEncontrada = getRevision(revision);
        if (revisionEncontrada == null) {
            throw new TallerMecanicoExcepcion("No existe ninguna revisión igual.");
        }
        revisionEncontrada.cerrar(fechaFin);
        return revisionEncontrada;

    }
    public Revision buscar(Revision revision){
        Objects.requireNonNull(revision,"No se puede buscar una revisión nula.");
        int indice = coleccionRevisiones.indexOf(revision);
        return (indice == -1) ? null : coleccionRevisiones.get(indice);

    }
    public void borrar(Revision revision) throws TallerMecanicoExcepcion {
       Objects.requireNonNull(revision,"No se puede borrar una revisión nula.");
        if (!coleccionRevisiones.contains(revision)){
            throw new TallerMecanicoExcepcion("No existe ninguna revisión igual.");
        }
        coleccionRevisiones.remove(revision);
    }
}
