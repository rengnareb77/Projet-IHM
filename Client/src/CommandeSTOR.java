package client;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class CommandeSTOR {
	public static void send( BufferedReader in,String commande )  {
        //recevoir le fichier
		
       
          //  File file = new File("./ressources/" + commande.split(" ")[1]); // on recup le nom du fichier stor file.txt
			File file = new File("./ressources/FicTest");
            
            if (!(file.exists())){
                System.out.println("le fichier n'existe pas, veuillez de le créer");
                return;
            }
            
    		//créer socket
        	try (Socket StorSocketCLient = new Socket("localhost",4000)) {// créer une socket client sur le port 4000
        		
        		//OutputStream output = StorSocketCLient.getOutputStream();
        		byte[] buffer = new byte[(int) file.length()]; // buffer 
        	    FileInputStream fis = new FileInputStream(file); //
        	    BufferedInputStream bis = new BufferedInputStream(fis);
        	    bis.read(buffer, 0, buffer.length);

        	    OutputStream os = StorSocketCLient.getOutputStream();
        	    os.write(buffer, 0, buffer.length);
        	    os.flush();
              
        	    StorSocketCLient.close();

             } catch (IOException e) {
                 throw new RuntimeException(e);
             }
            	
            
            
	}
}
