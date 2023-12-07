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
 * Clase principal para la interfaz de usuario del sistema de reservas
 * Muestra las mesas disponibles y permite al usuario hacer reservas.
 * 
 * @author Pedro
 */
public class Sucursal_Allende extends javax.swing.JFrame {
    
    // Lista para almacenar los ID de las mesas seleccionadas.
    private ArrayList<Integer> selectedMesas = new ArrayList<>();

        
    /**
     * Crea una nueva instancia de la clase Sucursal_Allende.
     * Inicializa los componentes de la interfaz de usuario y obtiene información sobre las mesas disponibles.
     */
    public Sucursal_Allende() {
        initComponents();
    
            
        getMesas();
    }
    
    /**
     * Obtiene información sobre las mesas disponibles desde la base datos y actualiza la interfaz de usuario en consecuencia.
     */
    private void getMesas() {
        // Establece la conexión a la base de datos
        Connection connection = ConexionBD.getConnection();
        
        // Consulta SQL para obtener información sobre las mesas disponibles en la sucursal actual
        String queryMesas = "SELECT idMesa, estado FROM mesa WHERE idSucursal = 1";

        try (PreparedStatement statement = connection.prepareStatement(queryMesas)) {
            // Ejecuta la consulta y obtiene los resultados
            ResultSet resultSet = statement.executeQuery();
           
            // Itera sobre los resultados para actualizar la interfaz de usuario con la información de las mesas
            while (resultSet.next()) {
                int idMesa = resultSet.getInt("idMesa");
                int estado = resultSet.getInt("estado");
               
                // Establece el icono y el estado del checkbox de la mesa según su disponibilidad
                switch (idMesa) {
                    case 1:
                        setMesaIcon(jCheckBoxMesa1, estado, idMesa);
                        break;

                    case 2:
                        setMesaIcon(jCheckBoxMesa2, estado, idMesa);
                        break;

                    case 3:
                        setMesaIcon(jCheckBoxMesa3, estado, idMesa);
                        break;

                    case 4:
                        setMesaIcon(jCheckBoxMesa4, estado, idMesa);
                        break;

                    case 5:
                        setMesaIcon(jCheckBoxMesa5, estado, idMesa);
                        break;

                    case 6:
                        setMesaIcon(jCheckBoxMesa6, estado, idMesa);
                        break;

                    case 7:
                        setMesaIcon(jCheckBoxMesa7, estado, idMesa);
                        break;

                    case 8:
                        setMesaIcon(jCheckBoxMesa8, estado, idMesa);
                        break;

                    case 9:
                        setMesaIcon(jCheckBoxMesa9, estado, idMesa);
                        break;

                    case 10:
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
     * Establece el icono y comportamiento de un checkbox de mesa según su disponibilidad.
     * 
     * @param checkbox El checkbox que representa la mesa en la sucursal.
     * @param estado       El estado de disponibildad de la mesa.
     * @param idMesa      El ID de la mesa.
     */
    private void setMesaIcon(JCheckBox checkbox, int estado, int idMesa) {
        // Rutas de los iconos según el estado de disponibilidad
        String iconPath = "";
        String selectedIconPath = "";

        // Establece las rutas para los recursos de la mesas, dependiendo de su disponibilidad
        switch (estado) {
            case 1:
                iconPath = "/Recursos/MesaDisponible.png";
                selectedIconPath = "/Recursos/MesaDisponibleS.png";
                break;
            case 2:
                iconPath = "/Recursos/MesaOcupada.png";
                selectedIconPath = "/Recursos/MesaOcupadaS.png";
                break;
            case 3:
                iconPath = "/Recursos/MesaProceso.png";
                selectedIconPath = "/Recursos/MesaProcesoS.png";
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
     * Obtiene el estado de disponibilidad de una mesa según su ID desde la base de datos.
     * 
     * @param mesaId El ID de la mesa.
     * @return                 El estado de disponbilidad de la mesa
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
     *  Abre la ventana de registro de reservas con las mesas seleccionadas.
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

        jPanelSucursal1 = new javax.swing.JPanel();
        jPanelAreaA = new javax.swing.JPanel();
        jLabelAreaA = new javax.swing.JLabel();
        jCheckBoxMesa1 = new javax.swing.JCheckBox();
        jCheckBoxMesa2 = new javax.swing.JCheckBox();
        jCheckBoxMesa3 = new javax.swing.JCheckBox();
        jPanelAreaB = new javax.swing.JPanel();
        jLabelAreaB = new javax.swing.JLabel();
        jCheckBoxMesa6 = new javax.swing.JCheckBox();
        jCheckBoxMesa7 = new javax.swing.JCheckBox();
        jCheckBoxMesa4 = new javax.swing.JCheckBox();
        jCheckBoxMesa5 = new javax.swing.JCheckBox();
        jPanelAreaC = new javax.swing.JPanel();
        jLabelAreaC = new javax.swing.JLabel();
        jCheckBoxMesa8 = new javax.swing.JCheckBox();
        jCheckBoxMesa9 = new javax.swing.JCheckBox();
        jCheckBoxMesa10 = new javax.swing.JCheckBox();
        jButtonRegresarSucursal1 = new javax.swing.JButton();
        jLabelBienvenido = new javax.swing.JLabel();
        jLabelDireccion = new javax.swing.JLabel();
        jLabelDireccionAllende = new javax.swing.JLabel();
        jLabelNumeroTelefonico = new javax.swing.JLabel();
        jLabelTelefono = new javax.swing.JLabel();
        jLabelTelefono1 = new javax.swing.JLabel();
        jButtonReservarMesas = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanelSucursal1.setBackground(new java.awt.Color(32, 17, 72));
        jPanelSucursal1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 204, 253), 3, true));

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

        jButtonRegresarSucursal1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/BotonRegresar.png"))); // NOI18N
        jButtonRegresarSucursal1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jButtonRegresarSucursal1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRegresarSucursal1ActionPerformed(evt);
            }
        });

        jLabelBienvenido.setFont(new java.awt.Font("Bahiana", 0, 48)); // NOI18N
        jLabelBienvenido.setForeground(new java.awt.Color(85, 231, 255));
        jLabelBienvenido.setText("Bienvenido a la Sucursal Allende");

        jLabelDireccion.setFont(new java.awt.Font("Bahiana", 0, 48)); // NOI18N
        jLabelDireccion.setForeground(new java.awt.Color(85, 231, 255));
        jLabelDireccion.setText("Direccion :");

        jLabelDireccionAllende.setFont(new java.awt.Font("Bahiana", 0, 48)); // NOI18N
        jLabelDireccionAllende.setForeground(new java.awt.Color(85, 231, 255));
        jLabelDireccionAllende.setText("Andador Allende, #10010");

        jLabelNumeroTelefonico.setFont(new java.awt.Font("Bahiana", 0, 48)); // NOI18N
        jLabelNumeroTelefonico.setForeground(new java.awt.Color(85, 231, 255));
        jLabelNumeroTelefonico.setText("449 154 25 87");

        jLabelTelefono.setFont(new java.awt.Font("Bahiana", 0, 48)); // NOI18N
        jLabelTelefono.setForeground(new java.awt.Color(85, 231, 255));
        jLabelTelefono.setText("Telefono :");

        jLabelTelefono1.setFont(new java.awt.Font("Bahiana", 0, 48)); // NOI18N
        jLabelTelefono1.setForeground(new java.awt.Color(85, 231, 255));
        jLabelTelefono1.setText("Horario : 10:00 AM - 08:00 PM");

        jButtonReservarMesas.setFont(new java.awt.Font("Barlow Condensed Medium", 0, 48)); // NOI18N
        jButtonReservarMesas.setText("Reservar Mesas");
        jButtonReservarMesas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonReservarMesasActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelSucursal1Layout = new javax.swing.GroupLayout(jPanelSucursal1);
        jPanelSucursal1.setLayout(jPanelSucursal1Layout);
        jPanelSucursal1Layout.setHorizontalGroup(
            jPanelSucursal1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSucursal1Layout.createSequentialGroup()
                .addGroup(jPanelSucursal1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelSucursal1Layout.createSequentialGroup()
                        .addComponent(jButtonRegresarSucursal1, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(114, 114, 114)
                        .addComponent(jLabelBienvenido, javax.swing.GroupLayout.PREFERRED_SIZE, 460, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelSucursal1Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(jPanelAreaA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanelAreaB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanelAreaC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanelSucursal1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelSucursal1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanelSucursal1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelSucursal1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(jPanelSucursal1Layout.createSequentialGroup()
                                    .addComponent(jLabelTelefono)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jLabelNumeroTelefonico, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jLabelDireccion)
                                .addComponent(jLabelDireccionAllende, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jLabelTelefono1))
                        .addContainerGap(12, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelSucursal1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonReservarMesas, javax.swing.GroupLayout.PREFERRED_SIZE, 319, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(64, 64, 64))))
        );
        jPanelSucursal1Layout.setVerticalGroup(
            jPanelSucursal1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSucursal1Layout.createSequentialGroup()
                .addGroup(jPanelSucursal1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelSucursal1Layout.createSequentialGroup()
                        .addGroup(jPanelSucursal1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButtonRegresarSucursal1)
                            .addComponent(jLabelBienvenido, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanelSucursal1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanelAreaA, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanelAreaB, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanelAreaC, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanelSucursal1Layout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addComponent(jLabelDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelDireccionAllende, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanelSucursal1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelNumeroTelefonico, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelTelefono1, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(113, 113, 113)
                        .addComponent(jButtonReservarMesas)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelSucursal1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelSucursal1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Maneja el evento de clic en el botón de "Regresar" de la interfaz de usuario.
     * Cierra la ventana actual.
     * @param evt Evento de acción generado por el boton
     */
    private void jButtonRegresarSucursal1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRegresarSucursal1ActionPerformed
        // TODO add your handling code here:
        // Cierra la ventana actual al hacer click en el botón "Regresar"
        this.dispose();
    }//GEN-LAST:event_jButtonRegresarSucursal1ActionPerformed

    /**
     * Maneja el evento de clic en el botón "Reservar Mesas" de la interfaz de usuario.
     * Verifica la disponbilidad de las mesas seleccionadas y solicita al usuario que esté registrado.
     * Abre la ventana de registro de reservas si el usuario está registrado, de lo contrario, abre la ventana de registro de clientes.
     * Muestra un mensaje de error si alguna mesa seleccionada no está disponible.
     * 
     * @param evt Evento de acción generado por el botón
     */
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
        if(jCheckBoxMesa1.isSelected()==false && jCheckBoxMesa2.isSelected()==false && jCheckBoxMesa3.isSelected()==false && jCheckBoxMesa4.isSelected()==false && jCheckBoxMesa5.isSelected()==false && jCheckBoxMesa6.isSelected()==false && jCheckBoxMesa7.isSelected()==false && jCheckBoxMesa8.isSelected()==false && jCheckBoxMesa9.isSelected()==false && jCheckBoxMesa10.isSelected()==false){
            MesaSelected= false;
        }
        
        if(MesaSelected){
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
        }else{
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
            java.util.logging.Logger.getLogger(Sucursal_Allende.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Sucursal_Allende.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Sucursal_Allende.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Sucursal_Allende.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        
        
        
        
        
        
        
        
        
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Sucursal_Allende().setVisible(true);
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
    private javax.swing.JLabel jLabelDireccionAllende;
    private javax.swing.JLabel jLabelNumeroTelefonico;
    private javax.swing.JLabel jLabelTelefono;
    private javax.swing.JLabel jLabelTelefono1;
    private javax.swing.JPanel jPanelAreaA;
    private javax.swing.JPanel jPanelAreaB;
    private javax.swing.JPanel jPanelAreaC;
    private javax.swing.JPanel jPanelSucursal1;
    // End of variables declaration//GEN-END:variables
}
