/*
 * TP JAVA RIP
 * Min Serveur FTP
 * */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

	public static void main(String[] args) {
		ServerSocket serveurFTP;
		Socket socket = null;
		BufferedReader br;
		PrintStream ps;
		String commande;
		
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
				System.out.println("Connexion établie");
				br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				ps = new PrintStream(socket.getOutputStream());
				CommandExecutor.pwOk = false;
				CommandExecutor.userOk = false;
				ps.println("1 Bienvenue ! ");
				ps.println("1 Serveur FTP Personnel.");
				ps.println("0 Authentification : ");
				
				// Attente de reception de commandes et leur execution
				while(true) {
					commande = br.readLine();
					if (commande == null || commande.equals("bye")) break;
					System.out.println(">> "+commande);
					CommandExecutor.executeCommande(ps, commande);
				}
				System.out.println("Fin de la connexion");
				System.out.println("Attente d'une nouvelle connexion...");
			} catch (IOException e) {
				break;
			}
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
