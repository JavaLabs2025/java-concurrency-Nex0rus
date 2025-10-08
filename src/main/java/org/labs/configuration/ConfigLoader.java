package org.labs.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

public class ConfigLoader {
    private static final Logger logger = LoggerFactory.getLogger(ConfigLoader.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();
    
    public static SimulationConfig loadConfig(String configPath) {
        try (InputStream inputStream = ConfigLoader.class.getClassLoader().getResourceAsStream(configPath)) {
            if (inputStream == null) {
                throw new RuntimeException("Configuration file not found: " + configPath);
            }
            
            SimulationConfig config = objectMapper.readValue(inputStream, SimulationConfig.class);
            logger.info("Loaded simulation configuration: {}", config);
            return config;
            
        } catch (IOException e) {
            logger.error("Failed to load configuration from {}", configPath, e);
            throw new RuntimeException("Failed to load configuration", e);
        }
    }
    
    public static SimulationConfig loadDefaultConfig() {
        return loadConfig("simulation-config.json");
    }
}
