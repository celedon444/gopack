package modelo;

import java.sql.Timestamp; 
/**
 * Representa la entidad Paquete con soporte para cronómetro de tiempo real.
 */
public class Paquete {
    private String guia;
    private String remitente;
    private String destinatario;
    private String direccion;
    private double peso;
    private double costoEnvio;
    private String tipo;
    private String estado;
    private String ciudadOrigen;
    private String ciudadDestino;
    private String ubicacionActual;
    private Timestamp fechaSistema; 

    //  constructor vacio
    public Paquete() {
    }

    //  constructor con parametros 
    public Paquete(String guia, String remitente, String destinatario, String direccion, double peso, double costoEnvio, String tipo, String  estado, String ubicacionActual, Timestamp fechaSistema) {
        this.guia = guia;
        this.remitente = remitente;
        this.destinatario = destinatario;
        this.direccion = direccion;
        this.peso = peso;
        this.costoEnvio = costoEnvio;
        this.tipo = tipo;
        this.estado = estado;
        this.ubicacionActual = ubicacionActual;
        this.fechaSistema = fechaSistema;
    }


    public String getGuia() { return guia; }
    public void setGuia(String guia) { this.guia = guia; }

    public String getRemitente() { return remitente; }
    public void setRemitente(String remitente) { this.remitente = remitente; }

    public String getDestinatario() { return destinatario; }
    public void setDestinatario(String destinatario) { this.destinatario = destinatario; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public double getPeso() { return peso; }
    public void setPeso(double peso) { this.peso = peso; }
    
    public double getCostoEnvio() { return costoEnvio; }
    public void setCostoEnvio(double costoEnvio) { this.costoEnvio = costoEnvio; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String   getEstado() { return estado; }
    public void setEstado(String  estado) { this.estado = estado; }
    
    public String getCiudadOrigen() { return ciudadOrigen; }
    public void setCiudadOrigen(String ciudadOrigen) { this.ciudadOrigen = ciudadOrigen; }

    public String getCiudadDestino() { return ciudadDestino; }
    public void setCiudadDestino(String ciudadDestino) { this.ciudadDestino = ciudadDestino; }
    
    public String getUbicacionActual() { return ubicacionActual; }
    public void setUbicacionActual(String ubicacionActual) { this.ubicacionActual = ubicacionActual; }

    public Timestamp getFechaSistema() { return fechaSistema; }
    public void setFechaSistema(Timestamp fechaSistema) { this.fechaSistema = fechaSistema; }

    @Override
    public String toString() {
        return "Paquete{" + "guia=" + guia + ", estado=" + estado + ", fecha=" + fechaSistema + '}';
    }
}