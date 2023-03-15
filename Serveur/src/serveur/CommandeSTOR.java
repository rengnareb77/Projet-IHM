package serveur;

import client.Client;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class CommandeSTOR extends Commande {

    public CommandeSTOR(Client client,PrintStream ps, String commandeStr) {
        super(client,ps, commandeStr);
    }

    public void execute() {
        BufferedReader br;
        System.out.println("Verification du fichier");
        if (commandeArgs.length < 1) {
            ps.println("2 Il faut spécifier un fichier");
            return;
        }
        
        File f = new File(client.userDir + "/" + this.commandeArgs[0]);
        
        if (f.exists()){
            ps.println("2 Le fichier existe déja");
        }
        
        ps.println("0");
        
        try {
            System.out.println("Création du socket");
            // Création du socket d'envoi du fichier
            ServerSocket serverSocketEnvoi = new ServerSocket(4000);
            Socket socketEnvoi =  serverSocketEnvoi.accept();
            
            
            br = new BufferedReader(new InputStreamReader(socketEnvoi.getInputStream()));
            // Client connecté
            System.out.println("Client connectée");
            ps.println("0");
            
            String reponse = br.readLine();
            
            if (reponse.startsWith("2")){
                ps.println(reponse);
                socketEnvoi.close();
                return;
            }
            
            // Attente du fichier
            ps.println("0");
            System.out.println("Lecture du fichier");
            
            // Lecture du fichier
            BufferedWriter bw = new BufferedWriter(new FileWriter(f));
            String line;
            while ((line = br.readLine()) != null) {
                bw.write(line+"\n");
            }
            bw.close();
            ps.println("0 Fichier reçu");
            
            socketEnvoi.close();
            
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

}
