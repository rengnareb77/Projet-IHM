//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.io.File;
import java.io.PrintStream;

public class CommandeRMDIR extends Commande {
    public CommandeRMDIR(Client client,PrintStream ps, String commandeStr) {
        super(client,ps, commandeStr);
    }
    
    public void execute() {
        // Vérification du nombre d'arguments
        if (commandeArgs.length < 1){
            this.ps.println("2 Il est nécessaire de spécifier un nom de dossier");
            return;
        }
        String path = client.userDir;
        File file = new File(path + "/" + this.commandeArgs[0]);
        File [] files = file.listFiles();
    
        // Vérification de l'existence du dossier
        if (files == null){
            this.ps.println("2 Le dossier n'existe pas");
            return;
        }
        
        if (!file.isDirectory()){
            this.ps.println("2 Le dossier fourni n'est pas un dossier");
            return;
        }
        
        if (files.length > 0){
            this.ps.println("2 Le dossier n'est pas vide");
            return;
        }
        
        if (file.delete()){
            this.ps.println("0 Le dossier a été supprimé");
        } else {
            this.ps.println("2 Le dossier n'a pas pu être supprimé");
        }

    }
}
