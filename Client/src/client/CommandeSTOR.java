package client;

import java.io.*;
import java.net.Socket;

public class CommandeSTOR {
	public static String send( BufferedReader in,String commande )  {
		PrintStream ps;
		try {
			String reponse = in.readLine();
			if (reponse.startsWith("2")){
				return reponse;
			}
			Socket socketEnvoi = new Socket("localhost", 4000);
			ps = new PrintStream(socketEnvoi.getOutputStream());
			reponse = in.readLine();
			
			if (reponse.startsWith("2")){
				return reponse;
			}
			File f = new File(commande.split(" ")[1]);
			
			if (!f.exists()){
				ps.println("2 Le fichier n'existe pas");
			}
			if (f.isDirectory()){
				ps.println("2 Le fichier est un dossier");
			}
			
			ps.println("0");
			
			reponse = in.readLine();
			if (reponse.startsWith("2")){
				return reponse;
			}
			
			// Envoie du fichier au serveur
			
			BufferedReader bw = new BufferedReader(new FileReader(f));
			String line;
			while ((line = bw.readLine()) != null) {
				ps.println(line);
			}
			bw.close();
			ps.close();
			socketEnvoi.close();
			return  in.readLine();
			
			
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
	}
}
