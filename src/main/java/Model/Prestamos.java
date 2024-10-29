package Model;

public class Prestamos {   
    
    // Atributos privados para encapsular la información del préstamo
    private int prestamoID; // Identificador único del préstamo
    private int clienteID; // Identificador del cliente asociado al préstamo
    private double montoPrestamo; // Monto total del préstamo otorgado
    private double interes; // Porcentaje de interés aplicado al préstamo
    private int plazo; // Número de meses para pagar el préstamo
    private String fechaDesembolso; // Fecha en que se realizó el desembolso del préstamo
    private String estado; // Estado del préstamo: 'Activo' o 'Pagado'
    private int empleadoID; // Identificador del empleado que aprobó el préstamo
    private String numeroCuenta; // Número de cuenta asociada al préstamo

    // Constructor por defecto (sin parámetros)
    public Prestamos() {
    }

    // Constructor con parámetros para inicializar todos los atributos de la clase
    public Prestamos(int prestamoID, int clienteID, double montoPrestamo, double interes, int plazo, String fechaDesembolso, String estado, int empleadoID, String numeroCuenta) {
        this.prestamoID = prestamoID; // Asigna el identificador del préstamo
        this.clienteID = clienteID; // Asigna el identificador del cliente
        this.montoPrestamo = montoPrestamo; // Asigna el monto del préstamo
        this.interes = interes; // Asigna el interés del préstamo
        this.plazo = plazo; // Asigna el plazo de pago del préstamo en meses
        this.fechaDesembolso = fechaDesembolso; // Asigna la fecha de desembolso
        this.estado = estado; // Asigna el estado del préstamo
        this.empleadoID = empleadoID; // Asigna el identificador del empleado
        this.numeroCuenta = numeroCuenta; // Asigna el número de cuenta asociada al préstamo
    } 

    // Métodos getter y setter para cada atributo
    // Permiten acceder y modificar los valores de los atributos de la clase

    public int getPrestamoID() {
        return prestamoID; // Devuelve el identificador del préstamo
    }

    public void setPrestamoID(int prestamoID) {
        this.prestamoID = prestamoID; // Asigna el identificador del préstamo
    }

    public int getClienteID() {
        return clienteID; // Devuelve el identificador del cliente
    }

    public void setClienteID(int clienteID) {
        this.clienteID = clienteID; // Asigna el identificador del cliente
    }

    public double getMontoPrestamo() {
        return montoPrestamo; // Devuelve el monto del préstamo
    }

    public void setMontoPrestamo(double montoPrestamo) {
        this.montoPrestamo = montoPrestamo; // Asigna el monto del préstamo
    }

    public double getInteres() {
        return interes; // Devuelve el interés aplicado al préstamo
    }

    public void setInteres(double interes) {
        this.interes = interes; // Asigna el interés del préstamo
    }

    public int getPlazo() {
        return plazo; // Devuelve el plazo en meses para el pago del préstamo
    }

    public void setPlazo(int plazo) {
        this.plazo = plazo; // Asigna el plazo del préstamo en meses
    }

    public String getFechaDesembolso() {
        return fechaDesembolso; // Devuelve la fecha de desembolso del préstamo
    }

    public void setFechaDesembolso(String fechaDesembolso) {
        this.fechaDesembolso = fechaDesembolso; // Asigna la fecha de desembolso
    }

    public String getEstado() {
        return estado; // Devuelve el estado del préstamo ('Activo' o 'Pagado')
    }

    public void setEstado(String estado) {
        this.estado = estado; // Asigna el estado del préstamo
    }

    public int getEmpleadoID() {
        return empleadoID; // Devuelve el identificador del empleado
    }

    public void setEmpleadoID(int empleadoID) {
        this.empleadoID = empleadoID; // Asigna el identificador del empleado
    }

    public String getNumeroCuenta() {
        return numeroCuenta; // Devuelve el número de cuenta asociada al préstamo
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta; // Asigna el número de cuenta asociada al préstamo
    }
}