package socketServidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import socketServidor.services.socketservice;

public class ejecutable {

	public static void main(String[] args) {
		try {
			ServerSocket servidor = new ServerSocket(2024);
			System.out.println("Servidor ejecutándose...");
			Socket cliente = servidor.accept();
			System.out.println("Se ha unido un cliente");
			
			socketservice.readServerInputs(cliente);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
