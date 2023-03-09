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

        try (ServerSocket dataSocket = new ServerSocket(4000)) {// créer une socket serveur sur le port 4000
            Socket socket = dataSocket.accept(); // attendre la connexion d'un client

            InputStream in = socket.getInputStream();// récupérer le flux d'entrée du socket
            OutputStream file = new FileOutputStream(filepath);// récupérer le flux de sortie du fichier

            byte[] buffer = new byte[4096];
            int count;
            // On copie le fichier
            while ((count = in.read(buffer)) > 0) {
                String s = new String(buffer, 0, count);// convertir le tableau de byte en String
                System.out.println(s);
                file.write(buffer, 0, count);// écrire dans le fichier
            }
            file.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
