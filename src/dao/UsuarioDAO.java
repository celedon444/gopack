package dao;

import conexion.ConexionBD;
import modelo.Usuario;
import java.sql.*;

public class UsuarioDAO {

    public Usuario validarUsuario(String user, String pass) {
        Usuario usuarioEncontrado = null;
        String sql = "SELECT username, rol FROM usuarios WHERE username=? AND password=?";

        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, user);
            ps.setString(2, pass);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // si lo encuentra, creamos el objeto con los datos de la BD
                    // usamos username como nombre para el ejemplo
                    usuarioEncontrado = new Usuario();
                    usuarioEncontrado.setNombre(rs.getString("username"));
                    usuarioEncontrado.setRol(rs.getString("rol"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error en LoginDAO: " + e.getMessage());
        }
        return usuarioEncontrado;
    }
public String obtenerNombrePorCedula(String cedula) {
    String nombre = "";
    // CAMBIO AQUÍ: la columna se llama 'nombre', no 'nombre_completo'
    String sql = "SELECT nombre FROM usuarios WHERE cedula = ?"; 
    
    try (Connection con = ConexionBD.conectar();
         PreparedStatement ps = con.prepareStatement(sql)) {
        
        ps.setString(1, cedula);
        
        // Es mejor usar try-with-resources también para el ResultSet
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                // CAMBIO AQUÍ: debe coincidir con el nombre de la columna en la BD
                nombre = rs.getString("nombre"); 
            }
        }
    } catch (SQLException e) {
        System.out.println("Error al obtener nombre: " + e.getMessage());
    }
    return nombre;
}
}