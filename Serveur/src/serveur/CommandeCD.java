package serveur;

import client.Client;

import java.io.File;
import java.io.PrintStream;

public class CommandeCD extends Commande {
	
	public CommandeCD(Client client,PrintStream ps, String commandeStr) {
		super(client,ps, commandeStr);
	}

	public void execute() {
		// Vérification du nombre d'arguments
		if (commandeArgs.length < 1) {
			ps.println("2 Il faut spécifier un répertoire");
			return;
		}
		
		StringBuilder path = new StringBuilder(client.userDir);
		File directory = new File(path + "/" + commandeArgs[0]);
		// Vérification de l'existence du répertoire
		if (!directory.exists()) {
			ps.println("2 Le répertoire spécifié n'existe pas.");
			return;
		}
		// Vérification que c'est un répertoire
		if (!directory.isDirectory()) {
			ps.println("2 Le répertoire spécifié n'est pas un répertoire.");
			return;
		}
		
		// Cas du déplacement vers le répertoire parent
		if (commandeArgs[0].contains("..")) {
			String commandePath = commandeArgs[0];
			if (commandePath.endsWith("/")) {
				commandePath = commandePath.substring(0, commandePath.length() - 1);
			}
			
			// Cas des multiples déplacements avec des ".."
			String[] listFolder =  commandePath.split("/");
			
			for (String folder : listFolder) {
				if (folder.equals(".."))
					path = new StringBuilder(path.substring(0, path.lastIndexOf("/")));
				else
					path.append("/").append(folder);
			}
			
			// Vérification que le chemin ne sort pas du répertoire de travail
			if (path.length() <= client.workspace.length()) {
			 ps.println("2 Vous ne pouvez pas sortir du répertoire de travail.");
			 return;
			}
			
			client.userDir = path.toString();
			ps.println("0 Déplacement effectué vers " + commandeArgs[0]);
			return;
		}
		
		client.userDir += "/" + commandeArgs[0];
		ps.println("0 Déplacement effectué vers " + commandeArgs[0]);
		
	}

}
