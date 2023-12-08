package ReservacionesUI;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pedro
 */
public class Modulos extends javax.swing.JFrame {

    /**
     * Creates new form Modulos
     */
    public Modulos() {
        initComponents();
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
        jButtonModuloReservaciones = new javax.swing.JButton();
        jButtonModuloComanda = new javax.swing.JButton();
        jButtonModuloIngredientes = new javax.swing.JButton();
        jLabelBienvenido = new javax.swing.JLabel();
        jLabelIconSushiRoll = new javax.swing.JLabel();
        jLabelModulosDisponibles = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(32, 17, 72));

        jButtonModuloReservaciones.setBackground(new java.awt.Color(85, 231, 255));
        jButtonModuloReservaciones.setFont(new java.awt.Font("Barlow Condensed Medium", 0, 36)); // NOI18N
        jButtonModuloReservaciones.setText("Reservaciones");
        jButtonModuloReservaciones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonModuloReservacionesActionPerformed(evt);
            }
        });

        jButtonModuloComanda.setBackground(new java.awt.Color(81, 231, 255));
        jButtonModuloComanda.setFont(new java.awt.Font("Barlow Condensed Medium", 0, 36)); // NOI18N
        jButtonModuloComanda.setText("Comandas");
        jButtonModuloComanda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonModuloComandaActionPerformed(evt);
            }
        });

        jButtonModuloIngredientes.setBackground(new java.awt.Color(85, 231, 255));
        jButtonModuloIngredientes.setFont(new java.awt.Font("Barlow Condensed Medium", 0, 36)); // NOI18N
        jButtonModuloIngredientes.setText("Ingredientes");
        jButtonModuloIngredientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonModuloIngredientesActionPerformed(evt);
            }
        });

        jLabelBienvenido.setFont(new java.awt.Font("Barlow Condensed Light", 1, 36)); // NOI18N
        jLabelBienvenido.setForeground(new java.awt.Color(0, 204, 253));
        jLabelBienvenido.setText("Bienvenido al Sistema NEO TOKIO");

        jLabelIconSushiRoll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/Logo Neo Tokio SushiRoll.png"))); // NOI18N

        jLabelModulosDisponibles.setFont(new java.awt.Font("Barlow Condensed Light", 1, 36)); // NOI18N
        jLabelModulosDisponibles.setForeground(new java.awt.Color(0, 204, 253));
        jLabelModulosDisponibles.setText("A que modulo deseas acceder?");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabelIconSushiRoll)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(95, 95, 95)
                                .addComponent(jLabelModulosDisponibles))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(80, 80, 80)
                                .addComponent(jLabelBienvenido))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addComponent(jButtonModuloReservaciones, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButtonModuloComanda, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButtonModuloIngredientes, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(53, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabelBienvenido)
                        .addGap(35, 35, 35)
                        .addComponent(jLabelModulosDisponibles))
                    .addComponent(jLabelIconSushiRoll))
                .addGap(83, 83, 83)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonModuloReservaciones, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonModuloIngredientes, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonModuloComanda, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(150, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonModuloComandaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonModuloComandaActionPerformed
        // TODO add your handling code here:
        
        Ventana321 moduloComanda = null;
        try {
            moduloComanda = new Ventana321(1,1);
        } catch (SQLException ex) {
            Logger.getLogger(Modulos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(Modulos.class.getName()).log(Level.SEVERE, null, ex);
        }
        moduloComanda.setVisible(true);

    }//GEN-LAST:event_jButtonModuloComandaActionPerformed

    private void jButtonModuloReservacionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonModuloReservacionesActionPerformed
        // TODO add your handling code here:
        MenuPrincipal menuPrincipal = new MenuPrincipal();
        menuPrincipal.setVisible(true);
    }//GEN-LAST:event_jButtonModuloReservacionesActionPerformed

    private void jButtonModuloIngredientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonModuloIngredientesActionPerformed
        // TODO add your handling code here:
        InterfazCocina interfazCocina = new InterfazCocina();
        interfazCocina.setVisible(true);
    }//GEN-LAST:event_jButtonModuloIngredientesActionPerformed

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
            java.util.logging.Logger.getLogger(Modulos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Modulos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Modulos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Modulos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Modulos().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonModuloComanda;
    private javax.swing.JButton jButtonModuloIngredientes;
    private javax.swing.JButton jButtonModuloReservaciones;
    private javax.swing.JLabel jLabelBienvenido;
    private javax.swing.JLabel jLabelIconSushiRoll;
    private javax.swing.JLabel jLabelModulosDisponibles;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
