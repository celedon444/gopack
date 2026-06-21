/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package conexion;

/**
 *
 * @author camil
 */
public class ConexionInfo {
    
     private String url = "jdbc:mysql://localhost:3306/sistema_logistica";
    private String username = "root";
    private String password = "cbn2016";

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
    
}
