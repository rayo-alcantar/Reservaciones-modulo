package ReservacionesUI;

// import necesaria para la conexión a la base de datos
import conexion.ConexionBD;

// imports necesarios para interactuar con bases de datos mediante JDBC
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// imports necesarios para trabajar con componentes gráficos específicos de Swing
import javax.swing.JOptionPane;

/**
 *
 * @author Pedro Quiroz
 */
public class RegistroCliente extends javax.swing.JFrame {

    /**
     * Constructor que inicializa la interfaz gráfica para el registro de
     * clientes.
     */
    public RegistroCliente() {
        initComponents();
    }

    /**
     * Método para agregar un nuevo cliente a la base de datos. Obtiene la
     * conexión a la base de datos y valida los datos del cliente antes de
     * realizar la inserción.
     *
     */
    private void setNuevoCliente() {
        // Obtener la conexión a la base de datos
        Connection connection = ConexionBD.getConnection();

        // Obtener valores de los campos de texto
        String nombre = jTextFieldNombreCliente.getText().toUpperCase();
        String apellidos = jTextFieldApellidosCliente.getText().toUpperCase();
        String telefono = jTextFieldTelefonoCliente.getText();
        String correo = jTextFieldEmailCliente.getText().toUpperCase();

        // Validar nombre y apellidos (permitir solo letras)
        if (!nombre.matches("[a-zA-Z]+") || !apellidos.matches("[a-zA-Z]+")) {
            JOptionPane.showMessageDialog(this, "Solo se permiten letras en los campos para el Nombre y Apellido", "Error de Validación", JOptionPane.ERROR_MESSAGE);
            return; // Salir del método si la validación falla
        }

        // Validar teléfono (permitir solo números)
        if (!telefono.matches("[0-9]+")) {
            JOptionPane.showMessageDialog(this, "Solo se permiten números (0 al 9) para el campo de Teléfono", "Error de Validación", JOptionPane.ERROR_MESSAGE);
            return; // Salir del método si la validación falla
        }

        // Verificar si todos los campos de texto tienen datos válidos
        if (nombre.isEmpty() || apellidos.isEmpty() || telefono.isEmpty() || correo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos deben ser completados", "Error de Validación", JOptionPane.ERROR_MESSAGE);
            return; // Salir del método si la validación falla
        }

        // Consulta SQL para insertar en la tabla 'cliente'
        String insertQuery = "INSERT INTO cliente (nombre, apellidos, telefono, correo) VALUES (?, ?, ?, ?)";

        try {
            // Crear una declaración preparada
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);

            // Establecer valores para los parámetros en la declaración preparada
            preparedStatement.setString(1, nombre);
            preparedStatement.setString(2, apellidos);
            preparedStatement.setString(3, telefono);
            preparedStatement.setString(4, correo);

            // Ejecutar la consulta
            int rowsAffected = preparedStatement.executeUpdate();

            // Cerrar la declaración preparada
            preparedStatement.close();

            // connection.commit();
            if (rowsAffected > 0) {
                // Si se afectaron filas, la inserción fue exitosa
                JOptionPane.showMessageDialog(this, "Cliente registrado exitosamente", "Registro Exitoso", JOptionPane.INFORMATION_MESSAGE);

                // Mostrar información sobre el cliente recién registrado
                displayClientInfo(connection, nombre, apellidos, telefono, correo);

                // Limpiar campos de texto
                jTextFieldNombreCliente.setText("");
                jTextFieldApellidosCliente.setText("");
                jTextFieldTelefonoCliente.setText("");
                jTextFieldEmailCliente.setText("");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // Manejar la excepción SQL según sea necesario
        }
    }

    /**
     * Método para mostrar la información de un cliente registrado.
     *
     * @param connection La conexión a la base de datos.
     * @param nombre Nombre del cliente.
     * @param apellidos Apellidos del cliente.
     * @param telefono Teléfono del cliente.
     * @param correo Correo electrónico del cliente.
     */
    private void displayClientInfo(Connection connection, String nombre, String apellidos, String telefono, String correo) {
        // Consulta para recuperar información sobre el cliente recién registrado
        String selectQuery = "SELECT * FROM cliente WHERE nombre = ? AND apellidos = ? AND telefono = ? AND correo = ?";

        try {
            // Crear una declaración preparada
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);

            // Establecer valores para los parámetros en la declaración preparada
            preparedStatement.setString(1, nombre);
            preparedStatement.setString(2, apellidos);
            preparedStatement.setString(3, telefono);
            preparedStatement.setString(4, correo);

            // Ejecutar la consulta
            ResultSet resultSet = preparedStatement.executeQuery();

            // Mostrar información sobre el cliente recién registrado
            if (resultSet.next()) {
                int idCliente = resultSet.getInt("idCliente");
                JOptionPane.showMessageDialog(this, "Información del Cliente:\nID: " + idCliente + "\nNombre: " + nombre
                        + "\nApellidos: " + apellidos + "\nTeléfono: " + telefono + "\nCorreo: " + correo, "Información del Cliente", JOptionPane.INFORMATION_MESSAGE);
            }

            // Cerrar el conjunto de resultados y la declaración preparada
            resultSet.close();
            preparedStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
            // Manejar la excepción SQL según sea necesario
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelRegistroCliente = new javax.swing.JPanel();
        jButtonRegresarRegistroCliente = new javax.swing.JButton();
        jTextFieldTelefonoCliente = new javax.swing.JTextField();
        jLabelNombreCliente = new javax.swing.JLabel();
        jLabelApellidosCliente = new javax.swing.JLabel();
        jLabelEmailCliente = new javax.swing.JLabel();
        jLabelTelefonoCliente = new javax.swing.JLabel();
        jTextFieldNombreCliente = new javax.swing.JTextField();
        jTextFieldApellidosCliente = new javax.swing.JTextField();
        jTextFieldEmailCliente = new javax.swing.JTextField();
        jLabelLogoNeoTokio = new javax.swing.JLabel();
        jLabelReservacionMesasTitulo = new javax.swing.JLabel();
        jButtonRegistrarCliente = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanelRegistroCliente.setBackground(new java.awt.Color(32, 17, 72));
        jPanelRegistroCliente.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(85, 231, 255), 5, true));

        jButtonRegresarRegistroCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/BotonRegresar.png"))); // NOI18N
        jButtonRegresarRegistroCliente.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jButtonRegresarRegistroCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRegresarRegistroClienteActionPerformed(evt);
            }
        });

        jTextFieldTelefonoCliente.setFont(new java.awt.Font("Barlow Condensed Medium", 0, 28)); // NOI18N

        jLabelNombreCliente.setBackground(new java.awt.Color(85, 231, 255));
        jLabelNombreCliente.setFont(new java.awt.Font("Barlow Condensed Medium", 0, 30)); // NOI18N
        jLabelNombreCliente.setForeground(new java.awt.Color(85, 231, 255));
        jLabelNombreCliente.setText("Nombre :");

        jLabelApellidosCliente.setBackground(new java.awt.Color(85, 231, 255));
        jLabelApellidosCliente.setFont(new java.awt.Font("Barlow Condensed Medium", 0, 30)); // NOI18N
        jLabelApellidosCliente.setForeground(new java.awt.Color(85, 231, 255));
        jLabelApellidosCliente.setText("Apellidos :");

        jLabelEmailCliente.setBackground(new java.awt.Color(85, 231, 255));
        jLabelEmailCliente.setFont(new java.awt.Font("Barlow Condensed Medium", 0, 30)); // NOI18N
        jLabelEmailCliente.setForeground(new java.awt.Color(85, 231, 255));
        jLabelEmailCliente.setText("E-Mail :");

        jLabelTelefonoCliente.setBackground(new java.awt.Color(85, 231, 255));
        jLabelTelefonoCliente.setFont(new java.awt.Font("Barlow Condensed Medium", 0, 30)); // NOI18N
        jLabelTelefonoCliente.setForeground(new java.awt.Color(85, 231, 255));
        jLabelTelefonoCliente.setText("Telefono :");

        jTextFieldNombreCliente.setFont(new java.awt.Font("Barlow Condensed Medium", 0, 28)); // NOI18N

        jTextFieldApellidosCliente.setFont(new java.awt.Font("Barlow Condensed Medium", 0, 28)); // NOI18N

        jTextFieldEmailCliente.setFont(new java.awt.Font("Barlow Condensed Medium", 0, 28)); // NOI18N

        jLabelLogoNeoTokio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/Logo Neo Tokio.png"))); // NOI18N
        jLabelLogoNeoTokio.setText("jLabel1");

        jLabelReservacionMesasTitulo.setFont(new java.awt.Font("Bahiana", 2, 48)); // NOI18N
        jLabelReservacionMesasTitulo.setForeground(new java.awt.Color(0, 204, 253));
        jLabelReservacionMesasTitulo.setText("registro de clientes");

        jButtonRegistrarCliente.setFont(new java.awt.Font("Barlow Condensed Medium", 0, 36)); // NOI18N
        jButtonRegistrarCliente.setText("Registrar Cliente");
        jButtonRegistrarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRegistrarClienteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelRegistroClienteLayout = new javax.swing.GroupLayout(jPanelRegistroCliente);
        jPanelRegistroCliente.setLayout(jPanelRegistroClienteLayout);
        jPanelRegistroClienteLayout.setHorizontalGroup(
            jPanelRegistroClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelRegistroClienteLayout.createSequentialGroup()
                .addComponent(jButtonRegresarRegistroCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(166, 166, 166)
                .addComponent(jLabelReservacionMesasTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanelRegistroClienteLayout.createSequentialGroup()
                .addGap(66, 66, 66)
                .addGroup(jPanelRegistroClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabelTelefonoCliente)
                    .addComponent(jLabelEmailCliente)
                    .addComponent(jLabelApellidosCliente)
                    .addComponent(jLabelNombreCliente))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelRegistroClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButtonRegistrarCliente)
                    .addGroup(jPanelRegistroClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jTextFieldTelefonoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextFieldEmailCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextFieldApellidosCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextFieldNombreCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabelLogoNeoTokio, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelRegistroClienteLayout.setVerticalGroup(
            jPanelRegistroClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelRegistroClienteLayout.createSequentialGroup()
                .addGroup(jPanelRegistroClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonRegresarRegistroCliente)
                    .addGroup(jPanelRegistroClienteLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabelReservacionMesasTitulo)))
                .addGroup(jPanelRegistroClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelRegistroClienteLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelRegistroClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldNombreCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelNombreCliente))
                        .addGap(18, 18, 18)
                        .addGroup(jPanelRegistroClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldApellidosCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelApellidosCliente))
                        .addGap(18, 18, 18)
                        .addGroup(jPanelRegistroClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldEmailCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelEmailCliente))
                        .addGap(18, 18, 18)
                        .addGroup(jPanelRegistroClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldTelefonoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelTelefonoCliente)))
                    .addGroup(jPanelRegistroClienteLayout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabelLogoNeoTokio)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButtonRegistrarCliente)
                .addGap(0, 19, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelRegistroCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelRegistroCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonRegresarRegistroClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRegresarRegistroClienteActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_jButtonRegresarRegistroClienteActionPerformed

    private void jButtonRegistrarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRegistrarClienteActionPerformed
        // TODO add your handling code here:
        setNuevoCliente();
    }//GEN-LAST:event_jButtonRegistrarClienteActionPerformed

    /**
     * @param args the command line arguments
     */
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
            java.util.logging.Logger.getLogger(RegistroCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RegistroCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RegistroCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RegistroCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RegistroCliente().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonRegistrarCliente;
    private javax.swing.JButton jButtonRegresarRegistroCliente;
    private javax.swing.JLabel jLabelApellidosCliente;
    private javax.swing.JLabel jLabelEmailCliente;
    private javax.swing.JLabel jLabelLogoNeoTokio;
    private javax.swing.JLabel jLabelNombreCliente;
    private javax.swing.JLabel jLabelReservacionMesasTitulo;
    private javax.swing.JLabel jLabelTelefonoCliente;
    private javax.swing.JPanel jPanelRegistroCliente;
    private javax.swing.JTextField jTextFieldApellidosCliente;
    private javax.swing.JTextField jTextFieldEmailCliente;
    private javax.swing.JTextField jTextFieldNombreCliente;
    private javax.swing.JTextField jTextFieldTelefonoCliente;
    // End of variables declaration//GEN-END:variables
}
