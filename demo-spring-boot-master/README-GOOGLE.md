# Demo - Hello World
Demo - Hello World

# Install Docker Desktop
- https://docs.docker.com/docker-for-windows/install/
- https://docs.docker.com/docker-for-mac/install/
```
$ docker version
```

# Maven
```
$ mvn clean package
```

# Docker-Compose
```
$ docker-compose -f docker-compose-local.yml up
```

# Docker Images
```
$ docker images
```

## Google - GCP - Create Free Account
https://cloud.google.com

## GCP CLI Setup
https://cloud.google.com/sdk/docs/install

## GCP CLI
```
$ gcloud components update
$ gcloud version
$ gcloud auth login
$ gcloud config set project boot-upc-api
$ gcloud init
$ gcloud services enable containerregistry.googleapis.com
$ gcloud auth configure-docker
$ docker image ls

$ docker tag {image}:latest gcr.io/{google-project-id}/{image}:latest
$ docker tag demo-boot-deploy:latest gcr.io/boot-upc-api/demo-boot-deploy:latest

$ docker push gcr.io/{google-project-id}/{image}:latest
$ docker push gcr.io/boot-upc-api/demo-boot-deploy:latest
```
- https://cloud.google.com/container-registry/docs/enable-service