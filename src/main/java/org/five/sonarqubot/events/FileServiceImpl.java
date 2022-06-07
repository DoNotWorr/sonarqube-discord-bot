package org.five.sonarqubot.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Component
public class FileServiceImpl implements FileService {
    Logger LOG = LoggerFactory.getLogger(EventListener.class);

    @Value("${sonar.scanner.code.folder}")
    String CODE_FOLDER;

    @Override
    public void createDirectory() {
        try {

            Path path = Paths.get(CODE_FOLDER);

            if (!Files.exists(path)) {
                Files.createDirectory(path);
                System.out.println("Directory created");
            }
        } catch (IOException e) {
            System.err.println("Sorry, there was a problem creating a directory");
            e.printStackTrace();
        }
    }

    @Override
    public Mono<String> createFile(String code) {
        UUID fileId = UUID.randomUUID();
        String fileExtension = ".java";
        String fileName = fileId + fileExtension;
        String path = CODE_FOLDER + "/" + fileName;
        try {
            File file = new File(path);

            // If file doesn't exists, then create it
            if (!file.exists()) {
                createDirectory();

                file.createNewFile();
                System.out.println("Temp file : " + file.getName());
            }
            writeToFile(code, file);
        } catch (IOException e) {
            System.err.println("Sorry, there was a problem creating temporary file");
            e.printStackTrace();
            return Mono.error(e);
        } catch (Exception e) {
            return Mono.error(e);
        }
        return Mono.just(fileName);
    }

    private void writeToFile(String code, File file) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file.getAbsoluteFile()))) {
            bw.write("public class ClassNameGeneratedBySonarQubot {\n" + code + "\n}"); //todo hardcoded for Java
        } catch (IOException e) {
            LOG.error("Sorry, there was a problem to write to the file");
            e.printStackTrace();
        }
    }
}
