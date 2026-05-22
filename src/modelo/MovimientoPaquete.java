/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.sql.Timestamp;

/**
 *
 * @author camil
 */
public class MovimientoPaquete {

    private int idMovimiento;
    private String guiaRastreo;
    private String estado;
    private String ubicacion;
    private String descripcion;
    private Timestamp fechaMovimiento;

    public MovimientoPaquete() {
    }

    public MovimientoPaquete(int idMovimiento,
            String guiaRastreo,
            String estado,
            String ubicacion,
            String descripcion,
            Timestamp fechaMovimiento) {

        this.idMovimiento = idMovimiento;
        this.guiaRastreo = guiaRastreo;
        this.estado = estado;
        this.ubicacion = ubicacion;
        this.descripcion = descripcion;
        this.fechaMovimiento = fechaMovimiento;
    }

    public int getIdMovimiento() {
        return idMovimiento;
    }

    public void setIdMovimiento(int idMovimiento) {
        this.idMovimiento = idMovimiento;
    }

    public String getGuiaRastreo() {
        return guiaRastreo;
    }

    public void setGuiaRastreo(String guiaRastreo) {
        this.guiaRastreo = guiaRastreo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Timestamp getFechaMovimiento() {
        return fechaMovimiento;
    }

    public void setFechaMovimiento(Timestamp fechaMovimiento) {
        this.fechaMovimiento = fechaMovimiento;
    }
}
