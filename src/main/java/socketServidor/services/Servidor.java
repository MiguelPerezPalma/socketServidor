package socketServidor.services;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
	
	private ServerSocket serverSocket;
	
	public Servidor() {
		try {
			//objeto serversocket, indicando el puerto al que se deber�n conectar
			this.serverSocket = new ServerSocket(2024);
			System.out.println("Servidor ejecut�ndose");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void listenToClients() throws IOException {
		while (true) {
			//creamos el objeto socket y aceptamos su conexi�n al servidor
			Socket cliente = this.serverSocket.accept();
			//creamos nuevo hilo en el que cada hilo es un nuevo cliente
			new Thread(new ClientWorker(cliente)).start();
		}
	}
}
