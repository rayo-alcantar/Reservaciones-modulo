package ReservacionesUI;

// import necesaria para la conexión a la base de datos
import conexion.ConexionBD;

// imports necesarios para la generacion de archivos PDF
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

// imports necesarios para interactuar con bases de datos mediante JDBC
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// imports necesarios para trabajar con componentes gráficos específicos de Swing
import javax.swing.JOptionPane;

// imports para obtener la Fecha y Hora actual que tiene el equipo donde se encuentra este programa
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// import de estructuras de datos para almacenar y manipular listas de elementos
import java.util.Arrays;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;

/**
 * Clase que representa la interfaz de registro de reservaciones en Neo Tokio.
 * Permite al usuario registrar una reservación asociada a un cliente y mesas
 * específicas.
 *
 * @author Pedro Quiroz
 */
public class RegistroReservacion extends javax.swing.JFrame {

    private Integer[] idMesas;
    private Integer idCliente;

    /**
     * Constructor que inicializa la interfaz de registro de reservaciones.
     *
     * @param idMesas Arreglo de identificadores de las mesas seleccionadas para
     * la reservación.
     */
    public RegistroReservacion(Integer[] idMesas) {
        initComponents();
        this.idMesas = idMesas;

        System.out.print("idMesa recibidas: ");
        for (Integer idMesa : idMesas) {
            System.out.print(idMesa + " ");
        }
        System.out.println();
    }

    private RegistroReservacion() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    /**
     * Método privado que retorna la fecha y hora actual en un formato
     * específico.
     *
     * @return String que representa la fecha y hora formateadas.
     */
    private String getFechaYHora() {
        // Obtiene la fecha y hora actual
        LocalDateTime currentDateTime = LocalDateTime.now();

        // Formatea la fecha y hora usando un patrón específico
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = currentDateTime.format(formatter);

        return formattedDateTime;
    }

    /**
     * Método privado que obtiene el ID del cliente a partir de los datos
     * ingresados en la interfaz.
     *
     * @return Integer que representa el ID del cliente encontrado, o null si no
     * se encuentra.
     */
    private Integer getIDCliente() {
        // Obtiene los valores de los JTextFields
        String nombre = jTextFieldNombreReservacion.getText().toUpperCase();
        String apellidos = jTextFieldApellidosReservacion.getText().toUpperCase();
        String telefono = jTextFieldTelefonoReservacion.getText();
        String correo = jTextFieldEmailReservacion.getText().toUpperCase();

        // Validar nombre y apellidos (permitir solo letras)
        if (!nombre.matches("[a-zA-Z\\s]+") || !apellidos.matches("[a-zA-Z\\s]+")) {
            JOptionPane.showMessageDialog(null, "Solo se permiten letras en los campos para el Nombre y Apellido", "Error de Validación", JOptionPane.ERROR_MESSAGE);
            clearTextFields();
            return null; // Salir del método si la validación falla
        }

        // Validar teléfono (permitir solo números)
        if (!telefono.matches("[0-9]+")) {
            JOptionPane.showMessageDialog(null, "Solo se permiten números (0 al 9) para el campo de Teléfono", "Error de Validación", JOptionPane.ERROR_MESSAGE);
            clearTextFields();
            return null; // Salir del método si la validación falla
        }

        // Llama al método getIdCliente para buscar el ID del cliente
        Integer idCliente = getIdCliente(nombre, apellidos, telefono, correo);

        if (idCliente != null) {
            // El cliente existe, se puede usar idCliente para operaciones adicionales
            System.out.println("Cliente encontrado con id: " + idCliente);
        } else {
            // El cliente no existe, muestra un mensaje de error
            JOptionPane.showMessageDialog(null, "Error, no se encuentra ningún cliente registrado con esos datos.", "Error", JOptionPane.ERROR_MESSAGE);
            System.out.println("Cliente no encontrado.");

            // Limpia todos los JTextFields
            clearTextFields();
        }

        // Llama al método getIdCliente para buscar el ID del cliente
        return idCliente;
    }

    // Método para limpiar todos los JTextFields
    private void clearTextFields() {
        jTextFieldNombreReservacion.setText("");
        jTextFieldApellidosReservacion.setText("");
        jTextFieldEmailReservacion.setText("");
        jTextFieldTelefonoReservacion.setText("");
    }

    /**
     * Método privado que busca el ID del cliente en la base de datos.
     *
     * @param nombre Nombre del cliente.
     * @param apellidos Apellidos del cliente.
     * @param correo Correo electrónico del cliente.
     * @param telefono Número de teléfono del cliente.
     * @return Integer que representa el ID del cliente encontrado, o null si no
     * se encuentra.
     */
    private Integer getIdCliente(String nombre, String apellidos, String telefono, String correo) {
        Connection connection = ConexionBD.getConnection();
        String query = "SELECT idCliente FROM cliente WHERE nombre = ? AND apellidos = ? AND telefono = ? AND correo = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, nombre);
            statement.setString(2, apellidos);
            statement.setString(3, telefono);
            statement.setString(4, correo);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("idCliente");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConexionBD.closeConnection(connection);
        }

        return null; // Retorna null si no se encuentra el cliente
    }

    /**
     * Método privado que realiza el registro de la reservación en la base de
     * datos.
     *
     * @param idCliente ID del cliente asociado a la reservación.
     * @param idMesas Arreglo de IDs de las mesas seleccionadas para la
     * reservación.
     */
    private void generarYAbrirPDFReservacion(Integer idCliente, Integer[] idMesas, String fechaYHora, String nombreCliente, String apellidosCliente, String telefonoCliente, String nombreSucursal) {
        String filename = "reservacion_" + idCliente + ".pdf";

        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.beginText();
                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 14);
                contentStream.setLeading(14.5f);
                contentStream.newLineAtOffset(25, 700);

                contentStream.showText("Detalles de la Reservación:");
                contentStream.newLine();
                contentStream.showText("Folio de Reservación: " + idCliente); // Asumiendo que el ID del cliente es el folio
                contentStream.newLine();
                contentStream.showText("Nombre del Cliente: " + nombreCliente);
                contentStream.newLine();
                contentStream.showText("Apellidos del Cliente: " + apellidosCliente);
                contentStream.newLine();
                contentStream.showText("Teléfono del Cliente: " + telefonoCliente);
                contentStream.newLine();
                contentStream.showText("Sucursal: " + nombreSucursal);
                contentStream.newLine();
                contentStream.showText("Mesas Reservadas: " + Arrays.toString(idMesas));
                contentStream.newLine();
                contentStream.showText("Fecha y Hora: " + fechaYHora);
                contentStream.newLine();

                contentStream.endText();
            }

            document.save(filename);

            // Abrir el PDF generado
            if (Desktop.isDesktopSupported()) {
                try {
                    File pdfFile = new File(filename);
                    Desktop.getDesktop().open(pdfFile);
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(null, "Error al abrir el archivo PDF.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "No se puede abrir el archivo PDF en este sistema.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al generar el archivo PDF.", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void setReservacion(Integer idCliente, Integer[] idMesas) {
        // Obtiene la fecha y hora formateadas
        String fechaYHora = getFechaYHora();

        // Conexión a la base de datos
        Connection connection = ConexionBD.getConnection();
        String nombreCliente = "";
        String apellidosCliente = "";
        String telefonoCliente = "";
        String nombreSucursal = "";

        // Consulta para obtener los datos del cliente
        String queryCliente = "SELECT nombre, apellidos, telefono FROM cliente WHERE idCliente = ?";
        try (PreparedStatement statementCliente = connection.prepareStatement(queryCliente)) {
            statementCliente.setInt(1, idCliente);
            ResultSet resultSet = statementCliente.executeQuery();
            if (resultSet.next()) {
                nombreCliente = resultSet.getString("nombre");
                apellidosCliente = resultSet.getString("apellidos");
                telefonoCliente = resultSet.getString("telefono");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Consulta para obtener el nombre de la sucursal para cada idMesa en el array
        String querySucursal = "SELECT s.nombre FROM sucursal s JOIN mesa m ON s.idSucursal = m.idSucursal WHERE m.idMesa = ?";
        for (Integer idMesa : idMesas) {
            try (PreparedStatement statementSucursal = connection.prepareStatement(querySucursal)) {
                statementSucursal.setInt(1, idMesa); 
                ResultSet resultSetSucursal = statementSucursal.executeQuery();
                if (resultSetSucursal.next()) {
                    nombreSucursal = resultSetSucursal.getString("nombre");
                    System.out.println("Nombre de sucursal para idMesa " + idMesa + ": " + nombreSucursal);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // Consulta de INSERT para la reservación
        String insertQuery = "INSERT INTO reservacion (idCliente, fecha, hora, idMesa) VALUES (?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            // Establece los parámetros para la consulta de INSERT
            preparedStatement.setInt(1, idCliente);
            preparedStatement.setString(2, fechaYHora.split(" ")[0]); // Insert solo para la fecha
            preparedStatement.setString(3, fechaYHora.split(" ")[1]); // Insert solo para la hora

            for (Integer idMesa : idMesas) {
                preparedStatement.setInt(4, idMesa);

                // Ejecuta la consulta de INSERT para cada idMesa
                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Reservación para la mesa " + idMesa + " registrada correctamente.");

                    // Llama el metodo encargado de hacer el UPDATE en la tabla mesa después que se confirma la reservacion con el usuario
                    setEstadoMesa(idMesa);
                } else {
                    System.out.println("Error al registrar la reservación para la mesa " + idMesa + ".");
                }
            }
            // Llamada al método para generar y abrir el PDF
            generarYAbrirPDFReservacion(idCliente, idMesas, fechaYHora, nombreCliente, apellidosCliente, telefonoCliente, nombreSucursal);
            // Calcula la fecha y hora límite para llegar al restaurante (fecha y hora actual + 20 minutos)
            LocalDateTime limiteDateTime = LocalDateTime.now().plusMinutes(20);

            // Formatea la fecha y hora límite usando un patrón específico
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String limiteFormattedDateTime = limiteDateTime.format(formatter);

            // Muestra un cuadro de diálogo de éxito después del INSERT exitoso
            String successMessage = "Reservación exitosa para mesa(s): " + Arrays.toString(idMesas)
                    + ". La fecha y hora límite para llegar al restaurante es: " + limiteFormattedDateTime;
            JOptionPane.showMessageDialog(null, successMessage, "Reservación Exitosa", JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConexionBD.closeConnection(connection);
        }
    }

    private void setEstadoMesa(Integer idMesa) {
        // Conexión a la base de datos
        Connection connection = ConexionBD.getConnection();

        // Consulta de UPDATE para cambiar el estado de la mesa (Estado 2 es para indicar que esta ocupada)
        String queryMesaOcupada = "UPDATE mesa SET estado = 2 WHERE idMesa = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryMesaOcupada)) {
            // Establece el parámetro para la consulta de UPDATE
            preparedStatement.setInt(1, idMesa);

            // Ejecuta la consulta para hacer el UPDATE en la tabla mesa
            int rowsAffected = preparedStatement.executeUpdate();

            // Mensajes en consola para verificar que la consulta se ejecuto sin problemas
            if (rowsAffected > 0) {
                System.out.println("Estado de la mesa " + idMesa + " actualizado a ocupado.");
            } else {
                System.out.println("Error al actualizar el estado de la mesa" + idMesa);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConexionBD.closeConnection(connection);
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

        jPanelReservacion = new javax.swing.JPanel();
        jTextFieldNombreReservacion = new javax.swing.JTextField();
        jTextFieldApellidosReservacion = new javax.swing.JTextField();
        jTextFieldEmailReservacion = new javax.swing.JTextField();
        jTextFieldTelefonoReservacion = new javax.swing.JTextField();
        jLabelNombreReservacion = new javax.swing.JLabel();
        jLabelApellidosReservacion = new javax.swing.JLabel();
        jLabelEmailReservacion = new javax.swing.JLabel();
        jLabelTelefonoReservacion = new javax.swing.JLabel();
        jButtonRegresarRegistroReservacion = new javax.swing.JButton();
        jLabelLogoNeoTokio = new javax.swing.JLabel();
        jButtonRegistrarReservacion = new javax.swing.JButton();
        jLabelReservacionMesasTitulo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanelReservacion.setBackground(new java.awt.Color(32, 17, 72));
        jPanelReservacion.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(85, 231, 255), 5, true));

        jTextFieldNombreReservacion.setFont(new java.awt.Font("Barlow Condensed Medium", 0, 28)); // NOI18N

        jTextFieldApellidosReservacion.setFont(new java.awt.Font("Barlow Condensed Medium", 0, 28)); // NOI18N

        jTextFieldEmailReservacion.setFont(new java.awt.Font("Barlow Condensed Medium", 0, 28)); // NOI18N

        jTextFieldTelefonoReservacion.setFont(new java.awt.Font("Barlow Condensed Medium", 0, 28)); // NOI18N

        jLabelNombreReservacion.setBackground(new java.awt.Color(85, 231, 255));
        jLabelNombreReservacion.setFont(new java.awt.Font("Barlow Condensed Medium", 0, 30)); // NOI18N
        jLabelNombreReservacion.setForeground(new java.awt.Color(85, 231, 255));
        jLabelNombreReservacion.setText("Nombre :");

        jLabelApellidosReservacion.setBackground(new java.awt.Color(85, 231, 255));
        jLabelApellidosReservacion.setFont(new java.awt.Font("Barlow Condensed Medium", 0, 30)); // NOI18N
        jLabelApellidosReservacion.setForeground(new java.awt.Color(85, 231, 255));
        jLabelApellidosReservacion.setText("Apellidos :");

        jLabelEmailReservacion.setBackground(new java.awt.Color(85, 231, 255));
        jLabelEmailReservacion.setFont(new java.awt.Font("Barlow Condensed Medium", 0, 30)); // NOI18N
        jLabelEmailReservacion.setForeground(new java.awt.Color(85, 231, 255));
        jLabelEmailReservacion.setText("E-Mail :");

        jLabelTelefonoReservacion.setBackground(new java.awt.Color(85, 231, 255));
        jLabelTelefonoReservacion.setFont(new java.awt.Font("Barlow Condensed Medium", 0, 30)); // NOI18N
        jLabelTelefonoReservacion.setForeground(new java.awt.Color(85, 231, 255));
        jLabelTelefonoReservacion.setText("Telefono :");

        jButtonRegresarRegistroReservacion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/BotonRegresar.png"))); // NOI18N
        jButtonRegresarRegistroReservacion.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jButtonRegresarRegistroReservacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRegresarRegistroReservacionActionPerformed(evt);
            }
        });

        jLabelLogoNeoTokio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/Logo Neo Tokio.png"))); // NOI18N
        jLabelLogoNeoTokio.setText("jLabel1");

        jButtonRegistrarReservacion.setFont(new java.awt.Font("Barlow Condensed Medium", 0, 36)); // NOI18N
        jButtonRegistrarReservacion.setText("Registrar Reservacion");
        jButtonRegistrarReservacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRegistrarReservacionActionPerformed(evt);
            }
        });

        jLabelReservacionMesasTitulo.setFont(new java.awt.Font("Bahiana", 2, 48)); // NOI18N
        jLabelReservacionMesasTitulo.setForeground(new java.awt.Color(0, 204, 253));
        jLabelReservacionMesasTitulo.setText("Reservación de Mesas para");

        javax.swing.GroupLayout jPanelReservacionLayout = new javax.swing.GroupLayout(jPanelReservacion);
        jPanelReservacion.setLayout(jPanelReservacionLayout);
        jPanelReservacionLayout.setHorizontalGroup(
            jPanelReservacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelReservacionLayout.createSequentialGroup()
                .addGroup(jPanelReservacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelReservacionLayout.createSequentialGroup()
                        .addComponent(jButtonRegresarRegistroReservacion, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(154, 154, 154)
                        .addComponent(jLabelReservacionMesasTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 368, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelReservacionLayout.createSequentialGroup()
                        .addGap(62, 62, 62)
                        .addGroup(jPanelReservacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanelReservacionLayout.createSequentialGroup()
                                .addGroup(jPanelReservacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabelApellidosReservacion)
                                    .addComponent(jLabelNombreReservacion))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanelReservacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextFieldApellidosReservacion, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextFieldNombreReservacion, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanelReservacionLayout.createSequentialGroup()
                                .addComponent(jLabelTelefonoReservacion)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextFieldTelefonoReservacion, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanelReservacionLayout.createSequentialGroup()
                                .addComponent(jLabelEmailReservacion)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextFieldEmailReservacion, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(32, 32, 32)
                        .addComponent(jLabelLogoNeoTokio, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelReservacionLayout.createSequentialGroup()
                        .addGap(247, 247, 247)
                        .addComponent(jButtonRegistrarReservacion)))
                .addContainerGap(95, Short.MAX_VALUE))
        );
        jPanelReservacionLayout.setVerticalGroup(
            jPanelReservacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelReservacionLayout.createSequentialGroup()
                .addGroup(jPanelReservacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelReservacionLayout.createSequentialGroup()
                        .addComponent(jButtonRegresarRegistroReservacion)
                        .addGap(10, 10, 10))
                    .addComponent(jLabelReservacionMesasTitulo, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGroup(jPanelReservacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelReservacionLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelLogoNeoTokio))
                    .addGroup(jPanelReservacionLayout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addGroup(jPanelReservacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldNombreReservacion, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelNombreReservacion))
                        .addGap(18, 18, 18)
                        .addGroup(jPanelReservacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldApellidosReservacion, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelApellidosReservacion))
                        .addGap(19, 19, 19)
                        .addGroup(jPanelReservacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldTelefonoReservacion, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelTelefonoReservacion))
                        .addGap(18, 18, 18)
                        .addGroup(jPanelReservacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldEmailReservacion, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelEmailReservacion))))
                .addGap(18, 18, 18)
                .addComponent(jButtonRegistrarReservacion)
                .addContainerGap(37, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanelReservacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelReservacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Método llamado al hacer clic en el botón "Regresar" de la interfaz de
     * registro de reservaciones. Cierra la ventana actual de registro de
     * reservaciones.
     *
     * @param evt Evento de acción generado al hacer clic en el botón
     * "Regresar".
     */
    private void jButtonRegresarRegistroReservacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRegresarRegistroReservacionActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_jButtonRegresarRegistroReservacionActionPerformed

    /**
     * Método llamado al hacer clic en el botón "Registrar Reservación" de la
     * interfaz de registro de reservaciones. Realiza las acciones necesarias
     * para registrar la reservación, incluida la obtención del ID del cliente y
     * la inserción en la base de datos.
     *
     * @param evt Evento de acción generado al hacer clic en el botón "Registrar
     * Reservación".
     */
    private void jButtonRegistrarReservacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRegistrarReservacionActionPerformed
        // TODO add your handling code here:
        // Llama a getIDCliente para obtener el idCliente
        idCliente = getIDCliente();

        // Verifica si idCliente no es nulo
        if (idCliente != null) {
            // Llama a setReservacion para insertar la reservación
            setReservacion(idCliente, idMesas);
        }
    }//GEN-LAST:event_jButtonRegistrarReservacionActionPerformed

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
            java.util.logging.Logger.getLogger(RegistroReservacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RegistroReservacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RegistroReservacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RegistroReservacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RegistroReservacion().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonRegistrarReservacion;
    private javax.swing.JButton jButtonRegresarRegistroReservacion;
    private javax.swing.JLabel jLabelApellidosReservacion;
    private javax.swing.JLabel jLabelEmailReservacion;
    private javax.swing.JLabel jLabelLogoNeoTokio;
    private javax.swing.JLabel jLabelNombreReservacion;
    private javax.swing.JLabel jLabelReservacionMesasTitulo;
    private javax.swing.JLabel jLabelTelefonoReservacion;
    private javax.swing.JPanel jPanelReservacion;
    private javax.swing.JTextField jTextFieldApellidosReservacion;
    private javax.swing.JTextField jTextFieldEmailReservacion;
    private javax.swing.JTextField jTextFieldNombreReservacion;
    private javax.swing.JTextField jTextFieldTelefonoReservacion;
    // End of variables declaration//GEN-END:variables
}
