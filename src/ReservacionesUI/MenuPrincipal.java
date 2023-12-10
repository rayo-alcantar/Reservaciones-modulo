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
 * Clase que representa el menú principal de la aplicación de reservaciones en
 * Neo Tokio. Permite acceder a las sucursales disponibles y al registro de
 * clientes.
 *
 * @author Pedro Quiroz
 */
public class MenuPrincipal extends javax.swing.JFrame {

    /**
     * Constructor que inicializa los componentes del formulario.
     */
    public MenuPrincipal() {
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

        jPanelMenuPrincipal = new javax.swing.JPanel();
        jLabelBienvenido = new javax.swing.JLabel();
        jLabelIconSushiRoll = new javax.swing.JLabel();
        jLabelSucursalesDisponibles = new javax.swing.JLabel();
        jButtonSucursalAllende = new javax.swing.JButton();
        jButtonSucursalGalerias = new javax.swing.JButton();
        jButtonSucursalEspacio = new javax.swing.JButton();
        jButtonSucursalExpoPlaza = new javax.swing.JButton();
        jButtonSucursalAvUniversidad = new javax.swing.JButton();
        jButtonSucursalVillaAsuncion = new javax.swing.JButton();
        jLabelMensajeRegistracionCliente = new javax.swing.JLabel();
        jButtonAbrirRegistroCliente = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanelMenuPrincipal.setBackground(new java.awt.Color(32, 17, 72));
        jPanelMenuPrincipal.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 204, 253), 3, true));

        jLabelBienvenido.setFont(new java.awt.Font("Barlow Condensed Light", 1, 36)); // NOI18N
        jLabelBienvenido.setForeground(new java.awt.Color(0, 204, 253));
        jLabelBienvenido.setText("Bienvenido al Sistema de Reservaciones NEO TOKIO");

        jLabelIconSushiRoll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/Logo Neo Tokio SushiRoll.png"))); // NOI18N

        jLabelSucursalesDisponibles.setFont(new java.awt.Font("Barlow Condensed Light", 1, 36)); // NOI18N
        jLabelSucursalesDisponibles.setForeground(new java.awt.Color(0, 204, 253));
        jLabelSucursalesDisponibles.setText("Sucursales Disponibles ");

        jButtonSucursalAllende.setBackground(new java.awt.Color(85, 231, 255));
        jButtonSucursalAllende.setFont(new java.awt.Font("Barlow Condensed Medium", 0, 48)); // NOI18N
        jButtonSucursalAllende.setForeground(new java.awt.Color(32, 17, 72));
        jButtonSucursalAllende.setText("Allende");
        jButtonSucursalAllende.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSucursalAllendeActionPerformed(evt);
            }
        });

        jButtonSucursalGalerias.setBackground(new java.awt.Color(85, 231, 255));
        jButtonSucursalGalerias.setFont(new java.awt.Font("Barlow Condensed Medium", 0, 48)); // NOI18N
        jButtonSucursalGalerias.setForeground(new java.awt.Color(32, 17, 72));
        jButtonSucursalGalerias.setText("Galerias");
        jButtonSucursalGalerias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSucursalGaleriasActionPerformed(evt);
            }
        });

        jButtonSucursalEspacio.setBackground(new java.awt.Color(85, 231, 255));
        jButtonSucursalEspacio.setFont(new java.awt.Font("Barlow Condensed Medium", 0, 48)); // NOI18N
        jButtonSucursalEspacio.setForeground(new java.awt.Color(32, 17, 72));
        jButtonSucursalEspacio.setText("Espacio");
        jButtonSucursalEspacio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSucursalEspacioActionPerformed(evt);
            }
        });

        jButtonSucursalExpoPlaza.setBackground(new java.awt.Color(85, 231, 255));
        jButtonSucursalExpoPlaza.setFont(new java.awt.Font("Barlow Condensed Medium", 0, 48)); // NOI18N
        jButtonSucursalExpoPlaza.setForeground(new java.awt.Color(32, 17, 72));
        jButtonSucursalExpoPlaza.setText("Expo Plaza");
        jButtonSucursalExpoPlaza.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSucursalExpoPlazaActionPerformed(evt);
            }
        });

        jButtonSucursalAvUniversidad.setBackground(new java.awt.Color(85, 231, 255));
        jButtonSucursalAvUniversidad.setFont(new java.awt.Font("Barlow Condensed Medium", 0, 36)); // NOI18N
        jButtonSucursalAvUniversidad.setForeground(new java.awt.Color(32, 17, 72));
        jButtonSucursalAvUniversidad.setText("Av. Universidad");
        jButtonSucursalAvUniversidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSucursalAvUniversidadActionPerformed(evt);
            }
        });

        jButtonSucursalVillaAsuncion.setBackground(new java.awt.Color(85, 231, 255));
        jButtonSucursalVillaAsuncion.setFont(new java.awt.Font("Barlow Condensed Medium", 0, 36)); // NOI18N
        jButtonSucursalVillaAsuncion.setForeground(new java.awt.Color(32, 17, 72));
        jButtonSucursalVillaAsuncion.setText("Villa Asuncion");
        jButtonSucursalVillaAsuncion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSucursalVillaAsuncionActionPerformed(evt);
            }
        });

        jLabelMensajeRegistracionCliente.setFont(new java.awt.Font("Barlow Condensed Light", 1, 20)); // NOI18N
        jLabelMensajeRegistracionCliente.setForeground(new java.awt.Color(0, 204, 253));
        jLabelMensajeRegistracionCliente.setText("Todavia no estas registrado en nuestro sistema?. Registrate ahora para poder reservar mesas y obtener premios con cada compra!");

        jButtonAbrirRegistroCliente.setBackground(new java.awt.Color(85, 231, 255));
        jButtonAbrirRegistroCliente.setFont(new java.awt.Font("Barlow Condensed Medium", 0, 24)); // NOI18N
        jButtonAbrirRegistroCliente.setForeground(new java.awt.Color(32, 17, 52));
        jButtonAbrirRegistroCliente.setText("¡Registrame!");
        jButtonAbrirRegistroCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAbrirRegistroClienteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelMenuPrincipalLayout = new javax.swing.GroupLayout(jPanelMenuPrincipal);
        jPanelMenuPrincipal.setLayout(jPanelMenuPrincipalLayout);
        jPanelMenuPrincipalLayout.setHorizontalGroup(
            jPanelMenuPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelMenuPrincipalLayout.createSequentialGroup()
                .addGroup(jPanelMenuPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButtonAbrirRegistroCliente)
                    .addGroup(jPanelMenuPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanelMenuPrincipalLayout.createSequentialGroup()
                            .addComponent(jLabelIconSushiRoll)
                            .addGroup(jPanelMenuPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanelMenuPrincipalLayout.createSequentialGroup()
                                    .addGap(68, 68, 68)
                                    .addComponent(jLabelBienvenido))
                                .addGroup(jPanelMenuPrincipalLayout.createSequentialGroup()
                                    .addGap(251, 251, 251)
                                    .addComponent(jLabelSucursalesDisponibles))))
                        .addGroup(jPanelMenuPrincipalLayout.createSequentialGroup()
                            .addGap(158, 158, 158)
                            .addGroup(jPanelMenuPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jButtonSucursalExpoPlaza, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jButtonSucursalAllende, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(18, 18, 18)
                            .addGroup(jPanelMenuPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanelMenuPrincipalLayout.createSequentialGroup()
                                    .addComponent(jButtonSucursalGalerias, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(jButtonSucursalEspacio, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanelMenuPrincipalLayout.createSequentialGroup()
                                    .addComponent(jButtonSucursalAvUniversidad, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(jButtonSucursalVillaAsuncion, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGroup(jPanelMenuPrincipalLayout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(jLabelMensajeRegistracionCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 947, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(79, Short.MAX_VALUE))
        );
        jPanelMenuPrincipalLayout.setVerticalGroup(
            jPanelMenuPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelMenuPrincipalLayout.createSequentialGroup()
                .addGroup(jPanelMenuPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelIconSushiRoll)
                    .addGroup(jPanelMenuPrincipalLayout.createSequentialGroup()
                        .addComponent(jLabelBienvenido)
                        .addGap(42, 42, 42)
                        .addComponent(jLabelSucursalesDisponibles)))
                .addGap(53, 53, 53)
                .addGroup(jPanelMenuPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonSucursalAllende, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonSucursalGalerias, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonSucursalEspacio, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(79, 79, 79)
                .addGroup(jPanelMenuPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonSucursalExpoPlaza, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanelMenuPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButtonSucursalAvUniversidad, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButtonSucursalVillaAsuncion, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(68, 68, 68)
                .addComponent(jLabelMensajeRegistracionCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonAbrirRegistroCliente)
                .addContainerGap(30, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelMenuPrincipal, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelMenuPrincipal, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonSucursalAllendeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSucursalAllendeActionPerformed
        // TODO add your handling code here
        // Obtener una conexión a la base de datos utilizando la clase ConexionBD
        Connection connection = ConexionBD.getConnection();

        // Definir la consulta SQL para contar la cantidad de mesas disponibles
        String query = "SELECT COUNT(*) AS estado FROM mesa "
                + "WHERE idSucursal = 1 AND idMesa BETWEEN 1 AND 10 AND estado = 1";
        try (
                // Crear un objeto PreparedStatement para ejecutar la consulta SQL
                PreparedStatement preparedStatement = connection.prepareStatement(query); // Ejecutar la consulta y obtener un conjunto de resultados
                 ResultSet resultSet = preparedStatement.executeQuery()) {
            // Verificar si hay resultados en el conjunto
            if (resultSet.next()) {
                // Obtener el valor del contador (estado) desde el conjunto de resultados
                int availableTables = resultSet.getInt("estado");

                // Verificar si hay mesas disponibles
                if (availableTables > 0) {
                    // Si hay mesas disponibles, abrir la ventana de la sucursal Allende
                    Sucursal_Allende sucursalAllende = new Sucursal_Allende();
                    sucursalAllende.setVisible(true);
                } else {
                    // Si no hay mesas disponibles, mostrar un mensaje emergente
                    JOptionPane.showMessageDialog(null, "No hay mesas disponibles en esta sucursal, porfavor selecciona otra o intenta mas tarde.", "Sin Mesas Disponibles", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } catch (SQLException e) {
            // Capturar y manejar excepciones de SQL, mostrando un mensaje emergente con la traza de la excepción
            JOptionPane.showMessageDialog(null, "Error al acceder a la base de datos:\n" + e.getMessage(), "Error de Base de Datos", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }//GEN-LAST:event_jButtonSucursalAllendeActionPerformed

    private void jButtonAbrirRegistroClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAbrirRegistroClienteActionPerformed
        // TODO add your handling code here:
        // Acción al hacer clic en el botón para abrir el registro de cliente.
        // Crear una instancia de RegistroCliente
        RegistroCliente registroCliente = new RegistroCliente();

        // Hacer visible la nueva ventana
        registroCliente.setVisible(true);
    }//GEN-LAST:event_jButtonAbrirRegistroClienteActionPerformed

    private void jButtonSucursalGaleriasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSucursalGaleriasActionPerformed
        // TODO add your handling code here:
        // Obtener una conexión a la base de datos utilizando la clase ConexionBD
        Connection connection = ConexionBD.getConnection();

        // Definir la consulta SQL para contar la cantidad de mesas disponibles
        String query = "SELECT COUNT(*) AS estado FROM mesa "
                + "WHERE idSucursal = 2 AND idMesa BETWEEN 11 AND 20 AND estado = 1";
        try (
                // Crear un objeto PreparedStatement para ejecutar la consulta SQL
                PreparedStatement preparedStatement = connection.prepareStatement(query); // Ejecutar la consulta y obtener un conjunto de resultados
                 ResultSet resultSet = preparedStatement.executeQuery()) {
            // Verificar si hay resultados en el conjunto
            if (resultSet.next()) {
                // Obtener el valor del contador (estado) desde el conjunto de resultados
                int availableTables = resultSet.getInt("estado");

                // Verificar si hay mesas disponibles
                if (availableTables > 0) {
                    // Si hay mesas disponibles, abrir la ventana de la sucursal Galerias
                    Sucursal_Galerias sucursalGalerias = new Sucursal_Galerias();
                    sucursalGalerias.setVisible(true);
                } else {
                    // Si no hay mesas disponibles, mostrar un mensaje emergente
                    JOptionPane.showMessageDialog(null, "No hay mesas disponibles en esta sucursal, porfavor selecciona otra o intenta mas tarde.", "Sin Mesas Disponibles", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } catch (SQLException e) {
            // Capturar y manejar excepciones de SQL, mostrando un mensaje emergente con la traza de la excepción
            JOptionPane.showMessageDialog(null, "Error al acceder a la base de datos:\n" + e.getMessage(), "Error de Base de Datos", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }//GEN-LAST:event_jButtonSucursalGaleriasActionPerformed

    private void jButtonSucursalEspacioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSucursalEspacioActionPerformed
        // TODO add your handling code here:
        // Obtener una conexión a la base de datos utilizando la clase ConexionBD
        Connection connection = ConexionBD.getConnection();

        // Definir la consulta SQL para contar la cantidad de mesas disponibles
        String query = "SELECT COUNT(*) AS estado FROM mesa "
                + "WHERE idSucursal = 3 AND idMesa BETWEEN 21 AND 30 AND estado = 1";
        try (
                // Crear un objeto PreparedStatement para ejecutar la consulta SQL
                PreparedStatement preparedStatement = connection.prepareStatement(query); // Ejecutar la consulta y obtener un conjunto de resultados
                 ResultSet resultSet = preparedStatement.executeQuery()) {
            // Verificar si hay resultados en el conjunto
            if (resultSet.next()) {
                // Obtener el valor del contador (estado) desde el conjunto de resultados
                int availableTables = resultSet.getInt("estado");

                // Verificar si hay mesas disponibles
                if (availableTables > 0) {
                    // Si hay mesas disponibles, abrir la ventana de la sucursal Espacio
                    Sucursal_Espacio sucursalEspacio = new Sucursal_Espacio();
                    sucursalEspacio.setVisible(true);
                } else {
                    // Si no hay mesas disponibles, mostrar un mensaje emergente
                    JOptionPane.showMessageDialog(null, "No hay mesas disponibles en esta sucursal, porfavor selecciona otra o intenta mas tarde.", "Sin Mesas Disponibles", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } catch (SQLException e) {
            // Capturar y manejar excepciones de SQL, mostrando un mensaje emergente con la traza de la excepción
            JOptionPane.showMessageDialog(null, "Error al acceder a la base de datos:\n" + e.getMessage(), "Error de Base de Datos", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }//GEN-LAST:event_jButtonSucursalEspacioActionPerformed

    private void jButtonSucursalExpoPlazaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSucursalExpoPlazaActionPerformed
        // TODO add your handling code here:
        // Acción al hacer clic en el botón de la sucursal  ExpoPlaza, se mostrara la ventana de la sucursal ExpoPlaza.
        // Obtener una conexión a la base de datos utilizando la clase ConexionBD
        Connection connection = ConexionBD.getConnection();

        // Definir la consulta SQL para contar la cantidad de mesas disponibles
        String query = "SELECT COUNT(*) AS estado FROM mesa "
                + "WHERE idSucursal = 4 AND idMesa BETWEEN 31 AND 40 AND estado = 1";
        try (
                // Crear un objeto PreparedStatement para ejecutar la consulta SQL
                PreparedStatement preparedStatement = connection.prepareStatement(query); // Ejecutar la consulta y obtener un conjunto de resultados
                 ResultSet resultSet = preparedStatement.executeQuery()) {
            // Verificar si hay resultados en el conjunto
            if (resultSet.next()) {
                // Obtener el valor del contador (estado) desde el conjunto de resultados
                int availableTables = resultSet.getInt("estado");

                // Verificar si hay mesas disponibles
                if (availableTables > 0) {
                    // Si hay mesas disponibles, abrir la ventana de la sucursal Expo Plaza
                    Sucursal_ExpoPlaza sucursalExpoplaza = new Sucursal_ExpoPlaza();
                    sucursalExpoplaza.setVisible(true);
                } else {
                    // Si no hay mesas disponibles, mostrar un mensaje emergente
                    JOptionPane.showMessageDialog(null, "No hay mesas disponibles en esta sucursal, porfavor selecciona otra o intenta mas tarde.", "Sin Mesas Disponibles", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } catch (SQLException e) {
            // Capturar y manejar excepciones de SQL, mostrando un mensaje emergente con la traza de la excepción
            JOptionPane.showMessageDialog(null, "Error al acceder a la base de datos:\n" + e.getMessage(), "Error de Base de Datos", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }//GEN-LAST:event_jButtonSucursalExpoPlazaActionPerformed

    private void jButtonSucursalAvUniversidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSucursalAvUniversidadActionPerformed
        // TODO add your handling code here:
        // Obtener una conexión a la base de datos utilizando la clase ConexionBD
        Connection connection = ConexionBD.getConnection();

        // Definir la consulta SQL para contar la cantidad de mesas disponibles
        String query = "SELECT COUNT(*) AS estado FROM mesa "
                + "WHERE idSucursal = 5 AND idMesa BETWEEN 41 AND 50 AND estado = 1";
        try (
                // Crear un objeto PreparedStatement para ejecutar la consulta SQL
                PreparedStatement preparedStatement = connection.prepareStatement(query); // Ejecutar la consulta y obtener un conjunto de resultados
                 ResultSet resultSet = preparedStatement.executeQuery()) {
            // Verificar si hay resultados en el conjunto
            if (resultSet.next()) {
                // Obtener el valor del contador (estado) desde el conjunto de resultados
                int availableTables = resultSet.getInt("estado");

                // Verificar si hay mesas disponibles
                if (availableTables > 0) {
                    // Si hay mesas disponibles, abrir la ventana de la sucursal Av Universidad
                    Sucursal_AvUniversidad sucursalAvuniversidad = new Sucursal_AvUniversidad();
                    sucursalAvuniversidad.setVisible(true);
                } else {
                    // Si no hay mesas disponibles, mostrar un mensaje emergente
                    JOptionPane.showMessageDialog(null, "No hay mesas disponibles en esta sucursal, porfavor selecciona otra o intenta mas tarde.", "Sin Mesas Disponibles", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } catch (SQLException e) {
            // Capturar y manejar excepciones de SQL, mostrando un mensaje emergente con la traza de la excepción
            JOptionPane.showMessageDialog(null, "Error al acceder a la base de datos:\n" + e.getMessage(), "Error de Base de Datos", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }//GEN-LAST:event_jButtonSucursalAvUniversidadActionPerformed

    private void jButtonSucursalVillaAsuncionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSucursalVillaAsuncionActionPerformed
        // TODO add your handling code here:
        // Acción al hacer clic en el botón de la sucursal Villa Asuncion, se mostrara la ventana de la sucursal Villa Asuncion.
        // Obtener una conexión a la base de datos utilizando la clase ConexionBD
        Connection connection = ConexionBD.getConnection();

        // Definir la consulta SQL para contar la cantidad de mesas disponibles
        String query = "SELECT COUNT(*) AS estado FROM mesa "
                + "WHERE idSucursal = 6 AND idMesa BETWEEN 51 AND 60 AND estado = 1";
        try (
                // Crear un objeto PreparedStatement para ejecutar la consulta SQL
                PreparedStatement preparedStatement = connection.prepareStatement(query); // Ejecutar la consulta y obtener un conjunto de resultados
                 ResultSet resultSet = preparedStatement.executeQuery()) {
            // Verificar si hay resultados en el conjunto
            if (resultSet.next()) {
                // Obtener el valor del contador (estado) desde el conjunto de resultados
                int availableTables = resultSet.getInt("estado");

                // Verificar si hay mesas disponibles
                if (availableTables > 0) {
                    // Si hay mesas disponibles, abrir la ventana de la sucursal Villa Asuncion
                   Sucursal_VillaAsuncion sucursalVillaasuncion = new Sucursal_VillaAsuncion();
                   sucursalVillaasuncion.setVisible(true);
                } else {
                    // Si no hay mesas disponibles, mostrar un mensaje emergente
                    JOptionPane.showMessageDialog(null, "No hay mesas disponibles en esta sucursal, porfavor selecciona otra o intenta mas tarde.", "Sin Mesas Disponibles", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } catch (SQLException e) {
            // Capturar y manejar excepciones de SQL, mostrando un mensaje emergente con la traza de la excepción
            JOptionPane.showMessageDialog(null, "Error al acceder a la base de datos:\n" + e.getMessage(), "Error de Base de Datos", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }//GEN-LAST:event_jButtonSucursalVillaAsuncionActionPerformed

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
    private javax.swing.JButton jButtonAbrirRegistroCliente;
    private javax.swing.JButton jButtonSucursalAllende;
    private javax.swing.JButton jButtonSucursalAvUniversidad;
    private javax.swing.JButton jButtonSucursalEspacio;
    private javax.swing.JButton jButtonSucursalExpoPlaza;
    private javax.swing.JButton jButtonSucursalGalerias;
    private javax.swing.JButton jButtonSucursalVillaAsuncion;
    private javax.swing.JLabel jLabelBienvenido;
    private javax.swing.JLabel jLabelIconSushiRoll;
    private javax.swing.JLabel jLabelMensajeRegistracionCliente;
    private javax.swing.JLabel jLabelSucursalesDisponibles;
    private javax.swing.JPanel jPanelMenuPrincipal;
    // End of variables declaration//GEN-END:variables
}
