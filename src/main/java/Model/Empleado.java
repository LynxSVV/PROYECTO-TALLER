package Model;

public class Empleado {
    
    // Atributos privados para encapsular la información del empleado
    private int empleadoID; // Identificador único del empleado
    private String nombre; // Nombre del empleado
    private String apellido; // Apellido del empleado
    private String dni; // Documento Nacional de Identidad del empleado
    private String cargo; // Cargo del empleado: 'Cajero' o 'Gerente'
    private String fechaContratacion; // Fecha de contratación del empleado
    private double salario; // Salario del empleado
    private String numeroCelular; // Número de celular del empleado

    // Constructor por defecto (sin parámetros)
    public Empleado() {
    }

    // Constructor con parámetros para inicializar todos los atributos de la clase
    public Empleado(int empleadoID, String nombre, String apellido, String dni, String cargo, String fechaContratacion, double salario, String numeroCelular) {
        this.empleadoID = empleadoID; // Asigna el identificador del empleado
        this.nombre = nombre; // Asigna el nombre del empleado
        this.apellido = apellido; // Asigna el apellido del empleado
        this.dni = dni; // Asigna el DNI del empleado
        this.cargo = cargo; // Asigna el cargo del empleado (Cajero o Gerente)
        this.fechaContratacion = fechaContratacion; // Asigna la fecha de contratación del empleado
        this.salario = salario; // Asigna el salario del empleado
        this.numeroCelular = numeroCelular; // Asigna el número de celular del empleado
    }

    // Métodos getter y setter para cada atributo
    // Permiten acceder y modificar los valores de los atributos de la clase
    
    public int getEmpleadoID() {
        return empleadoID; // Devuelve el ID del empleado
    }

    public void setEmpleadoID(int empleadoID) {
        this.empleadoID = empleadoID; // Asigna el ID del empleado
    }

    public String getNombre() {
        return nombre; // Devuelve el nombre del empleado
    }

    public void setNombre(String nombre) {
        this.nombre = nombre; // Asigna el nombre del empleado
    }

    public String getApellido() {
        return apellido; // Devuelve el apellido del empleado
    }

    public void setApellido(String apellido) {
        this.apellido = apellido; // Asigna el apellido del empleado
    }

    public String getDni() {
        return dni; // Devuelve el DNI del empleado
    }

    public void setDni(String dni) {
        this.dni = dni; // Asigna el DNI del empleado
    }

    public String getCargo() {
        return cargo; // Devuelve el cargo del empleado (Cajero o Gerente)
    }

    public void setCargo(String cargo) {
        this.cargo = cargo; // Asigna el cargo del empleado
    }

    public String getFechaContratacion() {
        return fechaContratacion; // Devuelve la fecha de contratación del empleado
    }

    public void setFechaContratacion(String fechaContratacion) {
        this.fechaContratacion = fechaContratacion; // Asigna la fecha de contratación del empleado
    }

    public double getSalario() {
        return salario; // Devuelve el salario del empleado
    }

    public void setSalario(double salario) {
        this.salario = salario; // Asigna el salario del empleado
    }

    public String getNumeroCelular() {
        return numeroCelular; // Devuelve el número de celular del empleado
    }

    public void setNumeroCelular(String numeroCelular) {
        this.numeroCelular = numeroCelular; // Asigna el número de celular del empleado
    }   
}