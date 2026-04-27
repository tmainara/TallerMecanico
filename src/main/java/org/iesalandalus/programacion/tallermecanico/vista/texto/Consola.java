package org.iesalandalus.programacion.tallermecanico.vista.texto;

import org.iesalandalus.programacion.tallermecanico.vista.eventos.Evento;
import org.iesalandalus.programacion.utilidades.Entrada;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Consola {
    private  static final  String CADENA_FORMATO_FECHA = "dd/MM/yyyy";

    private Consola() {
    }
    static void mostrarCabecera(String mensaje){
        System.out.printf( mensaje);
        System.out.println("-".repeat(mensaje.length()));
    }
     static void mostrarMenu(){
        mostrarCabecera("Gestión de taller mecánico");
         for (Evento opcion : Evento.values()) {
             System.out.println(opcion);
         }
     }
     static Evento elegirOpcion(){
        int numeroOpcion;
        do {
            System.out.print("Elige una opción: ");
            numeroOpcion = Entrada.entero();
        } while (!Evento.esValida(numeroOpcion));
        return Evento.get(numeroOpcion);
     }
     static int leerEntero(String mensaje){
         System.out.print(mensaje);
         return Entrada.entero();
     }
     static float leerReal(String mensaje){
         System.out.print(mensaje);
         return Entrada.real();
     }
    static String leerCadena(String mensaje){
    System.out.print(mensaje);
    return Entrada.cadena();
    }
    static LocalDate leerFecha(String mensaje) {
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

}
