import java.io.PrintStream;

public abstract class Commande {
	
	protected PrintStream ps;
	protected String commandeNom = "";
	protected String [] commandeArgs ;
	
	protected Client client;
	
	public Commande(Client client,PrintStream ps, String commandeStr) {
		this.ps = ps ;
		this.client = client;
		String [] args = commandeStr.split(" ");
		commandeNom = args[0];
		commandeArgs = new String[args.length-1];
		
		for(int i=0; i<commandeArgs.length; i++) {
			commandeArgs[i] = args[i+1];
		}
	}
	
	public abstract void execute();

}
