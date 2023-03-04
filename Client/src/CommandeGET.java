import java.io.*;
import java.util.Scanner;

public class CommandeGET {
    public static void send( BufferedReader in,String commande )  {
        //recevoir le fichier
        try{
            File file = new File(commande.split(" ")[1]);
            
            if (file.exists()){
                System.out.println("> Le fichier existe déjà souhaitez vous le remplacer ? (y/n)");
                Scanner sc = new Scanner(System.in);
                String reponse;
                do {
                    reponse = sc.nextLine();
                } while (!(reponse.equals("y") || reponse.equals("n")));
                
                if (reponse.equals("n")){
                    System.out.println("> Le fichier n'a pas été remplacé");
                    sc.close();
                    return;
                }
                file.delete();
            }
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            while (true) {
        
                String line = in.readLine();
                
                if (!line.isBlank() && (line.charAt(0) == '0' || line.charAt(0) == '2')) {
                    System.out.println(line);
                    break;
                }
                if (line.startsWith("\\0") || line.startsWith("\\2"))
                    line = line.substring(1);
                bw.write(line+"\n");
        
            }
            bw.close();
            
    
        }catch (IOException e){
            e.printStackTrace();
        }
        
    }
}
