package org.iesalandalus.programacion.tallermecanico.vista;

import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Revision;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.utilidades.Entrada;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Consola {
    private  static final  String CADENA_FORMATO_FECHA = "dd/MM/yyyy";

    private Consola() {
    }
    public static void mostrarCabecera(String mensaje){
        System.out.printf( mensaje);
        System.out.println("-".repeat(mensaje.length()));
    }
     public static void mostrarMenu(){
        mostrarCabecera("Gestión de taller mecánico");
         for (Opcion opcion : Opcion.values()) {
             System.out.println(opcion);
         }
     }
     public static Opcion elegirOpcion(){
        int numeroOpcion;
        do {
            System.out.print("Elige una opción: ");
            numeroOpcion = Entrada.entero();
        } while (!Opcion.esValida(numeroOpcion));
        return Opcion.get(numeroOpcion);
     }
     private static int leerEntero(String mensaje){
         System.out.print(mensaje);
         return Entrada.entero();
     }
     private static float leerReal(String mensaje){
         System.out.print(mensaje);
         return Entrada.real();
     }
    private static String leerCadena(String mensaje){
    System.out.print(mensaje);
    return Entrada.cadena();
    }
    private static LocalDate leerFecha(String mensaje) {
        LocalDate fechaInicial = null;
        DateTimeFormatter formato = DateTimeFormatter.ofPattern(CADENA_FORMATO_FECHA);
        do{
           System.out.print(mensaje);
           String fechaStr = Entrada.cadena();
           try {
               fechaInicial = LocalDate.parse(fechaStr,formato);
           } catch (Exception e) {
               System.out.println("Fecha no válida. Por favor, introduce una fecha en formato " + CADENA_FORMATO_FECHA + ".");
           }
       } while (fechaInicial == null);
        return fechaInicial;
    }
    public static Cliente leerCliente(){
        String nombre = leerCadena("Introduce el nombre del cliente: ");
        String dni = leerCadena("Introduce el DNI del cliente: ");
        String telefono = leerCadena("Introduce el teléfono del cliente: ");
        return new Cliente(nombre,dni,telefono);
    }
    public static Cliente leerClienteDni(){
        return Cliente.get("Introduce el DNI.");
    }
    public static String leerNuevoNombre(){
        return leerCadena("Introduce el nuevo nombre del cliente: ");
    }
    public static String leerNuevoTelefono() {
        return leerCadena("Introduce el nuevo teléfono del cliente: ");
    }
    public static Vehiculo leerVehiculo(){
        String marca = leerCadena("Introduce la marca del vehículo: ");
        String modelo = leerCadena("Introduce el modelo del vehículo: ");
        String matricula = leerCadena("Introduce la matrícula del vehículo: ");
        return new Vehiculo(marca,modelo,matricula);
    }
    public static Vehiculo leerVehiculoMatricula() {
        return Vehiculo.get("Introduce la matrícula del vehículo.");
    }
    public static Revision leerRevision(){
        Cliente cliente = leerClienteDni();
        Vehiculo vehiculo = leerVehiculoMatricula();
        LocalDate fechaInicio = leerFecha("Introduce la fecha de inicio de la revisión (dd/MM/yyyy): ");
        return new Revision(cliente,vehiculo,fechaInicio);
    }
     public static int leerHoras(){
         return leerEntero("Introduce el número de horas: ");
     }
     public static float leerPrecioMaterial(){
         return leerReal("Introduce el precio del material: ");
     }
     public static LocalDate leerFechaCierre(){
         return leerFecha("Introduce la fecha de cierre de la revisión (dd/MM/yyyy): ");
     }

}
