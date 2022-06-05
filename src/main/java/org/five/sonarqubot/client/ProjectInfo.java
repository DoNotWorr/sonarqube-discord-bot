package org.five.sonarqubot.client;

public class ProjectInfo {
    private String projectKey;
    private String projectToken;

    public ProjectInfo(){};

    public ProjectInfo(String projectKey, String projectToken){
        this.projectKey = projectKey;
        this.projectToken = projectToken;
    }
}
