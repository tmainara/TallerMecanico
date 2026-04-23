package org.iesalandalus.programacion.tallermecanico.modelo.dominio;

import org.iesalandalus.programacion.tallermecanico.modelo.TallerMecanicoExcepcion;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public abstract class Trabajo {
    static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final float FACTOR_DIA = 10;
    private LocalDate fechaFin;
    private LocalDate fechaInicio;
    private int horas;
    private Cliente cliente;
    private Vehiculo vehiculo;

    protected Trabajo (Cliente cliente, Vehiculo vehiculo, LocalDate fechaInicio){
        setCliente(cliente);
        setVehiculo(vehiculo);
        setFechaInicio(fechaInicio);
    }
    protected Trabajo (Trabajo trabajo){
        Objects.requireNonNull(trabajo,"El trabajo no puede ser nulo.");
        this.cliente = new Cliente(trabajo.cliente);
        this.vehiculo = trabajo.vehiculo;
        this.fechaInicio = trabajo.fechaInicio;
        this.fechaFin = trabajo.fechaFin;
        this.horas = trabajo.horas;
    }
    public static Trabajo copiar(Trabajo trabajo){
        Objects.requireNonNull(trabajo, "El trabajo no puede ser nulo.");

        if (trabajo instanceof Mecanico mecanico) {
            return new Mecanico(mecanico);
        }

        if (trabajo instanceof Revision revision) {
            return new Revision(revision);
        }

        throw new IllegalArgumentException("Tipo de trabajo desconocido.");
    }
    public static Trabajo get(Vehiculo vehiculo){
        Cliente clienteExistente = new Cliente("Belén Esteban", "12345678A","617885050");
        return new Mecanico(clienteExistente, vehiculo, LocalDate.now());
    }
    public Cliente getCliente() {
        return cliente;
    }

    private void setCliente(Cliente cliente) {
        Objects.requireNonNull(cliente,"El cliente no puede ser nulo.");
        this.cliente = cliente;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    private void setVehiculo(Vehiculo vehiculo) {
        Objects.requireNonNull(vehiculo,"El vehículo no puede ser nulo.");
        this.vehiculo = vehiculo;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    private void setFechaInicio(LocalDate fechaInicio) {
        Objects.requireNonNull(fechaInicio,"La fecha de inicio no puede ser nula.");
        if (fechaInicio.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de inicio no puede ser futura.");
        }
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    private void setFechaFin(LocalDate fechaFin) {
        Objects.requireNonNull(fechaFin,"La fecha de fin no puede ser nula.");
        if (fechaFin.isBefore(fechaInicio)) {
            throw new IllegalArgumentException("La fecha de fin no puede ser anterior a la fecha de inicio.");
        }
        if (fechaFin.isAfter(LocalDate.now())){
            throw new IllegalArgumentException("La fecha de fin no puede ser futura.");
        }
        this.fechaFin = fechaFin;
    }

    public int getHoras() {
        return horas;
    }
    public void anadirHoras (int horas) throws TallerMecanicoExcepcion {
        if (estaCerrado()) {
            throw new TallerMecanicoExcepcion("No se puede añadir horas, ya que el trabajo está cerrado.");
        }
        if (horas <= 0 ){
            throw new IllegalArgumentException("Las horas a añadir deben ser mayores que cero.");
        }
        this.horas += horas;

    }

    public boolean estaCerrado(){
        return fechaFin != null;
    }
    public void cerrar(LocalDate fechaFin) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(fechaFin,"La fecha de fin no puede ser nula.");
        if (estaCerrado()){
            throw new TallerMecanicoExcepcion("El trabajo ya está cerrado.");
        }
        setFechaFin(fechaFin);
    }
    public float getPrecio(){
        return getPrecioFijo() + getPrecioEspecifico();
    }
    private float getPrecioFijo(){
        return getDias() * FACTOR_DIA;
    }
    private float getDias(){
        if(fechaFin == null){
            return 0;
        }
        return ChronoUnit.DAYS.between(fechaInicio, fechaFin);
    }
    public abstract float getPrecioEspecifico();

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Trabajo trabajo)) return false;
        return Objects.equals(fechaInicio, trabajo.fechaInicio) && Objects.equals(cliente, trabajo.cliente) && Objects.equals(vehiculo, trabajo.vehiculo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fechaInicio, cliente, vehiculo);
    }
}




