package Model;
public class Cliente {
    private int clienteID;
    private String nombre;
    private String apellido;
    private String dni;
    private String direccion;
    private String telefono;
    private String email;
    private String fechaRegistro;
    public Cliente() {
    }
    public Cliente(int clienteID, String nombre, String apellido, String dni, String direccion, String telefono, String email, String fechaRegistro) {
        this.clienteID = clienteID;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.direccion = direccion;
        this.telefono = telefono;
        this.email = email;
        this.fechaRegistro = fechaRegistro;
    }
    public int getClienteID() {
        return clienteID;
    }
    public void setClienteID(int clienteID) {
        this.clienteID = clienteID;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getApellido() {
        return apellido;
    }
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    public String getDni() {
        return dni;
    }
    public void setDni(String dni) {
        this.dni = dni;
    }
    public String getDireccion() {
        return direccion;
    }
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    public String getTelefono() {
        return telefono;
    }
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getFechaRegistro() {
        return fechaRegistro;
    }
    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }     
}
