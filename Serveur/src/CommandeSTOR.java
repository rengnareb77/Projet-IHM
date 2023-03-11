import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class CommandeSTOR extends Commande {

    public CommandeSTOR(Client client,PrintStream ps, String commandeStr) {
        super(client,ps, commandeStr);
    }

    public void execute() {
        ps.println("0 Nouveau socket sur le port 4000 est créé pour la transmission des données");
        String filepath = System.getProperty("user.dir");
        System.out.println(filepath);
        System.out.println(this.commandeArgs[0]);
        File file = new File (filepath + "/ressources" +"/" +this.commandeArgs[0]);

        try (ServerSocket dataSocket = new ServerSocket(4000)) {// créer une socket serveur sur le port 4000
            Socket socket = dataSocket.accept(); // attendre la connexion d'un client
            System.out.println("la connexion a été accepté");
            
            InputStream in = socket.getInputStream();// récupérer le flux d'entrée du socket
            OutputStream fileS = new FileOutputStream(filepath+ "/ressources" +"/" +this.commandeArgs[0]);// récupérer le flux de sortie du fichier créer fichier si existe pas

            byte[] buffer = new byte[4096];
            int count;
            // On copie le fichier
            while ((count = in.read(buffer)) > 0) {
                String s = new String(buffer, 0, count);// convertir le tableau de byte en String
                System.out.println(s);
                fileS.write(buffer, 0, count);// écrire dans le fichier
            }
            fileS.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
