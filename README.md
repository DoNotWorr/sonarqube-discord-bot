# SonarQuBot

## Docker

> docker run -d --name sonarqube -e SONAR_ES_BOOTSTRAP_CHECKS_DISABLE=true -p 9000:9000 sonarqube:latest

## Discord Bot token

To make this work with your discord-bot you need to add your bot-token to an ***application.properties*** file inside the resources folder:

> token=botToken123

## Sonarqube server

 ***application.properties*** file inside the resources folder:

> sonarqubeAPI=port/api<br>
> user=username<br>
> password=password
