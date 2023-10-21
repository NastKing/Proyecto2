package fabricaSD;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public interface Ensamble {
	void crearMaterial(int cantidad);

	default int ObtenerValorAcBS(Connection connection, String nombre) {
        int cantidadActual = 0;
        try {
            String sql = "SELECT cantidad FROM Ensamble WHERE nombre = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, nombre);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                cantidadActual = resultSet.getInt("cantidad");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cantidadActual;
    }
	default void actualizarCantidadEnBaseDeDatos(Connection connection, String nombre, int nuevaCantidad) {
        try {
            String sql = "UPDATE Ensamble SET cantidad = ? WHERE nombre = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, nuevaCantidad);
            preparedStatement.setString(2, nombre);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
