package GUI;

import conexion.ConexionBD;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author Pedro Quiroz
 */
public class Mesas extends javax.swing.JFrame {

    private String selectedRestaurant;

    /**
     * Creates new form Mesas
     */
    public Mesas(String selectedRestaurant) {
        initComponents();
        this.selectedRestaurant = selectedRestaurant;
        jLabel1.setText("Sucursal Seleccionada: " + selectedRestaurant);
        setMesaImage();
        addImageClickListener();
    }

    private Mesas() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void setMesaImage() {
        Connection connection = ConexionBD.getConnection();

        try ( Connection conn = connection;  PreparedStatement stmt = conn.prepareStatement("SELECT estado FROM mesa WHERE idSucursal = ?");) {
            int idSucursal = getIdSucursal(selectedRestaurant);
            stmt.setInt(1, idSucursal);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int estado = rs.getInt("estado");

                switch (estado) {
                    case 1:
                        setMesaImageIcon("Disponible.png", jLabelMesa1);
                        setMesaImageIcon("Ocupado.png", jLabelMesa2);
                        break;
                    case 2:
                        setMesaImageIcon("Ocupado.png", jLabelMesa1);
                        setMesaImageIcon("EnProceso.png", jLabelMesa2);
                        break;
                    // Mas Cases si es que se ocupa
                    default:
                        break;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setMesaImageIcon(String imageName, javax.swing.JLabel mesaLabel) {
        String imagePath = "/Recursos/" + imageName;
        java.net.URL imageURL = getClass().getResource(imagePath);

        mesaLabel.setIcon(new javax.swing.ImageIcon(imageURL));
    }

    private void setMesaImageIcon(String imageName) {
        String imagePath = "/Recursos/" + imageName;
        java.net.URL imageURL = getClass().getResource(imagePath);

        javax.swing.JLabel mesaImageLabel = new javax.swing.JLabel();
        mesaImageLabel.setIcon(new javax.swing.ImageIcon(imageURL));

        jLabelMesa1.setIcon(new javax.swing.ImageIcon(imageURL));
    }

    private void addImageClickListener() {
        jLabelMesa1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //Checar el estado de la mesa
                if (isImageState("Disponible.png", jLabelMesa1)) {
                    showConfirmationDialog();
                } else {
                    openReservacion();
                }
            }
        });

        jLabelMesa2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //Checar el estado de la mesa
                if (isImageState("Disponible.png", jLabelMesa2)) {
                    showConfirmationDialog();
                } else {
                    openReservacion();
                }
            }
        });
    }

    private void openReservacion() {
        //Cargar el JFrame para reservar
        Reservación reservacionFrame = new Reservación(selectedRestaurant);
        reservacionFrame.setVisible(true);
    }

    private boolean isImageState(String imageName, JLabel mesaLabel) {
        //Obtener el estado de la mesa, con el nombre de la imagen
        return imageName.equals("Disponible.png") && mesaLabel.getIcon() != null && mesaLabel.getIcon().toString().endsWith(imageName);
    }

    private void showConfirmationDialog() {
        //Mostrar dialogo de confirmacion
        int option = JOptionPane.showConfirmDialog(Mesas.this,
                "Hacer reservaciones para esta mesa?",
                "Confirmacion", JOptionPane.YES_NO_OPTION);

        if (option == JOptionPane.YES_OPTION) {
            openReservacion();
        }
    }

    private int getIdSucursal(String selectedRestaurant) {
        Connection connection = ConexionBD.getConnection();

        try ( Connection conn = connection;  PreparedStatement stmt = conn.prepareStatement("SELECT idSucursal FROM sucursal WHERE nombre = ?");) {
            stmt.setString(1, selectedRestaurant);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("idSucursal");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lblRegresarBotonImagen = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabelMesa1 = new javax.swing.JLabel();
        jLabelMesa2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(51, 255, 255));
        setForeground(new java.awt.Color(102, 255, 255));

        jPanel1.setBackground(new java.awt.Color(102, 204, 255));

        lblRegresarBotonImagen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/BotonRegresar.png"))); // NOI18N
        lblRegresarBotonImagen.setText("Regresar");
        lblRegresarBotonImagen.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblRegresarBotonImagenMouseClicked(evt);
            }
        });

        jLabel1.setText("jLabel1");

        jLabelMesa1.setText("Mesa1");

        jLabelMesa2.setText("Mesa2");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblRegresarBotonImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(202, 202, 202)
                        .addComponent(jLabel1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(98, 98, 98)
                        .addComponent(jLabelMesa1)
                        .addGap(263, 263, 263)
                        .addComponent(jLabelMesa2)))
                .addContainerGap(413, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblRegresarBotonImagen)
                    .addComponent(jLabel1))
                .addGap(92, 92, 92)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelMesa1)
                    .addComponent(jLabelMesa2))
                .addContainerGap(370, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lblRegresarBotonImagenMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblRegresarBotonImagenMouseClicked
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_lblRegresarBotonImagenMouseClicked

    
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
            java.util.logging.Logger.getLogger(Mesas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Mesas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Mesas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Mesas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Mesas().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabelMesa1;
    private javax.swing.JLabel jLabelMesa2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblRegresarBotonImagen;
    // End of variables declaration//GEN-END:variables
}
