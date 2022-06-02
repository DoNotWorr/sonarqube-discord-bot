package CreateFiles;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class createTempFile {
   public static void main(String[] args) {

       try {
           String prefix = "code_";
           Path path = Paths.get("/Users/abdi/IdeaProjects/sonarqube-discord-bot/src/main/java/CreateFiles");

           // Create an temporary file in a specified directory.
           Path temp = Files.createTempFile(path, prefix, ".log");

           System.out.println("Temp file : " + temp);

       } catch (IOException e) {
           System.err.println("Sorry, there was a problem creating temporary file or directory");
           e.printStackTrace();
       }
   }
}
