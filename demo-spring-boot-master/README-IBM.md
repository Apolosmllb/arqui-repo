# Deployment to IBM Cloud Foundry
Deployment to IBM Cloud Foundry

# Maven
```
$ mvn clean package
```

## SingUp

```
https://ibm.com/academic
```

## Login

```
https://cloud.ibm.com/login
```

## Cloud Foundry CLI

```
https://github.com/cloudfoundry/cli/releases
https://docs.cloudfoundry.org/cf-cli/install-go-cli.html
```

## Login to IBM Cloud Foundry

```
$ cf login
API endpoint: https://api.us-south.cf.cloud.ibm.com
Email: YourUPCEmail
Password: YourStrongPasswordHere
```

## IBM Cloud Foundry Orgs

```
$ cf orgs
$ cf target -o pcsiebau@upc.edu.pe_1614003346552
```

## Deploy App

```
$ cf push
```

## List Apps

```
$ cf apps
```

## Scale Out

```
$ cf scale <app-name> -i 2
$ cf scale <app-name> -i 3
$ cf scale <app-name> -i 8

$ cf scale demo-boot-fas-ebautista -i 4
$ cf scale demo-boot-fas-ebautista -i 8
$ cf scale demo-boot-fas-ebautista -i 3
$ cf scale demo-boot-fas-ebautista -i 2
```

## Scale Up - Memory

```
$ cf scale <app-name> -m 128M
$ cf scale <app-name> -m 256M
$ cf scale <app-name> -m 512M
$ cf scale <app-name> -m 1G
$ cf scale <app-name> -m 2G
$ cf scale <app-name> -m 3G
$ cf scale <app-name> -m 4G

$ cf scale demo-boot-fas-ebautista -m 4GB
$ cf scale demo-boot-fas-ebautista -m 1GB
```

## Scale Up - Disk

```
$ cf scale <app-name> -k 2G
$ cf scale demo-boot-fas-ebautista -k 2GB
```

## Delete App

```
$ cf delete <app-name> -r
$ cf delete demo-boot-fas-ebautista -r
```
