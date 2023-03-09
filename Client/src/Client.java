import java.io.*;
import java.net.*;

public class Client{

    public String userDir;
    
    public boolean userOk = false;
    public boolean pwOk = false;
    public static void main(String[] args)  {
        String userInput;
        String line;
        Socket clientSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;
        BufferedReader stdIn;
        
        // Adresse du serveur
        String serverHostname = "localhost";
        int port = 2121;
    
        try {
            clientSocket = new Socket(serverHostname, port);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Erreur : hôte inconnu " + serverHostname);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Erreur : impossible de se connecter à " + serverHostname);
            System.exit(1);
        }
        
        System.out.println("Connecté au serveur " + serverHostname + " sur le port 2121.\n");
    
        // Le flux d'entrée standard -> Récupérer les entrées terminal de l'utilisateur
        stdIn = new BufferedReader(new InputStreamReader(System.in));
    
        StringBuilder serverResponse = new StringBuilder();
    
        try {
            // Récupérer la première réponse du serveur
            do {
                line = in.readLine();
                if (!(line.charAt(0) == '0' || line.charAt(0) == '2'))
                    line += "\n";
        
                serverResponse.append(line);
            } while (line.charAt(0) != '0' && line.charAt(0) != '2');
            System.out.println(serverResponse);
    
            // On envoie la commande au serveur et on affiche la réponse du serveur
            while (true) {
                userInput = stdIn.readLine();
                // On envoie la commande au serveur
                out.println(userInput);
                if ( userInput == null || userInput.equals("bye")) break;
                CommandeSender.sendCommande(in, userInput);
            }
            out.close();
            in.close();
            stdIn.close();
            clientSocket.close();
        }catch (IOException e) {
            System.out.println("La connexion au serveur a été perdue.");
        }
        
    }
}
