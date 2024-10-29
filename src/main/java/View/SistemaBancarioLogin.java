package View;
import Controller.ClienteController;
import Controller.CuentaBancariaController;
import Controller.EmpleadoController;
import Controller.PrestamosController;
import Controller.TransaccionesController;
import Controller.UsuarioSistemaController;
import Model.Cliente;
import Model.CuentaBancaria;
import Model.Empleado;
import Model.Prestamos;
import Model.Transacciones;
import Model.UsuarioDAO;
import Model.UsuarioSistema;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.text.SimpleDateFormat;
import javax.swing.ImageIcon;
import javax.swing.JPanel;


public class SistemaBancarioLogin extends javax.swing.JFrame {
    String usuario;
    String dniInsertadoModificarEmpleado;
    String dniInsertadoModificarCliente;
    
    public SistemaBancarioLogin() {
        initComponents();
        formulario();
        iniciarElementos();
    }
        //Elementos la interface del Jframe
        private void formulario(){
            this.setTitle("EMPRESA EMPLEADOS");
            this.setLocationRelativeTo(this);
            this.setResizable(false);
            this.setSize(new Dimension(1120,650));
        }
        //Elementos la interface del login
        public void formularioLogin(){
            SistemaBancarioLogin.setLocationRelativeTo(null); //Centrar la ventana en la pantalla
            SistemaBancarioLogin.setSize(new Dimension(360,415));
            SistemaBancarioLogin.setLocation(650, 250);
            
        }

        //Limpiar los campos de Empleado
        public void limpiarEmpleado(){
            txtNombreEmpleado.setText("");
            txtApellidoEmpleado.setText("");
            txtDniEmpleado.setText("");
            cboRolEmpleado.setSelectedIndex(0);
            txtFechaInicioEmpleado.setText("");
            txtSalarioEmpleado.setText("");
            txtCelularEmpleado.setText("");
            txtUsuarioEmpleado.setText("");
            txtContraseñaEmpleado.setText("");
            txtNombreEmpleado.requestFocus();
        }
        //Limpiar los campos Cliente
        public void limpiarCliente(){
             txtNombreCliente.setText("");
             txtApellidoCliente.setText(""); 
             txtDniCliente.setText("");
             txtDireccionCliente.setText("");
             txtCelularCliente.setText("");
             txtEmailCliente.setText("");
             txtFechaRegistroCliente.setText("");
             txtNumeroCuentaCliente.setText("");
             cboTipoCuenta.setSelectedIndex(0);
             txtSaldoCliente.setText("");
             txtNombreCliente.requestFocus();
        }
        //Limpiar los campos de Vaucher
        public void limpiarVaucher(){
            txtVaucherNombre.setText("Nombre :");
            txtVaucherNumeroCuenta.setText("Numero de cuenta:");
            txtVaucherMonto.setText("Monto ");
            txtVaucherNumeroCuenta.setText("Nº de Operacion");
            txtVaucherFecha.setText("Fecha: ");
            txtVaucherEmpleadoID.setText("Codigo Empleado");
        }
        //Limpiar los campo de Prestamo
        public void limpiarPrestamo(){
            txtNumeroCuentaPrestamo.setText("");
            txtMontoPrestamo.setText("");
            txtPlazoMesesPrestamo.setText("");
            txtFechaPrestamo.setText("");
        }
        //Funcion para habiliar todo del gerente
        public void vistaGerente(){
            mostrarEmpleados();
        }
        //Funcion para habilitar todo del cajero
        public void vistaCajero(){
            btnEmpleados.setVisible(false);
        }
        //Funcion para vista de Gerente y cajero
        public void vistaGeneral(){
            UsuarioSistemaController usuarioController = new UsuarioSistemaController();
            String rol =  usuarioController.verificarRol(usuario);
            String nombre = usuarioController.obtenerNombreCompleto(usuario);
            usuario = txtUsuario.getText();
            txtNombre2.setText(nombre);
            txtRol2.setText(rol);
            
        }
        //Damos valores a los Combos
        private void iniciarElementos(){
        DefaultListCellRenderer listRenderer = new DefaultListCellRenderer();
            listRenderer.setHorizontalAlignment(DefaultListCellRenderer.CENTER);
            cboRolEmpleado.setRenderer(listRenderer);
            this.cboRolEmpleado.addItem("Cajero");
            this.cboRolEmpleado.addItem("Gerente");
            
            listRenderer.setHorizontalAlignment(DefaultListCellRenderer.CENTER);
            cboTipoCuenta.setRenderer(listRenderer);
            this.cboTipoCuenta.addItem("Corriente");
            this.cboTipoCuenta.addItem("Ahorro");
            
        }
        //Funcion para mostrar los Empleados en la Tabla 
        private void mostrarEmpleados() { 
            DefaultTableModel model = (DefaultTableModel) tableEmpleados.getModel();
            model.setRowCount(0); // Elimina todas las filas actuales de la tabla

            EmpleadoController empleadoController = new EmpleadoController();
            UsuarioSistemaController usuarioController = new UsuarioSistemaController();

            List<Empleado> empleados = empleadoController.obtenerTodosLosEmpleados();
            List<UsuarioSistema> usuariosSistema = usuarioController.obtenerTodosLosUsuarios();

            Map<Integer, String> usuarioMap = new HashMap<>();
            for (UsuarioSistema usuario : usuariosSistema) {
                usuarioMap.put(usuario.getEmpleadoID(), usuario.getNombreUsuario());
            }
            for (Empleado empleado : empleados) {
                String nombreUsuario = usuarioMap.getOrDefault(empleado.getEmpleadoID(), "No asignado");

                model.addRow(new Object[]{
                    empleado.getNombre(),
                    empleado.getApellido(),
                    empleado.getDni(),
                    empleado.getCargo(),
                    empleado.getFechaContratacion(),
                    empleado.getSalario(),
                    empleado.getNumeroCelular(),
                    nombreUsuario, 
                });
            }
        }
        //Funcion para mostrar los Clientes en la tabla
        private void mostrarClientes(){
            DefaultTableModel model = (DefaultTableModel) tablaClientes.getModel();
            model.setRowCount(0); 
            
            ClienteController clienteController = new ClienteController();
            CuentaBancariaController cuentaBancariaController = new CuentaBancariaController();
            
            List<Cliente> clientes = clienteController.obtenerTodosLosClientes();
            List<CuentaBancaria> cuentas = cuentaBancariaController.obtenerTodasLasCuentas();
           

            Map<Integer, CuentaBancaria> cuentaMap = new HashMap<>();
            for (CuentaBancaria cuenta : cuentas) {
            cuentaMap.put(cuenta.getClienteID(), cuenta);
            }

            // Iterar sobre los clientes y buscar su cuenta bancaria en el Map
            for (Cliente cliente : clientes) {
               
                CuentaBancaria cuenta = cuentaMap.getOrDefault(cliente.getClienteID(), null);
                String numeroCuenta = (cuenta != null) ? cuenta.getNumeroCuenta() : "No asignada";
                String tipoCuenta = (cuenta != null) ? cuenta.getTipoCuenta() : "No asignada";
                double saldo = (cuenta != null) ? cuenta.getSaldo() : 0.0;

                // Agregar la fila a la tabla con la información del cliente y la cuenta bancaria
                model.addRow(new Object[]{
                    cliente.getNombre(),
                    cliente.getApellido(),
                    cliente.getDni(),
                    cliente.getDireccion(),
                    cliente.getTelefono(),
                    cliente.getEmail(),
                    cliente.getFechaRegistro(),
                    numeroCuenta,
                    tipoCuenta,
                    saldo
                });
            }
        }
        //Funcion para mostrar los prestamos
        public void mostrarPrestamos(){
            PrestamosController prestamoController = new PrestamosController();
            
           DefaultTableModel model = (DefaultTableModel) tablePrestamo.getModel();
            model.setRowCount(0); 

            List<Prestamos> prestamosList = prestamoController.obtenerTodosLosPrestamos();
            List<Cliente> clientesList = prestamoController.obtenerTodosLosClientes();

            for (Prestamos prestamo : prestamosList) {
                // Encontrar el cliente correspondiente por el número de cuenta
                Cliente cliente = clientesList.stream()
                    .filter(c -> c.getClienteID() == prestamoController.obtenerClienteIDPorNumeroCuenta(prestamo.getNumeroCuenta()))
                    .findFirst()
                    .orElse(null);

                // Concatenar nombre y apellido del cliente
                String nombreCompleto = (cliente != null) ? cliente.getNombre() + " " + cliente.getApellido() : "Desconocido";

                // Agregar la fila a la tabla con la información del préstamo
                model.addRow(new Object[]{
                    nombreCompleto,  // Mostrar nombre completo
                    prestamo.getMontoPrestamo(),
                    prestamo.getInteres(),
                    prestamo.getPlazo(),
                    prestamo.getFechaDesembolso(),
                    prestamo.getEstado(),
                    prestamo.getPrestamoID() // PrestamoID si es necesario
                });
            }
        }
        //Convertir salario a double
        private double convertirSalario(String InsertSalario){
            double salario = 0;
            try {
                salario = Double.parseDouble(InsertSalario);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "El salario debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
            }
            return salario; 
        }
        
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        SistemaBancarioLogin = new javax.swing.JFrame();
        panelLogin = new javax.swing.JPanel();
        txtUsuario = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtContraseña = new javax.swing.JPasswordField();
        btnIngresar1 = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        panelEmpleados = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtNombreEmpleado = new javax.swing.JTextField();
        txtApellidoEmpleado = new javax.swing.JTextField();
        txtDniEmpleado = new javax.swing.JTextField();
        txtCelularEmpleado = new javax.swing.JTextField();
        txtFechaInicioEmpleado = new javax.swing.JTextField();
        txtSalarioEmpleado = new javax.swing.JTextField();
        txtUsuarioEmpleado = new javax.swing.JTextField();
        txtContraseñaEmpleado = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableEmpleados = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        btnCrearEmpleado = new javax.swing.JButton();
        btnEliminarEmpleado = new javax.swing.JButton();
        btnNuevoEmpleado = new javax.swing.JButton();
        cboRolEmpleado = new javax.swing.JComboBox<>();
        Modificar = new javax.swing.JButton();
        btnAplicarCambiosEmpleados = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        panelClientes = new javax.swing.JPanel();
        txtNombreCliente = new javax.swing.JTextField();
        txtNumeroCuentaCliente = new javax.swing.JTextField();
        txtEmailCliente = new javax.swing.JTextField();
        txtApellidoCliente = new javax.swing.JTextField();
        txtCelularCliente = new javax.swing.JTextField();
        txtDniCliente = new javax.swing.JTextField();
        txtFechaRegistroCliente = new javax.swing.JTextField();
        txtDireccionCliente = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtSaldoCliente = new javax.swing.JTextField();
        cboTipoCuenta = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaClientes = new javax.swing.JTable();
        btnCrearCliente = new javax.swing.JButton();
        btnEliminarCliente = new javax.swing.JButton();
        btnNuevoCliente = new javax.swing.JButton();
        btnModificarCliente = new javax.swing.JButton();
        btnAplicarCambiosCliente = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
        panelTransacciones = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        txtNumeroCuentaTransaccion = new javax.swing.JTextField();
        txtMontoTransaccion = new javax.swing.JTextField();
        btnRetiro = new javax.swing.JButton();
        btnDeposito = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        txtVaucherNombre = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtVaucherNumeroCuenta = new javax.swing.JLabel();
        txtVaucherMonto = new javax.swing.JLabel();
        txtVaucherOperacion = new javax.swing.JLabel();
        txtVaucherFecha = new javax.swing.JLabel();
        txtVaucherEmpleadoID = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        btnNuevaTransaccion = new javax.swing.JButton();
        panelPrestamo = new javax.swing.JPanel();
        txtMontoPrestamo = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtPlazoMesesPrestamo = new javax.swing.JTextField();
        txtNumeroCuentaPrestamo = new javax.swing.JTextField();
        txtFechaPrestamo = new javax.swing.JTextField();
        btnRealizarPrestamo = new javax.swing.JButton();
        btnNuevoPrestamo = new javax.swing.JButton();
        DesactivarPrestamo = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        tablePrestamo = new javax.swing.JTable();
        panelPagosPrestamos = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jTextField25 = new javax.swing.JTextField();
        jTextField26 = new javax.swing.JTextField();
        jTextField27 = new javax.swing.JTextField();
        jTextField28 = new javax.swing.JTextField();
        jComboBox2 = new javax.swing.JComboBox<>();
        jButton16 = new javax.swing.JButton();
        jButton17 = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jLabel10 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        panelOpciones = new javax.swing.JPanel();
        btnClientes = new javax.swing.JButton();
        btnEmpleados = new javax.swing.JButton();
        btnTransacciones = new javax.swing.JButton();
        btnPrestamos = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        txtRol2 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        txtNombre2 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        panelDatos = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();

        SistemaBancarioLogin.setResizable(false);

        panelLogin.setBackground(new java.awt.Color(0, 0, 0));
        panelLogin.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        panelLogin.setForeground(new java.awt.Color(255, 255, 255));

        txtUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUsuarioActionPerformed(evt);
            }
        });
        txtUsuario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtUsuarioKeyTyped(evt);
            }
        });

        jLabel3.setBackground(new java.awt.Color(0, 0, 0));
        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("USUARIO");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("CONTRASEÑA");

        txtContraseña.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtContraseñaActionPerformed(evt);
            }
        });
        txtContraseña.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtContraseñaKeyTyped(evt);
            }
        });

        btnIngresar1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnIngresar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/llave.png"))); // NOI18N
        btnIngresar1.setText("Ingresar");
        btnIngresar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIngresar1ActionPerformed(evt);
            }
        });

        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/login (1).png"))); // NOI18N

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/user2.png"))); // NOI18N

        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/lock.png"))); // NOI18N

        javax.swing.GroupLayout panelLoginLayout = new javax.swing.GroupLayout(panelLogin);
        panelLogin.setLayout(panelLoginLayout);
        panelLoginLayout.setHorizontalGroup(
            panelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLoginLayout.createSequentialGroup()
                .addGroup(panelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelLoginLayout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(panelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(panelLoginLayout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelLoginLayout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel5)
                                .addGap(29, 29, 29)
                                .addComponent(txtContraseña, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(panelLoginLayout.createSequentialGroup()
                        .addGap(53, 53, 53)
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelLoginLayout.createSequentialGroup()
                        .addGap(118, 118, 118)
                        .addComponent(btnIngresar1)))
                .addContainerGap(38, Short.MAX_VALUE))
        );
        panelLoginLayout.setVerticalGroup(
            panelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLoginLayout.createSequentialGroup()
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(panelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelLoginLayout.createSequentialGroup()
                        .addGroup(panelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel11))
                        .addGap(18, 18, Short.MAX_VALUE))
                    .addGroup(panelLoginLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(txtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(panelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13)
                    .addGroup(panelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtContraseña, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(btnIngresar1)
                .addGap(21, 21, 21))
        );

        javax.swing.GroupLayout SistemaBancarioLoginLayout = new javax.swing.GroupLayout(SistemaBancarioLogin.getContentPane());
        SistemaBancarioLogin.getContentPane().setLayout(SistemaBancarioLoginLayout);
        SistemaBancarioLoginLayout.setHorizontalGroup(
            SistemaBancarioLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        SistemaBancarioLoginLayout.setVerticalGroup(
            SistemaBancarioLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SistemaBancarioLoginLayout.createSequentialGroup()
                .addComponent(panelLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 6, Short.MAX_VALUE))
        );

        panelEmpleados.setBackground(new java.awt.Color(204, 204, 204));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/informacion empleados.png"))); // NOI18N
        jLabel1.setText("INFORMACION EMPLEADOS");

        txtNombreEmpleado.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Nombre", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(255, 0, 0))); // NOI18N
        txtNombreEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreEmpleadoActionPerformed(evt);
            }
        });
        txtNombreEmpleado.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreEmpleadoKeyTyped(evt);
            }
        });

        txtApellidoEmpleado.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Apellido", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(255, 0, 0))); // NOI18N
        txtApellidoEmpleado.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtApellidoEmpleadoKeyTyped(evt);
            }
        });

        txtDniEmpleado.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "DNI", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(255, 0, 0))); // NOI18N
        txtDniEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDniEmpleadoActionPerformed(evt);
            }
        });
        txtDniEmpleado.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDniEmpleadoKeyTyped(evt);
            }
        });

        txtCelularEmpleado.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Nº Celular", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(255, 0, 0))); // NOI18N
        txtCelularEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCelularEmpleadoActionPerformed(evt);
            }
        });
        txtCelularEmpleado.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCelularEmpleadoKeyTyped(evt);
            }
        });

        txtFechaInicioEmpleado.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Fecha de Inicio", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(255, 0, 0))); // NOI18N
        txtFechaInicioEmpleado.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtFechaInicioEmpleadoKeyTyped(evt);
            }
        });

        txtSalarioEmpleado.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Salario", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(255, 0, 0))); // NOI18N
        txtSalarioEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSalarioEmpleadoActionPerformed(evt);
            }
        });
        txtSalarioEmpleado.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtSalarioEmpleadoKeyTyped(evt);
            }
        });

        txtUsuarioEmpleado.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Usuario", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(255, 0, 0))); // NOI18N
        txtUsuarioEmpleado.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtUsuarioEmpleadoKeyTyped(evt);
            }
        });

        txtContraseñaEmpleado.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Contraseña", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(255, 0, 0))); // NOI18N
        txtContraseñaEmpleado.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtContraseñaEmpleadoKeyTyped(evt);
            }
        });

        tableEmpleados.setBackground(new java.awt.Color(204, 204, 204));
        tableEmpleados.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        tableEmpleados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Nombre", "Apellido", "Dni", "Rol", "Fecha-Inicio", "Salario", "Nº Celular", "Usuario"
            }
        ));
        jScrollPane1.setViewportView(tableEmpleados);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("TABLA EMPLEADOS");

        btnCrearEmpleado.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnCrearEmpleado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/crear empleado.png"))); // NOI18N
        btnCrearEmpleado.setText("Crear Empleado");
        btnCrearEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCrearEmpleadoActionPerformed(evt);
            }
        });

        btnEliminarEmpleado.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnEliminarEmpleado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/eliminar.png"))); // NOI18N
        btnEliminarEmpleado.setText("Eliminar Empleado");
        btnEliminarEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarEmpleadoActionPerformed(evt);
            }
        });

        btnNuevoEmpleado.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnNuevoEmpleado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/nuevo empleado.png"))); // NOI18N
        btnNuevoEmpleado.setText("Nuevo Empleado");
        btnNuevoEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoEmpleadoActionPerformed(evt);
            }
        });

        cboRolEmpleado.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "ROL", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(255, 0, 0))); // NOI18N

        Modificar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        Modificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/modificar empleado.png"))); // NOI18N
        Modificar.setText("Modificar Empleado");
        Modificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ModificarActionPerformed(evt);
            }
        });

        btnAplicarCambiosEmpleados.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnAplicarCambiosEmpleados.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/aplicar cambios.png"))); // NOI18N
        btnAplicarCambiosEmpleados.setText("Aplicar Cambios Empleado");
        btnAplicarCambiosEmpleados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAplicarCambiosEmpleadosActionPerformed(evt);
            }
        });

        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/tabla empleados.png"))); // NOI18N

        javax.swing.GroupLayout panelEmpleadosLayout = new javax.swing.GroupLayout(panelEmpleados);
        panelEmpleados.setLayout(panelEmpleadosLayout);
        panelEmpleadosLayout.setHorizontalGroup(
            panelEmpleadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEmpleadosLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(panelEmpleadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelEmpleadosLayout.createSequentialGroup()
                        .addGroup(panelEmpleadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelEmpleadosLayout.createSequentialGroup()
                                .addGroup(panelEmpleadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelEmpleadosLayout.createSequentialGroup()
                                        .addComponent(txtCelularEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                                        .addComponent(txtUsuarioEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelEmpleadosLayout.createSequentialGroup()
                                        .addGroup(panelEmpleadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(cboRolEmpleado, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(txtNombreEmpleado, javax.swing.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE))
                                        .addGap(33, 33, 33)
                                        .addGroup(panelEmpleadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtApellidoEmpleado, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtFechaInicioEmpleado, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(29, 29, 29)
                                .addGroup(panelEmpleadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtDniEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtSalarioEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtContraseñaEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(panelEmpleadosLayout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel16)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 87, Short.MAX_VALUE)
                        .addGroup(panelEmpleadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnNuevoEmpleado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnCrearEmpleado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnEliminarEmpleado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Modificar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnAplicarCambiosEmpleados, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(52, 52, 52))
                    .addGroup(panelEmpleadosLayout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addContainerGap())
                    .addGroup(panelEmpleadosLayout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        panelEmpleadosLayout.setVerticalGroup(
            panelEmpleadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEmpleadosLayout.createSequentialGroup()
                .addGroup(panelEmpleadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelEmpleadosLayout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(panelEmpleadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNombreEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtApellidoEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDniEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panelEmpleadosLayout.createSequentialGroup()
                        .addGap(54, 54, 54)
                        .addComponent(btnCrearEmpleado)))
                .addGroup(panelEmpleadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelEmpleadosLayout.createSequentialGroup()
                        .addGroup(panelEmpleadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelEmpleadosLayout.createSequentialGroup()
                                .addGap(32, 32, 32)
                                .addGroup(panelEmpleadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtFechaInicioEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtSalarioEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(panelEmpleadosLayout.createSequentialGroup()
                                .addGap(34, 34, 34)
                                .addComponent(cboRolEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(31, 31, 31)
                        .addGroup(panelEmpleadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtUsuarioEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtContraseñaEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCelularEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(panelEmpleadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                    .addGroup(panelEmpleadosLayout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(btnEliminarEmpleado)
                        .addGap(18, 18, 18)
                        .addComponent(btnNuevoEmpleado)
                        .addGap(18, 18, 18)
                        .addComponent(Modificar)
                        .addGap(18, 18, 18)
                        .addComponent(btnAplicarCambiosEmpleados)
                        .addGap(18, 18, Short.MAX_VALUE)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16))
        );

        panelClientes.setBackground(new java.awt.Color(204, 204, 204));
        panelClientes.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelClientes.setForeground(new java.awt.Color(255, 255, 255));

        txtNombreCliente.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Nombre", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(255, 0, 0))); // NOI18N
        txtNombreCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreClienteActionPerformed(evt);
            }
        });
        txtNombreCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreClienteKeyTyped(evt);
            }
        });

        txtNumeroCuentaCliente.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Nª de cuenta", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(255, 0, 0))); // NOI18N
        txtNumeroCuentaCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNumeroCuentaClienteActionPerformed(evt);
            }
        });
        txtNumeroCuentaCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNumeroCuentaClienteKeyTyped(evt);
            }
        });

        txtEmailCliente.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Email", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(255, 0, 0))); // NOI18N
        txtEmailCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtEmailClienteKeyTyped(evt);
            }
        });

        txtApellidoCliente.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Apellido", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(255, 0, 0))); // NOI18N
        txtApellidoCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtApellidoClienteKeyTyped(evt);
            }
        });

        txtCelularCliente.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Nº Celular", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(255, 0, 0))); // NOI18N
        txtCelularCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCelularClienteActionPerformed(evt);
            }
        });
        txtCelularCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCelularClienteKeyTyped(evt);
            }
        });

        txtDniCliente.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Dni", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(255, 0, 51))); // NOI18N
        txtDniCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDniClienteActionPerformed(evt);
            }
        });
        txtDniCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDniClienteKeyTyped(evt);
            }
        });

        txtFechaRegistroCliente.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Fecha-Registro", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(255, 0, 0))); // NOI18N
        txtFechaRegistroCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFechaRegistroClienteActionPerformed(evt);
            }
        });
        txtFechaRegistroCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtFechaRegistroClienteKeyTyped(evt);
            }
        });

        txtDireccionCliente.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Direccion", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(255, 0, 0))); // NOI18N
        txtDireccionCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDireccionClienteKeyTyped(evt);
            }
        });

        jLabel4.setBackground(new java.awt.Color(0, 0, 0));
        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/clientes2.png"))); // NOI18N
        jLabel4.setText("CLIENTES");

        txtSaldoCliente.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Saldo", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(255, 0, 0))); // NOI18N
        txtSaldoCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSaldoClienteActionPerformed(evt);
            }
        });
        txtSaldoCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtSaldoClienteKeyTyped(evt);
            }
        });

        cboTipoCuenta.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tipo-Cuenta", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(255, 0, 0))); // NOI18N

        tablaClientes.setBackground(new java.awt.Color(204, 204, 204));
        tablaClientes.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        tablaClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Nombre", "Apellido", "Dni", "Direccion", "Nº Celular", "Email", "Fecha-Reg", "Nª Cuenta", "Tipo-Cuenta", "Saldo"
            }
        ));
        jScrollPane2.setViewportView(tablaClientes);

        btnCrearCliente.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnCrearCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/crear empleado.png"))); // NOI18N
        btnCrearCliente.setText("Crear cliente");
        btnCrearCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCrearClienteActionPerformed(evt);
            }
        });

        btnEliminarCliente.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnEliminarCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/eliminar.png"))); // NOI18N
        btnEliminarCliente.setText("Eliminar cliente");
        btnEliminarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarClienteActionPerformed(evt);
            }
        });

        btnNuevoCliente.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnNuevoCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/nuevo clientee.png"))); // NOI18N
        btnNuevoCliente.setText("Nuevo cliente");
        btnNuevoCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoClienteActionPerformed(evt);
            }
        });

        btnModificarCliente.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnModificarCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/modificar empleado.png"))); // NOI18N
        btnModificarCliente.setText("Modificar cliente");
        btnModificarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarClienteActionPerformed(evt);
            }
        });

        btnAplicarCambiosCliente.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnAplicarCambiosCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/aplicar cambios.png"))); // NOI18N
        btnAplicarCambiosCliente.setText("Aplicar cambios");
        btnAplicarCambiosCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAplicarCambiosClienteActionPerformed(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/tabla empleados.png"))); // NOI18N
        jLabel18.setText("TABLA CLIENTES");

        javax.swing.GroupLayout panelClientesLayout = new javax.swing.GroupLayout(panelClientes);
        panelClientes.setLayout(panelClientesLayout);
        panelClientesLayout.setHorizontalGroup(
            panelClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelClientesLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(panelClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelClientesLayout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addGroup(panelClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelClientesLayout.createSequentialGroup()
                                .addGroup(panelClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(txtCelularCliente)
                                    .addComponent(txtNombreCliente)
                                    .addComponent(cboTipoCuenta, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(panelClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(panelClientesLayout.createSequentialGroup()
                                        .addComponent(txtApellidoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtDniCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtDireccionCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(panelClientesLayout.createSequentialGroup()
                                        .addComponent(txtEmailCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtFechaRegistroCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtNumeroCuentaCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(txtSaldoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel18))
                        .addGap(18, 18, 18)
                        .addGroup(panelClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnEliminarCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnCrearCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnNuevoCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnModificarCliente, javax.swing.GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE)
                            .addComponent(btnAplicarCambiosCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap(42, Short.MAX_VALUE))
                    .addGroup(panelClientesLayout.createSequentialGroup()
                        .addComponent(jScrollPane2)
                        .addContainerGap())))
        );
        panelClientesLayout.setVerticalGroup(
            panelClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelClientesLayout.createSequentialGroup()
                .addGroup(panelClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelClientesLayout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(jLabel4)
                        .addGap(28, 28, 28)
                        .addGroup(panelClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNombreCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtApellidoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDniCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDireccionCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(24, 24, 24)
                        .addGroup(panelClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtCelularCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtEmailCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtFechaRegistroCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNumeroCuentaCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(24, 24, 24)
                        .addGroup(panelClientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cboTipoCuenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtSaldoCliente)))
                    .addGroup(panelClientesLayout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(btnCrearCliente)
                        .addGap(18, 18, 18)
                        .addComponent(btnEliminarCliente)
                        .addGap(18, 18, 18)
                        .addComponent(btnNuevoCliente)
                        .addGap(18, 18, 18)
                        .addComponent(btnModificarCliente)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnAplicarCambiosCliente)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        panelTransacciones.setBackground(new java.awt.Color(204, 204, 204));
        panelTransacciones.setForeground(new java.awt.Color(255, 255, 255));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/transaccion.png"))); // NOI18N
        jLabel6.setText("TRANSACCIÓN");

        txtNumeroCuentaTransaccion.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Nº de Cuenta", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(255, 0, 0))); // NOI18N
        txtNumeroCuentaTransaccion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNumeroCuentaTransaccionActionPerformed(evt);
            }
        });
        txtNumeroCuentaTransaccion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNumeroCuentaTransaccionKeyTyped(evt);
            }
        });

        txtMontoTransaccion.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Monto", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(255, 0, 0))); // NOI18N
        txtMontoTransaccion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMontoTransaccionActionPerformed(evt);
            }
        });
        txtMontoTransaccion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtMontoTransaccionKeyTyped(evt);
            }
        });

        btnRetiro.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnRetiro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/retiro.png"))); // NOI18N
        btnRetiro.setText("Retiro");
        btnRetiro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRetiroActionPerformed(evt);
            }
        });

        btnDeposito.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnDeposito.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/deposito.png"))); // NOI18N
        btnDeposito.setText("Depósito");
        btnDeposito.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDepositoActionPerformed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(153, 153, 153));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel2.setForeground(new java.awt.Color(255, 255, 255));

        jLabel7.setBackground(new java.awt.Color(0, 0, 0));
        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 0, 0));
        jLabel7.setText("VAUCHER LYNX");

        txtVaucherNombre.setBackground(new java.awt.Color(0, 0, 0));
        txtVaucherNombre.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtVaucherNombre.setText("Nombre : ");

        jLabel12.setBackground(new java.awt.Color(0, 0, 0));
        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel12.setText("Entidad Financiera: ProBank");

        txtVaucherNumeroCuenta.setBackground(new java.awt.Color(0, 0, 0));
        txtVaucherNumeroCuenta.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtVaucherNumeroCuenta.setText("Numero de cuenta:");

        txtVaucherMonto.setBackground(new java.awt.Color(0, 0, 0));
        txtVaucherMonto.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtVaucherMonto.setText("Monto ");

        txtVaucherOperacion.setBackground(new java.awt.Color(0, 0, 0));
        txtVaucherOperacion.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtVaucherOperacion.setText("Nº de Operacion");

        txtVaucherFecha.setBackground(new java.awt.Color(0, 0, 0));
        txtVaucherFecha.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtVaucherFecha.setText("Fecha: ");

        txtVaucherEmpleadoID.setBackground(new java.awt.Color(0, 0, 0));
        txtVaucherEmpleadoID.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtVaucherEmpleadoID.setText("Codigo Empleado");

        jLabel20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/rol.png"))); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(83, 83, 83)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtVaucherNumeroCuenta)
                            .addComponent(jLabel12)
                            .addComponent(txtVaucherNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtVaucherMonto))
                        .addGap(259, 259, 259))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtVaucherOperacion)
                            .addComponent(txtVaucherFecha)
                            .addComponent(txtVaucherEmpleadoID))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel7)
                .addGap(154, 154, 154)
                .addComponent(jLabel20)
                .addGap(93, 93, 93))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jLabel7))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel12)
                .addGap(27, 27, 27)
                .addComponent(txtVaucherNombre)
                .addGap(18, 18, 18)
                .addComponent(txtVaucherNumeroCuenta)
                .addGap(26, 26, 26)
                .addComponent(txtVaucherMonto)
                .addGap(18, 18, 18)
                .addComponent(txtVaucherOperacion)
                .addGap(27, 27, 27)
                .addComponent(txtVaucherFecha)
                .addGap(29, 29, 29)
                .addComponent(txtVaucherEmpleadoID)
                .addGap(47, 47, 47))
        );

        btnNuevaTransaccion.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnNuevaTransaccion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/nueva transacción.png"))); // NOI18N
        btnNuevaTransaccion.setText("Nueva Transacción");

        javax.swing.GroupLayout panelTransaccionesLayout = new javax.swing.GroupLayout(panelTransacciones);
        panelTransacciones.setLayout(panelTransaccionesLayout);
        panelTransaccionesLayout.setHorizontalGroup(
            panelTransaccionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTransaccionesLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(panelTransaccionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelTransaccionesLayout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(684, Short.MAX_VALUE))
                    .addGroup(panelTransaccionesLayout.createSequentialGroup()
                        .addGroup(panelTransaccionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelTransaccionesLayout.createSequentialGroup()
                                .addComponent(txtNumeroCuentaTransaccion, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtMontoTransaccion, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelTransaccionesLayout.createSequentialGroup()
                                .addGap(55, 55, 55)
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 494, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(panelTransaccionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnDeposito, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnRetiro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnNuevaTransaccion))
                        .addGap(48, 48, 48))))
        );
        panelTransaccionesLayout.setVerticalGroup(
            panelTransaccionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTransaccionesLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel6)
                .addGap(28, 28, 28)
                .addGroup(panelTransaccionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelTransaccionesLayout.createSequentialGroup()
                        .addComponent(btnRetiro, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(23, 23, 23)
                        .addComponent(btnDeposito)
                        .addGap(32, 32, 32)
                        .addComponent(btnNuevaTransaccion))
                    .addGroup(panelTransaccionesLayout.createSequentialGroup()
                        .addGroup(panelTransaccionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNumeroCuentaTransaccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtMontoTransaccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 342, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(60, Short.MAX_VALUE))
        );

        panelPrestamo.setBackground(new java.awt.Color(204, 204, 204));
        panelPrestamo.setForeground(new java.awt.Color(255, 255, 255));

        txtMontoPrestamo.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Monto", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(255, 0, 0))); // NOI18N
        txtMontoPrestamo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtMontoPrestamoKeyTyped(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/prestamo panel.png"))); // NOI18N
        jLabel8.setText("PRÉSTAMO");

        txtPlazoMesesPrestamo.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Plazo en meses", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(255, 0, 0))); // NOI18N
        txtPlazoMesesPrestamo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPlazoMesesPrestamoKeyTyped(evt);
            }
        });

        txtNumeroCuentaPrestamo.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Nº de cuenta", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(255, 0, 0))); // NOI18N
        txtNumeroCuentaPrestamo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNumeroCuentaPrestamoKeyTyped(evt);
            }
        });

        txtFechaPrestamo.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Fecha de Préstamo", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(255, 0, 0))); // NOI18N
        txtFechaPrestamo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtFechaPrestamoKeyTyped(evt);
            }
        });

        btnRealizarPrestamo.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnRealizarPrestamo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/realizar prestamo.png"))); // NOI18N
        btnRealizarPrestamo.setText("Realizar Préstamo");
        btnRealizarPrestamo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRealizarPrestamoActionPerformed(evt);
            }
        });

        btnNuevoPrestamo.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnNuevoPrestamo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/nuevo prestamo.png"))); // NOI18N
        btnNuevoPrestamo.setText("Nuevo Préstamo");
        btnNuevoPrestamo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoPrestamoActionPerformed(evt);
            }
        });

        DesactivarPrestamo.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        DesactivarPrestamo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/desactivar.png"))); // NOI18N
        DesactivarPrestamo.setText("Desactivar Préstamo");
        DesactivarPrestamo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DesactivarPrestamoActionPerformed(evt);
            }
        });

        tablePrestamo.setBackground(new java.awt.Color(204, 204, 204));
        tablePrestamo.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        tablePrestamo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Cliente", "Monto", "Interes", "Plazo", "Fecha Desembolso", "Estado-Prestamo"
            }
        ));
        jScrollPane4.setViewportView(tablePrestamo);

        javax.swing.GroupLayout panelPrestamoLayout = new javax.swing.GroupLayout(panelPrestamo);
        panelPrestamo.setLayout(panelPrestamoLayout);
        panelPrestamoLayout.setHorizontalGroup(
            panelPrestamoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPrestamoLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(panelPrestamoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelPrestamoLayout.createSequentialGroup()
                        .addGroup(panelPrestamoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelPrestamoLayout.createSequentialGroup()
                                .addComponent(txtNumeroCuentaPrestamo)
                                .addGap(18, 18, 18)
                                .addComponent(txtMontoPrestamo, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtPlazoMesesPrestamo, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(28, 28, 28)
                                .addComponent(txtFechaPrestamo, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(44, 44, 44))
                            .addGroup(panelPrestamoLayout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(panelPrestamoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(DesactivarPrestamo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnNuevoPrestamo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnRealizarPrestamo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(39, 39, 39))
                    .addGroup(panelPrestamoLayout.createSequentialGroup()
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 795, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(57, Short.MAX_VALUE))))
        );
        panelPrestamoLayout.setVerticalGroup(
            panelPrestamoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPrestamoLayout.createSequentialGroup()
                .addGroup(panelPrestamoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelPrestamoLayout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addComponent(btnRealizarPrestamo))
                    .addGroup(panelPrestamoLayout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(16, 16, 16)
                .addGroup(panelPrestamoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNuevoPrestamo)
                    .addComponent(txtPlazoMesesPrestamo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtFechaPrestamo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMontoPrestamo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNumeroCuentaPrestamo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addComponent(DesactivarPrestamo)
                .addGap(36, 36, 36)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelPagosPrestamos.setBackground(new java.awt.Color(204, 204, 204));
        panelPagosPrestamos.setForeground(new java.awt.Color(255, 255, 255));

        jLabel9.setBackground(new java.awt.Color(0, 0, 0));
        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/PAGO PRESTAMO.png"))); // NOI18N
        jLabel9.setText("PAGO PRESTAMOS");

        jTextField25.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Numero de Cuenta", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(255, 0, 51))); // NOI18N
        jTextField25.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField25ActionPerformed(evt);
            }
        });
        jTextField25.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField25KeyTyped(evt);
            }
        });

        jTextField26.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Monto a Pagar", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(255, 0, 0))); // NOI18N
        jTextField26.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField26KeyTyped(evt);
            }
        });

        jTextField27.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Fecha de Pago", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(255, 0, 0))); // NOI18N
        jTextField27.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField27KeyTyped(evt);
            }
        });

        jTextField28.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Monto", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(255, 0, 0))); // NOI18N
        jTextField28.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField28KeyTyped(evt);
            }
        });

        jButton16.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/realizar prestamo.png"))); // NOI18N
        jButton16.setText("Realizar Pago");

        jButton17.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/nuevo prestamo.png"))); // NOI18N
        jButton17.setText("Nuevo Pago");

        jTextArea2.setColumns(20);
        jTextArea2.setRows(5);
        jScrollPane5.setViewportView(jTextArea2);

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel10.setText("BOLETA LYNX");

        javax.swing.GroupLayout panelPagosPrestamosLayout = new javax.swing.GroupLayout(panelPagosPrestamos);
        panelPagosPrestamos.setLayout(panelPagosPrestamosLayout);
        panelPagosPrestamosLayout.setHorizontalGroup(
            panelPagosPrestamosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelPagosPrestamosLayout.createSequentialGroup()
                .addGroup(panelPagosPrestamosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelPagosPrestamosLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panelPagosPrestamosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelPagosPrestamosLayout.createSequentialGroup()
                                .addComponent(jTextField25, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jTextField26, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jTextField27, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelPagosPrestamosLayout.createSequentialGroup()
                                .addGap(13, 13, 13)
                                .addComponent(jLabel9)))
                        .addGap(18, 18, 18)
                        .addGroup(panelPagosPrestamosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField28)
                            .addComponent(jComboBox2, 0, 149, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 48, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelPagosPrestamosLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(281, 281, 281)))
                .addGroup(panelPagosPrestamosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(32, 32, 32))
            .addGroup(panelPagosPrestamosLayout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 614, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelPagosPrestamosLayout.setVerticalGroup(
            panelPagosPrestamosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPagosPrestamosLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(panelPagosPrestamosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(panelPagosPrestamosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelPagosPrestamosLayout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addGroup(panelPagosPrestamosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelPagosPrestamosLayout.createSequentialGroup()
                                .addGap(4, 4, 4)
                                .addComponent(jButton16))
                            .addGroup(panelPagosPrestamosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jTextField25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jTextField26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jTextField27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jTextField28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addComponent(jButton17))
                    .addGroup(panelPagosPrestamosLayout.createSequentialGroup()
                        .addGap(103, 103, 103)
                        .addComponent(jLabel10)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(39, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        panelOpciones.setBackground(new java.awt.Color(0, 0, 0));
        panelOpciones.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        btnClientes.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnClientes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/clientes.png"))); // NOI18N
        btnClientes.setText("CLIENTES");
        btnClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClientesActionPerformed(evt);
            }
        });

        btnEmpleados.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnEmpleados.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/empleados.png"))); // NOI18N
        btnEmpleados.setText("EMPLEADOS");
        btnEmpleados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEmpleadosActionPerformed(evt);
            }
        });

        btnTransacciones.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnTransacciones.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/transaccion.png"))); // NOI18N
        btnTransacciones.setText("TRANSACCIONES");
        btnTransacciones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTransaccionesActionPerformed(evt);
            }
        });

        btnPrestamos.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnPrestamos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/prestamos.png"))); // NOI18N
        btnPrestamos.setText("PRESTAMOS");
        btnPrestamos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrestamosActionPerformed(evt);
            }
        });

        jPanel3.setBackground(new java.awt.Color(102, 102, 102));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        txtRol2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtRol2.setForeground(new java.awt.Color(0, 0, 0));
        txtRol2.setText("PUESTO");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(28, Short.MAX_VALUE)
                .addComponent(txtRol2, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(txtRol2)
                .addGap(0, 9, Short.MAX_VALUE))
        );

        jPanel3.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 270, -1, 25));

        jLabel22.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setText("ROL:");
        jPanel3.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 270, -1, -1));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        txtNombre2.setBackground(new java.awt.Color(204, 204, 204));
        txtNombre2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtNombre2.setForeground(new java.awt.Color(0, 0, 0));
        txtNombre2.setText("BIENVENIDO ");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtNombre2, javax.swing.GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(txtNombre2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 10, Short.MAX_VALUE))
        );

        jPanel3.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 220, 110, 30));

        jLabel23.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 255, 255));
        jLabel23.setText("ENCARGADO:");
        jPanel3.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 230, -1, -1));

        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/empleado.png"))); // NOI18N
        jPanel3.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, 150, 140));

        jLabel21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/logito panel.png"))); // NOI18N
        jPanel3.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, -1, 43));

        javax.swing.GroupLayout panelOpcionesLayout = new javax.swing.GroupLayout(panelOpciones);
        panelOpciones.setLayout(panelOpcionesLayout);
        panelOpcionesLayout.setHorizontalGroup(
            panelOpcionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelOpcionesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 197, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelOpcionesLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelOpcionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnPrestamos, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelOpcionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(btnTransacciones, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addComponent(btnClientes, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnEmpleados, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 158, Short.MAX_VALUE)))
                .addGap(32, 32, 32))
        );
        panelOpcionesLayout.setVerticalGroup(
            panelOpcionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelOpcionesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(21, 21, 21)
                .addComponent(btnEmpleados)
                .addGap(18, 18, 18)
                .addComponent(btnClientes)
                .addGap(18, 18, 18)
                .addComponent(btnTransacciones)
                .addGap(18, 18, 18)
                .addComponent(btnPrestamos)
                .addGap(20, 20, 20))
        );

        panelDatos.setBackground(new java.awt.Color(204, 204, 204));
        panelDatos.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        panelDatos.setForeground(new java.awt.Color(255, 255, 255));

        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/fondo.png"))); // NOI18N

        javax.swing.GroupLayout panelDatosLayout = new javax.swing.GroupLayout(panelDatos);
        panelDatos.setLayout(panelDatosLayout);
        panelDatosLayout.setHorizontalGroup(
            panelDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDatosLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 740, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(60, Short.MAX_VALUE))
        );
        panelDatosLayout.setVerticalGroup(
            panelDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDatosLayout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(jLabel19)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(panelDatos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panelOpciones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panelDatos, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelOpciones, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    //Ejecutar comandos al abrir el programa
    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
      SistemaBancarioLogin.setVisible(true);
      formularioLogin();
      this.setVisible(false);
    }//GEN-LAST:event_formWindowOpened
    private void btnEmpleadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEmpleadosActionPerformed
        panelDatos.removeAll();
        panelEmpleados.setSize(870,557);
        panelEmpleados.setLocation(0, 0);
        panelDatos.add(panelEmpleados, BorderLayout.CENTER);
        panelDatos.revalidate();
        panelDatos.repaint();
        panelEmpleados.setVisible(true);
        btnAplicarCambiosEmpleados.setVisible(false);
        limpiarEmpleado();
    }//GEN-LAST:event_btnEmpleadosActionPerformed
    private void btnClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClientesActionPerformed
        panelDatos.removeAll();
        panelClientes.setSize(870,557);
        panelClientes.setLocation(0, 0);
        panelDatos.add(panelClientes, BorderLayout.CENTER);
        panelDatos.revalidate();
        panelDatos.repaint();
        panelClientes.setVisible(true);
        mostrarClientes();
        limpiarCliente();
        btnAplicarCambiosCliente.setVisible(false);
    }//GEN-LAST:event_btnClientesActionPerformed
    private void btnTransaccionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTransaccionesActionPerformed
        panelDatos.removeAll();
        panelTransacciones.setSize(870,557);
        panelTransacciones.setLocation(0, 0);
        panelDatos.add(panelTransacciones, BorderLayout.CENTER);
        panelDatos.revalidate();
        panelDatos.repaint();
        panelTransacciones.setVisible(true);
        limpiarVaucher();
        txtNumeroCuentaTransaccion.setText("");
        txtMontoTransaccion.setText("");
    }//GEN-LAST:event_btnTransaccionesActionPerformed
    private void btnPrestamosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrestamosActionPerformed
        panelDatos.removeAll();
        panelPrestamo.setSize(870,557);
        panelPrestamo.setLocation(0, 0);
        panelDatos.add(panelPrestamo, BorderLayout.CENTER);
        panelDatos.revalidate();
        panelDatos.repaint();
        panelPrestamo.setVisible(true);
        mostrarPrestamos();
        limpiarPrestamo();
    }//GEN-LAST:event_btnPrestamosActionPerformed
    private void btnNuevoEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoEmpleadoActionPerformed
        limpiarEmpleado();
        txtFechaInicioEmpleado.setEditable(true);
        txtFechaInicioEmpleado.setFocusable(true);
        txtDniEmpleado.setEditable(true);
        txtDniEmpleado.setFocusable(true);
        btnAplicarCambiosEmpleados.setVisible(false);
    }//GEN-LAST:event_btnNuevoEmpleadoActionPerformed
    private void btnCrearEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCrearEmpleadoActionPerformed
            String nombre = txtNombreEmpleado.getText().trim();
            String apellido = txtApellidoEmpleado.getText().trim(); 
            String dni = txtDniEmpleado.getText().trim();
            String cargo = cboRolEmpleado.getSelectedItem().toString();
            String fechaContratacionString = txtFechaInicioEmpleado.getText(); 
            String salarioString = txtSalarioEmpleado.getText().trim();
            String numeroCelular = txtCelularEmpleado.getText().trim();
            String nombreUsuario = txtUsuarioEmpleado.getText().trim();
            String contraseña = txtContraseñaEmpleado.getText().trim();
            String rol = cboRolEmpleado.getSelectedItem().toString();

            if (nombre.isEmpty() || apellido.isEmpty() || dni.isEmpty() || cargo.isEmpty() || 
                fechaContratacionString.isEmpty() || salarioString.isEmpty() || 
                numeroCelular.isEmpty() || nombreUsuario.isEmpty() || contraseña.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Todos los campos deben estar completos.", "Error", JOptionPane.ERROR_MESSAGE);
            }else{
                double salario = Double.parseDouble(salarioString);    
                // Convertir la fecha de String a java.sql.Date
                Date fechaContratacion = null;
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // Formato de entrada

                try {
                    java.util.Date utilDate = sdf.parse(fechaContratacionString); // Parsear la fecha
                    fechaContratacion = new Date(utilDate.getTime()); // Convertir a java.sql.Date
                } catch (ParseException e) {
                    e.printStackTrace();
                    // Manejar el error de formato de fecha aquí
                    return; // Salir del método si hay un error
                }

                // Instancias de los controladores
                EmpleadoController controllerEmple = new EmpleadoController();
                UsuarioSistemaController controllerUsuSistem = new UsuarioSistemaController();

                // 1. Agregar empleado y obtener el EmpleadoID generado
                int empleadoID = controllerEmple.agregarEmpleado(nombre, apellido, dni, cargo, fechaContratacion, salario, numeroCelular);


                if (empleadoID != 0) { // Verificar si el empleado fue creado con éxito
                    boolean usuarioCreado = controllerUsuSistem.agregarUsuario(empleadoID, nombreUsuario, contraseña, rol, fechaContratacion);
                    if (usuarioCreado) {
                        limpiarEmpleado();
                        mostrarEmpleados();
                    } else {
                        System.out.println("Error al crear el usuario del sistema.");
                    }
                } else {
                    System.out.println("Error al crear el empleado.");
                }
            }
    }//GEN-LAST:event_btnCrearEmpleadoActionPerformed
    private void btnEliminarEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarEmpleadoActionPerformed
        
      String dniInsertado = JOptionPane.showInputDialog(null, "Por favor, ingresa el DNI del Empleado:");
    
        if (dniInsertado != null && !dniInsertado.trim().isEmpty()) {
            EmpleadoController empleadoController = new EmpleadoController();
            UsuarioSistemaController usuarioSistemaController = new UsuarioSistemaController();

            try {
                int empleadoID = empleadoController.obtenerEmpleadoIDPorDni(dniInsertado);
                if (empleadoID != -1) { // Verifica que se encontró un empleado
                    usuarioSistemaController.eliminarUsuarioPorEmpleadoID(empleadoID);
                    empleadoController.eliminarEmpleadoPorDNI(dniInsertado);

                    JOptionPane.showMessageDialog(null, "Empleado y usuario eliminados con éxito.");
                    mostrarEmpleados();
                } else {
                    JOptionPane.showMessageDialog(null, "No se encontró un empleado con el DNI proporcionado.");
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error al eliminar el empleado: " + e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(null, "Entrada de DNI cancelada.");
        }
        
    }//GEN-LAST:event_btnEliminarEmpleadoActionPerformed
    private void ModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ModificarActionPerformed
        
        
        
        dniInsertadoModificarEmpleado = JOptionPane.showInputDialog(null, "Por favor, ingresa el DNI del Empleado:");
         if (dniInsertadoModificarEmpleado != null && !dniInsertadoModificarEmpleado.trim().isEmpty()) {
                
             txtFechaInicioEmpleado.setEditable(false);
             txtFechaInicioEmpleado.setFocusable(false);
             txtDniEmpleado.setEditable(false);
             txtDniEmpleado.setFocusable(false);
             btnAplicarCambiosEmpleados.setVisible(true);
             
             EmpleadoController empleadoController = new EmpleadoController();
             UsuarioSistemaController usuarioSistemaController = new UsuarioSistemaController();
             
             
             Empleado empleado = empleadoController.obtenerEmpleadoPorDniModificar(dniInsertadoModificarEmpleado);
        if (empleado != null) {
            txtNombreEmpleado.setText(empleado.getNombre());
            txtApellidoEmpleado.setText(empleado.getApellido());
            
            if (empleado.getCargo().equals("Cajero")) {
                cboRolEmpleado.setSelectedIndex(0);
            }else if(empleado.getCargo().equals("Gerente")){
                cboRolEmpleado.setSelectedIndex(1);
            }
            
            txtSalarioEmpleado.setText(String.valueOf(empleado.getSalario()));
            txtCelularEmpleado.setText(empleado.getNumeroCelular());
            
            UsuarioSistema usuarioSistema = usuarioSistemaController.obtenerUsuarioPorDniModificar(dniInsertadoModificarEmpleado);
            if (usuarioSistema != null) {
                txtUsuarioEmpleado.setText(usuarioSistema.getNombreUsuario());
                txtContraseñaEmpleado.setText(usuarioSistema.getContraseña());
                
               
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró el usuario asociado a este empleado.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "No se encontró ningún empleado con el DNI ingresado.");
        }
             
             
             
         }else{
             JOptionPane.showMessageDialog(null, "Entrada de DNI cancelada.");
         }
        
    }//GEN-LAST:event_ModificarActionPerformed
    private void btnAplicarCambiosEmpleadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAplicarCambiosEmpleadosActionPerformed
        
          String nombre = txtNombreEmpleado.getText().trim();
            String apellido = txtApellidoEmpleado.getText().trim(); 
            String cargo = cboRolEmpleado.getSelectedItem().toString();
            String salarioString = txtSalarioEmpleado.getText().trim();
            String numeroCelular = txtCelularEmpleado.getText().trim();
            String nombreUsuario = txtUsuarioEmpleado.getText().trim();
            String contraseña = txtContraseñaEmpleado.getText().trim();
        
            if (nombre.isEmpty() || apellido.isEmpty() || salarioString.isEmpty() || numeroCelular.isEmpty() || 
                nombreUsuario.isEmpty() || contraseña.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
                return; 
            }
            double salario = convertirSalario(salarioString);

            EmpleadoController empleadoController = new EmpleadoController();
            UsuarioSistemaController usuarioController = new UsuarioSistemaController();

            boolean empleadoActualizado = empleadoController.actualizarEmpleado(dniInsertadoModificarEmpleado, nombre, apellido, cargo, salario, numeroCelular);
            boolean usuarioActualizado = usuarioController.actualizarUsuario(dniInsertadoModificarEmpleado, nombreUsuario, contraseña);
            
            if (empleadoActualizado && usuarioActualizado) {
                JOptionPane.showMessageDialog(null, "Empleado y Usuario actualizado exitosamente.");
            } else {
                JOptionPane.showMessageDialog(null, "Error al actualizar el empleado.");
            }
            mostrarEmpleados();
            
    }//GEN-LAST:event_btnAplicarCambiosEmpleadosActionPerformed
    private void btnCrearClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCrearClienteActionPerformed

            String nombre = txtNombreCliente.getText().trim();
            String apellido = txtApellidoCliente.getText().trim(); 
            String dni = txtDniCliente.getText().trim();
            String direccion = txtDireccionCliente.getText().trim();
            String numeroCelular = txtCelularCliente.getText().trim();
            String email = txtEmailCliente.getText().trim();
            String fechaRegistroString = txtFechaRegistroCliente.getText().trim();
            String numCuenta = txtNumeroCuentaCliente.getText().trim();
            String TipoCuenta = cboTipoCuenta.getSelectedItem().toString();
            String SaldoString = txtSaldoCliente.getText().trim();
            
            if (nombre.isEmpty() || apellido.isEmpty() || dni.isEmpty() || direccion.isEmpty() || numeroCelular.isEmpty() 
                || email.isEmpty() || numCuenta.isEmpty() || SaldoString.isEmpty()) {
                
                JOptionPane.showMessageDialog(null, "Todos los campos deben estar completos.", "Error", JOptionPane.ERROR_MESSAGE);
            }else{
                double saldo = convertirSalario(SaldoString);
                
                Date fechaRegistro = null;
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // Formato de entrada
                try {
                    java.util.Date utilDate = sdf.parse(fechaRegistroString);
                    fechaRegistro = new Date(utilDate.getTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                    return; 
                }
                ClienteController clienteController = new ClienteController();
                CuentaBancariaController cuentaBancariaController = new CuentaBancariaController();
                
                int clienteID = clienteController.agregarCliente(nombre, apellido, dni, direccion, numeroCelular, email, fechaRegistro);
                    
                if (clienteID != 0) {
                    boolean cuentaBancariaCreada = cuentaBancariaController.agregarCuentaBancaria(clienteID, numCuenta, TipoCuenta, saldo, fechaRegistro);
                    if (cuentaBancariaCreada) {
                        JOptionPane.showMessageDialog(null, "Cliente y Cuenta Bancaria creado con exito.");
                        limpiarCliente();
                        mostrarClientes();
                    }else{
                        System.out.println("Error al crear el cuenta Bancaria.");
                    }
                }else {
                    System.out.println("Error al crear el Cliente.");
                }
                
                
            }
            
    }//GEN-LAST:event_btnCrearClienteActionPerformed
    private void btnEliminarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarClienteActionPerformed
        String dniInsertado = JOptionPane.showInputDialog(null, "Por favor, ingresa el DNI del Cliente:");
        
        if (dniInsertado != null && !dniInsertado.trim().isEmpty()) {
            ClienteController clienteController = new ClienteController();
            CuentaBancariaController cuentaBancariaController = new CuentaBancariaController();
            try {
                int ClienteID = clienteController.obtenerClienteIDPorDni(dniInsertado);
                if (ClienteID != -1) { // Verifica que se encontró un empleado
                    cuentaBancariaController.eliminarCuentaBancariaPorEmpleadoID(ClienteID);
                    clienteController.eliminarClientePorDNI(dniInsertado);
                    JOptionPane.showMessageDialog(null, "Cliente y cuenta bancaria eliminados con éxito.");
                    mostrarClientes();
                } else {
                    JOptionPane.showMessageDialog(null, "No se encontró un Cliente con el DNI proporcionado.");
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error al eliminar el empleado: " + e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(null, "Entrada de DNI cancelada.");
        }
    }//GEN-LAST:event_btnEliminarClienteActionPerformed
    private void btnNuevoClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoClienteActionPerformed
        limpiarCliente();
        txtSaldoCliente.setEnabled(true);
        txtSaldoCliente.setEditable(true);
        txtNumeroCuentaCliente.setEnabled(true);
        txtNumeroCuentaCliente.setEditable(true);
        txtDniCliente.setEditable(true);
        txtDniCliente.setEnabled(true);
        btnAplicarCambiosCliente.setVisible(true);
    }//GEN-LAST:event_btnNuevoClienteActionPerformed
    private void btnModificarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarClienteActionPerformed
        txtSaldoCliente.setEnabled(false);
        txtSaldoCliente.setEditable(false);
        txtNumeroCuentaCliente.setEnabled(false);
        txtNumeroCuentaCliente.setEditable(false);
        txtDniCliente.setEditable(false);
        txtDniCliente.setEnabled(false);
        
        dniInsertadoModificarCliente = JOptionPane.showInputDialog(null, "Por favor, ingresa el DNI del Cliente:");
            if (dniInsertadoModificarCliente != null && !dniInsertadoModificarCliente.trim().isEmpty()) {

                ClienteController clienteController = new ClienteController();
                CuentaBancariaController cuentaBancariaController = new CuentaBancariaController();


                Cliente cliente = clienteController.obtenerClientePorDniModificar(dniInsertadoModificarCliente);
            if (cliente != null) {
                txtNombreCliente.setText(cliente.getNombre());
                txtApellidoCliente.setText(cliente.getApellido());
                txtDireccionCliente.setText(cliente.getDireccion());
                txtCelularCliente.setText(cliente.getTelefono());
                txtEmailCliente.setText(cliente.getEmail());
                txtFechaRegistroCliente.setText(cliente.getFechaRegistro());

                CuentaBancaria cuentabancaria = cuentaBancariaController.obtenerCuentaBancariaPorDniModificar(dniInsertadoModificarCliente);
                if (cuentabancaria != null) {
                     if (cuentabancaria.getTipoCuenta().equals("Corriente")) {
                        cboTipoCuenta.setSelectedIndex(0);
                    }else {
                        cboTipoCuenta.setSelectedIndex(1);
                    }
                    btnAplicarCambiosCliente.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "No se encontró el usuario asociado a este empleado.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró ningún empleado con el DNI ingresado.");
            }
         }else{
             JOptionPane.showMessageDialog(null, "Entrada de DNI cancelada.");
         }
    }//GEN-LAST:event_btnModificarClienteActionPerformed
    private void btnAplicarCambiosClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAplicarCambiosClienteActionPerformed
            String nombre = txtNombreCliente.getText().trim();
            String apellido = txtApellidoCliente.getText().trim();
            String direccion = txtDireccionCliente.getText().trim();
            String celular = txtCelularCliente.getText().trim();
            String email = txtEmailCliente.getText().trim();
            String tipoCuenta = cboTipoCuenta.getSelectedItem().toString(); 

            // Validar que todos los campos estén completos
            if (nombre.isEmpty() || apellido.isEmpty() || direccion.isEmpty() || celular.isEmpty() || 
                email.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
                return; 
            }

            ClienteController clienteController = new ClienteController();
            CuentaBancariaController cuentaBancariaController = new CuentaBancariaController();

            // Actualizar información del cliente
            boolean cuentaActualizada = cuentaBancariaController.actualizarCuentaBancaria(dniInsertadoModificarCliente, tipoCuenta);
            boolean clienteActualizado = clienteController.actualizarCliente(dniInsertadoModificarCliente, nombre, apellido, direccion, celular, email);
            if (clienteActualizado && cuentaActualizada) {
                JOptionPane.showMessageDialog(null, "Cliente actualizado exitosamente.");
                limpiarCliente();
                mostrarClientes();
                btnAplicarCambiosCliente.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "Error al actualizar el cliente.");
            }
            
            
           
            
    }//GEN-LAST:event_btnAplicarCambiosClienteActionPerformed
    private void btnRetiroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRetiroActionPerformed
        String numeroCuenta = txtNumeroCuentaTransaccion.getText().trim();
        String montoString = txtMontoTransaccion.getText().trim();
        String tipoTransaccion = "retiro"; // Tipo de transacción

        TransaccionesController transaccionesController = new TransaccionesController();

        // Validar campos vacíos
        if (numeroCuenta.isEmpty() || montoString.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validar que el monto sea un número válido
        double monto;
        try {
            monto = Double.parseDouble(montoString);
            if (monto <= 0) {
                JOptionPane.showMessageDialog(null, "El monto debe ser mayor que cero.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "El monto debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Verificar la existencia de la cuenta
        Integer cuentaID = transaccionesController.obtenerCuentaIdPorNumeroCuenta(numeroCuenta);
        if (cuentaID == null) {
            JOptionPane.showMessageDialog(null, "El número de cuenta no existe.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Obtener ID del empleado que realiza la transacción
        int empleadoID = transaccionesController.obtenerEmpleadoIdPorUsuario(usuario); 

        // Restar monto de la cuenta
        if (transaccionesController.restarMontoDeCuenta(cuentaID, monto)) {
            // Insertar la transacción
            if (transaccionesController.insertarTransaccionRetiro(cuentaID, empleadoID, tipoTransaccion, monto, numeroCuenta)) {
                JOptionPane.showMessageDialog(null, "Retiro realizado exitosamente.");

                // Limpiar los campos de entrada
                txtNumeroCuentaTransaccion.setText("");
                txtMontoTransaccion.setText("");

                // Obtener información adicional para el voucher
                Transacciones ultimaTransaccion = transaccionesController.obtenerUltimaOperacionConFecha(); // Método que devuelve un objeto Transacciones
                String nombreCliente = transaccionesController.obtenerNombrePorNumeroCuenta(numeroCuenta);

                // Completar los campos del voucher
                txtVaucherNombre.setText("Nombre: " + nombreCliente);
                txtVaucherNumeroCuenta.setText("Número de cuenta: " + numeroCuenta);
                txtVaucherMonto.setText("Monto Retirado: " + monto);
                txtVaucherOperacion.setText("Nº de Operación: " + ultimaTransaccion.getTransaccionID());
                txtVaucherFecha.setText("Fecha: " + ultimaTransaccion.getFechaTransaccion()); // Asegúrate de tener el método getFechaTransaccion() en tu clase Transacciones
                txtVaucherEmpleadoID.setText("Código Empleado: " + empleadoID);
            } else {
                JOptionPane.showMessageDialog(null, "Error al registrar la transacción.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Error al restar el monto de la cuenta.");
        }
    }//GEN-LAST:event_btnRetiroActionPerformed
    private void btnDepositoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDepositoActionPerformed
        String numeroCuenta = txtNumeroCuentaTransaccion.getText().trim();
        String montoString = txtMontoTransaccion.getText().trim();
        String tipoTransaccion = "deposito"; // Tipo de transacción

        TransaccionesController transaccionesController = new TransaccionesController();

        // Validar campos vacíos
        if (numeroCuenta.isEmpty() || montoString.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validar que el monto sea un número válido
        double monto;
        try {
            monto = Double.parseDouble(montoString);
            if (monto <= 0) {
                JOptionPane.showMessageDialog(null, "El monto debe ser mayor que cero.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "El monto debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Verificar la existencia de la cuenta
        Integer cuentaID = transaccionesController.obtenerCuentaIdPorNumeroCuenta(numeroCuenta);
        if (cuentaID == null) {
            JOptionPane.showMessageDialog(null, "El número de cuenta no existe.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int empleadoID = transaccionesController.obtenerEmpleadoIdPorUsuario(usuario); 

        // Sumar monto a la cuenta
        if (transaccionesController.SumarMontoDeCuenta(cuentaID, monto)) {
            // Insertar la transacción
            if (transaccionesController.insertarTransaccionDeposito(cuentaID, empleadoID, tipoTransaccion, monto, numeroCuenta)) {
                JOptionPane.showMessageDialog(null, "Depósito realizado exitosamente.");

                // Limpiar los campos de entrada
                txtNumeroCuentaTransaccion.setText("");
                txtMontoTransaccion.setText("");

                // Obtener información adicional para el voucher
                Transacciones ultimaTransaccion = transaccionesController.obtenerUltimaOperacionConFecha(); // Método que devuelve un objeto Transacciones
                String nombreCliente = transaccionesController.obtenerNombrePorNumeroCuenta(numeroCuenta);

                // Completar los campos del voucher
                txtVaucherNombre.setText("Nombre: " + nombreCliente);
                txtVaucherNumeroCuenta.setText("Número de cuenta: " + numeroCuenta);
                txtVaucherMonto.setText("Monto Depositado: " + monto);
                txtVaucherOperacion.setText("Nº de Operación: " + ultimaTransaccion.getTransaccionID());
                txtVaucherFecha.setText("Fecha: " + ultimaTransaccion.getFechaTransaccion()); // Asegúrate de tener el método getFechaTransaccion() en tu clase Transacciones
                txtVaucherEmpleadoID.setText("Código Empleado: " + empleadoID);
            } else {
                JOptionPane.showMessageDialog(null, "Error al registrar la transacción.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Error al sumar el monto a la cuenta.");
        }
    }//GEN-LAST:event_btnDepositoActionPerformed
    private void btnRealizarPrestamoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRealizarPrestamoActionPerformed
        String numeroCuenta = txtNumeroCuentaPrestamo.getText().trim();
        String montoString = txtMontoPrestamo.getText().trim();
        String plazoString = txtPlazoMesesPrestamo.getText().trim();
        String fechaString = txtFechaPrestamo.getText().trim(); // Asegúrate de que sea un formato válido

        PrestamosController prestamoController= new PrestamosController();
        
        if (numeroCuenta.isEmpty() || montoString.isEmpty() || plazoString.isEmpty() || fechaString.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
       
        Integer clienteID = prestamoController.obtenerClienteIDPorNumeroCuenta(numeroCuenta);
        if (clienteID == null) {
            JOptionPane.showMessageDialog(null, "El número de cuenta no existe.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        double montoPrestamo;
        try {
            montoPrestamo = Double.parseDouble(montoString);
            if (montoPrestamo <= 0) {
                JOptionPane.showMessageDialog(null, "El monto debe ser mayor que cero.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "El monto debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int plazo;
        try {
            plazo = Integer.parseInt(plazoString);
            if (plazo <= 0) {
                JOptionPane.showMessageDialog(null, "El plazo debe ser mayor que cero.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "El plazo debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        
        int empleadoID = prestamoController.obtenerEmpleadoIdPorUsuario(usuario);

        double interes = prestamoController.calcularInteres(plazo, montoPrestamo);

        java.sql.Date fechaDesembolso = java.sql.Date.valueOf(fechaString);

        // Insertar el préstamo
        if (prestamoController.insertarPrestamo(clienteID, montoPrestamo, interes, plazo, fechaDesembolso, empleadoID, numeroCuenta)) {
            JOptionPane.showMessageDialog(null, "Préstamo guardado exitosamente.");
            mostrarPrestamos();
            limpiarPrestamo();
        } else {
            JOptionPane.showMessageDialog(null, "Error al guardar el préstamo.");
        }
    }//GEN-LAST:event_btnRealizarPrestamoActionPerformed

    private void btnNuevoPrestamoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoPrestamoActionPerformed
        limpiarPrestamo();
    }//GEN-LAST:event_btnNuevoPrestamoActionPerformed

    private void DesactivarPrestamoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DesactivarPrestamoActionPerformed
        String numeroCuenta = JOptionPane.showInputDialog(null, "Ingrese el número de cuenta para desactivar el préstamo:");
        PrestamosController prestamoController= new PrestamosController();
        // Validar que se ingresó un número de cuenta
        if (numeroCuenta == null || numeroCuenta.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor, ingrese un número de cuenta.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Verificar si el préstamo existe para el número de cuenta
        if (!prestamoController.existePrestamoPorNumeroCuenta(numeroCuenta)) {
            JOptionPane.showMessageDialog(null, "No hay préstamos asociados a este número de cuenta.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Actualizar el estado del préstamo a "No activo"
        if (prestamoController.desactivarPrestamo(numeroCuenta)) {
            JOptionPane.showMessageDialog(null, "El préstamo ha sido desactivado exitosamente.");
            mostrarPrestamos();
        } else {
            JOptionPane.showMessageDialog(null, "Error al desactivar el préstamo.");
        }
    }//GEN-LAST:event_DesactivarPrestamoActionPerformed

    private void txtDniEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDniEmpleadoActionPerformed
    
    }//GEN-LAST:event_txtDniEmpleadoActionPerformed

    private void txtCelularEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCelularEmpleadoActionPerformed

    }//GEN-LAST:event_txtCelularEmpleadoActionPerformed

    private void txtSalarioEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSalarioEmpleadoActionPerformed

    }//GEN-LAST:event_txtSalarioEmpleadoActionPerformed

    private void txtDniEmpleadoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDniEmpleadoKeyTyped
  char c = evt.getKeyChar();
    String text = txtDniEmpleado.getText();

    // Permitir solo dígitos
    if (!Character.isDigit(c)) {
        evt.consume(); // Evitar caracteres que no sean dígitos
    }

    // Limitar la longitud total a 8 caracteres
    if (text.length() >= 8) {
        evt.consume(); // Evitar que se exceda la longitud total
    }
    }//GEN-LAST:event_txtDniEmpleadoKeyTyped

    private void txtCelularEmpleadoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCelularEmpleadoKeyTyped
    char c = evt.getKeyChar();
    String text = txtCelularEmpleado.getText();

    // Permitir solo dígitos
    if (!Character.isDigit(c)) {
        evt.consume(); // Evitar caracteres que no sean dígitos
    }

    // Limitar la longitud total a 9 caracteres
    if (text.length() >= 9) {
        evt.consume(); // Evitar que se exceda la longitud total
    }
// TODO add your handling code here:
    }//GEN-LAST:event_txtCelularEmpleadoKeyTyped

    private void txtDniClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDniClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDniClienteActionPerformed

    private void txtDniClienteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDniClienteKeyTyped
                                       
    char c = evt.getKeyChar();
    // Verificar si el carácter no es un número o si el campo ya tiene 8 números
    if (!Character.isDigit(c) ||  txtDniCliente.getText().length() >= 8) {
        evt.consume(); // Evitar que se escriban caracteres no numéricos o más de 8 dígitos
    }
               // TODO add your handling code here:
    }//GEN-LAST:event_txtDniClienteKeyTyped

    private void txtCelularClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCelularClienteActionPerformed

    }//GEN-LAST:event_txtCelularClienteActionPerformed

    private void txtCelularClienteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCelularClienteKeyTyped
   char c = evt.getKeyChar();
    String text = txtCelularCliente.getText();

    // Permitir solo dígitos (0-9)
    if (!Character.isDigit(c)) {
        evt.consume(); // Evitar caracteres que no sean dígitos
    }

    // Limitar la longitud total a 10 caracteres (puedes ajustar este número según tus necesidades)
    if (text.length() >= 9) {
        evt.consume(); // Evitar que se exceda la longitud total
    }    // TODO add your handling code here:
    }//GEN-LAST:event_txtCelularClienteKeyTyped

    private void txtNumeroCuentaClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNumeroCuentaClienteActionPerformed
    
    }//GEN-LAST:event_txtNumeroCuentaClienteActionPerformed

    private void txtNumeroCuentaClienteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumeroCuentaClienteKeyTyped
        char c = evt.getKeyChar();
    String text = txtNumeroCuentaCliente.getText();

    // Permitir solo dígitos (0-9)
    if (!Character.isDigit(c)) {
        evt.consume(); // Evitar caracteres que no sean dígitos
    }

    // Limitar la longitud total a 20 caracteres
    if (text.length() >= 20) {
        evt.consume(); // Evitar que se exceda la longitud total
    } 
    }//GEN-LAST:event_txtNumeroCuentaClienteKeyTyped

    private void txtSaldoClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSaldoClienteActionPerformed
     
    }//GEN-LAST:event_txtSaldoClienteActionPerformed

    private void txtSaldoClienteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSaldoClienteKeyTyped
   char c = evt.getKeyChar();
    String text = txtSaldoCliente.getText();

    // Permitir solo dígitos y el punto decimal
    if (!Character.isDigit(c) && c != '.') {
        evt.consume(); // Evitar caracteres que no sean dígitos o el punto
    }

    // Limitar la longitud total a 20 caracteres
    if (text.length() >= 20) {
        evt.consume(); // Evitar que se exceda la longitud total
    }

    // Evitar más de un punto decimal
    if (c == '.') {
        if (text.contains(".")) {
            evt.consume(); // Evitar más de un punto decimal
        }
    }// TODO add y
    }//GEN-LAST:event_txtSaldoClienteKeyTyped

    private void txtNumeroCuentaTransaccionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNumeroCuentaTransaccionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNumeroCuentaTransaccionActionPerformed

    private void txtNumeroCuentaTransaccionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumeroCuentaTransaccionKeyTyped
   char c = evt.getKeyChar();
    String text = txtNumeroCuentaTransaccion.getText();

    // Permitir solo dígitos (0-9)
    if (!Character.isDigit(c)) {
        evt.consume(); // Evitar caracteres que no sean dígitos
    }

    // Limitar la longitud total a 20 caracteres
    if (text.length() >= 20) {
        evt.consume(); // Evitar que se exceda la longitud total
    }
    }//GEN-LAST:event_txtNumeroCuentaTransaccionKeyTyped

    private void txtMontoTransaccionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMontoTransaccionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMontoTransaccionActionPerformed

    private void txtMontoTransaccionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMontoTransaccionKeyTyped
        char c = evt.getKeyChar();
    String text = txtMontoTransaccion.getText();

    // Permitir solo dígitos y el punto decimal
    if (!Character.isDigit(c) && c != '.') {
        evt.consume(); // Evitar caracteres que no sean dígitos o el punto
    }

    // Limitar la longitud total a 20 caracteres
    if (text.length() >= 20) {
        evt.consume(); // Evitar que se exceda la longitud total
    }

    // Evitar más de un punto decimal
    if (c == '.') {
        if (text.contains(".")) {
            evt.consume(); // Evitar más de un punto decimal
        }
    }
    }//GEN-LAST:event_txtMontoTransaccionKeyTyped

    private void txtNumeroCuentaPrestamoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumeroCuentaPrestamoKeyTyped
        char c = evt.getKeyChar();
    String text = txtNumeroCuentaPrestamo.getText();

    // Permitir solo dígitos (0-9)
    if (!Character.isDigit(c)) {
        evt.consume(); // Evitar caracteres que no sean dígitos (incluyendo espacios y caracteres especiales)
    }

    // Limitar la longitud total a 20 caracteres
    if (text.length() >= 20) {
        evt.consume(); // Evitar que se exceda la longitud total
    }
    }//GEN-LAST:event_txtNumeroCuentaPrestamoKeyTyped

    private void txtMontoPrestamoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMontoPrestamoKeyTyped
    char c = evt.getKeyChar();
    String text = txtMontoPrestamo.getText();

    // Permitir solo dígitos y el punto
    if (!Character.isDigit(c) && c != '.') {
        evt.consume(); // Evitar caracteres que no sean dígitos o el punto
    }

    // Limitar la longitud total a 20 caracteres
    if (text.length() >= 20) {
        evt.consume(); // Evitar que se exceda la longitud total
    }

    // Evitar más de un punto decimal
    if (c == '.') {
        if (text.contains(".")) {
            evt.consume(); // Evitar más de un punto
        }
    }
    }//GEN-LAST:event_txtMontoPrestamoKeyTyped

    private void jTextField25KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField25KeyTyped
     char c = evt.getKeyChar();
    String text = jTextField25.getText();

    // Permitir solo dígitos y limitar la longitud a 20 caracteres
    if (!Character.isDigit(c) || text.length() >= 20) {
        evt.consume(); // Evitar que se escriban caracteres no numéricos o más de 20 dígitos
    }
    }//GEN-LAST:event_jTextField25KeyTyped

    private void jTextField28KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField28KeyTyped
    char c = evt.getKeyChar();
    String text = jTextField26.getText();

    // Permitir solo dígitos y el punto
    if (!Character.isDigit(c) && c != '.') {
        evt.consume(); // Evitar caracteres que no sean dígitos o el punto
    }

    // Limitar la longitud total a 20 caracteres
    if (text.length() >= 20) {
        evt.consume(); // Evitar que se exceda la longitud total
    }

    // Evitar más de un punto decimal
    if (c == '.') {
        if (text.contains(".")) {
            evt.consume(); // Evitar más de un punto
        }
    }
    }//GEN-LAST:event_jTextField28KeyTyped

    private void txtNombreEmpleadoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreEmpleadoKeyTyped
  char c = evt.getKeyChar();
    String text = txtNombreEmpleado.getText();

    // Permitir solo letras y espacios
    if (!Character.isLetter(c) && c != ' ') {
        evt.consume(); // Evitar caracteres que no sean letras o espacios
    }

    // Limitar la longitud total a 50 caracteres (puedes ajustar este número según tus necesidades)
    if (text.length() >= 50) {
        evt.consume(); // Evitar que se exceda la longitud total
    
}
    }//GEN-LAST:event_txtNombreEmpleadoKeyTyped

    private void txtApellidoEmpleadoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtApellidoEmpleadoKeyTyped
  char c = evt.getKeyChar();
    String text = txtApellidoEmpleado.getText();

    // Permitir solo letras y espacios
    if (!Character.isLetter(c) && c != ' ') {
        evt.consume(); // Evitar caracteres que no sean letras o espacios
    }

    // Limitar la longitud total a 50 caracteres (puedes ajustar este número según tus necesidades)
    if (text.length() >= 50) {
        evt.consume(); // Evitar que se exceda la longitud total
    }
    }//GEN-LAST:event_txtApellidoEmpleadoKeyTyped

    private void txtFechaInicioEmpleadoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFechaInicioEmpleadoKeyTyped
         char c = evt.getKeyChar();
    String text = txtFechaInicioEmpleado.getText();

    // Permitir solo dígitos y el guion
    if (!Character.isDigit(c) && c != '-') {
        evt.consume(); // Evitar caracteres que no sean dígitos o guiones
    }

    // Limitar la longitud total a 10 caracteres (YYYY-MM-DD)
    if (text.length() >= 10) {
        evt.consume(); // Evitar que se exceda la longitud total
    }

    // Controlar la posición del guion
    if (c == '-') {
        // Evitar guiones al inicio o consecutivos
        if (text.length() == 0 || text.charAt(text.length() - 1) == '-') {
            evt.consume(); // Evitar guiones al inicio o consecutivos
        } else {
            // Permitir solo 2 guiones en las posiciones adecuadas
            int dashCount = 0;
            for (int i = 0; i < text.length(); i++) {
                if (text.charAt(i) == '-') {
                    dashCount++;
                }
            }
            // Verificar si hay menos de 2 guiones
            if (dashCount >= 2) {
                evt.consume(); // Evitar más de 2 guiones
            }
        }
    }
    }//GEN-LAST:event_txtFechaInicioEmpleadoKeyTyped

    private void txtSalarioEmpleadoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSalarioEmpleadoKeyTyped
   char c = evt.getKeyChar();
    String text = txtSalarioEmpleado.getText();

    // Permitir solo dígitos y el punto decimal
    if (!Character.isDigit(c) && c != '.') {
        evt.consume(); // Evitar caracteres que no sean dígitos o el punto
    }

    // Limitar la longitud total a 20 caracteres (ajusta según sea necesario)
    if (text.length() >= 20) {
        evt.consume(); // Evitar que se exceda la longitud total
    }

    // Evitar más de un punto decimal
    if (c == '.') {
        if (text.contains(".")) {
            evt.consume(); // Evitar más de un punto decimal
        }
    }

    // Evitar que el punto esté al inicio
    if (text.isEmpty() && c == '.') {
        evt.consume(); // Evitar que el punto esté al inicio
    }
    }//GEN-LAST:event_txtSalarioEmpleadoKeyTyped

    private void txtNombreEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreEmpleadoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreEmpleadoActionPerformed

    private void txtUsuarioEmpleadoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUsuarioEmpleadoKeyTyped
  char c = evt.getKeyChar();
    String text = txtUsuarioEmpleado.getText();

    // Permitir solo letras y números
    if (!Character.isLetterOrDigit(c)) {
        evt.consume(); // Evitar caracteres que no sean letras o números
    }

    // Limitar la longitud total a 20 caracteres (ajusta según sea necesario)
    if (text.length() >= 20) {
        evt.consume(); // Evitar que se exceda la longitud total
    }  
    }//GEN-LAST:event_txtUsuarioEmpleadoKeyTyped

    private void txtContraseñaEmpleadoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtContraseñaEmpleadoKeyTyped
 char c = evt.getKeyChar();
    
    // Evitar espacios
    if (c == ' ') {
        evt.consume(); // Bloquear espacios
    }

    // Limitar la longitud total a 20 caracteres (ajusta según sea necesario)
    if (txtContraseñaEmpleado.getText().length() >= 20) {
        evt.consume(); // Evitar que se exceda la longitud total
    }
    }//GEN-LAST:event_txtContraseñaEmpleadoKeyTyped

    private void txtNombreClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreClienteActionPerformed

    }//GEN-LAST:event_txtNombreClienteActionPerformed

    private void txtNombreClienteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreClienteKeyTyped
   char c = evt.getKeyChar();
    String text = txtNombreCliente.getText();

    // Permitir solo letras y espacios
    if (!Character.isLetter(c) && c != ' ') {
        evt.consume(); // Evitar caracteres que no sean letras o espacios
    }

    // Limitar la longitud total a 50 caracteres (puedes ajustar este número según tus necesidades)
    if (text.length() >= 50) {
        evt.consume(); // Evitar que se exceda la longitud total
    }
    }//GEN-LAST:event_txtNombreClienteKeyTyped

    private void jTextField26KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField26KeyTyped
  char c = evt.getKeyChar();
    String text = jTextField26.getText();

    // Permitir solo dígitos y el punto
    if (!Character.isDigit(c) && c != '.') {
        evt.consume(); // Evitar caracteres que no sean dígitos o el punto
    }

    // Limitar la longitud total a 20 caracteres
    if (text.length() >= 20) {
        evt.consume(); // Evitar que se exceda la longitud total
    }

    // Evitar más de un punto decimal
    if (c == '.') {
        if (text.contains(".")) {
            evt.consume(); // Evitar más de un punto
        }
    }
    }//GEN-LAST:event_jTextField26KeyTyped

    private void jTextField27KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField27KeyTyped
     char c = evt.getKeyChar();
    String text = jTextField27.getText();

    // Permitir solo dígitos y el guion
    if (!Character.isDigit(c) && c != '-') {
        evt.consume(); // Evitar caracteres que no sean dígitos o guiones
    }

    // Limitar la longitud total a 10 caracteres (YYYY-MM-DD)
    if (text.length() >= 10) {
        evt.consume(); // Evitar que se exceda la longitud
    }

    // Evitar más de un guion consecutivo o en posiciones incorrectas
    if (c == '-') {
        // Evitar guiones al inicio o consecutivos
        if (text.length() == 0 || text.charAt(text.length() - 1) == '-') {
            evt.consume(); // Evitar guiones al inicio o consecutivos
        } else {
            // Permitir solo 2 guiones en las posiciones adecuadas
            int dashCount = 0;
            for (int i = 0; i < text.length(); i++) {
                if (text.charAt(i) == '-') {
                    dashCount++;
                }
            }
            // Evitar más de 2 guiones
            if (dashCount >= 2) {
                evt.consume(); // Evitar más de 2 guiones
            }
        }
    }
    }//GEN-LAST:event_jTextField27KeyTyped

    private void txtPlazoMesesPrestamoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPlazoMesesPrestamoKeyTyped
char c = evt.getKeyChar();
    String text = txtPlazoMesesPrestamo.getText();

    // Permitir solo dígitos
    if (!Character.isDigit(c)) {
        evt.consume(); // Evitar caracteres que no sean dígitos
    }

    // Limitar la longitud total a 2 caracteres (para meses)
    if (text.length() >= 2) {
        evt.consume(); // Evitar que se exceda la longitud total
    }        // TODO add your handling code here:
    }//GEN-LAST:event_txtPlazoMesesPrestamoKeyTyped

    private void txtFechaPrestamoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFechaPrestamoKeyTyped
   char c = evt.getKeyChar();
    String text = txtFechaPrestamo.getText();

    // Permitir solo dígitos y el guion
    if (!Character.isDigit(c) && c != '-') {
        evt.consume(); // Evitar caracteres que no sean dígitos o guiones
    }

    // Limitar la longitud total a 10 caracteres (DD-MM-YYYY)
    if (text.length() >= 10) {
        evt.consume(); // Evitar que se exceda la longitud total
    }

    // Evitar más de un guion consecutivo o en posiciones incorrectas
    if (c == '-') {
        // Evitar guiones al inicio o consecutivos
        if (text.length() == 0 || text.charAt(text.length() - 1) == '-') {
            evt.consume(); // Evitar guiones al inicio o consecutivos
        } else {
            // Permitir solo 2 guiones en las posiciones adecuadas
            int dashCount = 0;
            for (int i = 0; i < text.length(); i++) {
                if (text.charAt(i) == '-') {
                    dashCount++;
                }
            }
            // Verificar si hay menos de 2 guiones
            if (dashCount >= 2) {
                evt.consume(); // Evitar más de 2 guiones
            }
        }
    }
    }//GEN-LAST:event_txtFechaPrestamoKeyTyped

    private void txtApellidoClienteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtApellidoClienteKeyTyped
        char c = evt.getKeyChar();
    String text = txtApellidoCliente.getText();

    // Permitir solo letras y espacios
    if (!Character.isLetter(c) && c != ' ') {
        evt.consume(); // Evitar caracteres que no sean letras o espacios
    }

    // Limitar la longitud total a 50 caracteres (puedes ajustar este número según tus necesidades)
    if (text.length() >= 50) {
        evt.consume(); // Evitar que se exceda la longitud total
    }
    }//GEN-LAST:event_txtApellidoClienteKeyTyped

    private void txtDireccionClienteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDireccionClienteKeyTyped
   
    }//GEN-LAST:event_txtDireccionClienteKeyTyped

    private void txtEmailClienteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEmailClienteKeyTyped
        char c = evt.getKeyChar();
    String text = txtEmailCliente.getText();

    // Permitir letras, números, puntos, guiones y arroba
    if (!Character.isLetter(c) && !Character.isDigit(c) && c != '.' && c != '_' && c != '-' && c != '@') {
        evt.consume(); // Evitar caracteres que no sean válidos
    }

    // Asegurarse de que el símbolo '@' y el dominio de Gmail estén en su lugar
    if (text.contains("@") && c != '\b') {
        // Evitar que se ingrese más de un '@'
        if (c == '@') {
            evt.consume(); // Evitar múltiples símbolos '@'
        }
        // Validar el dominio
        if (!text.endsWith("@gmail.com") && text.contains("@")) {
            if (text.length() > 25) { // Evitar que el texto supere el límite razonable
                evt.consume();
            }
        }
    }

    // Limitar la longitud total a 50 caracteres (puedes ajustar este número según tus necesidades)
    if (text.length() >= 50) {
        evt.consume(); // Evitar que se exceda la longitud total
    }

    }//GEN-LAST:event_txtEmailClienteKeyTyped

    private void txtFechaRegistroClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFechaRegistroClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFechaRegistroClienteActionPerformed

    private void txtFechaRegistroClienteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFechaRegistroClienteKeyTyped
    char c = evt.getKeyChar();
    String text = txtFechaRegistroCliente.getText();

    // Permitir solo dígitos y el guion
    if (!Character.isDigit(c) && c != '-') {
        evt.consume(); // Evitar caracteres que no sean dígitos o guiones
    }

    // Limitar la longitud total a 10 caracteres (YYYY-MM-DD)
    if (text.length() >= 10) {
        evt.consume(); // Evitar que se exceda la longitud total
    }

    // Controlar la posición del guion
    if (c == '-') {
        // Evitar guiones al inicio o consecutivos
        if (text.length() == 0 || text.charAt(text.length() - 1) == '-') {
            evt.consume(); // Evitar guiones al inicio o consecutivos
        } else {
            // Permitir solo 2 guiones en las posiciones adecuadas
            int dashCount = 0;
            for (int i = 0; i < text.length(); i++) {
                if (text.charAt(i) == '-') {
                    dashCount++;
                }
            }
            // Verificar si hay menos de 2 guiones
            if (dashCount >= 2) {
                evt.consume(); // Evitar más de 2 guiones
            }
        }
    }
    }//GEN-LAST:event_txtFechaRegistroClienteKeyTyped

    private void btnIngresar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIngresar1ActionPerformed
        usuario = txtUsuario.getText();
        String contraseña = new String(txtContraseña.getPassword());
        UsuarioSistemaController usuarioController = new UsuarioSistemaController();
        boolean credencialesValidas = usuarioController.verificarCredenciales(usuario, contraseña);
        if (credencialesValidas) {
            JOptionPane.showMessageDialog(this, "Ingreso correcto", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            SistemaBancarioLogin.setVisible(false);
            this.setVisible(true);
            vistaGeneral();
            String rol =  usuarioController.verificarRol(usuario);
            if (rol.equals("Gerente")) {
                vistaGerente();
            }else if (rol.equals("Cajero")) {
                vistaCajero();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnIngresar1ActionPerformed

    private void txtContraseñaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtContraseñaKeyTyped
        char c = evt.getKeyChar();
        // Verificar si el carácter es una letra, número o si es un espacio, y que no exceda la longitud de 8 caracteres
        if (!Character.isLetterOrDigit(c) || c == ' ' || txtContraseña.getText().length() >= 20) {
            evt.consume(); // Evitar que se escriban caracteres no alfabéticos, numéricos, espacios, o más de 8 caracteres
        }        // TODO add your handling code here:
    }//GEN-LAST:event_txtContraseñaKeyTyped

    private void txtContraseñaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtContraseñaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtContraseñaActionPerformed

    private void txtUsuarioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUsuarioKeyTyped
        char c = evt.getKeyChar();
        // Verificar si el carácter es una letra o número, y que no exceda la longitud de 8 caracteres
        if (!Character.isLetterOrDigit(c) || txtUsuario.getText().length() >= 20) {
            evt.consume(); // Evitar que se escriban caracteres no alfabéticos ni numéricos, o más de 8 caracteres
        }
    }//GEN-LAST:event_txtUsuarioKeyTyped

    private void txtUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUsuarioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUsuarioActionPerformed

    private void jTextField25ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField25ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField25ActionPerformed

    
    
    
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SistemaBancarioLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SistemaBancarioLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SistemaBancarioLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SistemaBancarioLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SistemaBancarioLogin().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton DesactivarPrestamo;
    private javax.swing.JButton Modificar;
    private javax.swing.JFrame SistemaBancarioLogin;
    private javax.swing.JButton btnAplicarCambiosCliente;
    private javax.swing.JButton btnAplicarCambiosEmpleados;
    private javax.swing.JButton btnClientes;
    private javax.swing.JButton btnCrearCliente;
    private javax.swing.JButton btnCrearEmpleado;
    private javax.swing.JButton btnDeposito;
    private javax.swing.JButton btnEliminarCliente;
    private javax.swing.JButton btnEliminarEmpleado;
    private javax.swing.JButton btnEmpleados;
    private javax.swing.JButton btnIngresar1;
    private javax.swing.JButton btnModificarCliente;
    private javax.swing.JButton btnNuevaTransaccion;
    private javax.swing.JButton btnNuevoCliente;
    private javax.swing.JButton btnNuevoEmpleado;
    private javax.swing.JButton btnNuevoPrestamo;
    private javax.swing.JButton btnPrestamos;
    private javax.swing.JButton btnRealizarPrestamo;
    private javax.swing.JButton btnRetiro;
    private javax.swing.JButton btnTransacciones;
    private javax.swing.JComboBox<String> cboRolEmpleado;
    private javax.swing.JComboBox<String> cboTipoCuenta;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton17;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextField jTextField25;
    private javax.swing.JTextField jTextField26;
    private javax.swing.JTextField jTextField27;
    private javax.swing.JTextField jTextField28;
    private javax.swing.JPanel panelClientes;
    private javax.swing.JPanel panelDatos;
    private javax.swing.JPanel panelEmpleados;
    private javax.swing.JPanel panelLogin;
    private javax.swing.JPanel panelOpciones;
    private javax.swing.JPanel panelPagosPrestamos;
    private javax.swing.JPanel panelPrestamo;
    private javax.swing.JPanel panelTransacciones;
    private javax.swing.JTable tablaClientes;
    private javax.swing.JTable tableEmpleados;
    private javax.swing.JTable tablePrestamo;
    private javax.swing.JTextField txtApellidoCliente;
    private javax.swing.JTextField txtApellidoEmpleado;
    private javax.swing.JTextField txtCelularCliente;
    private javax.swing.JTextField txtCelularEmpleado;
    private javax.swing.JPasswordField txtContraseña;
    private javax.swing.JTextField txtContraseñaEmpleado;
    private javax.swing.JTextField txtDireccionCliente;
    private javax.swing.JTextField txtDniCliente;
    private javax.swing.JTextField txtDniEmpleado;
    private javax.swing.JTextField txtEmailCliente;
    private javax.swing.JTextField txtFechaInicioEmpleado;
    private javax.swing.JTextField txtFechaPrestamo;
    private javax.swing.JTextField txtFechaRegistroCliente;
    private javax.swing.JTextField txtMontoPrestamo;
    private javax.swing.JTextField txtMontoTransaccion;
    private javax.swing.JLabel txtNombre2;
    private javax.swing.JTextField txtNombreCliente;
    private javax.swing.JTextField txtNombreEmpleado;
    private javax.swing.JTextField txtNumeroCuentaCliente;
    private javax.swing.JTextField txtNumeroCuentaPrestamo;
    private javax.swing.JTextField txtNumeroCuentaTransaccion;
    private javax.swing.JTextField txtPlazoMesesPrestamo;
    private javax.swing.JLabel txtRol2;
    private javax.swing.JTextField txtSalarioEmpleado;
    private javax.swing.JTextField txtSaldoCliente;
    private javax.swing.JTextField txtUsuario;
    private javax.swing.JTextField txtUsuarioEmpleado;
    private javax.swing.JLabel txtVaucherEmpleadoID;
    private javax.swing.JLabel txtVaucherFecha;
    private javax.swing.JLabel txtVaucherMonto;
    private javax.swing.JLabel txtVaucherNombre;
    private javax.swing.JLabel txtVaucherNumeroCuenta;
    private javax.swing.JLabel txtVaucherOperacion;
    // End of variables declaration//GEN-END:variables
}
