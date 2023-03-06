import java.io.*;

public class CommandePASS extends Commande {
	
	public CommandePASS(PrintStream ps, String commandeStr) {
		super(ps, commandeStr);
	}

	public void execute() {
		if (!CommandExecutor.userOk) {
			ps.println("2 Veuillez utiliser la commande `user` avant");
			return;
		}
		String path = System.getProperty("user.dir");
		File f = new File(path);
		String[] files = f.list();
		if (files == null) {
			ps.println("2 Une erreur est survenue");
			return;
		}
		boolean contains = false;
		for (String file : files) {
			if (file.equals("pw.txt")) {
				contains = true;
				break;
			}
		}
		if (!contains) {
			ps.println("2 Une erreur est survenue");
			return;
		}
		try{
			BufferedReader br = new BufferedReader(new FileReader(path + "/pw.txt"));
			String pass = br.readLine();
			if (pass.equals(commandeArgs[0])) {
				CommandExecutor.pwOk = true;
				ps.println("1 Commande pass OK");
				ps.println("0 Vous êtes bien connecté sur notre serveur");
			}
			else {
				ps.println("2 Le mot de passe est incorrect");
			}
		} catch (IOException e) {
			ps.println("2 Une erreur est survenue");
			
		}
		
		
		
	}

}
