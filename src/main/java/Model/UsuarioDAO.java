package Model;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class UsuarioDAO {   
    private String url = "jdbc:sqlserver://DESKTOP-9JA2EIH:1433;databaseName=probankDB;encrypt=true;trustServerCertificate=true;";
    private String usuario = "sa"; 
    private String contraseña = "123"; 

    private Connection conectar() throws SQLException {
        return DriverManager.getConnection(url, usuario, contraseña);
    }
    //Para el ingreso del usuario al sistema
    public boolean verificarCredenciales(String nombreUsuario, String contraseña) {
    String sql = "SELECT Contraseña FROM usuariosistema WHERE NombreUsuario = ?"; //esta es la consulta
    try (Connection conexion = conectar(); PreparedStatement pstmt = conexion.prepareStatement(sql)) { //La conexion y aplicamos PreparedStatement por temas de seguridad
        pstmt.setString(1, nombreUsuario); //Asignamos el valor con el parametro
        ResultSet rs = pstmt.executeQuery(); //Ejecutamos la consulta
        if (rs.next()) { //toma el primer valor del campo y registro
            byte[] contraseñaEncriptadaBD = rs.getBytes("Contraseña");
            String contraseñaDesencriptada = desencriptarContraseña(contraseñaEncriptadaBD); //Desencriptamos para validad la contra
            if (contraseñaDesencriptada != null) {
                return contraseñaDesencriptada.equals(contraseña); //Retornamos true si la contra es correcta (si existe en ese usuario)
            } else {
                System.out.println("Error: La contraseña desencriptada es nula.");
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false; // Retorna false si hubo un error o no se encontró
}
    //Desencriptar la onntraseña
    private String desencriptarContraseña(byte[] contraseñaEncriptada) {
        String contraseñaDesencriptada = null;
        try (Connection conexion = conectar(); Statement stmt = conexion.createStatement()) {
            // Abre la clave simétrica
            stmt.execute("OPEN SYMMETRIC KEY MiClaveSimetrica DECRYPTION BY PASSWORD = 'angelo.159632478';");
            // Desencriptar la contraseña
            String sql = "SELECT CAST(DECRYPTBYKEY(?) AS NVARCHAR(255)) AS Desencriptado";
            try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
                pstmt.setBytes(1, contraseñaEncriptada); 
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    contraseñaDesencriptada = rs.getString("Desencriptado");
                }
            }
            // Cierra la clave
            stmt.execute("CLOSE SYMMETRIC KEY MiClaveSimetrica;");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contraseñaDesencriptada;
    }
    //Encriptar la contraseña
    private byte[] encriptarContraseña(String contraseña) {
        byte[] contraseñaEncriptada = null;
        try (Connection conexion = conectar(); Statement stmt = conexion.createStatement()) {
            // Abrir la clave simétrica en SQL Server
            stmt.execute("OPEN SYMMETRIC KEY MiClaveSimetrica DECRYPTION BY PASSWORD = 'angelo.159632478';");

            // Encriptar la contraseña
            String sql = "SELECT ENCRYPTBYKEY(KEY_GUID('MiClaveSimetrica'), ?)";
            try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
                pstmt.setString(1, contraseña); // Insertar la contraseña en formato NVARCHAR
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    contraseñaEncriptada = rs.getBytes(1); // Obtener el resultado en formato VARBINARY (byte[])
                }
            }

            // Cerrar la clave simétrica
            stmt.execute("CLOSE SYMMETRIC KEY MiClaveSimetrica;");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contraseñaEncriptada;
    }
    //Retarnamos el rol de Usuario para Mostrarlo en la interface
    public String obtenerRol(String nombreUsuario) {
        String rol = null;
        String sql = "SELECT rol FROM usuariosistema WHERE NombreUsuario = ?"; // Cambia "NombreUsuario" si tu columna tiene un nombre diferente

        try (Connection connection = conectar(); // Usa el método correcto para la conexión
             PreparedStatement statement = connection.prepareStatement(sql)) {
             
            statement.setString(1, nombreUsuario); // Asigna el valor del nombre de usuario al primer parámetro de la consulta
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                rol = resultSet.getString("rol"); // Cambia "rol" si tu columna tiene un nombre diferente
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Manejo de excepciones
        }

        return rol; // Devuelve el rol obtenido o null si no se encuentra
    }
    //Retornamos el nombre y apellido para mostrarlo en la interface
    public String obtenerNombreCompleto(String nombreUsuario) {
        String sql = "SELECT e.Nombre, e.Apellido " +
                     "FROM empleado e " +
                     "INNER JOIN usuariosistema u ON e.EmpleadoID = u.EmpleadoID " +
                     "WHERE u.NombreUsuario = ?";
        try (Connection conexion = conectar(); PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setString(1, nombreUsuario);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String nombre = rs.getString("Nombre");
                String apellido = rs.getString("Apellido");
                return nombre + " " + apellido; 
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; 
    }
    //Insertamos el Emp leado y Retornamos el EmpleadoID
    public int insertarEmpleado(String nombre, String apellido, String dni, String cargo, Date fechaContratacion, double salario, String numeroCelular) {
        String sql = "INSERT INTO empleado (Nombre, Apellido, Dni, Cargo, FechaContratacion, Salario, NumeroCelular) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conexion = conectar(); PreparedStatement stmt = conexion.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, nombre);
            stmt.setString(2, apellido);
            stmt.setString(3, dni);
            stmt.setString(4, cargo);
            stmt.setDate(5, fechaContratacion);
            stmt.setDouble(6, salario);
            stmt.setString(7, numeroCelular);

            int filasAfectadas = stmt.executeUpdate(); //Cuantas filas fueron afectada debe ser 1
            if (filasAfectadas > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1); 
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0; 
        }
    //Insertamos el usuario Referente al ID del Empleado y retornamos true o false
    public boolean insertarUsuario(int empleadoID, String nombreUsuario, String contraseña, String rol, Date fechaCreacion) {
        String sql = "INSERT INTO usuariosistema (EmpleadoID, NombreUsuario, Contraseña, Rol, FechaCreacion) " +
                     "VALUES (?, ?, ?, ?, ?)";

        try (Connection conexion = conectar(); PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, empleadoID);
            stmt.setString(2, nombreUsuario);
            stmt.setBytes(3, encriptarContraseña(contraseña)); // Aplicamos la encriptacion de la contraseña
            stmt.setString(4, rol);
            stmt.setDate(5, fechaCreacion);

            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
   }
    //Retornamos los empleados y su ID para la tabla Empleados 
    public List<Empleado> obtenerEmpleados() {
        List<Empleado> empleados = new ArrayList<>();
        String sql = "SELECT * FROM empleado";

        try (Connection conexion = conectar(); PreparedStatement stmt = conexion.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Empleado empleado = new Empleado();
                empleado.setEmpleadoID(rs.getInt("EmpleadoID"));
                empleado.setNombre(rs.getString("Nombre"));
                empleado.setApellido(rs.getString("Apellido"));
                empleado.setDni(rs.getString("DNI"));
                empleado.setCargo(rs.getString("Cargo"));
                empleado.setFechaContratacion(rs.getString("FechaContratacion")); 
                empleado.setSalario(rs.getDouble("Salario"));
                empleado.setNumeroCelular(rs.getString("NumeroCelular"));
                empleados.add(empleado);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return empleados;
    }      
    //Retornamos los usuarios y su ID para la tabla Empleados     
    public List<UsuarioSistema> obtenerUsuarios() {
        List<UsuarioSistema> usuarios = new ArrayList<>();
    String sql = "SELECT EmpleadoID, NombreUsuario FROM usuariosistema"; 

    try (Connection conexion = conectar(); PreparedStatement stmt = conexion.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
        while (rs.next()) {
            UsuarioSistema usuario = new UsuarioSistema();
            usuario.setEmpleadoID(rs.getInt("EmpleadoID")); 
            String nombreUsuario = rs.getString("NombreUsuario");
            usuario.setNombreUsuario(nombreUsuario);
            usuarios.add(usuario);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return usuarios;
    }
    //Obtener el EmpleadoID base al DNI para aplicar eliminar usuario por EmpleadoID
    public int obtenerEmpleadoIDPorDNI(String dni) {
        int empleadoID = -1; // Valor por defecto si no se encuentra el empleado
        String sql = "SELECT EmpleadoID FROM empleado WHERE DNI = ?";
        try (Connection conexion = conectar(); PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, dni);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                empleadoID = rs.getInt("EmpleadoID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return empleadoID;
    }
    //Eliminar el Empleado por DNI
    public void eliminarEmpleadoPorDNI(String dni) {
        String sql = "DELETE FROM empleado WHERE DNI = ?";

        try (Connection conexion = conectar(); PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, dni);
            //obtener el numero de filas afectas
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //Eliminar usuario por EmpleadoID
    public void eliminarUsuarioPorEmpleadoID(int empleadoID) {
        String sql = "DELETE FROM usuariosistema WHERE EmpleadoID = ?";

        try (Connection conexion = conectar(); PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, empleadoID);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //Obtenemos el empleado Para habilitar el modificar mediante el DNI
    public Empleado obtenerEmpleadoPorDNIModificar(String dni) {
    Empleado empleado = null;
    String sql = "SELECT Nombre, Apellido, Cargo, Salario, NumeroCelular " +
                 "FROM Empleado " +
                 "WHERE DNI = ?";
    try (Connection conexion = conectar(); 
         PreparedStatement stmt = conexion.prepareStatement(sql)) {
        stmt.setString(1, dni);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            empleado = new Empleado();
            empleado.setNombre(rs.getString("Nombre"));
            empleado.setApellido(rs.getString("Apellido"));
            empleado.setCargo(rs.getString("Cargo"));
            empleado.setSalario(rs.getDouble("Salario"));
            empleado.setNumeroCelular(rs.getString("NumeroCelular"));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return empleado; 
}
    //Obtener el Usuario para habilitar el modificar Mediante el dni
    public UsuarioSistema obtenerUsuarioPorDNIModificar(String dni) {
        UsuarioSistema usuarioSistema = null;
        String sql = "SELECT us.NombreUsuario, us.Contraseña " +
                     "FROM usuariosistema us " +
                     "JOIN Empleado e ON us.EmpleadoID = e.EmpleadoID " +
                     "WHERE e.DNI = ?";

        try (Connection conexion = conectar(); 
             PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, dni);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                usuarioSistema = new UsuarioSistema();
                usuarioSistema.setNombreUsuario(rs.getString("NombreUsuario"));
                usuarioSistema.setContraseña(desencriptarContraseña(rs.getBytes("Contraseña"))); // Guarda la contraseña encriptada
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuarioSistema; // Retorna el objeto UsuarioSistema con la información obtenida
    } 
    //Actualizamos el Empleado mediante el DNI
    public boolean actualizarEmpleado(String dni, String nombre, String apellido, String cargo, double salario, String numeroCelular) {
    String sql = "UPDATE empleado " +
                 "SET Nombre = ?, Apellido = ?, Cargo = ?, Salario = ?, NumeroCelular = ? " +
                 "WHERE DNI = ?"; 

    try (Connection conexion = conectar(); PreparedStatement stmt = conexion.prepareStatement(sql)) {
        stmt.setString(1, nombre);
        stmt.setString(2, apellido);
        stmt.setString(3, cargo);
        stmt.setDouble(4, salario);
        stmt.setString(5, numeroCelular);
        stmt.setString(6, dni); 

        int filasAfectadas = stmt.executeUpdate();
        return filasAfectadas > 0; 
    } catch (SQLException e) {
        e.printStackTrace();
        return false; 
    }
}
    //Actualizamos el Usuario mediante el DNI
    public boolean actualizarUsuario(String dni, String nombreUsuario, String contraseña) {
    String sql = "UPDATE usuariosistema " +
                 "SET NombreUsuario = ?, Contraseña = ? " +
                 "WHERE EmpleadoID = (SELECT EmpleadoID FROM empleado WHERE DNI = ?)";

    try (Connection conexion = conectar(); PreparedStatement stmt = conexion.prepareStatement(sql)) {
        stmt.setString(1, nombreUsuario);
        stmt.setBytes(2, encriptarContraseña(contraseña)); //Encriptamos al contra
        stmt.setString(3, dni); 

        int filasAfectadas = stmt.executeUpdate();
        return filasAfectadas > 0; 

    } catch (SQLException e) {
        e.printStackTrace();
        return false; 
    }
}
    //Insertamos el Cliente y Retornamos el ClienteID
    public int insertarCliente(String nombre, String apellido, String dni, String direccion, String celular, String email ,Date fechaContratacion ) {
        String sql = "INSERT INTO cliente (Nombre, Apellido, DNI, Direccion, Telefono, Email, FechaRegistro) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conexion = conectar(); PreparedStatement stmt = conexion.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, nombre);
            stmt.setString(2, apellido);
            stmt.setString(3, dni);
            stmt.setString(4, direccion);
            stmt.setString(5, celular);
            stmt.setString(6, email);
            stmt.setDate(7, fechaContratacion);

            int filasAfectadas = stmt.executeUpdate(); 
            if (filasAfectadas > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1); 
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0; 
        }
    //Insertamos la Cuenta Bancaria del cliente Referente al ID del Cliente y retornamos true o false
    public boolean insertarCuentaBancaria(int ClienteID, String numCuenta, String tipoCuenta, double Saldo, Date fechaApertura) {
        String sql = "INSERT INTO cuentaBancaria (ClienteID, NumeroCuenta, TipoCuenta, Saldo, FechaApertura) " +
                     "VALUES (?, ?, ?, ?, ?)";

        try (Connection conexion = conectar(); PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, ClienteID);
            stmt.setString(2, numCuenta);
            stmt.setString(3, tipoCuenta); 
            stmt.setDouble(4,Saldo );
            stmt.setDate(5, fechaApertura);

            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
   }
    //Retornamos los Clientes y su ID para la tabla cuenta Bancaria 
    public List<Cliente> obtenerClientes() {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM cliente";

        try (Connection conexion = conectar(); PreparedStatement stmt = conexion.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setClienteID(rs.getInt("ClienteID"));
                cliente.setNombre(rs.getString("Nombre"));
                cliente.setApellido(rs.getString("Apellido"));
                cliente.setDni(rs.getString("DNI"));
                cliente.setDireccion(rs.getString("Direccion"));
                cliente.setTelefono(rs.getString("Telefono")); 
                cliente.setEmail(rs.getString("Email"));
                cliente.setFechaRegistro(rs.getString("FechaRegistro"));
                clientes.add(cliente);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clientes;
    }      
    //Retornamos las cuentas bancarias y su ID para la tabla Empleados     
    public List<CuentaBancaria> obtenerCuentasBancarias() {
        List<CuentaBancaria> cuentas = new ArrayList<>();
    String sql = "SELECT ClienteID,NumeroCuenta,TipoCuenta,Saldo,FechaApertura FROM cuentabancaria"; 

    try (Connection conexion = conectar(); PreparedStatement stmt = conexion.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
        while (rs.next()) {
            CuentaBancaria cuenta = new CuentaBancaria();
            cuenta.setClienteID(rs.getInt("ClienteID")); 
            cuenta.setNumeroCuenta(rs.getString("NumeroCuenta"));
            cuenta.setTipoCuenta(rs.getString("TipoCuenta"));
            cuenta.setSaldo(rs.getDouble("Saldo"));
            cuentas.add(cuenta);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return cuentas;
    }   
    //Obtener el ClienteID base al DNI para aplicar eliminar cuenta bancaria por EmpleadoID
    public int obtenerClienteIDPorDNI(String dni) {
        int ClienteID = -1; 
        String sql = "SELECT ClienteID FROM cliente WHERE DNI = ?";
        try (Connection conexion = conectar(); PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, dni);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                ClienteID = rs.getInt("ClienteID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ClienteID;
    }
    //Eliminar el Cliente por DNI
    public void eliminarClientePorDNI(String dni) {
        String sql = "DELETE FROM cliente WHERE DNI = ?";

        try (Connection conexion = conectar(); PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, dni);
            //obtener el numero de filas afectas
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //Eliminar cuenta bancaria por ClienteID
    public void eliminarCuentaBancariaPorClienteID(int ClienteID) {
        String sql = "DELETE FROM cuentabancaria WHERE ClienteID = ?";
        try (Connection conexion = conectar(); PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, ClienteID);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //Obtenemos el cliente Para habilitar el modificar mediante el DNI
    public Cliente obtenerClientePorDNIModificar(String dni) {
    Cliente cliente = null;
    String sql = "SELECT Nombre, Apellido, Direccion, Telefono, Email, FechaRegistro " +
                 "FROM cliente " +
                 "WHERE DNI = ?";
    try (Connection conexion = conectar(); 
        PreparedStatement stmt = conexion.prepareStatement(sql)) {
        stmt.setString(1, dni);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            cliente = new Cliente();
            cliente.setNombre(rs.getString("Nombre"));
            cliente.setApellido(rs.getString("Apellido"));
            cliente.setDireccion(rs.getString("Direccion"));
            cliente.setTelefono(rs.getString("Telefono"));
            cliente.setEmail(rs.getString("Email"));
            cliente.setFechaRegistro(rs.getString("FechaRegistro"));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return cliente; 
}
    //Obtener la cuenta bancaria para habilitar el modificar Mediante el dni
    public CuentaBancaria obtenerCuentaBancariaPorDNIModificar(String dni) {
        CuentaBancaria cuentabancaria = null;
        String sql = "SELECT cb.NumeroCuenta " +
                     "FROM cuentabancaria cb " +
                     "JOIN cliente c ON cb.ClienteID = c.ClienteID " +
                     "WHERE c.DNI = ?";

        try (Connection conexion = conectar(); 
            PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, dni);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                cuentabancaria = new CuentaBancaria();
                cuentabancaria.setTipoCuenta(rs.getString("NumeroCuenta"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cuentabancaria; // Retorna el objeto UsuarioSistema con la información obtenida
    } 
    //Actualizamos el Cliente mediante el DNI
    public boolean actualizarCliente(String dni, String nombre, String apellido, String direccion, String celular, String email) {
    String sql = "UPDATE cliente " +
                 "SET Nombre = ?, Apellido = ?, Direccion = ?, Telefono = ?, Email = ?" +
                 "WHERE DNI = ?"; 

    try (Connection conexion = conectar(); PreparedStatement stmt = conexion.prepareStatement(sql)) {
        stmt.setString(1, nombre);
        stmt.setString(2, apellido);
        stmt.setString(3, direccion);
        stmt.setString(4, celular);
        stmt.setString(5, email);
        stmt.setString(6, dni);

        int filasAfectadas = stmt.executeUpdate();
        return filasAfectadas > 0; 
    } catch (SQLException e) {
        e.printStackTrace();
        return false; 
    }
}
    //Actualizamos el Usuario mediante el DNI
    public boolean actualizarCuentaBancaria(String dni, String tipoCuenta) {
    String sql = "UPDATE cuentabancaria " +
                 "SET TipoCuenta = ?" +
                 "WHERE ClienteID = (SELECT ClienteID FROM cliente WHERE DNI = ?)";

    try (Connection conexion = conectar(); PreparedStatement stmt = conexion.prepareStatement(sql)) {
        stmt.setString(1, tipoCuenta);
        stmt.setString(2, dni); 

        int filasAfectadas = stmt.executeUpdate();
        return filasAfectadas > 0; 

    } catch (SQLException e) {
        e.printStackTrace();
        return false; 
    }
}
    //obtener EmpleadoID por usuario
    public Integer obtenerEmpleadoIDporUsuario(String nombreUsuario) {
        String sql = "SELECT e.EmpleadoID " + // Agregué un espacio después de EmpleadoID
                     "FROM empleado e " +
                     "INNER JOIN usuariosistema u ON e.EmpleadoID = u.EmpleadoID " +
                     "WHERE u.NombreUsuario = ?";
        try (Connection conexion = conectar(); PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setString(1, nombreUsuario);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("EmpleadoID"); // Retorna el EmpleadoID como un Integer
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Retorna null si no se encuentra el empleado
    }
    //obtener CuentaID por numero de cuenta
    public Integer obtenerCuentaIDPorNumeroCuenta(String numeroCuenta) {
    String sql = "SELECT CuentaID FROM cuentabancaria WHERE NumeroCuenta = ?";
    try (Connection conexion = conectar(); PreparedStatement pstmt = conexion.prepareStatement(sql)) {
        pstmt.setString(1, numeroCuenta);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            return rs.getInt("CuentaID");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return null; // Si no se encuentra la cuenta
    }
    //aplicar retiro
    public boolean restarMontoDeCuenta(int cuentaID, double monto) {
    String sql = "UPDATE cuentabancaria SET Saldo = Saldo - ? WHERE CuentaID = ?";
    try (Connection conexion = conectar(); PreparedStatement pstmt = conexion.prepareStatement(sql)) {
        pstmt.setDouble(1, monto);
        pstmt.setInt(2, cuentaID);
        int filasAfectadas = pstmt.executeUpdate();
        return filasAfectadas > 0; // Retorna true si se actualizó el saldo
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false; // Retorna false si hubo un error
}
    // Método para insertar la transacción
    public boolean insertarTransaccionRetiro(int cuentaID, int empleadoID, String tipoTransaccion, double monto, String numeroCuenta) {
        String sql = "INSERT INTO transacciones (CuentaID, EmpleadoID, TipoTransaccion, Monto, FechaTransaccion, NumeroCuenta) VALUES (?, ?, ?, ?, ?, ?)";

        // Obtener la fecha actual en la zona horaria de Perú
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("America/Lima"));
        java.util.Date fechaActual = calendar.getTime();
        java.sql.Timestamp timestamp = new java.sql.Timestamp(fechaActual.getTime());

        try (Connection conexion = conectar(); PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setInt(1, cuentaID);
            pstmt.setInt(2, empleadoID);
            pstmt.setString(3, tipoTransaccion);
            pstmt.setDouble(4, monto);
            pstmt.setTimestamp(5, timestamp); // Establecer la fecha actual como un Timestamp
            pstmt.setString(6, numeroCuenta);

            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0; // Retorna true si se insertó la transacción
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Retorna false si hubo un error
    }
    // Método para sumar monto a la cuenta
    public boolean sumarMontoACuenta(int cuentaID, double monto) {
        String sql = "UPDATE cuentabancaria SET Saldo = Saldo + ? WHERE CuentaID = ?";
        try (Connection conexion = conectar(); PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setDouble(1, monto);
            pstmt.setInt(2, cuentaID);
            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0; // Retorna true si se actualizó el saldo
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Retorna false si hubo un error
    }
    // Método para insertar la transacción de depósito
    public boolean insertarTransaccionDeposito(int cuentaID, int empleadoID, String tipoTransaccion, double monto, String numeroCuenta) {
        String sql = "INSERT INTO transacciones (CuentaID, EmpleadoID, TipoTransaccion, Monto, FechaTransaccion, NumeroCuenta) VALUES (?, ?, ?, ?, ?, ?)";

        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("America/Lima"));
        java.util.Date fechaActual = calendar.getTime();
        java.sql.Timestamp timestamp = new java.sql.Timestamp(fechaActual.getTime());

        try (Connection conexion = conectar(); PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setInt(1, cuentaID);
            pstmt.setInt(2, empleadoID);
            pstmt.setString(3, tipoTransaccion);
            pstmt.setDouble(4, monto);
            pstmt.setTimestamp(5, timestamp); // Establecer la fecha actual como un Timestamp
            pstmt.setString(6, numeroCuenta);

            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0; // Retorna true si se insertó la transacción
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Retorna false si hubo un error
    }
    public String obtenerNombrePorNumeroCuenta(String numeroCuenta) {
    String sql = "SELECT cliente.Nombre, cliente.Apellido FROM cliente INNER JOIN cuentabancaria ON cliente.ClienteID = cuentabancaria.ClienteID WHERE cuentabancaria.NumeroCuenta = ?";
    String nombreCompleto = null;

    try (Connection conexion = conectar(); PreparedStatement pstmt = conexion.prepareStatement(sql)) {
        pstmt.setString(1, numeroCuenta);
        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
            String nombre = rs.getString("Nombre");
            String apellido = rs.getString("Apellido");
            // Concatenar nombre y apellido
            nombreCompleto = nombre + " " + apellido; // Puedes ajustar el formato si lo deseas
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return nombreCompleto;
}
    public Transacciones obtenerUltimaOperacionConFecha() {
        String sql = "SELECT TOP 1 TransaccionID, FechaTransaccion FROM transacciones ORDER BY TransaccionID DESC";
        Transacciones transaccion = null;

        try (Connection conexion = conectar(); PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int transaccionID = rs.getInt("TransaccionID");
                Timestamp fechaTransaccion = rs.getTimestamp("FechaTransaccion"); // Obtener Timestamp

                // Formatear la fecha y hora
                SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String fechaFormateada = formatoFecha.format(fechaTransaccion);

                // Crear una nueva instancia de Operacion
                transaccion = new Transacciones(transaccionID, fechaFormateada);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return transaccion; 
    }    
    public Integer obtenerClienteIDPorNumeroCuenta(String numeroCuenta) {
        String sql = "SELECT ClienteID FROM cuentabancaria WHERE NumeroCuenta = ?";
        Integer clienteID = null;

        try (Connection conexion = conectar(); PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setString(1, numeroCuenta);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                clienteID = rs.getInt("ClienteID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clienteID;
    }
    public double calcularInteres(int plazoMeses, double monto) {
    if (plazoMeses < 6) {
        return monto * 0.05 ; // 5% para menos de 6 meses
    } else {
        return monto * 0.10; // 10% para 1 año o más
    }
}
    public boolean insertarPrestamo(int clienteID, double montoPrestamo, double interes, int plazo, java.sql.Date fechaDesembolso, int empleadoID, String numeroCuenta) {
    String sql = "INSERT INTO prestamos (ClienteID, MontoPrestamo, Interes, Plazo, FechaDesembolso, Estado, EmpleadoID, NumeroCuenta) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    // Estado se establece en "activo"
    String estado = "activo";

    try (Connection conexion = conectar(); PreparedStatement pstmt = conexion.prepareStatement(sql)) {
        pstmt.setInt(1, clienteID);
        pstmt.setDouble(2, montoPrestamo);
        pstmt.setDouble(3, interes);
        pstmt.setInt(4, plazo);
        pstmt.setDate(5, fechaDesembolso); // Asegúrate de que fechaDesembolso es un objeto java.sql.Date
        pstmt.setString(6, estado);
        pstmt.setInt(7, empleadoID);
        pstmt.setString(8, numeroCuenta);

        int filasAfectadas = pstmt.executeUpdate();
        return filasAfectadas > 0; // Retorna true si se insertó el préstamo
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false; // Retorna false si hubo un error
}
    public boolean existePrestamoPorNumeroCuenta(String numeroCuenta) {
        String sql = "SELECT COUNT(*) FROM prestamos WHERE NumeroCuenta = ?";
        try (Connection conexion = conectar(); PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setString(1, numeroCuenta);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0; // Retorna true si hay préstamos asociados
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Retorna false si hubo un error
    }

    // Método para desactivar el préstamo
    public boolean desactivarPrestamo(String numeroCuenta) {
        String sql = "UPDATE prestamos SET Estado = 'No activo' WHERE NumeroCuenta = ?";
        try (Connection conexion = conectar(); PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setString(1, numeroCuenta);
            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0; // Retorna true si se actualizó el estado
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Retorna false si hubo un error
    }
    //Listar los prestamos
    public List<Prestamos> obtenerTodosLosPrestamos() {
    List<Prestamos> prestamos = new ArrayList<>();
    String sql = "SELECT p.MontoPrestamo, p.Interes, p.Plazo, p.FechaDesembolso, p.Estado, p.PrestamoID, cb.NumeroCuenta " +
                 "FROM prestamos p " +
                 "INNER JOIN cuentabancaria cb ON p.NumeroCuenta = cb.NumeroCuenta";

    try (Connection conexion = conectar(); PreparedStatement pstmt = conexion.prepareStatement(sql);
         ResultSet rs = pstmt.executeQuery()) {

        while (rs.next()) {
            // Crear un nuevo objeto Prestamos
            Prestamos prestamo = new Prestamos();
            prestamo.setPrestamoID(rs.getInt("PrestamoID"));
            prestamo.setMontoPrestamo(rs.getDouble("MontoPrestamo"));
            prestamo.setInteres(rs.getDouble("Interes"));
            prestamo.setPlazo(rs.getInt("Plazo"));
            prestamo.setFechaDesembolso(rs.getDate("FechaDesembolso").toString());
            prestamo.setEstado(rs.getString("Estado"));
            prestamo.setNumeroCuenta(rs.getString("NumeroCuenta"));

            // Agregar a la lista
            prestamos.add(prestamo);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return prestamos;
}

    public List<Cliente> obtenerTodosLosClientesNombreApellido() {
    List<Cliente> clientes = new ArrayList<>();
    String sql = "SELECT c.ClienteID, c.Nombre, c.Apellido " +
                 "FROM cliente c " +
                 "INNER JOIN cuentabancaria cb ON c.ClienteID = cb.ClienteID " +
                 "INNER JOIN prestamos p ON cb.NumeroCuenta = p.NumeroCuenta"; // Aquí se unen las tablas para obtener clientes con préstamos

    try (Connection conexion = conectar(); PreparedStatement pstmt = conexion.prepareStatement(sql);
         ResultSet rs = pstmt.executeQuery()) {

        while (rs.next()) {
            int clienteID = rs.getInt("ClienteID");
            String nombre = rs.getString("Nombre");
            String apellido = rs.getString("Apellido");

            // Crear un objeto Cliente y agregarlo a la lista
            Cliente cliente = new Cliente(clienteID, nombre, apellido, "", "", "", "", "");
            clientes.add(cliente);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return clientes;
}
    
    
    
    /*
        public void eliminarEmpleadoPorDNI(String dni) {
            String sql = "DELETE FROM empleado WHERE DNI = '" + dni + "'"; // Concatenación directa
            try (Connection conexion = conectar(); Statement stmt = conexion.createStatement()) {
                int filasAfectadas = stmt.executeUpdate(sql); // Ejecutar la consulta

                // Verificar si se eliminó algún registro
                if (filasAfectadas > 0) {
                    System.out.println("Empleado eliminado con éxito.");
                } else {
                    System.out.println("No se encontró ningún empleado con ese DNI.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    */
}