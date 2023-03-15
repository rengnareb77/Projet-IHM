package client;

import java.io.*;
import java.net.Socket;

public class CommandeGET {
    public static String send( BufferedReader in,String commande )  {
        String line;
        // Socket de reception du fichier
        Socket socketReception;
        
        // Flux de lecture du socket
        BufferedReader lectureReception;
        
        // Flux d'écriture du socket
        PrintStream ecritureReception = null;
        
        // Flux d'écriture du fichier
        BufferedWriter ecritureFichier;
    
        String serverName = "localhost";
        int port = 4000;
        
        try{
            String response = in.readLine();
            if (response.startsWith("2")){
                return response;
            }
            // Connexion au serveur
            socketReception = new Socket(serverName, port);
            ecritureReception = new PrintStream(socketReception.getOutputStream());
    
            // Création du fichier
            File file = new File(commande.split(" ")[1]);
    
            // Si le fichier existe déjà
            if (file.exists()){
                file.delete();
                ecritureReception.println("2 Le fichier existe déjà.");
                socketReception.close();
                ecritureReception.close();
                return "2 Le fichier existe déjà.";
            }
    
            // Ok pour la réception
            ecritureReception.println("0");
            
            // Attente que le serveur ait envoyé le fichier
            response = in.readLine();
            if (response.startsWith("2")){
                return response;
            }
            
            // Réception du fichier
            lectureReception = new BufferedReader(new InputStreamReader(socketReception.getInputStream()));
            ecritureFichier = new BufferedWriter(new FileWriter(file));
            // Lecture du fichier
            while ((line = lectureReception.readLine()) != null) {
                line += "\n";
                ecritureFichier.write(line);
            }
            ecritureFichier.close();
            
            lectureReception.close();
            ecritureReception.close();
            socketReception.close();
    
            return "0 Fichier reçu avec succès";
            
        } catch (IOException e){
            return "2 Erreur lors de la réception du fichier";
        }
        
        
    }
}
