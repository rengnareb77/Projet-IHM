package serveur;

import client.Client;

import java.io.File;
import java.io.PrintStream;

public class CommandeLS extends Commande {
	
	public CommandeLS(Client client,PrintStream ps, String commandeStr) {
		super(client,ps, commandeStr);
	}

	public void execute() {
		String path = client.userDir;
		File directory = new File(path);
		
		if (!directory.exists()) {
			ps.println("2 Le répertoire spécifié n'existe pas.");
			return;
		}
		File[] files = directory.listFiles();
		if (files == null) {
			ps.println("2 Le répertoire spécifié n'est pas un répertoire.");
			return;
		}
		if (files.length == 0) {
			ps.println("0 Le répertoire spécifié est vide.");
			return;
		}
		for (int i = 0; i < files.length ; i++) {
			String number = "";
			if (i == files.length - 1) {
				number = "0 ";
			} else {
				number = "1 ";
			}
			
			if (files[i].isDirectory()) {
				ps.println(number + files[i].getName() + "/");
			} else {
				ps.println(number + files[i].getName());
			}
		}
		
		
		
		
		
	}

}
