import java.io.File;
import java.io.PrintStream;

public class CommandeCD extends Commande {
	
	public CommandeCD(PrintStream ps, String commandeStr) {
		super(ps, commandeStr);
	}

	public void execute() {
		// Vérification du nombre d'arguments
		if (commandeArgs.length < 1) {
			ps.println("2 Il faut spécifier un répertoire");
			return;
		}
		
		String path = System.getProperty("user.dir");
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
		
		// Déplacement vers le répertoire (non fonctionnel)
		System.setProperty("user.dir", directory.getAbsolutePath());
		ps.println("0 Déplacement effectué vers " + commandeArgs[0]);
		
		System.out.println(directory.getAbsolutePath());
	}

}
