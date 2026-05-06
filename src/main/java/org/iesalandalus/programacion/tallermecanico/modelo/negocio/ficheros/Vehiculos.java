package org.iesalandalus.programacion.tallermecanico.modelo.negocio.ficheros;

import org.iesalandalus.programacion.tallermecanico.modelo.TallerMecanicoExcepcion;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Vehiculo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Vehiculos implements org.iesalandalus.programacion.tallermecanico.modelo.negocio.IVehiculos {

    private static final String FICHERO_VEHICULOS = "datos/vehiculos.json";
    private static final String RAIZ = "vehiculos";
    private static final String VEHICULO = "vehiculo";
    private static final String MARCA = "marca";
    private static final String MODELO = "modelo";
    private static final String MATRICULA = "matricula";

    private final List<Vehiculo> coleccionVehiculos;
    private Vehiculos instancia;

    private Vehiculos(){
        coleccionVehiculos = new ArrayList<>();
    }

    @Override
    public List<Vehiculo> get(){
        return new ArrayList<>(coleccionVehiculos);
    }
    @Override
    public void insertar(Vehiculo vehiculo) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(vehiculo,"No se puede insertar un vehículo nulo.");
        if (coleccionVehiculos.contains(vehiculo)){
            throw new TallerMecanicoExcepcion("Ya existe un vehículo con esa matrícula.");
        }

        coleccionVehiculos.add(vehiculo);
    }
    @Override
    public Vehiculo buscar(Vehiculo vehiculo){
        Objects.requireNonNull(vehiculo,"No se puede buscar un vehículo nulo.");
        int indice = coleccionVehiculos.indexOf(vehiculo);
        return (indice == -1) ? null : coleccionVehiculos.get(indice);
    }
    @Override
    public void borrar(Vehiculo vehiculo) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(vehiculo,"No se puede borrar un vehículo nulo.");
        if (!coleccionVehiculos.contains(vehiculo)){
            throw new TallerMecanicoExcepcion("No existe ningún vehículo con esa matrícula.");
        }
        coleccionVehiculos.remove(vehiculo);
    }
}
