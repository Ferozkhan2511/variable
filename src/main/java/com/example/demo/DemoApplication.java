package com.example.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@SpringBootApplication
@Controller
public class DemoApplication implements CommandLineRunner {

    @Value("${spring.application.name}")
    private String appName;

    @Value("${server.port}")
    private String serverPort;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // Display the content in the console
        System.out.println("Application Name: " + appName);
        System.out.println("Server Port: " + serverPort);
    }

    @GetMapping("/")
    public String displayProperties(Model model) {
        // Read properties from the application.properties file
        Properties properties = readPropertiesFromFile("src/main/resources/application.properties");

        // Add properties to the model
        Map<String, String> propertiesMap = new HashMap<>();
        properties.forEach((key, value) -> propertiesMap.put(key.toString(), value.toString()));
        model.addAttribute("properties", propertiesMap);

        // Return the template name
        return "properties";
    }

    private Properties readPropertiesFromFile(String fileName) {
        Properties properties = new Properties();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            properties.load(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }
}
