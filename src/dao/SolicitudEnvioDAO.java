/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import conexion.ConexionBD;
import modelo.SolicitudEnvio;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author camil
 */
public class SolicitudEnvioDAO {

    public boolean registrarSolicitud(SolicitudEnvio solicitud) {  // registramos la solicitud en sql

        String sql
                = "INSERT INTO solicitudes_envio("
                + "remitente,"
                + "destinatario,"
                + "ciudad_origen,"
                + "ciudad_destino,"
                + "direccion_entrega,"
                + "peso,"
                + "costo_envio,"
                + "tipo_envio,"
                + "estado"
                + ") "
                + "VALUES(?,?,?,?,?,?,?,?,?)";

        try (
                Connection con = ConexionBD.conectar(); // conectamos a base de datos
                 PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(   // enviamos datos al insert 
                    1,
                    solicitud.getRemitente()
            );

            ps.setString(
                    2,
                    solicitud.getDestinatario()
            );

            ps.setString(
                    3,
                    solicitud.getCiudadOrigen()
            );

            ps.setString(
                    4,
                    solicitud.getCiudadDestino()
            );

            ps.setString(
                    5,
                    solicitud.getDireccionEntrega()
            );

            ps.setDouble(
                    6,
                    solicitud.getPeso()
            );

            ps.setDouble(
                    7,
                    solicitud.getCostoEnvio()
            );

            ps.setString(
                    8,
                    solicitud.getTipoEnvio()
            );

            ps.setString(
                    9,
                    solicitud.getEstado()
            );

            int resultado = ps.executeUpdate();

            return resultado > 0;

        } catch (SQLException e) {

            System.out.println(
                    "Error registrar solicitud: "
                    + e.getMessage()
            );

            return false;
        }
    }

    public ResultSet listarSolicitudes() {  // listamos todas solicitudes de envio de paquetes

        ResultSet resultado = null;

        try {

            Connection con = ConexionBD.conectar(); // conectamos a sql

            String sql // cultamos las solicitudes
                    = "SELECT * FROM solicitudes_envio "
                    + "ORDER BY id_solicitud DESC";

            PreparedStatement ps
                    = con.prepareStatement(sql);  // preparamos la consulta

            resultado = ps.executeQuery(); // ejecutamos la consulta

        } catch (SQLException e) {

            System.out.println(
                    "Error listar solicitudes: "
                    + e.getMessage()
            );
        }

        return resultado;
    }

    public String generarGuia() {

        String letras = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";  // letras disponible para generar la guia aleatoria

        String guia = "";  // variable para guardar la guia

        for (int i = 0; i < 2; i++) {   // generamos 2 letras aleatorias

            int posicion = (int) (Math.random() * letras.length());

            guia += letras.charAt(posicion);
        }

        int numero = (int) (Math.random() * 900) + 100;  // generamos 3 numeros aleatorios

        guia += numero;

        return guia;  // retornamos guia completa
    }

    public boolean aceptarSolicitud(
            int idSolicitud,
            String guia
    ) {

        String sql // actualizamos la solicitud
                = "UPDATE solicitudes_envio "
                + "SET estado = ?, guia = ? "
                + "WHERE id_solicitud = ?";

        try (
                Connection con = ConexionBD.conectar(); // conectamos a base de datos
                 PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, "ACEPTADA"); // enviamos datos al update

            ps.setString(2, guia);

            ps.setInt(3, idSolicitud);

            int resultado = ps.executeUpdate(); // ejecutamos el update

            return resultado > 0;

        } catch (SQLException e) {

            System.out.println(
                    "Error aceptar solicitud: "
                    + e.getMessage()
            );

            return false;
        }
    }

    public boolean rechazarSolicitud(
            int idSolicitud
    ) {

        String sql // sql para rechazar la solicitud
                = "UPDATE solicitudes_envio "
                + "SET estado = ? "
                + "WHERE id_solicitud = ?";

        try (
                Connection con = ConexionBD.conectar(); // conectamos
                 PreparedStatement ps = con.prepareStatement(sql)) { // preparamoos

            ps.setString(1, "RECHAZADA"); // nuevo estado, en caso que ADMIN quiera RECHAZAR

            ps.setInt(2, idSolicitud); // ID de la solicitus

            int resultado = ps.executeUpdate(); // ejecutanmos update

            return resultado > 0;

        } catch (SQLException e) {

            System.out.println(
                    "Error rechazar solicitud: "
                    + e.getMessage()
            );

            return false;
        }
    }

}
