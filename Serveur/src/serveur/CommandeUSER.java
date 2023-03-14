package serveur;

import client.Client;

import java.io.File;
import java.io.PrintStream;


public class CommandeUSER extends Commande {
	
	public CommandeUSER(Client client,PrintStream ps, String commandeStr) {
		super(client,ps, commandeStr);
	}

	public void execute() {
		// Ce serveur accepte uniquement le user breton
        String path = client.userDir;
        boolean contains = false;
        File file = new File(path);
        String[] files = file.list();
        if (files == null) {
            ps.println("2 Le user " + commandeArgs[0] + " n'existe pas");
            return;
        }
        for (String f : files) {
            if (f.equals(commandeArgs[0].toLowerCase())) {
                contains = true;
                break;
            }
        }
        
        if (contains) {
            client.userOk = true;
            ps.println("0 Commande user OK");
			path = path + "/" + commandeArgs[0].toLowerCase();
			client.userDir =  path;
        }
        else {
            ps.println("2 Le user " + commandeArgs[0] + " n'existe pas");
        }
		
		
	}

}
