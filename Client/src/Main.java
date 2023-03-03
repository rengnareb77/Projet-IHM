import java.io.*;
import java.net.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // Adresse du serveur
        String serverHostname = "localhost";
        
        // Le socket client
        Socket clientSocket = null;
        
        // Le flux de sortie vers le serveur -> Envoyer des données au serveur
        PrintWriter out = null;
        
        // Le flux d'entrée du serveur -> Récupérer les données du serveur
        BufferedReader in = null;
        
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
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        
        String userInput;
        
    
        StringBuilder serverResponse = new StringBuilder();
        
        // On attend la première réponse du serveur
        boolean good = true;
        while(good){
            String line = in.readLine();
        
            if(line.charAt(0) == '0'|| line.charAt(0) == '2'){
                good = false;
            } else {
                line += "\n";
            }
            serverResponse.append(line);
        }
        System.out.println(serverResponse);
    
        // Tant que l'utilisateur n'a pas entré "bye"
        // On envoie la commande au serveur et on affiche la réponse du serveur
        while ((userInput = stdIn.readLine()) != null) {
            // On envoie la commande au serveur
            out.println(userInput);
            
            // On quitte la boucle si l'utilisateur a entré "bye"
            if (userInput.equalsIgnoreCase("bye")) {
                break;
            }
            
            serverResponse = new StringBuilder();
            String line;
            // Récupérer la réponse du serveur tant que la réponse n'est pas
            // finie (commence par 0 ou 2)
            do {
                line = in.readLine();
                if (line.isBlank()) {
                    break;
                }
                if (!(line.charAt(0) == '0' || line.charAt(0) == '2')) {
                    line += "\n";
                }
                serverResponse.append(line);
            } while (!(line.charAt(0) == '0' || line.charAt(0) == '2'));
            
            // On affiche la réponse du serveur
            System.out.println(serverResponse);
        }
        
        out.close();
        in.close();
        stdIn.close();
        clientSocket.close();
    }
    
    
}
