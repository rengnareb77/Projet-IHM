package client;

import java.io.BufferedReader;
import java.io.IOException;

public class CommandeSender {
    public static String sendCommande(BufferedReader in, String commande) throws IOException{
        switch (commande.split(" ")[0]) {
            case "get" -> { return CommandeGET.send(in,commande);}
            case "stor" -> { return CommandeSTOR.send(in, commande); }
            default -> {
                StringBuilder serverResponse = new StringBuilder();
                String line;
                // Récupérer la réponse du serveur tant que la réponse n'est pas
                // finie (commence par 0 ou 2)
                
                    do {
                        line = in.readLine();
                        if (line == null) {
                            throw new IOException();
                        }
                        if (line.isBlank()) {
                            break;
                        }
                        if (!(line.charAt(0) == '0' || line.charAt(0) == '2')) {
                            line += "\n";
                        }
                        serverResponse.append(line);
                    } while (!(line.charAt(0) == '0' || line.charAt(0) == '2'));
    
                    return serverResponse.toString();
                
            }
        }
    }
}
