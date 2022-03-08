package socketServidor.services;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import socketServidor.DAO.accountController;
import socketServidor.DAO.userController;
import socketServidor.models.SendServer;

public class socketservice {

	public static void readServerInputs(final Socket cliente) {
		new Thread(() -> {
			System.out.println("Server");
			try {
				while (!cliente.isClosed()) {

					leer(cliente);
				}
			} catch (Exception ex) {
				try {
					cliente.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		}).start();

	}

	public static void leer(Socket cliente) {
		try {
			ObjectInputStream dataInputStream = new ObjectInputStream(cliente.getInputStream());
			try {
				SendServer sendtoserver = (SendServer) dataInputStream.readObject();
				System.out.println(sendtoserver);

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

					if (u2.checkCredentials(u2.getName(), u2.getPassword())) {
						// existe el usuario
						System.out.println("Usuario introducido correcto");
						userController u3 = new userController();
						u3.getUserById(u2.getId());

						// se envia esta informacion
						sendtoserver2 = new SendServer(1, u3, true);

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
					ac1 = (accountController) sendtoserver.getObject1();
					ac1.IngresaDinero(ac1.getId(), sendtoserver.getMoney());
					System.out.println("Ingreso realizado");
					System.out.println(ac1.toString());

					// se envia esta informacion
					sendtoserver2 = new SendServer(2, ac1);

					// enviar al cliente
					sendDataToClient(cliente, sendtoserver2);

					break;

				// retirar dinero
				case 3:
					ac1 = (accountController) sendtoserver.getObject1();
					ac1.RetiraDinero(ac1.getId(), sendtoserver.getMoney());
					System.out.println("Retirada realizada");
					System.out.println(ac1.toString());

					// se envia esta informacion
					sendtoserver2 = new SendServer(3, ac1);

					// enviar al cliente
					sendDataToClient(cliente, sendtoserver2);

					break;

				// listar cuentas
				case 4:
					ac1 = (accountController) sendtoserver.getObject1();

					// se envia esta informacion
					sendtoserver2 = new SendServer(4, ac1.getAllAccounts());

					// enviar al cliente
					sendDataToClient(cliente, sendtoserver2);
					break;

				// listar usuarios
				case 5:
					u1 = (userController) sendtoserver.getObject1();

					// se envia esta informacion
					sendtoserver2 = new SendServer(5, u1.getAllUsers());

					// enviar al cliente
					sendDataToClient(cliente, sendtoserver2);
					break;

				// registrarse
				case 6:
					u1 = (userController) sendtoserver.getObject1();

					u2 = new userController();
					// u2.setId(opcion);
					u2.setName(null); //arreglar
					u2.setPassword(null); //arreglar
					u2.setWallet(0);
					u2.createAccount(u2);
					System.out.println("Usuario añadido a la base de datos");

					ac1 = new accountController();
					//ac.setId(opcion);
					ac1.setMiuser(u2);
					ac1.setMoney(0);
					ac1.createAccount(ac1);
					System.out.println("Cuenta añadida y asociada al nuevo usuario");
					
					// se envia esta informacion
					sendtoserver2 = new SendServer(6, u2, ac1);

					// enviar al cliente
					sendDataToClient(cliente, sendtoserver2);
					break;

				// crear cuenta
				case 7:
					ac1 = (accountController) sendtoserver.getObject1();

					ac2 = new accountController();
					ac2.setMiuser(null); //arreglar
					ac2.setMoney(0);

					// se envia esta informacion
					sendtoserver2 = new SendServer(7, ac2);

					// enviar al cliente
					sendDataToClient(cliente, sendtoserver2);
					break;

				default:
					break;

				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				cliente.close();
				dataInputStream.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void sendDataToClient(Socket client, Object objeto) {
		if (client != null && !client.isClosed()) {
			ObjectOutputStream objectOutputStream;
			try {
				objectOutputStream = new ObjectOutputStream(client.getOutputStream());
				objectOutputStream.writeObject(objeto);
				objectOutputStream.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
