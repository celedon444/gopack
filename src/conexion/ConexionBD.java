package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class ConexionBD {

    public static Connection conectar() {
        Connection con = null;

        try {
            ConexionInfo info = new ConexionInfo();
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(
                info.getUrl(),
                info.getUsername(),
                info.getPassword()
            );

            System.out.println("Conexion exitosa");

        } catch (Exception e) {
            System.out.println("Error de conexion: " + e.getMessage());
        }

        return con;
    }
}