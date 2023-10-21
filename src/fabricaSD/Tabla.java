package fabricaSD;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

class Tabla implements Ensamble {

    private int cantidadTabla;
    private Connection connection;

    public Tabla(int cantidadTabla, Connection connection) {
        this.cantidadTabla = cantidadTabla;
        this.connection = connection;
    }

    @Override
    public void crearMaterial(int incremento) {
        Scanner sn = new Scanner(System.in);
        System.out.println("             (\\(\\");
        System.out.println("             (-.-)");
        System.out.println("            o_(\")(\")");

        int cantidadActual = ObtenerValorAcBS(connection, "tabla");
        actualizarCantidadEnBaseDeDatos(connection, "tabla", cantidadActual + incremento);

        System.out.println("Cantidad de: " + incremento + " tablas fabricadas \uD83D\uDEE0 \uD83D\uDEE0.");
        sn.nextLine();
    }
}