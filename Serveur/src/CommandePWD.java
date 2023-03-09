import java.io.File;
import java.io.PrintStream;

public class CommandePWD extends Commande {
	
	public CommandePWD(Client client,PrintStream ps, String commandeStr) {
		super(client,ps, commandeStr);
	}

	public void execute() {
		String s = client.userDir;
		ps.println("0 " + s);
	}

}
