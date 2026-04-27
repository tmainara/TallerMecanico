package org.iesalandalus.programacion.tallermecanico.vista.texto;


import org.iesalandalus.programacion.tallermecanico.modelo.dominio.*;
import org.iesalandalus.programacion.tallermecanico.vista.eventos.Evento;
import org.iesalandalus.programacion.tallermecanico.vista.eventos.GestorEventos;

import java.time.LocalDate;
import java.util.List;

public class VistaTexto implements org.iesalandalus.programacion.tallermecanico.vista.Vista {
    private GestorEventos gestorEventos;

    @Override
    public GestorEventos gestorEventos() {
        return gestorEventos;
    }

    @Override
    public void comenzar() {
        System.out.println("¡¡¡Bienvenido al taller mecánico de Maricarmen!!!");
    }

    private void ejecutar(Evento opcion) {
        try {
            gestorEventos.notificar(opcion);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @Override
    public void terminar() {
        System.out.println("¡¡¡Hasta luego Maricarmennnn!!!");
    }

    @Override
    public Cliente leerCliente() {
        String nombre = Consola.leerCadena("Introduce el nombre del cliente: ");
        String dni = Consola.leerCadena("Introduce el DNI del cliente: ");
        String telefono = Consola.leerCadena("Introduce el teléfono del cliente: ");
        return new Cliente(nombre, dni, telefono);
    }
    @Override
    public Cliente leerClienteDni() {
        String dni = Consola.leerCadena("Introduce el DNI del cliente: ");
        return new Cliente("Nombre", dni, "Teléfono");
    }

    @Override
    public String leerNuevoNombre() {
        return Consola.leerCadena("Introduce el nuevo nombre del cliente: ");
    }

    @Override
    public String leerNuevoTelefono() {
        return Consola.leerCadena("Introduce el nuevo teléfono del cliente: ");
    }

    @Override
    public Vehiculo leerVehiculo() {
        String marca = Consola.leerCadena("Introduce la marca del vehículo: ");
        String modelo = Consola.leerCadena("Introduce el modelo del vehículo: ");
        String matricula = Consola.leerCadena("Introduce la matrícula del vehículo: ");
        return new Vehiculo(marca, modelo, matricula);
    }

    @Override
    public Vehiculo leerVehiculoMatricula() {
        String matricula = Consola.leerCadena("Introduce la matrícula del vehículo: ");
        return new Vehiculo("Marca", "Modelo", matricula);
    }

    @Override
    public Trabajo leerRevision() {
        Cliente cliente = leerCliente();
        Vehiculo vehiculo = leerVehiculo();
        return new Revision(cliente, vehiculo, Consola.leerFecha("Introduce la fecha de inicio de la revisión (dd/MM/yyyy): "));
    }

    @Override
    public Trabajo leerMecanico() {
        Cliente cliente = leerCliente();
        Vehiculo vehiculo = leerVehiculo();
        return new Mecanico(cliente, vehiculo, Consola.leerFecha("Introduce la fecha de inicio del trabajo mecánico (dd/MM/yyyy): "));
    }

    @Override
    public Trabajo leerTrabajoVehiculo() {
        Cliente cliente = leerCliente();
        Vehiculo vehiculo = leerVehiculo();
        return new Revision(cliente, vehiculo, Consola.leerFecha("Introduce la fecha de inicio del trabajo (dd/MM/yyyy): "));
    }

    @Override
    public int leerHoras() {
        return Consola.leerEntero("Introduce el número de horas a añadir: ");
    }

    @Override
    public float leerPrecioMaterial() {
        return Consola.leerReal("Introduce el precio del material a añadir: ");
    }

    @Override
    public LocalDate leerFechaCierre() {
        return Consola.leerFecha("Introduce la fecha de fin del trabajo (dd/MM/yyyy): ");
    }

    @Override
    public void notificarResultado(Evento evento, String texto, boolean exito) {
        String resultado = exito ? "éxito" : "fracaso";
        System.out.printf("Resultado de la operación %s: %s%n", evento, resultado);
        if (texto != null && !texto.isEmpty()) {
            System.out.println(texto);
        }
    }

    @Override
    public void mostrarCliente(Cliente cliente) {
        System.out.println(cliente);
    }

    @Override
    public void mostrarVehiculo(Vehiculo vehiculo) {
        System.out.println(vehiculo);
    }

    @Override
    public void mostrarTrabajo(Trabajo trabajo) {
        System.out.println(trabajo);
    }

    @Override
    public void mostrarClientes(List<Cliente> clientes) {
        if (clientes.isEmpty()) {
            System.out.println("No hay clientes para mostrar.");
        } else {
            System.out.println("Clientes:");
            for (Cliente cliente : clientes) {
                System.out.println(cliente);
            }
        }
    }

    @Override
    public void mostrarVehiculos(List<Vehiculo> vehiculos) {
        if (vehiculos.isEmpty()) {
            System.out.println("No hay vehículos para mostrar.");
        } else {
            System.out.println("Vehículos:");
            for (Vehiculo vehiculo : vehiculos) {
                System.out.println(vehiculo);
            }
        }
    }

    @Override
    public void mostrarTrabajos(List<Trabajo> trabajos) {
        if (trabajos.isEmpty()) {
            System.out.println("No hay trabajos para mostrar.");
        } else {
            System.out.println("Trabajos:");
            for (Trabajo trabajo : trabajos) {
                System.out.println(trabajo);
            }
        }
    }
}




