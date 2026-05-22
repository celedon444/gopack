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

    // 1. Constructor vacío (Estándar POJO)
    public Usuario() {
    }

    // 2. Constructor con parámetros
    public Usuario(String nombre, String password, String rol) {
        this.nombre = nombre;
        this.password = password;
        this.rol = rol;
    }

    // 3. Métodos Getter y Setter (Encapsulamiento)
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

    // 4. Método toString para depuración rápida
    @Override
    public String toString() {
        return "Usuario{" + "nombre=" + nombre + ", rol=" + rol + '}';
    }
}