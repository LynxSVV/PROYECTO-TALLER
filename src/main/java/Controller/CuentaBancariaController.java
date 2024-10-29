package Controller;
import Model.CuentaBancaria;
import Model.UsuarioDAO;
import Model.UsuarioSistema;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
public class CuentaBancariaController {   
     private UsuarioDAO usuarioDao; 
    public CuentaBancariaController() {
        usuarioDao = new UsuarioDAO();
    }
    //Insertamos la Cuenta Bancaria del cliente Referente al ID del Cliente y retornamos true o false
    public boolean agregarCuentaBancaria(int ClienteID, String numCuenta, String tipoCuenta, double Saldo, Date fechaApertura) {
        return usuarioDao.insertarCuentaBancaria(ClienteID, numCuenta, tipoCuenta, Saldo, fechaApertura);
    }
    //Retornamos todas las cuentas bancarias
    public List<CuentaBancaria> obtenerTodasLasCuentas() {
    List<CuentaBancaria> cuentas = new ArrayList<>();
    try {
        cuentas = usuarioDao.obtenerCuentasBancarias();
    } catch (Exception e) {
        System.err.println("Error al obtener empleados desde el controlador: " + e.getMessage());
    }
    return cuentas;
    }
    //Eliminar cuenta bancaria por EmpleadoID
    public void eliminarCuentaBancariaPorEmpleadoID(int empleadoID) {
        try {
            usuarioDao.eliminarCuentaBancariaPorClienteID(empleadoID);
        } catch (Exception e) {
            System.err.println("Error al eliminar el usuario: " + e.getMessage());
        }
    }
    //Obtener la cuenta bancaria para habilitar el modificar Mediante el dni
    public CuentaBancaria obtenerCuentaBancariaPorDniModificar(String dni) {
        return usuarioDao.obtenerCuentaBancariaPorDNIModificar(dni);
    }
    //Aplicar cambios de modificar
    public boolean actualizarCuentaBancaria(String dni, String tipoCuenta) {
        return usuarioDao.actualizarCuentaBancaria(dni, tipoCuenta);
    }
}
