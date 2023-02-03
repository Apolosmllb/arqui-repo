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

# Azure
- https://azure.microsoft.com/es-es/global-infrastructure/geographies

## Azure - Create Free Account
- https://azure.microsoft.com/en-us/free/students
- https://azure.microsoft.com/en-us/free
- https://portal.azure.com

## Azure CLI Setup
- https://docs.microsoft.com/en-us/cli/azure/install-azure-cli
- https://docs.microsoft.com/en-us/cli/azure/install-azure-cli-windows?tabs=azure-cli
- https://docs.microsoft.com/en-us/cli/azure/install-azure-cli-macos

## Azure CLI
```
$ az upgrade
$ az version
$ az login
$ az account list-locations -o table
```

## Azure ACR
```
$ az group create -n UPC-FAS -l southcentralus
$ az acr create -g UPC-FAS -n demobootdeploy --sku Basic --admin-enabled true
$ az acr login -n demobootdeploy
$ az acr show -n demobootdeploy --query loginServer --output tsv
$ docker image ls
$ docker tag demo-boot-deploy:latest demobootdeploy.azurecr.io/demo-boot-deploy:latest
$ docker push demobootdeploy.azurecr.io/demo-boot-deploy:latest
$ az acr repository list -n demobootdeploy -o table
$ az acr repository show-tags -n demobootdeploy --repository demo-boot-deploy -o table
```

## Create App Service
- fasdemo

## CLI appsettings
```
$ az webapp config appsettings set --resource-group UPC-FAS --name fasdemo --settings WEBSITES_PORT=8080
$ az webapp config appsettings set --resource-group UPC-FAS --name fasdemo --settings ENVIRONMENT=production
```
- Restart appservice to take effect the config appsettings
```
$ az acr repository delete -n demobootdeploy -t demo-boot-deploy:latest
$ az group delete -n UPC-FAS
$ az logout
```