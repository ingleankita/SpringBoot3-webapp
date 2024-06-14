package dev.ankitaingle.runnerz;

import org.springframework.stereotype.Component; // annotation: indicates that this class is available to Spring

@Component
public class WelcomeMessage {
    public String getWelcomeMessage() {
        return "Welcome to the Spring Boot Application!";
    }
}
