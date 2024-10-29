package Model;

public class UsuarioSistema {

    // Atributos privados que almacenan la información del usuario del sistema
    private int usuarioID; // Identificador único del usuario
    private int empleadoID; // Identificador del empleado asociado a este usuario
    private String nombreUsuario; // Nombre de usuario del sistema
    private String contraseña; // Contraseña del usuario (debe ser cifrada)
    private String rol; // Rol del usuario ('Cajero' o 'Gerente')
    private String fechaCreacion; // Fecha en la que se creó la cuenta de usuario

    // Constructor por defecto (sin parámetros)
    public UsuarioSistema() {
    }

    // Constructor con parámetros para inicializar todos los atributos
    public UsuarioSistema(int usuarioID, int empleadoID, String nombreUsuario, String contraseña, String rol, String fechaCreacion) {
        this.usuarioID = usuarioID; // Asigna el ID del usuario
        this.empleadoID = empleadoID; // Asigna el ID del empleado asociado
        this.nombreUsuario = nombreUsuario; // Asigna el nombre de usuario
        this.contraseña = contraseña; // Asigna la contraseña (debe ser cifrada antes de almacenar)
        this.rol = rol; // Asigna el rol del usuario en el sistema
        this.fechaCreacion = fechaCreacion; // Asigna la fecha de creación de la cuenta
    }

    // Métodos getter y setter para acceder y modificar los atributos

    public int getUsuarioID() {
        return usuarioID; // Devuelve el identificador único del usuario
    }

    public void setUsuarioID(int usuarioID) {
        this.usuarioID = usuarioID; // Asigna el identificador único del usuario
    }

    public int getEmpleadoID() {
        return empleadoID; // Devuelve el identificador del empleado asociado
    }

    public void setEmpleadoID(int empleadoID) {
        this.empleadoID = empleadoID; // Asigna el identificador del empleado asociado
    }

    public String getNombreUsuario() {
        return nombreUsuario; // Devuelve el nombre de usuario
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario; // Asigna el nombre de usuario
    }

    public String getContraseña() {
        return contraseña; // Devuelve la contraseña del usuario
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña; // Asigna la contraseña del usuario
    }

    public String getRol() {
        return rol; // Devuelve el rol del usuario ('Cajero' o 'Gerente')
    }

    public void setRol(String rol) {
        this.rol = rol; // Asigna el rol del usuario ('Cajero' o 'Gerente')
    }

    public String getFechaCreacion() {
        return fechaCreacion; // Devuelve la fecha en que fue creada la cuenta del usuario
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion; // Asigna la fecha de creación de la cuenta del usuario
    }
}
