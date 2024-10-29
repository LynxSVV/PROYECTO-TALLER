package Controller;
import Model.Cliente;
import Model.Empleado;
import Model.UsuarioDAO;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
public class ClienteController {
     private UsuarioDAO usuarioDao; 
    public ClienteController() {
        usuarioDao = new UsuarioDAO();
    }  
    //Insertamos el Cliente y Retornamos el ClienteID
    public int agregarCliente(String nombre, String apellido, String dni, String direccion, String celular, String email, Date fechaContratacion){
        return usuarioDao.insertarCliente(nombre, apellido, dni, direccion, celular, email, fechaContratacion);
    }
    //Retornar todos los clientes
    public List<Cliente> obtenerTodosLosClientes() {
    List<Cliente> clientes = new ArrayList<>();
    try {
        clientes = usuarioDao.obtenerClientes();
    } catch (Exception e) {
        System.err.println("Error al obtener empleados desde el controlador: " + e.getMessage());
    }
    return clientes;
    }
    //Obtener el ClienteID por el dni
    public int obtenerClienteIDPorDni(String dni) {
        int ClienteID = -1; 
        try {
            ClienteID = usuarioDao.obtenerClienteIDPorDNI(dni);
        } catch (Exception e) {
            System.err.println("Error al obtener el Cliente: " + e.getMessage());
        }
        return ClienteID;
    }
    //Eliminar el cliiente por DNI
    public void eliminarClientePorDNI(String dni) {
        try {
            usuarioDao.eliminarClientePorDNI(dni);
        } catch (Exception e) {
            System.err.println("Error al eliminar el Cliente: " + e.getMessage());
        }
    }
    //Obtener datos del Cliente y habilitar para modificar
    public Cliente obtenerClientePorDniModificar(String dni) {
        return usuarioDao.obtenerClientePorDNIModificar(dni);
    }
    //Aplicar cambios en modificar
    public boolean actualizarCliente(String dni, String nombre, String apellido, String Direccion, String numeroCelular,String email) {
        return usuarioDao.actualizarCliente(dni, nombre, apellido, Direccion, numeroCelular, email);
    }
}
