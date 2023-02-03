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

# AWS
- https://towardsdatascience.com/deploying-a-docker-container-with-ecs-and-fargate-7b0cbc9cd608

## AWS - Create Free Tier Account
- https://aws.amazon.com/free

## AWS Console - Identity and Access Management (IAM)
- https://console.aws.amazon.com
```
Add user with Programmatic access and set AdministratorAccess policy (for testing purposes)
```

## AWS CLI Setup
- https://docs.aws.amazon.com/cli/latest/userguide/install-cliv2-windows.html
- https://docs.aws.amazon.com/cli/latest/userguide/install-cliv2-mac.html

## AWS CLI
```
$ aws --version
$ aws configure
$ aws iam list-users
```

## AWS CLI - ECR
```
$ aws ecr get-login-password --region us-east-2 | docker login --username AWS --password-stdin {aws-account-id}.dkr.ecr.us-east-2.amazonaws.com
$ aws ecr get-login-password --region us-east-2 | docker login --username AWS --password-stdin 899150741402.dkr.ecr.us-east-2.amazonaws.com

$ aws ecr create-repository --repository-name demo-boot-deploy

$ docker tag {docker-image-name}:latest {aws-account-id}.dkr.ecr.us-east-2.amazonaws.com/{repo-name}
$ docker tag demo-boot-deploy:latest 899150741402.dkr.ecr.us-east-2.amazonaws.com/demo-boot-deploy

$ docker push {aws-account-id}.dkr.ecr.us-east-2.amazonaws.com/{repo-name}
$ docker push 899150741402.dkr.ecr.us-east-2.amazonaws.com/demo-boot-deploy
```

## AWS Console - ECS
```
1. Crear un cluster (Networking only)
2. Crear un task definition (Fargate)
3. Ejecutar task
```

## AWS Console - App Runner

## AWS ECS-CLI Setup
- https://docs.aws.amazon.com/AmazonECS/latest/developerguide/ECS_CLI_installation.html
- https://github.com/aws/amazon-ecs-cli

## AWS ECS-CLI

```
$ sudo chmod +x /usr/local/bin/ecs-cli
$ ecs-cli --version
```

## CONFIGURE PROFILE

```
$ ecs-cli configure profile \
--profile-name upc \
--access-key {access-key} \
--secret-key {secret-key}
```

## CONFIGURE CLUSTER

```
$ ecs-cli configure \
--cluster fas \
--default-launch-type FARGATE \
--region us-east-2 \
--config-name fas
```

## CREATE IAM ROLE

```
$ aws iam create-service-linked-role \
  --aws-service-name ecs.amazonaws.com

$ aws iam \
  --region us-east-2 create-role \
  --role-name ecsTaskExecutionRole \
  --assume-role-policy-document file://ecs-task-execution-assume-role.json

$ aws iam \
  --region us-east-2 attach-role-policy \
  --role-name ecsTaskExecutionRole \
  --policy-arn arn:aws:iam::aws:policy/service-role/AmazonECSTaskExecutionRolePolicy
```

## LAUNCH CLUSTER

```
$ ecs-cli up \
--cluster-config fas \
--capability-iam \
--force
```

## CREATE SECURITY GROUP

```
$ aws ec2 create-security-group \
  --group-name "fas-sg" \
  --description "FAS SG" \
  --vpc-id "{vpc}"

$ aws ec2 authorize-security-group-ingress \
  --group-id "{sgid}" \
  --protocol tcp \
  --port 8080 \
  --cidr 0.0.0.0/0
  
$ aws ec2 authorize-security-group-ingress \
  --group-id "{sgid}" \
  --protocol tcp \
  --port 80 \
  --cidr 0.0.0.0/0
```

## CREATE LOAD BALANCER

```
$ aws elbv2 create-load-balancer \
--name fas-lb  \
--subnets {subnet} {subnet} \
--security-groups {sgid}

$ aws elbv2 create-target-group \
--name fas-tg \
--protocol HTTP \
--port 8080 \
--vpc-id {vpc} \
--target-type ip

$ aws elbv2 create-listener \
--load-balancer-arn {arn} \
--protocol HTTP --port 80  \
--default-actions Type=forward,TargetGroupArn=arn:{arn}
```

## ECS-CLI COMPOSE

```
$ ecs-cli compose \
  --project-name hello-world service up \
  --target-groups "targetGroupArn={arn},containerPort=8080,containerName=demo-boot-deploy" \
  --create-log-groups \
  --cluster-config fas \
  --cluster fas

$ ecs-cli ps --cluster fas
```

## REMOVE RESOURCES

```
$ ecs-cli compose \
  --project-name hello-world service down \
  --cluster-config fas

$ ecs-cli down \
  --force \
  --cluster-config fas
```