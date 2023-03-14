package serveur;

import client.Client;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class CommandeGET extends Commande {
	
	public CommandeGET(Client client,PrintStream ps, String commandeStr) {
		super(client,ps, commandeStr);
	}

	
	public void execute()  {
		System.out.println(">> " + this.commandeNom);
		String line;
		// Si le client n'a pas spécifié de fichier
		if (commandeArgs.length < 1) {
			ps.println("2 Il faut spécifier un fichier");
			return;
		}
		
		String path = client.userDir;
		File file = new File(path + "/" + commandeArgs[0]);
		
		// Si le fichier n'existe pas
		if (!file.exists()) {
			ps.println("2 Le fichier spécifié n'existe pas.");
			return;
		}
		
		// Si le fichier spécifié n'est pas un fichier
		if (!file.isFile()) {
			ps.println("2 Le fichier spécifié n'est pas un fichier.");
			return;
		}
		
		// Sinon OK
		ps.println("0");
		
		// Envoi du fichier
		try {
			// Création du socket d'envoi du fichier
			ServerSocket serverSocketEnvoi = new ServerSocket(4000);
			Socket socketEnvoi =  serverSocketEnvoi.accept();
			
			// Stream d'envoi du fichier
			PrintStream psFichier = new PrintStream(socketEnvoi.getOutputStream());
			
			// Reader pour lire la réponse du client
			BufferedReader brFichier = new BufferedReader(new InputStreamReader(socketEnvoi.getInputStream()));
			
			// Reader pour lire le fichier à envoyer
			BufferedReader bw = new BufferedReader(new FileReader(file));
			
			line = brFichier.readLine();
			// Si le client a refusé l'envoi du fichier
			if (line.startsWith("2")) {
				ps.println(line);
				serverSocketEnvoi.close();
				socketEnvoi.close();
				psFichier.close();
				brFichier.close();
				bw.close();
				return;
			}
		
			// Envoi du fichier
			while ((line = bw.readLine()) != null) {
				psFichier.println(line);
			}
			bw.close();
			psFichier.close();
			
			// Fin d'envoi du fichier
			ps.println("0");
			
			// Fermeture des streams et sockets
			
			brFichier.close();
			socketEnvoi.close();
			serverSocketEnvoi.close();
			
		} catch (IOException e) {
			e.printStackTrace();
			ps.println("2 Une erreur est survenue lors de l'envoi du fichier.");
		}
	}
}
