package socket.services;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

import socket.DAO.accountController;
import socket.DAO.userController;
import socket.models.Send;
import socket.models.account;
import socket.models.user;

public class socketservice {

	// El servidor crea un nuevo hilo para el cliente que se une, del cual
	// estï¿½ a la escucha hasta que se cierra su conexion.
	// El servidor leerï¿½ las acciones del cliente (o mas bien las opciones
	// que solicita al servidor, como iniciar sesión o ingresar dinero)

	public static void readServerInputs(final Socket cliente) {
		new Thread(() -> {
			try {
				while (!cliente.isClosed()) {

					leer(cliente);
				}
			} catch (Exception ex) {
				try {
					// si ocurre algï¿½n error se cierra la conexiï¿½n con el cliente
					cliente.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}

		}).start();

	}

	// la información que le llega al cliente pasa por este método
	private static void sendDataToClient(Socket client, Send objeto) {

		// si existe el cliente y no se ha cerrado la conexión con él, continuamos
		if (client != null && !client.isClosed()) {

			// flujo de salida
			ObjectOutputStream objectOutputStream;
			try {
				// escritura y envio de los datos hacia cliente
				objectOutputStream = new ObjectOutputStream(client.getOutputStream());
				objectOutputStream.writeObject(objeto);
				// objectOutputStream.flush();
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
			// flujo de entrada que obtenemos del cliente
			dataInputStream = new ObjectInputStream(cliente.getInputStream());
			// pasamos los datos del flujo a un objeto
			Send paquete = (Send) dataInputStream.readObject();
			Send nuevoPaquete;

			user userClient = paquete.getObj1();
			account accountClient = paquete.getObj2();

			int opcion = paquete.getSelect();
			switch (opcion) {

			// iniciar sesion
			case 1:
				// comprueba las credenciales recibidas del cliente para evitar clientes ya
				// existentes
				if (userController.checkCredentials(userClient)) {
					// existe el usuario
					accountClient = accountController.getAccountByUserId(userClient.getId());

					// se envia esta informacion
					paquete = new Send(1, userClient, accountClient);
					// enviar al cliente
					sendDataToClient(cliente, paquete);

				} else {
					userClient.setId(-1);
					paquete = new Send(1, userClient);
					sendDataToClient(cliente, paquete);
				}
				break;

			// ingresar dinero
			case 2:
				// actualizamos la cuenta a la que ingresar
				accountClient = accountController.actualizaDinero(accountClient, true, paquete.getObj2().getMoney());

				// se crea el paquete
				nuevoPaquete = new Send(2, null, accountClient);

				// enviar el paquete al cliente
				sendDataToClient(cliente, nuevoPaquete);

				break;

			// retirar dinero
			case 3:
				// actualizamos la cuenta a la que retirar
				accountClient = accountController.actualizaDinero(accountClient, false, paquete.getObj2().getMoney());

				// se crea el paquete
				nuevoPaquete = new Send(2, null, accountClient);

				// enviar el paquete al cliente
				sendDataToClient(cliente, nuevoPaquete);

				break;

			// registrarse
			case 4:
				userController.createUser(userClient);
				accountClient.setMiuser(userClient);
				accountController.createAccount(accountClient);

				paquete = new Send(4, userClient, accountClient);
				sendDataToClient(cliente, paquete);
				break;

			default:
				break;
			}

		} catch (IOException | ClassNotFoundException e) {
			if (e instanceof EOFException) {
				throw new SocketException(e.getMessage());
			} else if (e instanceof ClassNotFoundException) {
				throw new SocketException("Clase no encontrada");
			} else {
				System.out.println("Se ha desconectado un cliente");
				throw new SocketException("El cliente se ha desconectado");
			}
		}
	}
}
