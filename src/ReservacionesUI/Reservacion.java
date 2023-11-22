package ReservacionesUI;

import conexion.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * Poner el numero de sucursal donde se hizo la reservacion
 * Hacer mas sencillo el registro de los clientes
 * Corregir Shushi por Sushi en el logo
 * Ponernos deacuerdo con los colores para las demas interfaces de los otros equipos
 * Hacer un diseño del restaurante( poner donde estan los baños, la cocina, la entrada) en el mapa de las mesas
 * 
 * @author Pedro Quiroz
 */
public class Reservacion extends javax.swing.JFrame {

    private DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private DateFormat horaFormat = new SimpleDateFormat("HH:mm");
    private final int idMesa;
    private List<Integer> selectedTables;

    /**
     * Creates new form Reservacion
     */
    public Reservacion(int idMesa) {
        this.idMesa = idMesa;
        initComponents();
        
    }

    Reservacion() {
        this.idMesa = 0; // Provide a default value or update as needed
        initComponents();
       setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }
    
    private void MensajeReservacion(){
        
        Date date = new Date();
        String fecha = dateFormat.format(date);
        
        
        Date horario = new Date();
        String hora = horaFormat.format(horario);
        
        JOptionPane.showMessageDialog(this, "Reservación realizada con éxito. \nFecha Reservada: "+fecha+" Hora Reservada: "+hora+" \nTiene 20 minutos antes de que su reservación expire", "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }

    public void setMesas(List<Integer> selectedTables) {
        this.selectedTables = selectedTables;
    }
    
     private void realizarReservacion() {
        try {
            Connection connection = ConexionBD.getConnection();

            // Iterate through selected tables and perform reservation logic
            for (Integer mesa : selectedTables) {
                // You should add your logic here to update the estado of the mesa in the database
                // For demonstration purposes, let's assume a simple update query
                String updateSql = "UPDATE mesa SET estado = ? WHERE idMesa = ?";
                PreparedStatement updateStatement = connection.prepareStatement(updateSql);
                updateStatement.setInt(1, 2); // Assuming 2 represents the reserved state
                updateStatement.setInt(2, mesa);
                updateStatement.executeUpdate();
            }

            // Display a message or perform any additional logic as needed
            MensajeReservacion();
            // Optionally, you might want to close the current frame after reservation
            this.dispose();

        } catch (SQLException e) {
            // Handle database-related exceptions
            e.printStackTrace(); // You might want to handle exceptions more gracefully in a real application
            JOptionPane.showMessageDialog(this, "Error en la reserva, por favor inténtalo de nuevo.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

     public int consultarIDC(){
         int idc=0;
         try {
             /*TELEFONO: 449283748392*/
            
            String nombre = new String(txtNombre.getText());
            String Apellidos = new String(txtApellidos.getText());
            String telefono = new String(txtTelefono.getText());
            String Correo = new String(txtCorreo.getText());
            Connection connection = ConexionBD.getConnection();
            String sql = "SELECT idCliente FROM cliente WHERE nombre = ? AND apellidos = ? AND  telefono = ? AND correo = ? ";
            PreparedStatement statement = connection.prepareStatement(sql);
            
            statement.setString(1, nombre);
            statement.setString(2, Apellidos);
            statement.setString(3, telefono);
            statement.setString(4, Correo);
            ResultSet rs = statement.executeQuery();
            
            if (rs.next()) {
                idc = rs.getInt("idCliente");
                
            realizarReservacion();
            
            String sql2 = "INSERT INTO reservacion (idCliente, fecha, hora, idMesa) VALUES (?,?,?,?) ";
            PreparedStatement statement2 = connection.prepareStatement(sql2);
            Date date = new Date();
            String fecha = dateFormat.format(date);
            Date horario = new Date();
            String hora = horaFormat.format(horario);
            
            statement.setString(1, fecha);
            statement.setString(2, hora);
            ResultSet rs2 = statement.executeQuery();
            
            
            }else{
                JOptionPane.showMessageDialog(this, "No se encontro el cliente, verifique los datos", "Error", JOptionPane.ERROR_MESSAGE);

            }
            
            
            

        } catch (SQLException e) {
             System.out.println(e);
        }
         
         
         return idc;
     }
     
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabelApellidosReservacion = new javax.swing.JLabel();
        txtApellidos = new javax.swing.JTextField();
        jLabelCorreoReservacion = new javax.swing.JLabel();
        txtCorreo = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        lblRegresarReservacionBotonImagen = new javax.swing.JLabel();
        lblTituloReservacion = new javax.swing.JLabel();
        jLabelCorreoReservacion1 = new javax.swing.JLabel();
        txtTelefono = new javax.swing.JTextField();
        jLabelReservacionNombre = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabelApellidosReservacion.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabelApellidosReservacion.setText("Apellidos:");

        txtApellidos.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N

        jLabelCorreoReservacion.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabelCorreoReservacion.setText("Correo:");

        txtCorreo.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N

        jButton1.setFont(new java.awt.Font("Century Gothic", 0, 24)); // NOI18N
        jButton1.setText("RESERVAR");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        lblRegresarReservacionBotonImagen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/BotonRegresar.png"))); // NOI18N
        lblRegresarReservacionBotonImagen.setText("Regresar");
        lblRegresarReservacionBotonImagen.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblRegresarReservacionBotonImagenMouseClicked(evt);
            }
        });

        lblTituloReservacion.setFont(new java.awt.Font("Century Gothic", 0, 36)); // NOI18N
        lblTituloReservacion.setText("Reservación de Mesa(s)");

        jLabelCorreoReservacion1.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabelCorreoReservacion1.setText("Telefono:");

        txtTelefono.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N

        jLabelReservacionNombre.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabelReservacionNombre.setText("Nombre:");

        txtNombre.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        txtNombre.setName("txtNombre"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblRegresarReservacionBotonImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblTituloReservacion)
                        .addGap(0, 26, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabelReservacionNombre)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabelCorreoReservacion)
                                    .addComponent(jLabelApellidosReservacion)
                                    .addComponent(jLabelCorreoReservacion1))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtApellidos)
                                    .addComponent(txtCorreo)
                                    .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(layout.createSequentialGroup()
                .addGap(164, 164, 164)
                .addComponent(jButton1)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblRegresarReservacionBotonImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblTituloReservacion)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelReservacionNombre)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtApellidos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelApellidosReservacion))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelCorreoReservacion))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelCorreoReservacion1))
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lblRegresarReservacionBotonImagenMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblRegresarReservacionBotonImagenMouseClicked
        // TODO add your handling code here:
        this.setVisible(false);
    }//GEN-LAST:event_lblRegresarReservacionBotonImagenMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        
        
        consultarIDC();
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(Reservacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Reservacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Reservacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Reservacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Reservacion().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabelApellidosReservacion;
    private javax.swing.JLabel jLabelCorreoReservacion;
    private javax.swing.JLabel jLabelCorreoReservacion1;
    private javax.swing.JLabel jLabelReservacionNombre;
    private javax.swing.JLabel lblRegresarReservacionBotonImagen;
    private javax.swing.JLabel lblTituloReservacion;
    private javax.swing.JTextField txtApellidos;
    private javax.swing.JTextField txtCorreo;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtTelefono;
    // End of variables declaration//GEN-END:variables

}
