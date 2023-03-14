package serveur;/*
 * TP JAVA RIP
 * Min Serveur FTP
 * */


import client.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicReference;

public class Serveur {

	public static void main(String[] args) {
		
		ServerSocket serveurFTP;
		Socket socket;
		
		System.out.println("Lancement du Serveur FTP");

		try {
			serveurFTP = new ServerSocket(2121);
		} catch (IOException e) {
			System.out.println("Erreur de creation du serveur FTP");
			throw new RuntimeException(e);
		}
		
		while (true){
			
			try {
				socket = serveurFTP.accept();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			Socket finalSocket = socket;
			AtomicReference<Boolean> fin = new AtomicReference<>(false);
			Thread t = new Thread(()  -> {
				try {
					Client client = new Client(System.getProperty("user.dir"));
					client.userDir = System.getProperty("user.dir");
					String commande;
					System.out.println("Connexion établie");
					BufferedReader br = new BufferedReader(new InputStreamReader(finalSocket.getInputStream()));
					PrintStream ps = new PrintStream(finalSocket.getOutputStream());
					client.pwOk = false;
					client.userOk = false;
					ps.println("1 Bienvenue ! ");
					ps.println("1 Serveur FTP Personnel.");
					ps.println("0 Authentification : ");
					
					// Attente de reception de commandes et leur execution
					while(true) {
						commande = br.readLine();
						if (commande == null || commande.equals("bye")) break;
						System.out.println(">> "+ commande);
						CommandExecutor.executeCommande(client,ps, commande);
					}
					System.out.println("Fin de la connexion");
					System.out.println("Attente d'une nouvelle connexion...");
				} catch (IOException e) {
					fin.set(true);
				}
				
			});
			t.start();
			if (fin.get()) break;
		}
		
		try {
			serveurFTP.close();
			if (socket != null)
				socket.close();
			System.out.println("Serveur FTP fermé");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
	}

}
