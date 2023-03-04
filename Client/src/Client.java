import java.io.*;
import java.net.*;

public class Client {
    
    public static void main(String[] args) throws IOException {
        String userInput;
        Socket clientSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;
        BufferedReader stdIn;
        
        // Adresse du serveur
        String serverHostname = "localhost";
    
        try {
            clientSocket = new Socket(serverHostname, 2121);
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
    
        while(true){
            String line = in.readLine();
    
            if(line.charAt(0) == '0'|| line.charAt(0) == '2') break;
            else line += "\n";
            serverResponse.append(line);
        }
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
    }
}
