package dao;

import conexion.ConexionBD;
import modelo.Paquete;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class PaqueteDAO {

    public boolean registrar(Paquete paquete) {

        String sql = "INSERT INTO paquetes (guia_rastreo, ciudad_origen, ciudad_destino, remitente, destinatario, direccion_entrega, peso, costo_envio, tipo_envio, estado, ubicacion_actual) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = ConexionBD.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, paquete.getGuia());
            ps.setString(2, paquete.getCiudadOrigen());
            ps.setString(3, paquete.getCiudadDestino());
            ps.setString(4, paquete.getRemitente());
            ps.setString(5, paquete.getDestinatario());
            ps.setString(6, paquete.getDireccion());
            ps.setDouble(7, paquete.getPeso());
            ps.setDouble(8, paquete.getCostoEnvio());
            ps.setString(9, paquete.getTipo());
            ps.setString(10, paquete.getEstado());
            ps.setString(11, paquete.getCiudadOrigen());

            int resultado = ps.executeUpdate();
            return resultado > 0;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al registrar: " + e.getMessage());
            return false;
        }
    }

    public List<Paquete> listarTodos() {
        List<Paquete> lista = new ArrayList<>();
        String sql = "SELECT * FROM paquetes";

        try (Connection con = ConexionBD.conectar(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Paquete p = new Paquete();
                p.setGuia(rs.getString("guia_rastreo"));
                p.setCiudadOrigen(rs.getString("ciudad_origen"));
                p.setCiudadDestino(rs.getString("ciudad_destino"));
                p.setRemitente(rs.getString("remitente"));
                p.setDestinatario(rs.getString("destinatario"));
                p.setDireccion(rs.getString("direccion_entrega"));
                p.setPeso(rs.getDouble("peso"));
                p.setCostoEnvio(rs.getDouble("costo_envio"));
                p.setTipo(rs.getString("tipo_envio"));
                p.setEstado(rs.getString("estado"));
                p.setUbicacionActual(rs.getString("ubicacion_actual"));
                p.setFechaSistema(rs.getTimestamp("fecha_registro"));

                lista.add(p);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al listar: " + e.getMessage());
        }
        return lista;
    }

    public Paquete buscarPorGuia(String guia) {    // rastreamos paquete por guia
        String sql = "SELECT * FROM paquetes WHERE guia_rastreo = ?";
        try (Connection con = ConexionBD.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, guia);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Paquete p = new Paquete();
                    p.setGuia(rs.getString("guia_rastreo"));
                    p.setCiudadOrigen(rs.getString("ciudad_origen"));
                    p.setCiudadDestino(rs.getString("ciudad_destino"));
                    p.setRemitente(rs.getString("remitente"));
                    p.setDestinatario(rs.getString("destinatario"));
                    p.setDireccion(rs.getString("direccion_entrega"));
                    p.setPeso(rs.getDouble("peso"));
                    p.setCostoEnvio(rs.getDouble("costo_envio"));
                    p.setTipo(rs.getString("tipo_envio"));
                    p.setEstado(rs.getString("estado"));
                    p.setUbicacionActual(rs.getString("ubicacion_actual"));
                    p.setFechaSistema(rs.getTimestamp("fecha_registro"));
                    return p;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar: " + e.getMessage());
        }
        return null;
    }

    public boolean actualizarEstado(String guia, String nuevoEstado, String nuevaUbicacion) {
        Paquete paquete = buscarPorGuia(guia);
        if (paquete == null) {
            System.out.println("No se encontró el paquete para actualizar estado.");
            return false;
        }

        String sql = "UPDATE paquetes "
                + "SET estado = ?, ubicacion_actual = ? "
                + "WHERE guia_rastreo = ?";

        try (Connection con = ConexionBD.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nuevoEstado);
            ps.setString(2, nuevaUbicacion); // Guardamos la ciudad real seleccionada en la vista
            ps.setString(3, guia);

            int resultado = ps.executeUpdate();
            return resultado > 0;

        } catch (SQLException e) {
            System.out.println("Error actualizar estado: " + e.getMessage());
            return false;
        }
    }

    public boolean existeGuia(String guia) {

        String sql
                = "SELECT guia_rastreo "
                + "FROM paquetes "
                + "WHERE guia_rastreo = ?";

        try (Connection con = ConexionBD.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, guia);

            try (ResultSet rs = ps.executeQuery()) {

                return rs.next();
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error verificando guía: "
                    + e.getMessage()
            );

            return false;
        }
    }
}
