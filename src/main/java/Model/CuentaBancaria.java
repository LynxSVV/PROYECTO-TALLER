package Model;
public class CuentaBancaria {   
    // Atributos privados para encapsular la información de la cuenta bancaria
    private int cuentaID; // Identificador único de la cuenta bancaria
    private int clienteID; // Identificador del cliente asociado a la cuenta
    private String numeroCuenta; // Número de cuenta bancaria (único)
    private String tipoCuenta; // Tipo de cuenta: 'Ahorros' o 'Corriente'
    private double saldo; // Saldo actual de la cuenta
    private String fechaApertura; // Fecha en que se abrió la cuenta
    // Constructor por defecto (sin parámetros)
    public CuentaBancaria() {
    } 
    // Constructor con parámetros para inicializar todos los atributos de la clase
    public CuentaBancaria(int cuentaID, int clienteID, String numeroCuenta, String tipoCuenta, double saldo, String fechaApertura) {
        this.cuentaID = cuentaID; // Asigna el identificador de la cuenta
        this.clienteID = clienteID; // Asigna el identificador del cliente asociado
        this.numeroCuenta = numeroCuenta; // Asigna el número de cuenta bancaria
        this.tipoCuenta = tipoCuenta; // Asigna el tipo de cuenta ('Ahorros' o 'Corriente')
        this.saldo = saldo; // Asigna el saldo inicial de la cuenta
        this.fechaApertura = fechaApertura; // Asigna la fecha de apertura de la cuenta
    }
    // Métodos getter y setter para cada atributo
    // Permiten acceder y modificar los valores de los atributos de la clase    
    public int getCuentaID() {
        return cuentaID; // Devuelve el ID de la cuenta
    }
    public void setCuentaID(int cuentaID) {
        this.cuentaID = cuentaID; // Asigna el ID de la cuenta
    }
    public int getClienteID() {
        return clienteID; // Devuelve el ID del cliente
    }
    public void setClienteID(int clienteID) {
        this.clienteID = clienteID; // Asigna el ID del cliente
    }
    public String getNumeroCuenta() {
        return numeroCuenta; // Devuelve el número de cuenta bancaria
    }
    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta; // Asigna el número de cuenta bancaria
    }
    public String getTipoCuenta() {
        return tipoCuenta; // Devuelve el tipo de cuenta ('Ahorros' o 'Corriente')
    }
    public void setTipoCuenta(String tipoCuenta) {
        this.tipoCuenta = tipoCuenta; // Asigna el tipo de cuenta
    }
    public double getSaldo() {
        return saldo; // Devuelve el saldo de la cuenta
    }
    public void setSaldo(double saldo) {
        this.saldo = saldo; // Asigna el saldo de la cuenta
    }
    public String getFechaApertura() {
        return fechaApertura; // Devuelve la fecha de apertura de la cuenta
    }
    public void setFechaApertura(String fechaApertura) {
        this.fechaApertura = fechaApertura; // Asigna la fecha de apertura de la cuenta
    }     
}

