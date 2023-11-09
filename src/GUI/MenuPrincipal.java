package GUI;

import conexion.ConexionBD;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Pedro Quiroz
 */
public class MenuPrincipal extends javax.swing.JFrame {
    /**
     * Creates new form MenuPrincipal
     */
     private DefaultListModel<String> restaurantListModel;

    public MenuPrincipal() {
        initComponents();
        restaurantListModel = new DefaultListModel<>();
        lstRestaurantes.setModel(restaurantListModel);
        fetchSucursalData(); // Populate the JList with data from the database
        addListSelectionListener();
    }

    private void fetchSucursalData() {
        ConexionBD conexion = new ConexionBD(); // Create an instance of ConexionBD

        try {
            // Establish the database connection
            conexion.getConnection(); // You may need to call the appropriate method in ConexionBD

            // Create a SQL statement
            Statement statement = conexion.getConnection().createStatement();

            // Execute a query to retrieve the restaurant names
            String query = "SELECT nombre FROM sucursal";
            ResultSet resultSet = statement.executeQuery(query);

            // Clear the existing items in the JList
            restaurantListModel.clear();

            // Add retrieved data to the JList
            while (resultSet.next()) {
                String restaurantName = resultSet.getString("nombre");
                restaurantListModel.addElement(restaurantName);
            }

            // Close the result set and statement (connection should be managed by ConexionBD)
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close the database connection in the ConexionBD class
            conexion.getConnection(); // You may need to call the appropriate method in ConexionBD
        }
    }

    private void addListSelectionListener() {
        lstRestaurantes.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) { // Double-click
                    String selectedRestaurant = lstRestaurantes.getSelectedValue();
                    openRestaurantDetailsWindow(selectedRestaurant);
                }
            }
        });
    }

    private void openRestaurantDetailsWindow(String restaurantName) {
        JFrame detailsFrame = new JFrame(restaurantName + " Details");

        // Create a JTable to display tables
        JTable table = new JTable();

        // Define the table model to hold the table data
        DefaultTableModel tableModel = new DefaultTableModel();

        // Add columns to the table model
        tableModel.addColumn("Mesa");
        tableModel.addColumn("Capacidad");
        tableModel.addColumn("Area");
        tableModel.addColumn("Estado");

        // Fetch table data for the selected restaurant
        fetchTableData(restaurantName, tableModel);

        // Set the table model
        table.setModel(tableModel);

        // Create a scroll pane for the table
        JScrollPane tableScrollPane = new JScrollPane(table);

        detailsFrame.add(tableScrollPane);
        detailsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        detailsFrame.setSize(400, 300);
        detailsFrame.setVisible(true);
    }

   private void fetchTableData(String restaurantName, DefaultTableModel tableModel) {
    ConexionBD conexion = new ConexionBD(); // Create an instance of ConexionBD

    try {
        // Establish the database connection
        conexion.getConnection(); // You may need to call the appropriate method in ConexionBD

        // Create a SQL statement
        Statement statement = conexion.getConnection().createStatement();

        // Execute a query to retrieve the table data for the selected restaurant
        String query = "SELECT idMesa, capacidad, area, estado FROM mesa WHERE idSucursal = " +
                "(SELECT idSucursal FROM sucursal WHERE nombre = '" + restaurantName + "')";
        ResultSet resultSet = statement.executeQuery(query);

        // Add retrieved data to the table model
        while (resultSet.next()) {
            String tableNumber = resultSet.getString("idMesa");
            int capacity = resultSet.getInt("capacidad");
            String area = resultSet.getString("area");
            int estado = resultSet.getInt("estado");
            String status = (estado == 1) ? "Disponible" : (estado == 2) ? "Ocupado" : "Unknown"; // Translation
            tableModel.addRow(new Object[]{tableNumber, capacity, area, status});
        }

        // Close the result set and statement (connection should be managed by ConexionBD)
        resultSet.close();
        statement.close();
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        // Close the database connection in the ConexionBD class
        conexion.getConnection(); // You may need to call the appropriate method in ConexionBD
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

        jScrollPane1 = new javax.swing.JScrollPane();
        lstRestaurantes = new javax.swing.JList<>();
        lblRestaurantes = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lstRestaurantes.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(lstRestaurantes);

        lblRestaurantes.setText("Restaurantes");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblRestaurantes)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(524, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(lblRestaurantes)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(239, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(MenuPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MenuPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MenuPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MenuPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MenuPrincipal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblRestaurantes;
    private javax.swing.JList<String> lstRestaurantes;
    // End of variables declaration//GEN-END:variables
}
