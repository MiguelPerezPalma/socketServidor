package socketServidor;

import java.io.IOException;

import socketServidor.services.Servidor;

public class ejecutable {

	public static void main(String[] args) {
		try {
			//ejecuta el servidor y se pone a la escucha de clientes
			new Servidor().listenToClients();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
