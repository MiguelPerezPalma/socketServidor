package socketServidor.services;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

import socketServidor.DAO.accountController;
import socketServidor.DAO.userController;
import socketServidor.models.SendServer;
import socketServidor.models.account;
import socketServidor.models.user;

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
	public static void leer(Socket cliente) throws SocketException {
		ObjectInputStream dataInputStream = null;
		try {
			// flujo que obtenemos del cliente
			dataInputStream = new ObjectInputStream(cliente.getInputStream());

			// pasamos los datos del flujo a un objeto
			SendServer paquete = (SendServer) dataInputStream.readObject();
			SendServer nuevoPaquete;

			user userClient = (user) paquete.getObject1();
			account accountClient = (account) paquete.getObject2();

			int opcion = paquete.getOpcion();
			switch (opcion) {

			// iniciar sesion
			case 1:
				// comprueba las credenciales recibidas del cliente para evitar clientes ya
				// existentes
				if (userController.checkCredentials(userClient.getName(), userClient.getPassword())) {
					// existe el usuario
					System.out.println("Usuario introducido correcto");
					userClient = userController.getUserById(userClient.getId());
					accountClient = accountController.getAccountByUserId(userClient.getId());

					// se envia esta informacion
					paquete = new SendServer(1, userClient, accountClient);
					// enviar al cliente
					sendDataToClient(cliente, paquete);

					System.out.println("Paquete enviado con éxito");
				}
				break;

			// ingresar dinero
			case 2:
				// obtenemos la cuenta a la que ingresar
				accountClient = accountController.actualizaDinero(accountClient, true, paquete.getMoney());
				System.out.println("Ingreso realizado");

				// se envia esta informacion
				nuevoPaquete = new SendServer(2, accountClient);

				// enviar al cliente
				sendDataToClient(cliente, nuevoPaquete);

				break;

//			// retirar dinero
			case 3:
				// obtenemos la cuenta a la que retirar
				accountClient = accountController.actualizaDinero(accountClient, false, paquete.getMoney());
				System.out.println("Retirada realizada");

				// se envia esta informacion
				nuevoPaquete = new SendServer(2, accountClient);

				// enviar al cliente
				sendDataToClient(cliente, nuevoPaquete);

				break;
//
//			// listar cuentas
//			case 4:
//				ac1 = new accountController();
//
//				// se envia esta informacion
//				sendtoserver2 = new SendServer(4, ac1.getAllAccounts());
//
//				// enviar al cliente
//				sendDataToClient(cliente, sendtoserver2);
//				break;
//
//			// listar usuarios
//			case 5:
//				u1 = new userController();
//
//				// se envia esta informacion
//				sendtoserver2 = new SendServer(5, u1.getAllUsers());
//
//				// enviar al cliente
//				sendDataToClient(cliente, sendtoserver2);
//				break;
//
//			// registrarse
//			case 6:
//				// nos traemos la información que nos proporciona el cliente
//				u1 = (userController) paquete.getObject1();
//
//				// y se lo asignamos a un nuevo usuario
//				u2 = new userController();
//				u2.setName(u1.getName());
//				u2.setPassword(u1.getPassword());
//				u2.setWallet(0);
//				u2.createUser(u2);
//				System.out.println("Usuario añadido a la base de datos");
//
//				// a su vez le asignamos una nueva cuenta por defecto
//				ac1 = (accountController) paquete.getObject2();
//				ac2 = new accountController();
//				ac2.setMiuser(u2);
//				ac2.setMoney(0);
//				ac2.createAccount(ac1);
//				System.out.println("Cuenta añadida y asociada al nuevo usuario");
//
//				// se envia esta informacion
//				sendtoserver2 = new SendServer(6, u2, ac1);
//
//				// enviar al cliente
//				sendDataToClient(cliente, sendtoserver2);
//				break;

			default:
				break;

			}
		} catch (IOException | ClassNotFoundException e) {
			if (e instanceof EOFException) {
				throw new SocketException(e.getMessage());
			} else if (e instanceof ClassNotFoundException) {
				throw new SocketException("Clase no encontrada");
			} else {
				e.printStackTrace();
			}
		}
	}
}
