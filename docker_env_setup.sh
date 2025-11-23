# ! Step 1: Stop down all docker container
docker compose stop data-provider-app heatmap-ui-app stock-data-app

# ! Step 2: Remove old container if any
docker rm data-provider-app
docker rm stock-data-app
docker rm heatmap-ui-app

# ! Step 3: maven install and docker build
cd project-data-provider
mvn clean install
docker build -t project-data-provider:0.0.1 -f Dockerfile .
cd ..
cd project-stock-data
mvn clean install
docker build -t project-stock-data:0.0.1 -f Dockerfile .
cd ..
cd project-heatmap-ui
mvn clean install
docker build -t project-heatmap-ui:0.0.1 -f Dockerfile .
cd ..

# ! Step 4: docker run (docker-compose is shortcut for docker run)
docker compose up -d