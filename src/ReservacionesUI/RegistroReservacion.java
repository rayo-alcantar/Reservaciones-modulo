package ReservacionesUI;

// import necesaria para la conexión a la base de datos
import conexion.ConexionBD;
import java.awt.Container;

// imports necesarios para la generacion de archivos PDF
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.commons.io.IOUtils;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

// imports necesarios para interactuar con bases de datos mediante JDBC
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// imports necesarios para trabajar con componentes gráficos específicos de Swing
import javax.swing.JOptionPane;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

// imports para obtener la Fecha y Hora actual que tiene el equipo donde se encuentra este programa
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// import de estructuras de datos para almacenar y manipular listas de elementos
import java.util.Arrays;

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
     *
     * El método utiliza la clase LocalDateTime para obtener la fecha y hora
     * actual. Luego, formatea esta información utilizando un patrón específico
     * definido por DateTimeFormatter ("yyyy-MM-dd HH:mm:ss"). La fecha y hora
     * formateadas se devuelven como una cadena de texto.
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
     *
     * El método obtiene los valores de los campos de texto en la interfaz que
     * contienen la información del cliente, como nombre, apellidos, teléfono y
     * correo. Realiza validaciones para asegurar que los campos cumplen con los
     * requisitos (por ejemplo, solo letras en los campos de nombre y apellidos,
     * y solo números en el campo de teléfono).
     *
     * Si la validación es exitosa, llama al método "getIdCliente" para buscar
     * el ID del cliente en la base de datos utilizando la información
     * proporcionada. Si el cliente existe, se muestra un mensaje en la consola
     * indicando que el cliente fue encontrado, y se devuelve el ID del cliente.
     *
     * Si el cliente no existe o hay errores de validación, se muestra un
     * mensaje de error en la interfaz y se devuelve null. Además, se imprime un
     * mensaje en la consola indicando que el cliente no fue encontrado.
     *
     * Al final, se llama al método "clearTextFields" para limpiar los campos de
     * texto en la interfaz.
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
     * Método privado que hacer la consulta para obtener el idCliente
     *
     * @param nombre Nombre del cliente
     * @param apellidos Apellidos del cliente
     * @param telefono Telefono del cliente
     * @param correo Correo electronico del cliente
     * @return Retorna el valor del idCliente si es que encontro algo
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
     * Método privado que obtiene los datos de la reservación para generar un
     * archivo PDF a manera de ticket para el usuario.
     *
     * @param idCliente Identificador del cliente en la base de datos.
     * @param idMesas Identificador de las mesas en la base de datos.
     *
     * El método realiza consultas a la base de datos para obtener información
     * relacionada con la sucursal, la reservación y el cliente, y luego utiliza
     * estos datos para generar un archivo PDF que sirve como ticket de
     * reservación. La información obtenida incluye detalles de la sucursal, la
     * reservación y del cliente.
     *
     */
    private void getDatosReservacion(Integer idCliente, Integer[] idMesas) {
        Connection connection = ConexionBD.getConnection();

        // String para datos de la sucursal
        String nombreSucursal = "";
        String direccionSucursal = "";
        String telefonoSucursal = "";

        // Strings para datos del cliente
        String nombreCliente = "";
        String apellidosCliente = "";
        String telefonoCliente = "";
        String correoCliente = "";

        // String para reservacion
        String folioReserva = "";
        String fechaReserva = "";
        String horaReserva = "";

        try {
            // Consulta que obtiene la informacion relacionada de la sucursal usando 
            // JOIN de las tablas sucursal y mesa con el idMesa que tenemos actualmente
            // en el array Integer [] idMesas
            String querySucursal = "SELECT s.nombre, s.direccion, s.telefono "
                    + "FROM mesa m "
                    + "JOIN sucursal s ON m.idSucursal = s.idSucursal "
                    + "WHERE m.idMesa = ? "
                    + "LIMIT 1";

            try (PreparedStatement stmtSucursal = connection.prepareStatement(querySucursal)) {
                stmtSucursal.setInt(1, idMesas[0]);
                ResultSet rsSucursal = stmtSucursal.executeQuery();

                if (rsSucursal.next()) {
                    nombreSucursal = rsSucursal.getString("nombre");
                    direccionSucursal = rsSucursal.getString("direccion");
                    telefonoSucursal = rsSucursal.getString("telefono");
                }
            }

            // Consulta que obtiene los datos de la reservacion usando el idCliente
            String queryReservacion = "SELECT FolioReserva, fecha, hora "
                    + "FROM reservacion "
                    + "WHERE idCliente = ? "
                    + "LIMIT 1";

            try (PreparedStatement stmtReservacion = connection.prepareStatement(queryReservacion)) {
                stmtReservacion.setInt(1, idCliente);
                ResultSet rsReservacion = stmtReservacion.executeQuery();

                if (rsReservacion.next()) {
                    folioReserva = rsReservacion.getString("FolioReserva");
                    fechaReserva = rsReservacion.getString("fecha");
                    horaReserva = rsReservacion.getString("hora");
                }
            }

            // Consulta para obtener los datos del cliente usando su identificador
            String queryCliente = "SELECT nombre, apellidos, telefono, correo "
                    + "FROM cliente "
                    + "WHERE idCliente = ? "
                    + "LIMIT 1";

            try (PreparedStatement stmtCliente = connection.prepareStatement(queryCliente)) {
                stmtCliente.setInt(1, idCliente);
                ResultSet rsCliente = stmtCliente.executeQuery();

                if (rsCliente.next()) {
                    nombreCliente = rsCliente.getString("nombre");
                    apellidosCliente = rsCliente.getString("apellidos");
                    telefonoCliente = rsCliente.getString("telefono");
                    correoCliente = rsCliente.getString("correo");
                }
            }

            // Llamado del metodo setPDF para generar un archivo PDF a manera de Ticket para el usuario
            setPDF(nombreSucursal, direccionSucursal, telefonoSucursal,
                    nombreCliente, apellidosCliente, telefonoCliente, correoCliente,
                    folioReserva, fechaReserva, horaReserva, idMesas);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConexionBD.closeConnection(connection);
        }
    }

    /**
     * Método privado que genera un archivo PDF como ticket de reservación con
     * la información proporcionada de la sucursal, la reservación y el cliente.
     *
     * @param nombreSucursal Nombre de la sucursal.
     * @param direccionSucursal Dirección de la sucursal.
     * @param telefonoSucursal Teléfono de la sucursal.
     * @param nombreCliente Nombre del cliente.
     * @param apellidosCliente Apellidos del cliente.
     * @param telefonoCliente Teléfono del cliente.
     * @param correoCliente Correo electrónico del cliente.
     * @param folioReserva Folio de la reservación.
     * @param fechaReserva Fecha de la reservación.
     * @param horaReserva Hora de la reservación.
     * @param idMesas Identificador de las mesas reservadas.
     *
     * El método utiliza la biblioteca Apache PDFBox para generar un documento
     * PDF con la información proporcionada. El PDF contiene detalles de la
     * sucursal, la reservación y del cliente, presentados de manera organizada
     * y legible.
     *
     * El archivo PDF se guarda en el sistema con el nombre "Ticket Neo Tokio
     * [idCliente].pdf". Además, se intenta abrir automáticamente el archivo PDF
     * generado en el visor predeterminado del sistema, y se manejan posibles
     * errores durante el proceso.
     *
     * En caso de excepción durante la generación del PDF o al intentar abrirlo,
     * se muestra un mensaje de error al usuario.
     */
    private void setPDF(String nombreSucursal, String direccionSucursal, String telefonoSucursal, String nombreCliente, String apellidosCliente, String telefonoCliente, String correoCliente,
            String folioReserva, String fechaReserva, String horaReserva, Integer[] idMesas) {
        // Nombre del archivo PDF a generar
        String filename = "Ticket Neo Tokio " + idCliente + ".pdf";

        try (PDDocument document = new PDDocument()) {
            // Crear una nueva página en el documento
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {

                // Configurar la imagen del encabezado del PDF
                setImagenPDF(document, page, contentStream, "/Recursos/NeoTokio.png");

                // Configuración de la fuente y posición para el contenido del PDF
                contentStream.beginText();
                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 14);
                contentStream.setLeading(14.5f);
                contentStream.newLineAtOffset(25, 700);

                // Agregar título al PDF
                contentStream.showText("Ticket de Reservación Neo Tokio");
                contentStream.newLine();
                contentStream.newLine();

                // Agregar detalles de la reservación al PDF
                contentStream.showText("***Detalles de la Reservacion***");
                contentStream.newLine();
                contentStream.showText("Folio de Reservación : " + folioReserva);
                contentStream.newLine();
                contentStream.showText("Sucursal : " + nombreSucursal);
                contentStream.newLine();
                contentStream.showText("Direccion : " + direccionSucursal);
                contentStream.newLine();
                contentStream.showText("Telefono : " + telefonoSucursal);
                contentStream.newLine();
                contentStream.showText("Mesas: " + Arrays.toString(idMesas));
                contentStream.newLine();
                contentStream.showText("Fecha : " + fechaReserva);
                contentStream.newLine();
                contentStream.showText("Hora : " + horaReserva);
                contentStream.newLine();
                contentStream.newLine();

                // Agregar detalles del cliente al PDF
                contentStream.showText("Detalles del Cliente");
                contentStream.newLine();
                contentStream.showText("Nombre(s) : " + nombreCliente);
                contentStream.newLine();
                contentStream.showText("Apellido(s) : " + apellidosCliente);
                contentStream.newLine();
                contentStream.showText("Telefono : " + telefonoCliente);
                contentStream.newLine();
                contentStream.showText("Correo : " + correoCliente);

                contentStream.endText();
            }

            // Guardar el documento PDF
            document.save(filename);

            // Cerrar el documento PDF
            document.close();

            // Intentar abrir el archivo PDF en el visor predeterminado del sistema
            if (Desktop.isDesktopSupported()) {
                try {
                    File pdfFile = new File(filename);
                    Desktop.getDesktop().open(pdfFile);
                } catch (IOException e) {
                    // Manejar el error al abrir el archivo PDF
                    JOptionPane.showMessageDialog(null, "Error al abrir el archivo PDF.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                // Mostrar un mensaje si no se puede abrir el archivo PDF en el sistema
                JOptionPane.showMessageDialog(null, "No se puede abrir el archivo PDF en este sistema.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            // Manejar el error al generar el archivo PDF
            JOptionPane.showMessageDialog(null, "Error al generar el archivo PDF.", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    /**
     * Método privado que establece una imagen en una página específica de un
     * documento PDF.
     *
     * @param document Documento PDF al que se agregará la imagen.
     * @param page Página en la que se colocará la imagen.
     * @param contentStream Flujo de contenido de la página.
     * @param imagePath Ruta de la imagen en el proyecto.
     *
     * El método lee la imagen desde el archivo especificado por la ruta
     * "imagePath" en el proyecto y la coloca en la esquina superior derecha de
     * la página actual del documento PDF.
     *
     * Si la imagen no se encuentra en la ruta proporcionada, se muestra un
     * mensaje en la consola de error y la ejecución del método se interrumpe.
     *
     * En caso de error al leer el archivo de imagen, se muestra un mensaje en
     * la consola de error y se imprime la traza de la excepción.
     */
    private void setImagenPDF(PDDocument document, PDPage page, PDPageContentStream contentStream, String imagePath) {
        try {
            // Obtener el flujo de entrada de la imagen desde la ruta especificada en el proyecto
            InputStream imageStream = getClass().getResourceAsStream(imagePath);

            // Verificar si el flujo de entrada de la imagen es nulo
            if (imageStream == null) {
                System.err.println("Image file not found: " + imagePath);
                return;
            }

            // Crear un objeto PDImageXObject a partir del flujo de entrada de la imagen
            PDImageXObject pdImage = PDImageXObject.createFromByteArray(document, IOUtils.toByteArray(imageStream), "image/png");

            // Colocar la imagen en la esquina superior derecha de la página
            contentStream.drawImage(pdImage, page.getMediaBox().getWidth() - 100, page.getMediaBox().getHeight() - 100, 100, 100);
        } catch (IOException e) {
            // Manejar el error al leer el archivo de imagen
            System.err.println("Error reading image file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Método privado que realiza una reservación para un cliente en mesas
     * específicas.
     *
     * @param idCliente Identificador del cliente en la base de datos.
     * @param idMesas Identificador de las mesas en la base de datos.
     *
     * El método realiza una consulta de INSERT en la base de datos para
     * registrar la reservación del cliente en las mesas seleccionadas.
     * Posteriormente, ejecuta una consulta de UPDATE en la tabla "mesa" para
     * actualizar el estado de las mesas después de confirmar la reservación con
     * el cliente.
     *
     * Además, calcula la fecha y hora límite para llegar al restaurante (fecha
     * y hora actual + 20 minutos), muestra un cuadro de diálogo de éxito
     * después del INSERT exitoso, y llama al método "getDatosReservacion" para
     * obtener y mostrar la información detallada de la reservación.
     *
     * En caso de excepción durante el proceso de reservación, se muestra la
     * traza de la excepción en la consola.
     */
    private void setReservacion(Integer idCliente, Integer[] idMesas) {
        // Obtiene la fecha y hora formateadas
        String fechaYHora = getFechaYHora();

        // Conexión a la base de datos
        Connection connection = ConexionBD.getConnection();

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

            // Calcula la fecha y hora límite para llegar al restaurante (fecha y hora actual + 20 minutos)
            LocalDateTime limiteDateTime = LocalDateTime.now().plusMinutes(20);

            // Formatea la fecha y hora límite usando un patrón específico
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String limiteFormattedDateTime = limiteDateTime.format(formatter);

            // Muestra un cuadro de diálogo de éxito después del INSERT exitoso
            String successMessage = "Reservación exitosa para mesa(s): " + Arrays.toString(idMesas)
                    + ". La fecha y hora límite para llegar al restaurante es: " + limiteFormattedDateTime;
            JOptionPane.showMessageDialog(null, successMessage, "Reservación Exitosa", JOptionPane.INFORMATION_MESSAGE);

            getDatosReservacion(idCliente, idMesas);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConexionBD.closeConnection(connection);
        }
    }

    /**
     * Método privado que cambia el estado de una mesa a "ocupada" en la base de
     * datos.
     *
     * @param idMesa Identificador de la mesa cuyo estado se actualizará.
     *
     * El método realiza una consulta de UPDATE en la base de datos para cambiar
     * el estado de la mesa con el identificador proporcionado a "ocupada"
     * (estado 2). El estado de la mesa se actualiza para reflejar que está
     * ocupada después de confirmar la reservación con el cliente.
     *
     * Se muestran mensajes en la consola para verificar que la consulta se
     * ejecutó correctamente.
     *
     * En caso de excepción durante el proceso de actualización del estado de la
     * mesa, se muestra el mensaje de la excepción en la consola.
     */
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

        jButtonRegresarRegistroReservacion.setBackground(new java.awt.Color(32, 17, 72));
        jButtonRegresarRegistroReservacion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/PalillosBotonRegreso.png"))); // NOI18N
        jButtonRegresarRegistroReservacion.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jButtonRegresarRegistroReservacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRegresarRegistroReservacionActionPerformed(evt);
            }
        });

        jLabelLogoNeoTokio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/Logo Neo Tokio.png"))); // NOI18N
        jLabelLogoNeoTokio.setText("jLabel1");

        jButtonRegistrarReservacion.setBackground(new java.awt.Color(0, 204, 253));
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
                    .addComponent(jLabelReservacionMesasTitulo)
                    .addComponent(jButtonRegresarRegistroReservacion))
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
     *
     * Este método es invocado al interactuar con el botón "Registrar
     * Reservación" en la interfaz de registro de reservaciones. Realiza las
     * siguientes acciones: 1. Llama al método getIDCliente para obtener el
     * identificador del cliente. 2. Verifica si el identificador del cliente no
     * es nulo. 3. Si el identificador del cliente es válido, llama al método
     * setReservacion para insertar la reservación en la base de datos. 4.
     * Verifica si la reservación fue exitosa (puedes ajustar la condición según
     * la lógica de tu aplicación). 5. Si la reservación fue exitosa, llama al
     * método setPDF para generar el archivo PDF del ticket de la reservación.
     * 6. Verifica si la generación del PDF fue exitosa (puedes ajustar la
     * condición según la lógica de tu aplicación). 7. Si tanto la reservación
     * como la generación del PDF fueron exitosas, cierra la ventana principal
     * que contiene el botón. 8. Si no se encuentra la instancia de JFrame, se
     * imprime un mensaje de error en la consola.
     *
     */
    private void jButtonRegistrarReservacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRegistrarReservacionActionPerformed
        // TODO add your handling code here:
        // Llama a getIDCliente para obtener el idCliente
        idCliente = getIDCliente();

        // Verifica si idCliente no es nulo
        if (idCliente != null) {
            // Llama a setReservacion para insertar la reservación
            setReservacion(idCliente, idMesas);

            // Verifica si la reservación fue exitosa (puedes agregar una condición basada en el resultado de setReservacion)
            boolean reservacionExitosa = true;

            // Si la reservación fue exitosa, llama a setPDF para generar el PDF
            if (reservacionExitosa) {

                // Verifica si la generación del PDF fue exitosa (puedes agregar una condición basada en el resultado de setPDF)
                boolean pdfGeneradoExitoso = true;

                // Si la generación del PDF fue exitosa, cierra el JFrame
                if (pdfGeneradoExitoso) {
                    // Obtener la ventana principal que contiene el botón
                    Container container = jButtonRegistrarReservacion.getParent();

                    // Buscar la instancia de JFrame ascendiendo en la jerarquía de contenedores
                    while (!(container instanceof JFrame) && container != null) {
                        container = container.getParent();
                    }

                    // Verificar si se encontró la instancia de JFrame y cerrarla
                    if (container instanceof JFrame) {
                        JFrame frame = (JFrame) container;
                        frame.dispose();
                    } else {
                        System.err.println("Error: No se pudo encontrar la instancia de JFrame.");
                    }
                }
            }
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
