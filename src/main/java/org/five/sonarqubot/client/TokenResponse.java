package org.five.sonarqubot.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.format.annotation.DateTimeFormat;

public class TokenResponse {
    private String login;
    private String name;
    private String token;
    private DateTimeFormat createdAt;

    public TokenResponse(){};

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public DateTimeFormat getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(DateTimeFormat createdAt) {
        this.createdAt = createdAt;
    }

    
    public TokenResponse(String login, String name, DateTimeFormat createdAt, String token) {
        this.login = login;
        this.name = name;

        this.createdAt = createdAt;
        this.token = token;
    }

    @Override
    public String toString() {
        return "TokenResponse{" +
                "login='" + login + '\'' +
                ", name='" + name + '\'' +
                ", token='" + token + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
