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
public class GestionReporte {
    
     private int idReporte;

    private String guiaPaquete;

    private String motivo;

    private String descripcion;

    private String rutaEvidencia;

    private Timestamp fechaRegistro;

    private String estado;

    

    public int getIdReporte() {
        return idReporte;
    }

    public void setIdReporte(int idReporte) {
        this.idReporte = idReporte;
    }

    public String getGuiaPaquete() {
        return guiaPaquete;
    }

    public void setGuiaPaquete(String guiaPaquete) {
        this.guiaPaquete = guiaPaquete;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getRutaEvidencia() {
        return rutaEvidencia;
    }

    public void setRutaEvidencia(String rutaEvidencia) {
        this.rutaEvidencia = rutaEvidencia;
    }

    public Timestamp getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Timestamp fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
}
