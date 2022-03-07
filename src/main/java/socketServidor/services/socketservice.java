package socketServidor.services;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import socketServidor.DAO.accountController;
import socketServidor.DAO.userController;
import socketServidor.models.SendServer;

public class socketservice {
	public static void main(String[] args) {

		ServerSocket servidor;
		Socket cliente = null;

		try {
			servidor = new ServerSocket(2024);
			cliente = servidor.accept();
			
			readServerInputs(cliente);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		

	}

	private static void readServerInputs(final Socket cliente) {
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
				accountController ac;
				SendServer sendtoserver2;
				
				int opcion = sendtoserver.getOpcion();
				switch (opcion) {
				
				//iniciar sesion
				case 1:
					u1 = (userController) sendtoserver.getObject1();
					u2 = new userController();
					
					if (u2.checkCredentials(u2.getName(), u2.getPassword())) {
						//existe el usuario
						System.out.println("Usuario introducido correcto");
						userController u3 = new userController();
						u3.getUserById(u2.getId());
						
						//se envia esta informacion
						sendtoserver2 = new SendServer(1, u3, true);
						
						//enviar al cliente
						sendDataToClient(cliente, sendtoserver2);
					} else {
						System.out.println("Usuario introducido incorrecto");
						
						//se envia esta informacion
						sendtoserver2 = new SendServer(1, new Object(), false);
						
						//enviar al cliente
						sendDataToClient(cliente, sendtoserver2);
					}
					break;
					
				//ingresar dinero
				case 2:
					ac = (accountController) sendtoserver.getObject1();
					ac.IngresaDinero(ac.getId(), sendtoserver.getMoney());
					System.out.println("Ingreso realizado");
					System.out.println(ac.toString());
					
					//se envia esta informacion
					sendtoserver2 = new SendServer(2, ac);
					
					//enviar al cliente
					sendDataToClient(cliente, sendtoserver2);
					
					break;
				
				//retirar dinero
				case 3:
					ac = (accountController) sendtoserver.getObject1();
					ac.RetiraDinero(ac.getId(), sendtoserver.getMoney());
					System.out.println("Retirada realizada");
					System.out.println(ac.toString());
					
					//se envia esta informacion
					sendtoserver2 = new SendServer(3, ac);
					
					//enviar al cliente
					sendDataToClient(cliente, sendtoserver2);
					
					break;
					
				//listar cuentas
				case 4:
					ac = (accountController) sendtoserver.getObject1();
					
					//se envia esta informacion
					sendtoserver2 = new SendServer(4, ac.getAllAccounts());
					
					//enviar al cliente
					sendDataToClient(cliente, sendtoserver2);
					break;
					
				//listar usuarios
				case 5:
					u1 = (userController) sendtoserver.getObject1();
					
					//se envia esta informacion
					sendtoserver2 = new SendServer(5, u1.getAllUsers());
					
					//enviar al cliente
					sendDataToClient(cliente, sendtoserver2);
					break;
					
				//registrarse
				case 6:
					u1 = (userController) sendtoserver.getObject1();
					
					u2 = new userController();
					//u2.setId(opcion);
					u2.setName(null);
					u2.setPassword(null);
					u2.setWallet(0);
					u2.createAccount(u2);
					System.out.println("Usuario añadido a la base de datos");
					
					ac = new accountController();
					//ac.setId(opcion);
					ac.setMiuser(u2);
					ac.setMoney(0);
					ac.createAccount(ac);
					System.out.println("Cuenta añadida y asociada al nuevo usuario");
					
					//se envia esta informacion
					sendtoserver2 = new SendServer(6, ac);
					
					//enviar al cliente
					sendDataToClient(cliente, sendtoserver2);

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
	
	private static void sendDataToClient(Socket client,Object objeto) {
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
