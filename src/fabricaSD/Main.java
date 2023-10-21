package fabricaSD;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

	public class Main {
		private static Producto productoGlobal;
	    public static void main(String[] args) {
	
	    	
	        Connection connection = null;
	        String url = "jdbc:mariadb://localhost:3306/fabrica";
	        String user = "root";
	        String pwd = "1234";

	        try {
	            connection = DriverManager.getConnection(url, user, pwd);

	            Scanner scanner = new Scanner(System.in);
	            
	           
	            while (true) {
	            	dibujarConejo();
	            	System.out.println("Bienvenido a la Fabrica de Sillas");
	            	System.out.println("Mi nombre es Saltarin");
	            	System.out.print("Pulsa una tecla para continuar");
	            	scanner.nextLine();
	            	System.out.println("");
	            	System.out.println("---------------------------------");
	                System.out.println("Operaciones Dentro de la fabrica");
	                System.out.println("---------------------------------");
	                System.out.println("");
	                System.out.println("1. Ingresar Modelos silla");
	                System.out.println("2. Ver Inventario Productos");
	                System.out.println("3. Crear Existencias del Producto");
	                System.out.println("4. Eliminar Producto");
	                System.out.println("5. Vender Silla");
	                System.out.println("6. Ver Inventario Material");
	                System.out.println("7. Salir del programa");
	                System.out.print("---->Ingrese la opcion:");
	                int opcion = scanner.nextInt();
	                int incremento = 0;
	               

	                switch (opcion) {
	                    case 1:
	                    	dibujarConejo();
	                        System.out.print("Ingresa un Nombre Para Modelo De Silla ");
	                        scanner.nextLine();
	                        String nombre = scanner.nextLine();
	                        System.out.print("Que precio Va a tener esta Silla: ");
	                        float precio = scanner.nextFloat();
	                        
	                        int cantidad =0;
	                        Producto producto = new Producto(nombre, precio,cantidad);
	                        productoGlobal = producto;
	                        insertarProducto(connection, producto);
	                        break;
	                    case 2:
	                        leerProductos(connection);
	                       // Leerclavo(connection);
	                        break;
	                    case 3:
	                        scanner.nextLine();
	                        
	                        System.out.println("Para añadir Existencias ");
	                        System.out.println("Ingresa El modelo De la silla: <------");
	                        String nombreActualizar= scanner.nextLine();
	                        
	                        //VALIDAR SI EXISTE NOMBRE EN BASE DE DATOS
	                        if(validarNombreExistente (connection, nombreActualizar)) {
	                        	//COMO ACTUALIZACION PERO GUARDAMOS EL PRECIO DEL OBJETO PARA QUE NO SEA MODIFICADO
		                        float nuevoPrecio = obtenerPrecioSilla(connection, nombreActualizar);
		                        
		                        System.out.println("");
		                        System.out.println("Ingrese Cantidad a Crear");
		                        System.out.println("----Del producto-----");
		                        int nuevaCantidad = scanner.nextInt();
		                        scanner.nextLine();
		                        
		                        
		                        if (puedeCrearSillas(connection, nuevaCantidad)) {//VALIDADOR SI SE PUEDE O NO CREAR LA SILLAS SEGUN LAS CANTIDADES DE MATERIALES
		                            System.out.println("Se pueden crear las sillas.");
		                            
		                            int cantidadP = ObtenerCantPro(connection, nombreActualizar);
		                            cantidadP += nuevaCantidad;
		                            ActualizarInventa(connection, nombreActualizar, nuevoPrecio, cantidadP);
		                            
		                            //RESTAR MATERIALES AL CREAR SILLA
		                            int tablasNecesarias = 5 * nuevaCantidad; 
		                            int clavosNecesarios = 10 * nuevaCantidad;
		                            restarMateriales(connection, "tabla", tablasNecesarias);
		                            restarMateriales(connection, "clavo", clavosNecesarios);
		                        } else {
		                        	while (!puedeCrearSillas(connection,nuevaCantidad)) {
			                        	 //SI NO SE PUEDE CREARE SILLAS CREA MATERIALES
			                            //CREAR CLAVOS
		                        		System.out.println("");
		                        		System.out.println("");
		                        		dibujarConejo();
		                        		System.out.println("Vaya al Parecer No Tenemos Materiales Suficientes");
		                        		System.out.println("Para Crear La cantidad de: "+nuevaCantidad+" Sillas.");
		                        		System.out.println("------------------------------------------");
		                        		System.out.println("Este es el Inventario Actual de Materiales");
		                        		Leerclavo(connection);
		                        		System.out.println("Presiona una tecla para continuar <----");
		                        		scanner.nextLine();
		                        		System.out.println("");
		                        		System.out.println("");
		                        		dibujarConejo();
		                        		System.out.println("Creemos Clavos");
		                        		int clavosNecesarioss;
			                            System.out.println(" Ingresa la cantidad a Crear: ");
			                            System.out.println(" Necesitas---->: "+ (clavosNecesarioss = 10 * nuevaCantidad));
				                    	incremento = scanner.nextInt();
				                        Clavo clavo = new Clavo(0, connection);
				                        clavo.crearMaterial(incremento);
				                        //CREAR TABLAS
				                        scanner.nextLine();
				                        dibujarConejo();
		                        		System.out.println("Ahora Creemos Tablas");
				                    	System.out.print("Ingrese la cantidad a Crear: ");
				                    	System.out.println(" Necesitas---->: "+ (clavosNecesarioss = 5 * nuevaCantidad));
				                         incremento = scanner.nextInt();
				                        Tabla tabla = new Tabla(0, connection);
				                        tabla.crearMaterial(incremento);
			                        }
		                        	
		                            scanner.nextLine();
		                            int cantidadP = ObtenerCantPro(connection, nombreActualizar);
		                            cantidadP += nuevaCantidad;
		                            ActualizarInventa(connection, nombreActualizar, nuevoPrecio, cantidadP);
		                            scanner.nextLine();
		                            //RESTAR MATERIALES AL CREAR SILLA
		                            int tablasNecesarias = 5 * nuevaCantidad; 
		                            int clavosNecesarios = 10 * nuevaCantidad;
		                            restarMateriales(connection, "tabla", tablasNecesarias);
		                            restarMateriales(connection, "clavo", clavosNecesarios);
		                        	
		                        }
	                        }else {
	                        	dibujarConejo();
	                        	System.out.println(" No existe el producto");
	                        	System.out.println("");
	                        	System.out.println("");
	                        	System.out.println("");
	                        }
	                        
	                        
	                        break;
	                    case 4:
	                    	 scanner.nextLine();
	                    	 dibujarConejo();
	                        System.out.print("Ingrese el nombre del producto a eliminar: ");
	                        String nombreEliminar = scanner.nextLine();
	                        eliminarProducto(connection, nombreEliminar);
	                        break;
	                    /*case 5:
	                    
	                    	 scanner.nextLine();
	                    	System.out.print("Ingrese la cantidad a incrementar: ");
	                    	incremento = scanner.nextInt();
	                        Clavo clavo = new Clavo(0, connection);
	                        clavo.crearMaterial(incremento);
	                        break;
	                        */
	                    case 5:
	                    	//INGRESO DE INFO
	                    	dibujarConejo();
	                    	System.out.println("Venta y Facturación de Sillas");
	                        scanner.nextLine();
	                        System.out.println("1. Ingresar datos del cliente");
	                        System.out.println("2. Obtener cliente existente por ID");
	                        System.out.print("Ingrese la opción: ");
	                        int subOption = scanner.nextInt();
	                        scanner.nextLine(); // Limpiar el buffer

	                        switch (subOption) {
	                            case 1:
	                                System.out.println("Ingrese los datos del cliente:");
	                                System.out.print("Nombre: ");
	                                String nombreCliente = scanner.nextLine();
	                                System.out.print("Apellido: ");
	                                String apellidoCliente = scanner.nextLine();
	                                System.out.print("NIT: ");
	                                int nitCliente = scanner.nextInt();
	                                scanner.nextLine(); 
	                                
	                                
	                                Cliente cliente = new Cliente(nombreCliente, apellidoCliente, nitCliente);
	                                insertarCliente(connection, cliente);
	                                break;
	                            case 2:
	                            	System.out.println("");
	                                System.out.println("Nombre Del Cliente A quien va la Factura: ");
	                                System.out.print("Ej: <<<< Ricardo<<<< Ingrese unicamente el nombre");
	                                String clienteNo = scanner.nextLine();
	                               
	                                Cliente clienteExistente = obtenerClientePorNo(connection, clienteNo);
	                                // Obtener el cliente de la base de datos
	                                if (clienteExistente != null) {
	                                	System.out.println("Ingrese el nombre del producto: ");
	                                    String nombreProducto = scanner.nextLine();
	                                    if(validarNombreExistente (connection, nombreProducto)) {
	                                    	System.out.println("Cantidad A vender: ");
		                                    int cantidadV = scanner.nextInt();
		                                    int cantidadPp = ObtenerCantPro(connection, nombreProducto);
		                                    if (cantidadV<=cantidadPp) {
		                                    	int cantidadV2 = cantidadPp -cantidadV;
		                                    	float nuevoPrecio = obtenerPrecioSilla(connection, nombreProducto);
		                                    	Factura factura = new Factura(connection, clienteExistente.getNombre(), nombreProducto, cantidadV);
			                                	 factura.imprimirFactura(connection);
			                                	 ActualizarInventa(connection, nombreProducto, nuevoPrecio, cantidadV2);
		                                    	
		                                    }else {
		                                    	 System.out.print("No hay suficientes sillas: ");
		                                    }
		                                	 
	                                    }
	                                    

	                                	
	                                    
	                                	 

	                                } else {
	                                    System.out.println("No se encontró ningún cliente con el nombre proporcionado.");
	                                }
	                                break;
	                            default:
	                                System.out.println("Ingrese una opción válida.");
	                                break;
	                        }
	                        break;
	                    case 6:
	                    	System.out.println("------------------------------------------");
                    		System.out.println("Este es el Inventario Actual de Materiales");
                    		Leerclavo(connection);
                    		scanner.nextLine();	                    	
                    		break;
                    		
	                    case 7:
	                        System.out.println("Nos vemos :D.");
	                        return;
	                    default:
	                        System.out.println("INGRESE OPCION CORRECTA.");
	                        break;
	                }
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        } finally {
	            if (connection != null) {
	             try {
	                connection.close();
	                } catch (SQLException e) {
	                    e.printStackTrace();
	                }
	            }
	        }
	    }
	    
	    //*************************************Metodos de PRODUCTO***************************************************//
	    public static void insertarProducto(Connection connection, Producto producto) throws SQLException {
	        String sql = "INSERT INTO producto (nombre, precio, cantidad) VALUES (?, ?, ?)";
	        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
	            preparedStatement.setString(1, producto.getNombre());
	            preparedStatement.setFloat(2, producto.getPrecio());
	            preparedStatement.setInt(3, producto.getCantidad());
	            preparedStatement.executeUpdate();
	            System.out.println("Producto agregado");
	        }
	    }

	    public static void leerProductos(Connection connection) throws SQLException {
	        String sql = "SELECT * FROM producto";
	        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
	             ResultSet resultSet = preparedStatement.executeQuery()) {
	            while (resultSet.next()) {
	             String nombre = resultSet.getString("nombre");
	             Float precio = resultSet.getFloat("precio");
	             int cantidad = resultSet.getInt("cantidad");
	             System.out.println("Nombre: " + nombre + ", Precio: " + precio+" Existencias: "+ cantidad);
	            }
	        }
	    }

	    public static void ActualizarInventa(Connection connection, String nombre, float nuevoPrecio, int nuevaCantidad) throws SQLException {
	        String sql = "UPDATE producto SET precio = ?, cantidad = ? WHERE nombre = ?";
	        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
	            preparedStatement.setFloat(1, nuevoPrecio);
	            preparedStatement.setInt(2, nuevaCantidad);
	            preparedStatement.setString(3, nombre);
	            preparedStatement.executeUpdate();
	            System.out.println("------------------------");
	            System.out.println("Se crearon Las Sillas");
	            System.out.println("------------------------");
	        }
	    }

	    public static void eliminarProducto(Connection connection, String nombre) throws SQLException {
	        String sql = "DELETE FROM producto WHERE nombre = ?";
	        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
	            preparedStatement.setString(1, nombre);
	            preparedStatement.executeUpdate();
	            System.out.println("Producto eliminado");
	        }
	    }
	    public static float obtenerPrecioSilla(Connection connection, String nombre) throws SQLException {
	        String sql = "SELECT precio FROM producto WHERE nombre = ?";
	        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
	            preparedStatement.setString(1, nombre);
	            ResultSet resultSet = preparedStatement.executeQuery();
	            if (resultSet.next()) {
	                float precio = resultSet.getFloat("precio");
	                return precio;
	            }
	        }
	        return 0;
	    }
	    public static int ObtenerCantPro(Connection connection, String nombre) {
	        int cantidadActualP = 0;
	        try {
	            String sql = "SELECT cantidad FROM producto WHERE nombre = ?";
	            PreparedStatement preparedStatement = connection.prepareStatement(sql);
	            preparedStatement.setString(1, nombre);
	            ResultSet resultSet = preparedStatement.executeQuery();
	            if (resultSet.next()) {
	                cantidadActualP = resultSet.getInt("cantidad");
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return cantidadActualP;
	    }
	  //*************************************FIN PRODUCTO***************************************************//
	    public static void Leerclavo(Connection connection) throws SQLException {
	        String sql = "SELECT * FROM Ensamble";
	        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
	             ResultSet resultSet = preparedStatement.executeQuery()) {
	            while (resultSet.next()) {
	            	String nombre = "clavo";
	             nombre = resultSet.getString("nombre");
	             int cantidad = resultSet.getInt("cantidad");
	             System.out.println("Nombre: " + nombre + ", cantidad: "+ cantidad);
	            }
	        }
	    }
	    public static void restarMateriales(Connection connection, String nombre, int cantidad) throws SQLException {
	        String sql = "UPDATE Ensamble SET cantidad = cantidad - ? WHERE nombre = ?";
	        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
	            preparedStatement.setInt(1, cantidad);
	            preparedStatement.setString(2, nombre);
	            preparedStatement.executeUpdate();
	            System.out.println(""+nombre+" Restados correctamente.");
	        }
	    }
	  //*************************************INICIO VALIDADORES***************************************************//
	    public static boolean puedeCrearSillas(Connection connection, int cantidadSillas) {
	        int cantidadClavo = 0;
	        int cantidadTabla = 0;
	        try {
	            String sqlClavo = "SELECT cantidad FROM Ensamble WHERE nombre = 'clavo'";
	            String sqlTabla = "SELECT cantidad FROM Ensamble WHERE nombre = 'tabla'";

	            PreparedStatement preparedStatementClavo = connection.prepareStatement(sqlClavo);
	            ResultSet resultSetClavo = preparedStatementClavo.executeQuery();
	            if (resultSetClavo.next()) {
	                cantidadClavo = resultSetClavo.getInt("cantidad");
	            }

	            PreparedStatement preparedStatementTabla = connection.prepareStatement(sqlTabla);
	            ResultSet resultSetTabla = preparedStatementTabla.executeQuery();
	            if (resultSetTabla.next()) {
	                cantidadTabla = resultSetTabla.getInt("cantidad");
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        // Suponemos que la silla utiliza los siguientes materiales por unidad
	        int tablasNecesariasPorSilla = 5;
	        int clavosNecesariosPorSilla = 10;

	        int tablasNecesarias = tablasNecesariasPorSilla * cantidadSillas;
	        int clavosNecesarios = clavosNecesariosPorSilla * cantidadSillas;

	        return cantidadTabla >= tablasNecesarias && cantidadClavo >= clavosNecesarios;
	    }
	    //VALIDADRO EXISTENCIA CASE 3
	    public static boolean validarNombreExistente(Connection connection, String nombre) throws SQLException {
	        String sql = "SELECT * FROM producto WHERE nombre = ?";
	        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
	            preparedStatement.setString(1, nombre);
	            ResultSet resultSet = preparedStatement.executeQuery();
	            return resultSet.next();
	        }
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
	    public static void insertarCliente(Connection connection, Cliente cliente) throws SQLException {
	        String sqlInsert = "INSERT INTO Cliente (nombre, apellido, nit) VALUES ( ?, ?, ?)";
	        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlInsert)) {
	            preparedStatement.setString(1, cliente.getNombre());
	            preparedStatement.setString(2, cliente.getApellido());
	            preparedStatement.setInt(3, cliente.getNit());
	            preparedStatement.executeUpdate();
	            System.out.println("Cliente agregado a la base de datos.");
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    public static void dibujarConejo() {
	        System.out.println("   (\\(\\");
	        System.out.println("   ( -.-)");
	        System.out.println("o_(\")(\")");
	    }
	
	}

