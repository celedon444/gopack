/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import conexion.ConexionBD;
import modelo.MovimientoPaquete;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author camil
 */

public class MovimientoDAO {

    public boolean registrarMovimiento(MovimientoPaquete mov) {      // registramos los movimientos

        String sql = "INSERT INTO movimientos_paquete "
                + "(guia_rastreo, estado, ubicacion, descripcion) "
                + "VALUES (?, ?, ?, ?)";

        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, mov.getGuiaRastreo());
            ps.setString(2, mov.getEstado());
            ps.setString(3, mov.getUbicacion());
            ps.setString(4, mov.getDescripcion());

            int resultado = ps.executeUpdate();

            return resultado > 0;

        } catch (SQLException e) {

            System.out.println("Error registrar movimiento: "
                    + e.getMessage());

            return false;
        }
    }

    
    public List<MovimientoPaquete> obtenerMovimientos(String guia) {     // obtenemos el historial por medio de la guia

        List<MovimientoPaquete> lista = new ArrayList<>();

        String sql = "SELECT * FROM movimientos_paquete "
                + "WHERE guia_rastreo = ? "
                + "ORDER BY fecha_movimiento ASC";

        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, guia);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    MovimientoPaquete mov = new MovimientoPaquete();

                    mov.setIdMovimiento(
                            rs.getInt("id_movimiento")
                    );

                    mov.setGuiaRastreo(
                            rs.getString("guia_rastreo")
                    );

                    mov.setEstado(
                            rs.getString("estado")
                    );

                    mov.setUbicacion(
                            rs.getString("ubicacion")
                    );

                    mov.setDescripcion(
                            rs.getString("descripcion")
                    );

                    mov.setFechaMovimiento(
                            rs.getTimestamp("fecha_movimiento")
                    );

                    lista.add(mov);
                }
            }

        } catch (SQLException e) {

            System.out.println("Error listar movimientos: "
                    + e.getMessage());
        }

        return lista;
    }
}