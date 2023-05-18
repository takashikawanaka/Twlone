package com.twlone;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

//@EnableScheduling
@SpringBootApplication
public class TwloneApplication {
    public static void main(String[] args) {
        SpringApplication.run(TwloneApplication.class, args);
    }

    @PostConstruct
    public void checkDirectory() {
        for (String pathString : List.of("./icons", "./backs", "./medias")) {
            Path path = Paths.get(pathString);
            if (!Files.exists(path)) {
                try {
                    Files.createDirectory(path);
                    System.out.println("Create " + pathString + " Directory");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
