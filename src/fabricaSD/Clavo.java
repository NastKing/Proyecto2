package fabricaSD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

class Clavo implements Ensamble {

        private int cantidadClavos ;
        private Connection connection;
        
        public Clavo(int cantidadClavos,Connection connection) {
        	this.cantidadClavos = cantidadClavos;
        	this.connection = connection;
        }
        
        public  int getCantidadClavos() {
            return cantidadClavos;
        }
        public void setCantidadClavos(int cantidadClavos) {
            this.cantidadClavos = cantidadClavos;
        }
    @Override
    public void crearMaterial(int incremento) {
        Scanner sn = new Scanner(System.in);
        System.out.println("             (\\(\\");
        System.out.println("             (-.-)");
        System.out.println("            o_(\")(\")");
        
        int cantidadActual = ObtenerValorAcBS(connection, "clavo");;
        this.setCantidadClavos(cantidadActual + incremento);
        actualizarCantidadEnBaseDeDatos(connection, "clavo", cantidadActual + incremento);;
        
        System.out.println("Cantidad de: " + incremento + " clavos Fabricados \uD83D\uDEE0 \uD83D\uDEE0.");
        sn.nextLine();
    }

    /*public int obtenerCantidadActualDeBaseDeDatos() {
        String nombreClavo = "clavo"; // Nombre del registro
        String sql = "SELECT cantidad FROM Ensamble WHERE nombre = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, nombreClavo); 
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("cantidad");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // 
        }
        return 0; 
    }
    private void actualizarCantidadEnBaseDeDatos() {
        String nombreClavo = "clavo"; // Nombre del registro a actualizar
        String sql = "UPDATE Ensamble SET cantidad = ? WHERE nombre = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, this.cantidadClavos); 
            preparedStatement.setString(2, nombreClavo); 
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }*/
   
    }
