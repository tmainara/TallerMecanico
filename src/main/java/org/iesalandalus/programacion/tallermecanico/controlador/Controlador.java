package org.iesalandalus.programacion.tallermecanico.controlador;

import org.iesalandalus.programacion.tallermecanico.modelo.FabricaModelo;
import org.iesalandalus.programacion.tallermecanico.modelo.Modelo;
import org.iesalandalus.programacion.tallermecanico.modelo.TallerMecanicoExcepcion;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.FabricaFuenteDatos;
import org.iesalandalus.programacion.tallermecanico.vista.FabricaVista;
import org.iesalandalus.programacion.tallermecanico.vista.Vista;
import org.iesalandalus.programacion.tallermecanico.vista.eventos.Evento;
import org.iesalandalus.programacion.tallermecanico.vista.eventos.GestorEventos;


import java.util.Objects;

public class Controlador implements IControlador {
    private final Modelo modelo;
    private final Vista vista;

    public Controlador(FabricaModelo fabricaModelo, FabricaFuenteDatos fabricaFuenteDatos, FabricaVista fabricaVista) {
        Objects.requireNonNull(fabricaModelo, "La fábrica de modelo no puede ser nula.");
        Objects.requireNonNull(fabricaFuenteDatos, "La fábrica de fuente de datos no puede ser nula.");
        Objects.requireNonNull(fabricaVista, "La fábrica de vista no puede ser nula.");

        this.modelo = fabricaModelo.crear(fabricaFuenteDatos);
        this.vista = fabricaVista.crear();
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
    public void actualizar(Evento evento) {
        System.out.println("Evento recibido: " + evento);
        try {
            switch (evento) {
                case INSERTAR_CLIENTE -> modelo.insertar(vista.leerCliente());
                case BUSCAR_CLIENTE -> System.out.println(modelo.buscar(vista.leerCliente()));
                case BORRAR_CLIENTE -> modelo.buscar(vista.leerCliente());
                case LISTAR_CLIENTES -> vista.mostrarClientes(modelo.getClientes());
                case MODIFICAR_CLIENTE -> modelo.modificar(vista.leerCliente(), vista.leerNuevoNombre(), vista.leerNuevoTelefono());
                case INSERTAR_VEHICULO -> modelo.insertar(vista.leerVehiculo());
                case BUSCAR_VEHICULO -> System.out.println(modelo.buscar(vista.leerVehiculoMatricula()));
                case BORRAR_VEHICULO -> modelo.buscar(vista.leerVehiculo());
                case LISTAR_VEHICULOS -> vista.mostrarVehiculos(modelo.getVehiculos());
                case INSERTAR_REVISION -> modelo.insertar(vista.leerRevision());
                case INSERTAR_MECANICO -> modelo.insertar(vista.leerMecanico());
                case BUSCAR_TRABAJO -> System.out.println(modelo.buscar(vista.leerTrabajoVehiculo()));
                case BORRAR_TRABAJO -> modelo.borrar(vista.leerTrabajoVehiculo());
                case LISTAR_TRABAJOS -> vista.mostrarTrabajos(modelo.getTrabajos());
                case LISTAR_TRABAJOS_CLIENTE -> vista.mostrarTrabajos(modelo.getTrabajos(vista.leerClienteDni()));
                case LISTAR_TRABAJOS_VEHICULO -> vista.mostrarTrabajos(modelo.getTrabajos(vista.leerVehiculoMatricula()));
                case ANADIR_HORAS_TRABAJO -> modelo.anadirHoras(vista.leerTrabajoVehiculo(), vista.leerHoras());
                case ANADIR_PRECIO_MATERIAL_TRABAJO -> modelo.anadirPrecioMaterial(vista.leerTrabajoVehiculo(), vista.leerPrecioMaterial());
                case CERRAR_TRABAJO -> modelo.cerrar(vista.leerTrabajoVehiculo(), vista.leerFechaCierre());
                case SALIR -> terminar();
            }
        } catch (TallerMecanicoExcepcion e) {
            System.out.printf("Error al ejecutar la acción %s: %s%n", evento, e.getMessage());
        }
    }

}


