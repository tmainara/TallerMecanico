package org.iesalandalus.programacion.tallermecanico.modelo.negocio.memoria;

import org.iesalandalus.programacion.tallermecanico.modelo.TallerMecanicoExcepcion;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Mecanico;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Trabajo;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.ITrabajos;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Trabajos implements ITrabajos {
    private final List<Trabajo> coleccionTrabajos;

    public Trabajos(){
        coleccionTrabajos = new ArrayList<>();
    }

    @Override
    public List<Trabajo> get(){
        return new ArrayList<>(coleccionTrabajos);
    }

    @Override
    public List<Trabajo> get(Cliente cliente) {
        List<Trabajo> resultado = new ArrayList<>();
        for (Trabajo trabajo : coleccionTrabajos) {
            if (trabajo.getCliente().equals(cliente)) {
                resultado.add(trabajo);
            }
        }
        return resultado;
    }

    @Override
    public List<Trabajo> get(Vehiculo vehiculo) {
        List<Trabajo> resultado = new ArrayList<>();
        for (Trabajo trabajo : coleccionTrabajos) {
            if (trabajo.getVehiculo().equals(vehiculo)) {
                resultado.add(trabajo);
            }
        }
        return resultado;
    }



    @Override
    public void insertar(Trabajo trabajo) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(trabajo,"No se puede insertar un trabajo nulo.");
        comprobarTrabajo(trabajo.getCliente(), trabajo.getVehiculo(), trabajo.getFechaInicio());
        coleccionTrabajos.add(trabajo);
    }


    private void comprobarTrabajo(Cliente cliente, Vehiculo vehiculo, LocalDate fechaRevision) throws TallerMecanicoExcepcion {
        for (Trabajo trabajo : coleccionTrabajos) {
            if (!trabajo.estaCerrado()) {
                if (trabajo.getCliente().equals(cliente)) {
                    throw new TallerMecanicoExcepcion("El cliente tiene otro trabajo en curso.");
                }
                if (trabajo.getVehiculo().equals(vehiculo)) {
                    throw new TallerMecanicoExcepcion("El vehículo está actualmente en el taller.");
                }
            } else {
                if (trabajo.getCliente().equals(cliente) && !fechaRevision.isAfter(trabajo.getFechaFin())) {
                    throw new TallerMecanicoExcepcion("El cliente tiene otro trabajo posterior.");
                }
                if (trabajo.getVehiculo().equals(vehiculo) && !fechaRevision.isAfter(trabajo.getFechaFin())) {
                    throw new TallerMecanicoExcepcion("El vehículo tiene otro trabajo posterior.");
                }
            }
        }
    }

    @Override
    public Trabajo anadirHoras(Trabajo trabajo, int horas) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(trabajo, "No puedo añadir horas a un trabajo nulo.");

        Trabajo trabajoAbierto = getTrabajoAbierto(trabajo.getVehiculo());

        if (trabajoAbierto == null) {
            throw new TallerMecanicoExcepcion("No existe ningún trabajo abierto para dicho vehículo.");
        }

        if (!trabajoAbierto.equals(trabajo)) {
            throw new TallerMecanicoExcepcion("El trabajo indicado no es el trabajo abierto del vehículo.");
        }

        trabajoAbierto.anadirHoras(horas);
        return trabajoAbierto;
    }


    public Trabajo getTrabajoAbierto(Vehiculo vehiculo) {
        Objects.requireNonNull(vehiculo, "No se puede buscar un trabajo de un vehículo nulo.");
        for (Trabajo trabajo : coleccionTrabajos) {
            if (trabajo.getVehiculo().equals(vehiculo) && !trabajo.estaCerrado()) {
                return trabajo;
            }
        }
        return null;
    }
    @Override
    public Trabajo anadirPrecioMaterial(Trabajo trabajo, float precioMaterial) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(trabajo, "No puedo añadir precio del material a un trabajo nulo.");
        Trabajo trabajoAbierto = getTrabajoAbierto(trabajo.getVehiculo());

        if (trabajoAbierto == null) {
            throw new TallerMecanicoExcepcion("No existe ningún trabajo abierto para dicho vehículo.");
        }
        if (trabajoAbierto instanceof Mecanico mecanico) {
            mecanico.anadirPrecioMaterial(precioMaterial);
        } else {
            throw new TallerMecanicoExcepcion("No se puede añadir precio al material para este tipo de trabajos.");
        }
        return trabajoAbierto;
    }



    @Override
    public Trabajo cerrar(Trabajo trabajo, LocalDate fechaFin) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(trabajo, "No puedo cerrar un trabajo nulo.");
        Trabajo trabajoAbierto = getTrabajoAbierto(trabajo.getVehiculo());
        if (trabajoAbierto == null) {
            throw new TallerMecanicoExcepcion("No existe ningún trabajo abierto para dicho vehículo.");
        }
        if (!trabajoAbierto.equals(trabajo)) {
            throw new TallerMecanicoExcepcion("El trabajo indicado no es el trabajo abierto del vehículo.");
        }
        trabajoAbierto.cerrar(fechaFin);
        return trabajoAbierto;
    }
    @Override
    public Trabajo buscar(Trabajo trabajo){
        Objects.requireNonNull(trabajo,"No se puede buscar un trabajo nulo.");
        int indice = coleccionTrabajos.indexOf(trabajo);
        return (indice == -1) ? null : coleccionTrabajos.get(indice);

    }
    @Override
    public void borrar(Trabajo trabajo) throws TallerMecanicoExcepcion {
       Objects.requireNonNull(trabajo,"No se puede borrar un trabajo nulo.");
        if (!coleccionTrabajos.contains(trabajo)){
            throw new TallerMecanicoExcepcion("No existe ningún trabajo igual.");
        }
        coleccionTrabajos.remove(trabajo);
    }
}
