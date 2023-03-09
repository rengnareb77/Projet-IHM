//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.io.File;
import java.io.PrintStream;

public class CommandeMKDIR extends Commande {
    public CommandeMKDIR(Client client,PrintStream ps, String commandeStr) {
        super(client,ps, commandeStr);
    }
    
    public void execute() {
        if (commandeArgs.length < 1){
            this.ps.println("2 Il est nécessaire de spécifier un nom de dossier");
            return;
        }
        String path = client.userDir;
        File file = new File(path + "/" + this.commandeArgs[0]);
        if (file.exists()){
            this.ps.println("2 Le dossier existe déjà");
            return;
        }
        if (file.mkdir()){
            this.ps.println("0 Le dossier a été créé");
        } else {
            this.ps.println("2 Le dossier n'a pas pu être créé");
        }
    }
}
