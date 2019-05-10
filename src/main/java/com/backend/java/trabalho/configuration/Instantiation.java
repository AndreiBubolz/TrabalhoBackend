package com.backend.java.trabalho.configuration;

import com.backend.java.trabalho.util.FileHandler;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class Instantiation implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {

        FileHandler fileHandler = new FileHandler();
        fileHandler.splitInfo(fileHandler.readCSV());

    }
}