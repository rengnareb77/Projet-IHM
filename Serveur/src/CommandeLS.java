import java.io.File;
import java.io.PrintStream;

public class CommandeLS extends Commande {
	
	public CommandeLS(PrintStream ps, String commandeStr) {
		super(ps, commandeStr);
	}

	public void execute() {
		String path = System.getProperty("user.dir");
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
