import java.io.PrintStream;

public class CommandExecutor {
	
	public static boolean userOk = false ;
	public static boolean pwOk = false ;
	
	public static void executeCommande(PrintStream ps, String commande) {
		if(userOk && pwOk) {
			switch (commande.split(" ")[0]) {
				case "cd" -> (new CommandeCD(ps, commande)).execute();
				case "get" -> (new CommandeGET(ps, commande)).execute();
				case "ls" -> (new CommandeLS(ps, commande)).execute();
				case "pwd" -> (new CommandePWD(ps, commande)).execute();
				case "stor" -> (new CommandeSTOR(ps, commande)).execute();
				case "mkdir" -> (new CommandeMKDIR(ps, commande)).execute();
				case "rmdir" -> (new CommandeRMDIR(ps, commande)).execute();
				default -> ps.println("2 Commande inconnue !");
			}
			
		} else {
			if(commande.split(" ")[0].equals("pass") || commande.split(" ")[0].equals("user")) {
				// Le mot de passe pour l'authentification
				if(commande.split(" ")[0].equals("pass"))
					(new CommandePASS(ps, commande)).execute();
	
				// Le login pour l'authentification
				if(commande.split(" ")[0].equals("user"))
					(new CommandeUSER(ps, commande)).execute();
			}
			else
				ps.println("2 Vous n'êtes pas connecté !");
		}
	}

}
