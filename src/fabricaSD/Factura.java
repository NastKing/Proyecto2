package fabricaSD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class Factura {
    private Cliente cliente;
    private Orden orden;
    private String nombreProducto;
    private int cantidad;
    //private Producto producto;

    public Factura(Connection connection, String nombre, String nombreProducto,int cantidad) {
        this.cliente = obtenerClientePorNo(connection, nombre);
        this.orden = new Orden();
        this.nombreProducto = nombreProducto;
        this.cantidad = cantidad;
     
    }
    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

   public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public void agregarProducto(String nombreProducto) {
       
    }

    public float calcularTotal(float precioUni, int cantidad) {
        return precioUni * cantidad;
    }

    public void imprimirFactura(Connection connection) {
        System.out.println("---------------------------------------------------------------------------------------------");
        System.out.println("|                    Factura ");
        System.out.println("|               Fabrica De Sillas S.A");
        System.out.println("|        1ra calle 3-93 kaminal juyu 1 ZONA 7");
        System.out.println("|");
        System.out.println("|");
        System.out.println("| Numero de Orden :" + orden.getNumeroOrden());
        System.out.println("|");
        System.out.println("|");
        System.out.println("|Nombre: " + cliente.getNombre() + " " + cliente.getApellido() + "    Nit: " + cliente.getNit());
        System.out.println("-------------------------------------------------------------------------------------");
        
        float precioUni = obtenerPrecioSilla(connection, nombreProducto);
        float cantidadT = calcularTotal(precioUni, cantidad);
        
        System.out.println("|Cantidad      Descripcion       Precio Unit     ");
        System.out.println("|" + cantidad + "              " + nombreProducto + "           Q : " + precioUni);
        
        System.out.println("|                    Total a pagar: $" + cantidadT);
        System.out.println("..........................................................................................");
    }

    private int generarNumeroAleatorio() {
        Random random = new Random();
        return random.nextInt(1000) + 1; // Genera un número aleatorio entre 1 y 1000
    }

   

    private float obtenerPrecioSilla(Connection connection, String nombreProducto) {
        float precio = 0;
        try {
            String sql = "SELECT precio FROM producto WHERE nombre = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, nombreProducto);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                precio = resultSet.getFloat("precio");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return precio;
    }
    public static Cliente obtenerClientePorNo(Connection connection, String clienteNo) {
        try {
            String sql = "SELECT * FROM cliente WHERE nombre = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, clienteNo);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String nombre = resultSet.getString("nombre");
                String apellido = resultSet.getString("apellido");
                int nit = resultSet.getInt("nit");

                // Crear y devolver un objeto Cliente
                return new Cliente(nombre, apellido, nit);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null; // Si no se encuentra ningún cliente, se devuelve null
    }
}

