package conexion;

import com.mysql.cj.jdbc.DatabaseMetaData;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;

public class ConexionBD {
    
    
    // Conexion BD Equipo01
    private static final String JDBC_URL = "jdbc:mysql://148.211.124.58:3306/neotokio";
    private static final String JDBC_USER = "neotokio";
    private static final String JDBC_PASSWORD = "jFrB)(A!_s1AYwj0";
    
    
    /*
    // Conexion BD Angel
    private static final String JDBC_URL = "jdbc:mysql://Rayoscompany.com:3306/Proyecto";
    private static final String JDBC_USER = "java";
    private static final String JDBC_PASSWORD = "Java_proyecto123220224";
     */
    public static Connection getConnection() {
        Connection conn = null;
        try {
            // Cargar el driver JBDC MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establecer conexión
            conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
    
        /**
     * Sacamos los ingredientes que tiene un platillo
     * @param idPlatillo para identificar el platillo
     * @return
     * @throws SQLException 
     */
    public static ArrayList<String> obtenerIngredientesPorID(int idPlatillo) throws SQLException {
        ArrayList<String> ingredientes = new ArrayList<>();
        try (Connection conn = getConnection()) {
                    // Consulta para obtener los ingredientes de un platillo por su ID
            String query = "SELECT i.nombre from ingrediente i join ingredientesporplatillo p on i.idIngrediente = p.idIngrediente  WHERE p.idPlatillo = ?";            
            /*
            Se hace un select del nombre de ingrediente de la tabla ingredientesporplatillo donde el idingredientes este relacionado al idplatillo
            */
            PreparedStatement ps = conn.prepareStatement(query); //Ejecutar consulta
            ps.setInt(1, idPlatillo); // se usa solo el idplatillo que se nos proporciona 
            ResultSet rs = ps.executeQuery(); 
                    // Iterar sobre los resultados y añadir los ingredientes a la lista
while (rs.next()) {
String ingrediente = rs.getString("nombre");
                ingredientes.add(ingrediente);
            }
        } catch (SQLException e) {// Imprime la traza de la excepción en caso de error
            e.printStackTrace();
        
        }        
return ingredientes; // Devuelve la lista de ingredientes
    }
    /**
     * Sacamos el precio del platillo desde la base de datos
     * @param idPlato para identificar el plato 
     * @return 
     */
    public int obtenerPrecioPlatoDesdeBD(int idPlato) {
        int precio = 0;
        try (Connection conn = getConnection()) {
                    // Consulta para obtener el precio de un platillo por su ID
            String query = "SELECT precio FROM platillo WHERE idPlatillo = ?";
            /*
            Se hace un select donde sacamos el precio del platillo gracias a su id que tenemos
            */
            PreparedStatement ps = conn.prepareStatement(query); //Ejecutar consulta
            ps.setInt(1, idPlato);
            ResultSet rs = ps.executeQuery();
        // Verificar si se encontró el precio del platillo y obtenerlo
            if (rs.next()) {
                precio = rs.getInt("precio");
            } else {
                System.out.println("Precio del plato no encontrado en la base de datos.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return precio;
    }
    /**
     * Sacamos el precio de la bebida desde la base de datos
     * @param idBebida
     * @return 
     */
        public int obtenerPrecioBebidaDesdeBD(int idBebida) {
        int precio = 0;
            try (Connection conn = getConnection()) {
            // Consulta para obtener el precio de un platillo por su ID
            String query = "SELECT precio FROM bebida WHERE idBebida = ?";
            /*
            Se hace un select donde sacamos el precio de la bebida gracias a su idbebida que tenemos
            */
            PreparedStatement ps = conn.prepareStatement(query); //Ejecutar consulta
            ps.setInt(1, idBebida);
            ResultSet rs = ps.executeQuery();
            // Verificar si se encontró el precio del platillo y obtenerlo
            if (rs.next()) {
                precio = rs.getInt("precio");
            } else {
                System.out.println("Precio de la bebida no encontrado en la base de datos.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return precio;
    }
        

    public static void main(String[] args) {
        Connection connection = getConnection();
        if (connection != null) {
            System.out.println("Conexion exitosa a la base de datos neotokio!");

            boolean continueChecking = true;
            while (continueChecking) {
                try {
                    // Obtener una lista de todas las tablas
                    DatabaseMetaData metaData = (DatabaseMetaData) connection.getMetaData();
                    String catalog = null;
                    String schemaPattern = null;
                    String tableNamePattern = null;
                    String[] types = {"TABLE"};
                    ResultSet tables = metaData.getTables(catalog, schemaPattern, tableNamePattern, types);

                    System.out.println("Lista de tablas dentro de la base de datos:");
                    while (tables.next()) {
                        String tableName = tables.getString("TABLE_NAME");
                        System.out.println(tableName);
                    }

                    tables.close();

                    // Solicitar al usuario que seleccione una tabla
                    Scanner scanner = new Scanner(System.in);
                    System.out.print("Ingresa el nombre de la tabla para ver sus filas y atributos: ");
                    String selectedTable = scanner.nextLine();

                    // Construir y ejecutar la consulta SQL para obtener todas las filas
                    String query = "SELECT * FROM " + selectedTable;
                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery(query);

                    // Obtener metadatos para recuperar los nombres de los atributos
                    ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
                    int columnCount = resultSetMetaData.getColumnCount();

                    // Imprimir los nombres de los atributos
                    System.out.println("Atributos de la tabla " + selectedTable + ":");
                    for (int i = 1; i <= columnCount; i++) {
                        String attributeName = resultSetMetaData.getColumnName(i);
                        System.out.println(attributeName);
                    }

                    // Imprimir las filas
                    System.out.println("Filas de la tabla " + selectedTable + ":");
                    while (resultSet.next()) {
                        for (int i = 1; i <= columnCount; i++) {
                            System.out.print(resultSet.getString(i) + "\t");
                        }
                        System.out.println();
                    }

                    resultSet.close();
                    statement.close();

                    // Preguntar al usuario si desea seguir checando la Base de Datos.re
                    System.out.print("¿Deseas seguir revisando la base de datos? (Si/No): ");
                    String userInput = scanner.nextLine().toLowerCase();
                    continueChecking = userInput.equals("si") || userInput.equals("si");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Error al conectarse a la base de datos.");
        }
    }

    public static void closeConnection(Connection connection) {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately in your application
        }
    }
}
