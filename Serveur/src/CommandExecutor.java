import java.io.PrintStream;

public class CommandExecutor {
	
	
	public static void executeCommande(Client client,PrintStream ps, String commande) {
		if(client.userOk && client.pwOk) {
			switch (commande.split(" ")[0]) {
				case "cd" -> (new CommandeCD(client,ps, commande)).execute();
				case "get" -> (new CommandeGET(client,ps, commande)).execute();
				case "ls" -> (new CommandeLS(client,ps, commande)).execute();
				case "pwd" -> (new CommandePWD(client,ps, commande)).execute();
				case "stor" -> (new CommandeSTOR(client,ps, commande)).execute();
				case "mkdir" -> (new CommandeMKDIR(client,ps, commande)).execute();
				case "rmdir" -> (new CommandeRMDIR(client,ps, commande)).execute();
				default -> ps.println("2 Commande inconnue !");
			}
			
		} else {
			if(commande.split(" ")[0].equals("pass") || commande.split(" ")[0].equals("user")) {
				// Le mot de passe pour l'authentification
				if(commande.split(" ")[0].equals("pass"))
					(new CommandePASS(client,ps, commande)).execute();
	
				// Le login pour l'authentification
				if(commande.split(" ")[0].equals("user"))
					(new CommandeUSER(client,ps, commande)).execute();
			}
			else
				ps.println("2 Vous n'êtes pas connecté !");
		}
	}

}
