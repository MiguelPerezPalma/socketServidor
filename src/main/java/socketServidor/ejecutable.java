package socketServidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import socketServidor.Coneection.Conexion;
import socketServidor.services.socketservice;

public class ejecutable {

	public static void main(String[] args) {
		
		try {
			// el servidor comienza a ejecutarse
			ServerSocket servidor = new ServerSocket(2024);
			System.out.println("Servidor ejecutándose...");
			
			// el servidor acepta la conexión con el cliente
			while (true) {
				Socket cliente = servidor.accept();
				System.out.println("Se ha unido un cliente");
				socketservice.readServerInputs(cliente);
			}
		} catch (IOException e) {
			e.printStackTrace();
			Conexion.cerrar();
		}
	}
}
