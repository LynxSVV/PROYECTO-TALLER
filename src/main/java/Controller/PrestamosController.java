package Controller;
import Model.Cliente;
import Model.Prestamos;
import Model.UsuarioDAO;
import java.util.List;
public class PrestamosController {   
    private UsuarioDAO usuarioDao;
    public PrestamosController() {
        this.usuarioDao = new UsuarioDAO();
    }   
    public Integer obtenerClienteIDPorNumeroCuenta(String numeroCuenta) {
        return usuarioDao.obtenerClienteIDPorNumeroCuenta(numeroCuenta);
    }
     public double calcularInteres(int plazoMeses, double monto) {
        return usuarioDao.calcularInteres(plazoMeses, monto);
    }
    public boolean insertarPrestamo(int clienteID, double montoPrestamo, double interes, int plazo, java.sql.Date fechaDesembolso, int empleadoID, String numeroCuenta){
        return usuarioDao.insertarPrestamo(clienteID, montoPrestamo, interes, plazo, fechaDesembolso, empleadoID, numeroCuenta);
    }
    public Integer obtenerEmpleadoIdPorUsuario(String usuario){
        return usuarioDao.obtenerEmpleadoIDporUsuario(usuario);
    }
    public boolean existePrestamoPorNumeroCuenta(String numeroCuenta) {
        return usuarioDao.existePrestamoPorNumeroCuenta(numeroCuenta);
    }
    public boolean desactivarPrestamo(String numeroCuenta) {
        return usuarioDao.desactivarPrestamo(numeroCuenta);
    }
    public List<Prestamos> obtenerTodosLosPrestamos(){
        return usuarioDao.obtenerTodosLosPrestamos();
    }   
    public List<Cliente> obtenerTodosLosClientes(){
        return usuarioDao.obtenerTodosLosClientesNombreApellido();
    }    
}   
