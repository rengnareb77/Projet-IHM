import java.io.*;
import java.net.*;

public class Main {
    public static void main(String[] args) throws IOException {
        String serverHostname = "localhost"; // Remplacez "localhost" par l'adresse IP ou le nom d'hôte de votre serveur
        
        Socket clientSocket = null;
        PrintWriter out = null;
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
        
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String userInput;
        
        System.out.println("Connecté au serveur " + serverHostname + " sur le port 2121.\n");
    
        StringBuilder serverResponse = new StringBuilder();
        boolean good = true;
        while(good){
            String line = in.readLine();
        
            if(line.charAt(0) == '0'){
                good = false;
            } else {
                line += "\n";
            }
            serverResponse.append(line);
        }
        System.out.println(serverResponse.toString());
    
        while ((userInput = stdIn.readLine()) != null) {
            out.println(userInput);
            if (userInput.equalsIgnoreCase("quit")) {
                break;
            }
            while(good){
                String line = in.readLine();
        
                if(line.charAt(0) == '0'){
                    good = false;
                } else {
                    line += "\n";
                }
                serverResponse.append(line);
            }
            System.out.println(serverResponse);
        }
        
        out.close();
        in.close();
        stdIn.close();
        clientSocket.close();
    }
}
