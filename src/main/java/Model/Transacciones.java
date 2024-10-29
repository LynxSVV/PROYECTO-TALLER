package Model; 

public class Transacciones {

    // Atributos privados para encapsular la información de la transacción
    private int transaccionID; // Identificador único de la transacción
    private int cuentaID; // Identificador de la cuenta asociada a la transacción
    private int empleadoID; // Identificador del empleado que realizó la transacción
    private String tipoTransaccion; // Tipo de transacción: 'Depósito', 'Retiro', etc.
    private double monto; // Monto de la transacción
    private String fechaTransaccion; // Fecha en la que se realizó la transacción

    // Constructor por defecto (sin parámetros)
    public Transacciones() {
    }

    // Constructor con dos parámetros, utilizado en caso de que solo se tenga el ID y la fecha
    public Transacciones(int transaccionID, String fechaTransaccion) {
        this.transaccionID = transaccionID; // Asigna el ID de la transacción
        this.fechaTransaccion = fechaTransaccion; // Asigna la fecha de la transacción
    }

    // Constructor con todos los atributos para inicializar un objeto Transacciones completo
    public Transacciones(int transaccionID, int cuentaID, int empleadoID, String tipoTransaccion, double monto, String fechaTransaccion) {
        this.transaccionID = transaccionID; // Asigna el ID de la transacción
        this.cuentaID = cuentaID; // Asigna el ID de la cuenta involucrada
        this.empleadoID = empleadoID; // Asigna el ID del empleado que realizó la transacción
        this.tipoTransaccion = tipoTransaccion; // Asigna el tipo de transacción
        this.monto = monto; // Asigna el monto de la transacción
        this.fechaTransaccion = fechaTransaccion; // Asigna la fecha de la transacción
    }

    // Métodos getter y setter para cada atributo
    // Permiten acceder y modificar los valores de los atributos de la clase

    public int getTransaccionID() {
        return transaccionID; // Devuelve el identificador de la transacción
    }

    public void setTransaccionID(int transaccionID) {
        this.transaccionID = transaccionID; // Asigna el identificador de la transacción
    }

    public int getCuentaID() {
        return cuentaID; // Devuelve el identificador de la cuenta involucrada
    }

    public void setCuentaID(int cuentaID) {
        this.cuentaID = cuentaID; // Asigna el identificador de la cuenta involucrada
    }

    public int getEmpleadoID() {
        return empleadoID; // Devuelve el identificador del empleado
    }

    public void setEmpleadoID(int empleadoID) {
        this.empleadoID = empleadoID; // Asigna el identificador del empleado
    }

    public String getTipoTransaccion() {
        return tipoTransaccion; // Devuelve el tipo de transacción ('Depósito', 'Retiro', etc.)
    }

    public void setTipoTransaccion(String tipoTransaccion) {
        this.tipoTransaccion = tipoTransaccion; // Asigna el tipo de transacción
    }

    public double getMonto() {
        return monto; // Devuelve el monto de la transacción
    }

    public void setMonto(double monto) {
        this.monto = monto; // Asigna el monto de la transacción
    }

    public String getFechaTransaccion() {
        return fechaTransaccion; // Devuelve la fecha en la que se realizó la transacción
    }

    public void setFechaTransaccion(String fechaTransaccion) {
        this.fechaTransaccion = fechaTransaccion; // Asigna la fecha de la transacción
    }
}