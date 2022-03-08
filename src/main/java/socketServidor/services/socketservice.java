package socketServidor.services;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import socketServidor.DAO.accountController;
import socketServidor.DAO.userController;
import socketServidor.models.SendServer;

public class socketservice {

	// El servidor crea un nuevo hilo para el cliente que se une, del cual
	// está a la escucha hasta que se cierra su conexion.
	// El servidor leerá las acciones del cliente (o mas bien las opciones
	// que solicita al servidor, como iniciar sesión o ingresar dinero)

	public static void readServerInputs(final Socket cliente) {
		new Thread(() -> {
			try {
				while (!cliente.isClosed()) {

					leer(cliente);
				}
			} catch (Exception ex) {
				try {
					// si ocurre algún error se cierra la conexión con el cliente
					cliente.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}

		}).start();

	}

	// la información que le llega al cliente pasa por este método
	private static void sendDataToClient(Socket client, Object objeto) {

		// si existe el cliente y no se ha cerrado la conexión con él continuamos
		if (client != null && !client.isClosed()) {

			// flujo de salida
			ObjectOutputStream objectOutputStream;
			try {
				// escritura y envio de los datos hacia cliente
				objectOutputStream = new ObjectOutputStream(client.getOutputStream());
				objectOutputStream.writeObject(objeto);
				objectOutputStream.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// el servidor está pendiente de las peticiones del cliente para realizar una
	// respuesta
	public static void leer(Socket cliente) {
		try {
			// flujo que obtenemos del cliente
			ObjectInputStream dataInputStream = new ObjectInputStream(cliente.getInputStream());
			try {
				// pasamos los datos del flujo a un objeto
				SendServer sendtoserver = (SendServer) dataInputStream.readObject();

				userController u1;
				userController u2;
				accountController ac1;
				accountController ac2;
				SendServer sendtoserver2;

				int opcion = sendtoserver.getOpcion();
				switch (opcion) {

				// iniciar sesion
				case 1:
					u1 = (userController) sendtoserver.getObject1();
					u2 = new userController();

					// comprueba las credenciales recibidas del cliente para evitar clientes ya
					// existentes
					if (u2.checkCredentials(u1.getName(), u1.getPassword())) {
						// existe el usuario
						System.out.println("Usuario introducido correcto");
						userController u3 = new userController();
						u3.getUserById(u2.getId());

						// se envia esta informacion
						sendtoserver2 = new SendServer(1, u3);

						// enviar al cliente
						sendDataToClient(cliente, sendtoserver2);
					} else {
						System.out.println("Usuario introducido incorrecto");

						// se envia esta informacion
						sendtoserver2 = new SendServer(1, new Object(), false);

						// enviar al cliente
						sendDataToClient(cliente, sendtoserver2);
					}
					break;

				// ingresar dinero
				case 2:
					// obtenemos la cuenta a la que ingresar
					ac1 = (accountController) sendtoserver.getObject1();
					// por id, le asignamos la cantidad que se suma propuesta por el cliente a la
					// cuenta
					ac1.IngresaDinero(ac1.getId(), sendtoserver.getMoney());
					System.out.println("Ingreso realizado");

					// se envia esta informacion
					sendtoserver2 = new SendServer(2, ac1);

					// enviar al cliente
					sendDataToClient(cliente, sendtoserver2);

					break;

				// retirar dinero
				case 3:
					// obtenemos la cuenta a la que retirar
					ac1 = (accountController) sendtoserver.getObject1();
					// por id, le retiramos la cantidad propuesta por el cliente a la cuenta
					ac1.RetiraDinero(ac1.getId(), sendtoserver.getMoney());
					System.out.println("Retirada realizada");

					// se envia esta informacion
					sendtoserver2 = new SendServer(3, ac1);

					// enviar al cliente
					sendDataToClient(cliente, sendtoserver2);

					break;

				// listar cuentas
				case 4:
					ac1 = new accountController();

					// se envia esta informacion
					sendtoserver2 = new SendServer(4, ac1.getAllAccounts());

					// enviar al cliente
					sendDataToClient(cliente, sendtoserver2);
					break;

				// listar usuarios
				case 5:
					u1 = new userController();

					// se envia esta informacion
					sendtoserver2 = new SendServer(5, u1.getAllUsers());

					// enviar al cliente
					sendDataToClient(cliente, sendtoserver2);
					break;

				// registrarse
				case 6:
					// nos traemos la información que nos proporciona el cliente
					u1 = (userController) sendtoserver.getObject1();

					// y se lo asignamos a un nuevo usuario
					u2 = new userController();
					u2.setName(u1.getName());
					u2.setPassword(u1.getPassword());
					u2.setWallet(0);
					u2.createUser(u2);
					System.out.println("Usuario añadido a la base de datos");

					// a su vez le asignamos una nueva cuenta por defecto
					ac1 = (accountController) sendtoserver.getObject2();
					ac2 = new accountController();
					ac2.setMiuser(u2);
					ac2.setMoney(0);
					ac2.createAccount(ac1);
					System.out.println("Cuenta añadida y asociada al nuevo usuario");

					// se envia esta informacion
					sendtoserver2 = new SendServer(6, u2, ac1);

					// enviar al cliente
					sendDataToClient(cliente, sendtoserver2);
					break;

				default:
					break;

				}
			} catch (ClassNotFoundException e) {
				// si ocurre algún error se cierran las conexiones con el cliente
				e.printStackTrace();
				cliente.close();
				dataInputStream.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
