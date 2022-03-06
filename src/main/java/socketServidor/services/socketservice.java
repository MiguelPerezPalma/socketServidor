package socketServidor.services;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

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

	public static  void leer(Socket cliente) {
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
