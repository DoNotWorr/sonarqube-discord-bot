# SonarQuBot

## Docker

Run this command to start SonarQube server.

> docker run -d --name sonarqube -e SONAR_ES_BOOTSTRAP_CHECKS_DISABLE=true -p 9000:9000 sonarqube:latest

## Add your secrets

Create a file named `secret.properties` in `src/main/resources`. The following secrets are required to run the
application.

### Discord Bot

> token=botToken123

### Sonarqube server

> user=username<br>
> password=password
