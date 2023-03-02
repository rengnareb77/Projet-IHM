import java.io.File;
import java.io.PrintStream;

public class CommandeUSER extends Commande {
	
	public CommandeUSER(PrintStream ps, String commandeStr) {
		super(ps, commandeStr);
	}

	public void execute() {
		// Ce serveur accepte uniquement le user breton
		if(commandeArgs[0].equalsIgnoreCase("breton")) {
			CommandExecutor.userOk = true;
			ps.println("0 Commande user OK");
			File file = new File(commandeArgs[0]);
			if (!file.exists()) {
				file.mkdir();
			}
			
			
			
		}
		else {
			ps.println("2 Le user " + commandeArgs[0] + " n'existe pas");
		}
		
	}

}
