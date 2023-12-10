package ReservacionesUI;

// import necesaria para la conexión a la base de datos
import conexion.ConexionBD;

// imports para manejar eventos de acción y componentes gráficos de la interfaz de usuario
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// imports para trabajar un URL y obtener recursos del sistema
import java.net.URL;

// imports necesarios para interactuar con bases de datos mediante JDBC
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// import de estructuras de datos para almacenar y manipular listas de elementos
import java.util.ArrayList;

// imports necesarios para trabajar con componentes gráficos específicos de Swing
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;

/**
 *
 * @author Pedro Quiroz
 */
public class Sucursal_AvUniversidad extends javax.swing.JFrame {

    // Lista para almacenar los ID de las mesas seleccionadas.
    private ArrayList<Integer> selectedMesas = new ArrayList<>();

    /**
     * Clase principal para la interfaz de usuario del sistema de reservas
     * Muestra las mesas disponibles y permite al usuario hacer reservas.
     *
     */
    public Sucursal_AvUniversidad() {
        initComponents();
        getMesas();
    }

    /**
     * Obtiene información sobre las mesas disponibles desde la base datos y
     * actualiza la interfaz de usuario en consecuencia.
     */
    private void getMesas() {
        // Establece la conexión a la base de datos
        Connection connection = ConexionBD.getConnection();

        // Consulta SQL para obtener información sobre las mesas disponibles en la sucursal actual
        String queryMesas = "SELECT idMesa, estado FROM mesa WHERE idSucursal = 5";

        try (PreparedStatement statement = connection.prepareStatement(queryMesas)) {
            // Ejecuta la consulta y obtiene los resultados
            ResultSet resultSet = statement.executeQuery();

            // Itera sobre los resultados para actualizar la interfaz de usuario con la información de las mesas
            while (resultSet.next()) {
                int idMesa = resultSet.getInt("idMesa");
                int estado = resultSet.getInt("estado");

                // Establece el icono y el estado del checkbox de la mesa según su disponibilidad
                switch (idMesa) {
                    case 41:
                        setMesaIcon(jCheckBoxMesa1, estado, idMesa);
                        break;

                    case 42:
                        setMesaIcon(jCheckBoxMesa2, estado, idMesa);
                        break;

                    case 43:
                        setMesaIcon(jCheckBoxMesa3, estado, idMesa);
                        break;

                    case 44:
                        setMesaIcon(jCheckBoxMesa4, estado, idMesa);
                        break;

                    case 45:
                        setMesaIcon(jCheckBoxMesa5, estado, idMesa);
                        break;

                    case 46:
                        setMesaIcon(jCheckBoxMesa6, estado, idMesa);
                        break;

                    case 47:
                        setMesaIcon(jCheckBoxMesa7, estado, idMesa);
                        break;

                    case 48:
                        setMesaIcon(jCheckBoxMesa8, estado, idMesa);
                        break;

                    case 49:
                        setMesaIcon(jCheckBoxMesa9, estado, idMesa);
                        break;

                    case 50:
                        setMesaIcon(jCheckBoxMesa10, estado, idMesa);
                        break;
                }
            }
        } catch (SQLException e) {
            // Maneja excepciones relacionadas con la base de datos
            e.printStackTrace();
        } finally {
            // Cierra la conexión a la base datos en cualquier caso
            ConexionBD.closeConnection(connection);
        }
    }

    /**
     * Establece el icono y comportamiento de un checkbox de mesa según su
     * disponibilidad.
     *
     * @param checkbox El checkbox que representa la mesa en la sucursal.
     * @param estado El estado de disponibildad de la mesa.
     * @param idMesa El ID de la mesa.
     */
    private void setMesaIcon(JCheckBox checkbox, int estado, int idMesa) {
        // Rutas de los iconos según el estado de disponibilidad
        String iconPath = "";
        String selectedIconPath = "";

        // Establece las rutas para los recursos de la mesas, dependiendo de su disponibilidad
        switch (estado) {
              case 1: // Mesa Libre
                iconPath = "/Recursos/MesaDisponible.png";
                selectedIconPath = "/Recursos/MesaDisponibleS.png";
                break;
            case 2: // Mesa Reservada
                iconPath = "/Recursos/MesaProceso.png";
                selectedIconPath = "/Recursos/MesaProcesoS.png";
                break;
            case 3: // Mesa Ocupada
                iconPath = "/Recursos/MesaOcupada.png";
                selectedIconPath = "/Recursos/MesaOcupadaS.png";
                break;
            case 4: // Mesa Pidiendo
                iconPath = "/Recursos/MesaPidiendo.png";
                selectedIconPath = "/Recurso/MesaPidiendoS.png";
                break;
            case 5: // Mesa Esperando Comida
                iconPath = "/Recursos/MesaEsperaComida.png";
                selectedIconPath = "/Recursos/MesaEsperaComidaS.png";
                break;
            case 6: // Mesa Servida
                iconPath = "/Recursos/MesaServida.png";
                selectedIconPath = "/Recursos/MesaServidaS.png";
                break;
            case 7: // Mesa Esperando cuenta
                iconPath = "/Recursos/MesaEsperaCuenta.png";
                selectedIconPath = "/Recursos/MesaEsperaCuentaS.png";
                break;
            case 8: // Mesa pagando
                iconPath = "/Recursos/MesaPagando.png";
                selectedIconPath = "/Recursos/MesaPagandoS.png";
                break;
            default:
                iconPath = "/Recursos/MesaDisponible.png";
                selectedIconPath = "/Recursos/MesaDisponibleS.png";
        }

        // Obtiene los URLs de los iconos
        URL iconURL = getClass().getResource(iconPath);
        URL selectedIconURL = getClass().getResource(selectedIconPath);

        // Agrega un ActionListener para manejar las selección/deselección del checkbox
        checkbox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (checkbox.isSelected()) {
                    selectedMesas.add(idMesa);
                } else {
                    selectedMesas.remove(Integer.valueOf(idMesa));
                }
            }
        });

        // Establece los iconos para los checkbox
        if (iconURL != null && selectedIconURL != null) {
            checkbox.setIcon(new ImageIcon(iconURL));
            checkbox.setSelectedIcon(new ImageIcon(selectedIconURL));
        } else {
            // Maneja el caso en el no se encuentran las rutas para los recursos
            System.out.println("Error, No se encontro la ruta para el recurso " + estado);
        }
    }

    /**
     * Obtiene el estado de disponibilidad de una mesa según su ID desde la base
     * de datos.
     *
     * @param mesaId El ID de la mesa.
     * @return El estado de disponbilidad de la mesa
     */
    private int getMesaEstadoById(int mesaId) {
        // Establece la conexión a la base de datos
        Connection connection = ConexionBD.getConnection();

        // Consulta SQL para obtener el estado de disponibilidad para la mesa según su ID
        String queryEstado = "SELECT estado FROM mesa WHERE idMesa = ?";

        try (PreparedStatement statement = connection.prepareStatement(queryEstado)) {
            // Establece el ID de la mesa en la consulta y ejecuta
            statement.setInt(1, mesaId);
            ResultSet resultSet = statement.executeQuery();

            // Procesa los resultados y devuelve el estado de disponibilidad
            if (resultSet.next()) {
                return resultSet.getInt("estado");
            } else {
                // Maneja el caso en el que no se encuentre la mesa con el ID proporcionado
                System.out.println("Error: No se encontró la mesa con id " + mesaId);
                return -1;
            }
        } catch (SQLException ex) {
            // Maneja excepciones relacionadas con la base de datos
            ex.printStackTrace();
            return -1;
        } finally {
            // Cierra la conexión a la base de datos en cualquier caso
            ConexionBD.closeConnection(connection);
        }
    }

    /**
     * Abre la ventana de registro de reservas con las mesas seleccionadas.
     */
    private void enviarMesasSeleccionadas() {
        // Convierte la lista de mesas seleccionadas a un array y abre la ventana de registro de reservas
        Integer[] selectedMesaArray = selectedMesas.toArray(new Integer[0]);
        RegistroReservacion registroReservacion = new RegistroReservacion(selectedMesaArray);
        registroReservacion.setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelAvUniversidad = new javax.swing.JPanel();
        jButtonRegresarSucursal1 = new javax.swing.JButton();
        jPanelAreaC = new javax.swing.JPanel();
        jLabelAreaC = new javax.swing.JLabel();
        jCheckBoxMesa8 = new javax.swing.JCheckBox();
        jCheckBoxMesa9 = new javax.swing.JCheckBox();
        jCheckBoxMesa10 = new javax.swing.JCheckBox();
        jPanelAreaA = new javax.swing.JPanel();
        jLabelAreaA = new javax.swing.JLabel();
        jCheckBoxMesa1 = new javax.swing.JCheckBox();
        jCheckBoxMesa2 = new javax.swing.JCheckBox();
        jCheckBoxMesa3 = new javax.swing.JCheckBox();
        jButtonReservarMesas = new javax.swing.JButton();
        jLabelDireccionAvUniversidad = new javax.swing.JLabel();
        jLabelLocal = new javax.swing.JLabel();
        jLabelNumeroTelefonico = new javax.swing.JLabel();
        jLabelTelefono = new javax.swing.JLabel();
        jLabelBienvenido = new javax.swing.JLabel();
        jPanelAreaB = new javax.swing.JPanel();
        jLabelAreaB = new javax.swing.JLabel();
        jCheckBoxMesa6 = new javax.swing.JCheckBox();
        jCheckBoxMesa7 = new javax.swing.JCheckBox();
        jCheckBoxMesa4 = new javax.swing.JCheckBox();
        jCheckBoxMesa5 = new javax.swing.JCheckBox();
        jLabelTelefono1 = new javax.swing.JLabel();
        jLabelDireccion = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanelAvUniversidad.setBackground(new java.awt.Color(32, 17, 72));
        jPanelAvUniversidad.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 204, 253), 5, true));

        jButtonRegresarSucursal1.setBackground(new java.awt.Color(32, 17, 72));
        jButtonRegresarSucursal1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/PalillosBotonRegreso.png"))); // NOI18N
        jButtonRegresarSucursal1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jButtonRegresarSucursal1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRegresarSucursal1ActionPerformed(evt);
            }
        });

        jPanelAreaC.setBackground(new java.awt.Color(32, 17, 162));
        jPanelAreaC.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(85, 231, 255), 5, true));
        jPanelAreaC.setPreferredSize(new java.awt.Dimension(165, 550));

        jLabelAreaC.setFont(new java.awt.Font("Bahiana", 2, 36)); // NOI18N
        jLabelAreaC.setForeground(new java.awt.Color(32, 17, 72));
        jLabelAreaC.setText("Area C");

        jCheckBoxMesa8.setText("jCheckBoxMesa8");
        jCheckBoxMesa8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/MesaDisponible.png"))); // NOI18N

        jCheckBoxMesa9.setText("jCheckBox2");
        jCheckBoxMesa9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/MesaDisponible.png"))); // NOI18N

        jCheckBoxMesa10.setText("jCheckBox5");
        jCheckBoxMesa10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/MesaDisponible.png"))); // NOI18N

        javax.swing.GroupLayout jPanelAreaCLayout = new javax.swing.GroupLayout(jPanelAreaC);
        jPanelAreaC.setLayout(jPanelAreaCLayout);
        jPanelAreaCLayout.setHorizontalGroup(
            jPanelAreaCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAreaCLayout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jLabelAreaC, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanelAreaCLayout.createSequentialGroup()
                .addGroup(jPanelAreaCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jCheckBoxMesa8, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCheckBoxMesa9, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jCheckBoxMesa10, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 5, Short.MAX_VALUE))
        );
        jPanelAreaCLayout.setVerticalGroup(
            jPanelAreaCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAreaCLayout.createSequentialGroup()
                .addComponent(jLabelAreaC)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addComponent(jCheckBoxMesa8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBoxMesa9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBoxMesa10))
        );

        jPanelAreaA.setBackground(new java.awt.Color(85, 231, 255));
        jPanelAreaA.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(118, 85, 255), 5, true));

        jLabelAreaA.setFont(new java.awt.Font("Bahiana", 2, 36)); // NOI18N
        jLabelAreaA.setForeground(new java.awt.Color(32, 17, 72));
        jLabelAreaA.setText("Area A");

        jCheckBoxMesa1.setText("jCheckBox1");
        jCheckBoxMesa1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/MesaDisponible.png"))); // NOI18N

        jCheckBoxMesa2.setText("jCheckBox2");
        jCheckBoxMesa2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/MesaDisponible.png"))); // NOI18N

        jCheckBoxMesa3.setText("jCheckBox3");
        jCheckBoxMesa3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/MesaDisponible.png"))); // NOI18N

        javax.swing.GroupLayout jPanelAreaALayout = new javax.swing.GroupLayout(jPanelAreaA);
        jPanelAreaA.setLayout(jPanelAreaALayout);
        jPanelAreaALayout.setHorizontalGroup(
            jPanelAreaALayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAreaALayout.createSequentialGroup()
                .addGroup(jPanelAreaALayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jCheckBoxMesa1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanelAreaALayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jCheckBoxMesa2, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jCheckBoxMesa3, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 7, Short.MAX_VALUE))
            .addGroup(jPanelAreaALayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jLabelAreaA, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelAreaALayout.setVerticalGroup(
            jPanelAreaALayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAreaALayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelAreaA)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addComponent(jCheckBoxMesa1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jCheckBoxMesa2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBoxMesa3))
        );

        jButtonReservarMesas.setBackground(new java.awt.Color(0, 204, 253));
        jButtonReservarMesas.setFont(new java.awt.Font("Barlow Condensed Medium", 0, 48)); // NOI18N
        jButtonReservarMesas.setText("Reservar Mesas");
        jButtonReservarMesas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonReservarMesasActionPerformed(evt);
            }
        });

        jLabelDireccionAvUniversidad.setFont(new java.awt.Font("Bahiana", 0, 48)); // NOI18N
        jLabelDireccionAvUniversidad.setForeground(new java.awt.Color(85, 231, 255));
        jLabelDireccionAvUniversidad.setText("Av. Universidad");

        jLabelLocal.setFont(new java.awt.Font("Bahiana", 0, 48)); // NOI18N
        jLabelLocal.setForeground(new java.awt.Color(85, 231, 255));
        jLabelLocal.setText("# 12");

        jLabelNumeroTelefonico.setFont(new java.awt.Font("Bahiana", 0, 48)); // NOI18N
        jLabelNumeroTelefonico.setForeground(new java.awt.Color(85, 231, 255));
        jLabelNumeroTelefonico.setText("449 235 41 81 ");

        jLabelTelefono.setFont(new java.awt.Font("Bahiana", 0, 48)); // NOI18N
        jLabelTelefono.setForeground(new java.awt.Color(85, 231, 255));
        jLabelTelefono.setText("Telefono :");

        jLabelBienvenido.setFont(new java.awt.Font("Bahiana", 0, 48)); // NOI18N
        jLabelBienvenido.setForeground(new java.awt.Color(85, 231, 255));
        jLabelBienvenido.setText("Bienvenido a la Sucursal Av. Universidad");

        jPanelAreaB.setBackground(new java.awt.Color(255, 25, 179));
        jPanelAreaB.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 0, 111), 5, true));

        jLabelAreaB.setFont(new java.awt.Font("Bahiana", 2, 36)); // NOI18N
        jLabelAreaB.setForeground(new java.awt.Color(32, 17, 72));
        jLabelAreaB.setText("Area B");

        jCheckBoxMesa6.setText("jCheckBoxMesa6");
        jCheckBoxMesa6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/MesaDisponible.png"))); // NOI18N

        jCheckBoxMesa7.setText("jCheckBoxMesa7");
        jCheckBoxMesa7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/MesaDisponible.png"))); // NOI18N

        jCheckBoxMesa4.setText("jCheckBox1");
        jCheckBoxMesa4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/MesaDisponible.png"))); // NOI18N

        jCheckBoxMesa5.setText("jCheckBox2");
        jCheckBoxMesa5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/MesaDisponible.png"))); // NOI18N

        javax.swing.GroupLayout jPanelAreaBLayout = new javax.swing.GroupLayout(jPanelAreaB);
        jPanelAreaB.setLayout(jPanelAreaBLayout);
        jPanelAreaBLayout.setHorizontalGroup(
            jPanelAreaBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAreaBLayout.createSequentialGroup()
                .addGroup(jPanelAreaBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelAreaBLayout.createSequentialGroup()
                        .addComponent(jCheckBoxMesa6, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBoxMesa7, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelAreaBLayout.createSequentialGroup()
                        .addComponent(jCheckBoxMesa4, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBoxMesa5, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanelAreaBLayout.createSequentialGroup()
                .addGap(114, 114, 114)
                .addComponent(jLabelAreaB, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelAreaBLayout.setVerticalGroup(
            jPanelAreaBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAreaBLayout.createSequentialGroup()
                .addComponent(jLabelAreaB)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanelAreaBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBoxMesa6)
                    .addComponent(jCheckBoxMesa7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelAreaBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBoxMesa4)
                    .addComponent(jCheckBoxMesa5)))
        );

        jLabelTelefono1.setFont(new java.awt.Font("Bahiana", 0, 48)); // NOI18N
        jLabelTelefono1.setForeground(new java.awt.Color(85, 231, 255));
        jLabelTelefono1.setText("Horario : 10:00 AM - 08:00 PM");

        jLabelDireccion.setFont(new java.awt.Font("Bahiana", 0, 48)); // NOI18N
        jLabelDireccion.setForeground(new java.awt.Color(85, 231, 255));
        jLabelDireccion.setText("Direccion :");

        javax.swing.GroupLayout jPanelAvUniversidadLayout = new javax.swing.GroupLayout(jPanelAvUniversidad);
        jPanelAvUniversidad.setLayout(jPanelAvUniversidadLayout);
        jPanelAvUniversidadLayout.setHorizontalGroup(
            jPanelAvUniversidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAvUniversidadLayout.createSequentialGroup()
                .addGroup(jPanelAvUniversidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanelAvUniversidadLayout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(jPanelAreaA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanelAreaB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanelAreaC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelAvUniversidadLayout.createSequentialGroup()
                        .addComponent(jButtonRegresarSucursal1, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabelBienvenido, javax.swing.GroupLayout.PREFERRED_SIZE, 554, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanelAvUniversidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelAvUniversidadLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanelAvUniversidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelDireccion)
                            .addComponent(jLabelDireccionAvUniversidad, javax.swing.GroupLayout.PREFERRED_SIZE, 432, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelLocal)
                            .addGroup(jPanelAvUniversidadLayout.createSequentialGroup()
                                .addComponent(jLabelTelefono)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabelNumeroTelefonico, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabelTelefono1)))
                    .addGroup(jPanelAvUniversidadLayout.createSequentialGroup()
                        .addGap(65, 65, 65)
                        .addComponent(jButtonReservarMesas, javax.swing.GroupLayout.PREFERRED_SIZE, 319, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelAvUniversidadLayout.setVerticalGroup(
            jPanelAvUniversidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAvUniversidadLayout.createSequentialGroup()
                .addGroup(jPanelAvUniversidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelAvUniversidadLayout.createSequentialGroup()
                        .addGroup(jPanelAvUniversidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelBienvenido, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButtonRegresarSucursal1, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanelAvUniversidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanelAreaA, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanelAreaB, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanelAreaC, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanelAvUniversidadLayout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addComponent(jLabelDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelDireccionAvUniversidad, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelLocal, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelAvUniversidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelNumeroTelefonico, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelTelefono1, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(84, 84, 84)
                        .addComponent(jButtonReservarMesas)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelAvUniversidad, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelAvUniversidad, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonRegresarSucursal1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRegresarSucursal1ActionPerformed
        // TODO add your handling code here:
        // Cierra la ventana actual al hacer click en el botón "Regresar"
        this.dispose();
    }//GEN-LAST:event_jButtonRegresarSucursal1ActionPerformed

    private void jButtonReservarMesasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonReservarMesasActionPerformed
        // TODO add your handling code here:
        // Verifica si todas las mesas seleccionadas están disponibles
        boolean allMesasAvailable = true;
        boolean MesaSelected = true;

        for (Integer mesaId : selectedMesas) {
            int estado = getMesaEstadoById(mesaId);
            if (estado != 1) {
                allMesasAvailable = false;
                break;  //Se cierra la ventana si alguna mesa seleccionada no esta disponible.
            }
        }

        if (jCheckBoxMesa1.isSelected() == false && jCheckBoxMesa2.isSelected() == false && jCheckBoxMesa3.isSelected() == false && jCheckBoxMesa4.isSelected() == false && jCheckBoxMesa5.isSelected() == false && jCheckBoxMesa6.isSelected() == false && jCheckBoxMesa7.isSelected() == false && jCheckBoxMesa8.isSelected() == false && jCheckBoxMesa9.isSelected() == false && jCheckBoxMesa10.isSelected() == false) {
            MesaSelected = false;
        }
        if (MesaSelected) {
            if (allMesasAvailable) {
                // Pregunta al usuario si está registrado
                int option = JOptionPane.showConfirmDialog(null, "Necesitas estar registrado para hacer tu reservación. ¿Ya estás registrado en nuestro sistema?", "Registro", JOptionPane.YES_NO_OPTION);

                if (option == JOptionPane.YES_OPTION) {
                    // El usuario está registrado, abre la ventana de registro de reservas
                    enviarMesasSeleccionadas();
                } else {
                    // Cliente no está registrado, se abre la ventana de registro de clientes
                    RegistroCliente registroCliente = new RegistroCliente();
                    registroCliente.setVisible(true);
                }
            } else {
                // Muestra un mensaje de error si alguna mesa seleccionada no está disponible
                JOptionPane.showMessageDialog(null, "Error, solo puedes reservar mesas que estén actualmente disponibles.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Error, Debe seleccionar una mesa para reservar.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButtonReservarMesasActionPerformed

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
            java.util.logging.Logger.getLogger(Sucursal_AvUniversidad.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Sucursal_AvUniversidad.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Sucursal_AvUniversidad.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Sucursal_AvUniversidad.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Sucursal_AvUniversidad().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonRegresarSucursal1;
    private javax.swing.JButton jButtonReservarMesas;
    private javax.swing.JCheckBox jCheckBoxMesa1;
    private javax.swing.JCheckBox jCheckBoxMesa10;
    private javax.swing.JCheckBox jCheckBoxMesa2;
    private javax.swing.JCheckBox jCheckBoxMesa3;
    private javax.swing.JCheckBox jCheckBoxMesa4;
    private javax.swing.JCheckBox jCheckBoxMesa5;
    private javax.swing.JCheckBox jCheckBoxMesa6;
    private javax.swing.JCheckBox jCheckBoxMesa7;
    private javax.swing.JCheckBox jCheckBoxMesa8;
    private javax.swing.JCheckBox jCheckBoxMesa9;
    private javax.swing.JLabel jLabelAreaA;
    private javax.swing.JLabel jLabelAreaB;
    private javax.swing.JLabel jLabelAreaC;
    private javax.swing.JLabel jLabelBienvenido;
    private javax.swing.JLabel jLabelDireccion;
    private javax.swing.JLabel jLabelDireccionAvUniversidad;
    private javax.swing.JLabel jLabelLocal;
    private javax.swing.JLabel jLabelNumeroTelefonico;
    private javax.swing.JLabel jLabelTelefono;
    private javax.swing.JLabel jLabelTelefono1;
    private javax.swing.JPanel jPanelAreaA;
    private javax.swing.JPanel jPanelAreaB;
    private javax.swing.JPanel jPanelAreaC;
    private javax.swing.JPanel jPanelAvUniversidad;
    // End of variables declaration//GEN-END:variables
}
