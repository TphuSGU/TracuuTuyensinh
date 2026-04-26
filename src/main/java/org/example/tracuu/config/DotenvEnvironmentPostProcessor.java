package org.example.tracuu.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * Loads environment variables from a .env file in the project root
 * and makes them available as Spring properties.
 *
 * Usage in application.properties: ${DB_HOST}, ${DB_PORT}, etc.
 */
public class DotenvEnvironmentPostProcessor implements EnvironmentPostProcessor {

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        Path dotenvPath = Paths.get(".env");
        if (!Files.exists(dotenvPath)) {
            return;
        }

        try {
            Map<String, Object> envVars = new HashMap<>();
            Files.readAllLines(dotenvPath).forEach(line -> {
                line = line.trim();
                // Skip comments and empty lines
                if (line.isEmpty() || line.startsWith("#")) {
                    return;
                }
                int equalsIndex = line.indexOf('=');
                if (equalsIndex > 0) {
                    String key = line.substring(0, equalsIndex).trim();
                    String value = line.substring(equalsIndex + 1).trim();
                    // Remove surrounding quotes if present
                    if ((value.startsWith("\"") && value.endsWith("\""))
                            || (value.startsWith("'") && value.endsWith("'"))) {
                        value = value.substring(1, value.length() - 1);
                    }
                    envVars.put(key, value);
                }
            });

            environment.getPropertySources()
                    .addFirst(new MapPropertySource("dotenvProperties", envVars));

        } catch (IOException e) {
            throw new RuntimeException("Failed to load .env file", e);
        }
    }
}
