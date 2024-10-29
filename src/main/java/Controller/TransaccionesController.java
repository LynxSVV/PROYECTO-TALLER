package Controller;
import Model.Transacciones;
import Model.UsuarioDAO;
public class TransaccionesController { 
    private UsuarioDAO usuarioDao;  
    public TransaccionesController() {
        usuarioDao = new UsuarioDAO();
    }
    public Integer obtenerEmpleadoIdPorUsuario(String usuario){
        return usuarioDao.obtenerEmpleadoIDporUsuario(usuario);
    }
    //obtener CuentaID por numero de cuenta
    public Integer obtenerCuentaIdPorNumeroCuenta(String numeroCuenta) {
        return usuarioDao.obtenerCuentaIDPorNumeroCuenta(numeroCuenta);
    }
    //aplicar retiro
    public boolean restarMontoDeCuenta(int cuentaID, double monto){
        return usuarioDao.restarMontoDeCuenta(cuentaID, monto);
    } 
    public boolean insertarTransaccionRetiro(int cuentaID, int empleadoID, String tipoTransaccion, double monto, String numeroCuenta){
        return usuarioDao.insertarTransaccionRetiro(cuentaID, empleadoID, tipoTransaccion, monto, numeroCuenta);
    }
     public boolean SumarMontoDeCuenta(int cuentaID, double monto){
        return usuarioDao.sumarMontoACuenta(cuentaID, monto);
    } 
    public boolean insertarTransaccionDeposito(int cuentaID, int empleadoID, String tipoTransaccion, double monto, String numeroCuenta){
        return usuarioDao.insertarTransaccionDeposito(cuentaID, empleadoID, tipoTransaccion, monto, numeroCuenta);
    }
    public String obtenerNombrePorNumeroCuenta(String numeroCuenta) {
        return usuarioDao.obtenerNombrePorNumeroCuenta(numeroCuenta);
    }
    public Transacciones obtenerUltimaOperacionConFecha() {
    Transacciones operacion = usuarioDao.obtenerUltimaOperacionConFecha();  
    if (operacion == null) {
        // Manejo de casos en que no hay operaciones
        System.out.println("No se encontraron transacciones.");
    }
    return operacion; // Devuelve el objeto Transacciones o null si no hay operaciones
    }
}
