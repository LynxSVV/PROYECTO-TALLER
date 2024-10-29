package Controller;
import Model.Empleado;
import Model.UsuarioDAO;
import Model.UsuarioSistema;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
public class UsuarioSistemaController {  
    private UsuarioDAO usuarioDao; 
    public UsuarioSistemaController() {
        usuarioDao = new UsuarioDAO();
    }
    //Para el ingreso del usuario al sistema
    public boolean verificarCredenciales(String nombreUsuario, String contraseña) {
        try {
            return usuarioDao.verificarCredenciales(nombreUsuario, contraseña);
        } catch (Exception e) {
            e.printStackTrace();
            return false; 
        }
    }
    //Retarnamos el rol de Usuario para Mostrarlo en la interface
    public String verificarRol(String usuarioId) { 
        String rol = usuarioDao.obtenerRol(usuarioId); 
        if (rol != null) {
            if (rol.equals("Gerente")) {
                return "Gerente"; 
            } else if (rol.equals("Cajero")) {
                return "Cajero"; 
            } else {
                System.out.println("Rol no reconcido");
                return null; 
            }
        } else {
            System.out.println("No se puede obtener el rol de usuario");;
            return null; 
        }
    }
    //Retornamos el nombre y apellido para mostrarlo en la interface
    public String obtenerNombreCompleto(String nombreUsuario) {
    return usuarioDao.obtenerNombreCompleto(nombreUsuario);
}
    //Insertamos el usuario Referente al ID del Empleado y retornamos true o false
    public boolean agregarUsuario(int empleadoID, String nombreUsuario, String contraseña, String rol, Date fechaCreacion) {
        return usuarioDao.insertarUsuario(empleadoID, nombreUsuario, contraseña, rol, fechaCreacion);
    }
    //Retornamos los usuarios y su ID para la tabla Empleados 
    public List<UsuarioSistema> obtenerTodosLosUsuarios() {
    List<UsuarioSistema> usuarioSistem = new ArrayList<>();
    try {
        usuarioSistem = usuarioDao.obtenerUsuarios();
    } catch (Exception e) {
        System.err.println("Error al obtener empleados desde el controlador: " + e.getMessage());
    }
    return usuarioSistem;
    }
    //Eliminar usuario por EmpleadoID
    public void eliminarUsuarioPorEmpleadoID(int empleadoID) {
        try {
            usuarioDao.eliminarUsuarioPorEmpleadoID(empleadoID);
        } catch (Exception e) {
            System.err.println("Error al eliminar el usuario: " + e.getMessage());
        }
    }
    //Obtener el Usuario para habilitar el modificar Mediante el dni
    public UsuarioSistema obtenerUsuarioPorDniModificar(String dni) {
        return usuarioDao.obtenerUsuarioPorDNIModificar(dni);
    }
    //Actualizamos el Usuario mediante el DNI
    public boolean actualizarUsuario(String dni, String nombreUsuario, String contraseña) {
        return usuarioDao.actualizarUsuario(dni, nombreUsuario, contraseña);
    }
}
