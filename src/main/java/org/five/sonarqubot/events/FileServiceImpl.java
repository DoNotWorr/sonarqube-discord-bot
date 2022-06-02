package org.five.sonarqubot.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class FileServiceImpl implements FileService {
    Logger LOG = LoggerFactory.getLogger(EventListener.class);

    @Override
    public void createDirectory() {
        try {
            String fileName = "files-to-scan";
            Path path = Paths.get(fileName);

            if (!Files.exists(path)) {
                Files.createDirectory(path);
                System.out.println("Directory created");
            }
            System.out.println("Directory already exists");

        } catch (IOException e) {
            System.err.println("Sorry, there was a problem creating a directory");
            e.printStackTrace();
        }
    }

    @Override
    public Mono<String> createFile(String code) {

        try {
            String path = "files-to-scan/file-to-scan";
            File file = new File(path);

            // If file doesn't exists, then create it
            if (!file.exists()) {
                createDirectory();

                file.createNewFile();
                System.out.println("Temp file : " + file.getName());
            }
            System.out.println("File already exists");

            writeToFile(code, file);

        } catch (IOException e) {
            System.err.println("Sorry, there was a problem creating temporary file");
            e.printStackTrace();
        }
        return Mono.just(code);
    }

    private void writeToFile(String code, File file) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file.getAbsoluteFile()))) {
            bw.write(code);
        } catch (IOException e) {
            LOG.error("Sorry, there was a problem to write to the file");
            e.printStackTrace();
        }
    }
}
