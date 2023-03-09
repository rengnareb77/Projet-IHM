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
		
		String path = client.userDir;
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
		
		if (commandeArgs[0].contains("..")) {
			String commandePath = commandeArgs[0];
			if (commandePath.endsWith("/")) {
				commandePath = commandePath.substring(0, commandePath.length() - 1);
			}
			int nbDots = commandePath.split("/").length;
			for (int i = 0; i < nbDots; i++) {
				path = path.substring(0, path.lastIndexOf("/"));
			}
			client.userDir =  path;
			ps.println("0 Déplacement effectué vers " + commandeArgs[0]);
			return;
		}
		
		// Déplacement vers le répertoire (non fonctionnel)
		client.userDir =  directory.getAbsolutePath();
		ps.println("0 Déplacement effectué vers " + commandeArgs[0]);
		
	}

}
