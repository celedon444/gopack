package modelo;

/**
 * Representa la entidad Usuario en el sistema.
 * Aplicamos Encapsulamiento para proteger las credenciales.
 */
public class Usuario {
    // Atributos privados
    private String nombre;
    private String password;
    private String rol; 

    // constructor vacio (Estandar POJO)
    public Usuario() {
    }

    // constructor con parametros
    public Usuario(String nombre, String password, String rol) {
        this.nombre = nombre;
        this.password = password;
        this.rol = rol;
    }

    // metodos getter y setter (encapsulamiento)
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    //  metodo toString para depuracion rapida
    @Override
    public String toString() {
        return "Usuario{" + "nombre=" + nombre + ", rol=" + rol + '}';
    }
}