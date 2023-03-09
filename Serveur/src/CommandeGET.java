import java.io.*;

public class CommandeGET extends Commande {
	
	public CommandeGET(Client client,PrintStream ps, String commandeStr) {
		super(client,ps, commandeStr);
	}

	public void execute() {
		if (commandeArgs.length < 1) {
			ps.println("2 Il faut spécifier un fichier");
			return;
		}
		
		String path = client.userDir;
		File file = new File(path + "/" + commandeArgs[0]);
		// Vérification de l'existence du fichier
		if (!file.exists()) {
			ps.println("2 Le fichier spécifié n'existe pas.");
			return;
		}
		// Vérification que c'est un fichier
		if (!file.isFile()) {
			ps.println("2 Le fichier spécifié n'est pas un fichier.");
			return;
		}
		
		// Envoi du fichier
		try {
			FileReader fw = new FileReader(file);
			BufferedReader bw = new BufferedReader(fw);
		
			String line;
			while ((line = bw.readLine()) != null) {
				if (line.startsWith("0") || line.startsWith("2"))
					line = "\\" + line;
				ps.println(line);
			}
			bw.close();
			ps.println("0 Le fichier a été reçu avec succès.");
		} catch (IOException e) {
			ps.println("2 Une erreur est survenue lors de l'envoi du fichier.");
		}
	}
}
