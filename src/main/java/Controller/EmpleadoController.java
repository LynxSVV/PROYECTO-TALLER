package Controller;
import Model.Empleado;
import Model.UsuarioDAO;
import Model.UsuarioSistema;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
public class EmpleadoController {  
   private UsuarioDAO empleadoDao;
    public EmpleadoController() {
        this.empleadoDao = new UsuarioDAO();
    }
    //Insertamos el Empleado y Retornamos el EmpleadoID
    public int agregarEmpleado(String nombre, String apellido, String dni, String cargo, Date fechaContratacion, double salario, String numeroCelular) {
        return empleadoDao.insertarEmpleado(nombre, apellido, dni, cargo, fechaContratacion, salario, numeroCelular);
    }
   //Retornamos los empleados y su ID para la tabla Empleados 
    public List<Empleado> obtenerTodosLosEmpleados() {
    List<Empleado> empleados = new ArrayList<>();
    try {
        empleados = empleadoDao.obtenerEmpleados();
    } catch (Exception e) {
        System.err.println("Error al obtener empleados desde el controlador: " + e.getMessage());
    }
    return empleados;
    }
    //Obtener el EmpleadoID base al DNI para aplicar eliminar usuario por EmpleadoID
    public int obtenerEmpleadoIDPorDni(String dni) {
        int empleadoID = -1; 
        try {
            empleadoID = empleadoDao.obtenerEmpleadoIDPorDNI(dni);
        } catch (Exception e) {
            System.err.println("Error al obtener el EmpleadoID: " + e.getMessage());
        }
        return empleadoID;
    }
    //Eliminar el Empleado por DNI
    public void eliminarEmpleadoPorDNI(String dni) {
        try {
            empleadoDao.eliminarEmpleadoPorDNI(dni);
        } catch (Exception e) {
            System.err.println("Error al eliminar el empleado: " + e.getMessage());
        }
    }
    //Obtenemos el empleado Para habilitar el modificar mediante el DNI
    public Empleado obtenerEmpleadoPorDniModificar(String dni) {
        return empleadoDao.obtenerEmpleadoPorDNIModificar(dni);
    }
    //Actualizamos el Empleado mediante el DNI
    public boolean actualizarEmpleado(String dni, String nombre, String apellido, String cargo, double salario, String numeroCelular) {
        return empleadoDao.actualizarEmpleado(dni, nombre, apellido, cargo, salario, numeroCelular);
    }  
}
