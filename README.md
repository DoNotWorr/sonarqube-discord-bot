# SonarQuBot

## Docker

Package the jar-file first:

> mvn package

...then build docker container:

> docker compose build

...and run it:

> docker compose up

When you want to close it:

> docker compose down

## Discord Bot token

To make this work with your discord-bot you need to add your bot-token to an ***app.properties*** file inside the root folder:

> token=botToken123