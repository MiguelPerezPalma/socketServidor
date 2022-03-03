package socketServidor.services;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

//clase que valida y procesa la solicitud de red enviada por el cliente
public class ClientWorker implements Runnable {
	
	private Socket socket;
	private DataInputStream flujoEntrada;
	private DataOutputStream flujoSalida;
	
	public ClientWorker(Socket socket) throws IOException {
		//objeto socket del cliente
		this.socket = socket;
		//flujo de entrada (cliente -> servidor)
		this.flujoEntrada = new DataInputStream(this.socket.getInputStream());
	}
	
	public void run() {
		boolean isConnected = true;
		
		while (isConnected) {
			try {
				//si el cliente está conectado, ...
				isConnected = this;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
