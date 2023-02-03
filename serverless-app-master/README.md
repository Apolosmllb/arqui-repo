# Serverless Development on AWS

## Commands

```
$ node -v
$ npm -v
$ npm install -g aws-cdk@latest
$ cdk --version
$ aws --version
$ docker -v
$ sam --version
$ npm install yarn -g
$ yarn -v
$ yarn
$ echo $AWS_PROFILE
$ echo $CDK_NEW_BOOTSTRAP
$ aws configure
$ npx cdk bootstrap --cloudformation-execution-policies arn:aws:iam::aws:policy/AdministratorAccess aws://899150741402/us-east-2
$ cd infrastructure
$ npx cdk deploy
```

https://aws.amazon.com/serverless/sam/

## AWS SES - AWS Systems Manager (Parameter Store)
```
dms-globomantics-email
```
## AWS Cognito - Test Users
```
AuthUserPool

Temp Password
Aws@123456789

**admin**
efrain@thibau.io
Admin@123456789

**contributor**
efrainbautista@gmail.com
Contributor@123456789

**reader**
efrainbautista@outlook.com
Reader@123456789
```

## Approach

This learning path is focused on creating a modern full-stack serverless application on AWS using real world techniques and approaches. It follows the following opinionated concepts:

- It leverages a [monorepo](https://en.wikipedia.org/wiki/Monorepo) structure with [Yarn workspaces](https://classic.yarnpkg.com/en/docs/workspaces/)
- The backend services are created for Node.js wtih JavaScript and are exposed as REST API's
- The web application is created using React using hooks
- The infrastructure is defined with TypeScript using the [AWS CDK](https://aws.amazon.com/cdk/)
- Continuous delivery is implemented using an AWS CDK Pipeline
- No third-party services are leveraged as the focus is on a cloud-native approach with AWS only

## Following Along

This repository is designed to be used alongside the learning path. While the course consists of 11 courses, there are 5 courses where the concepts are applied to the sample application. For each of these courses, you will have a starting and ending branch. In addition, there will be a link to compare those two branches.

### Implementing Serverless Web Application Hosting and Delivery on AWS

Learn about leveraging Amazon S3 and CloudFront to distribute serverless web applications on a global scale.

### Building a Serverless API Tier with Amazon API Gateway

Learn about using AWS Lambda as your compute service while exposing an API with API Gateway all while using Amazon DynamoDB to store application data.

### Utilizing Amazon EventBridge in Serverless Applications

Learn about using Amazon EventBridge alongside Amazon S3 and Step Functions to create powerful microservices for your serverless application.

### Implementing Authentication for a Serverless HTTP API on AWS

Learn about securing serverless applications with Amazon Cognito for both web applications and the API tier.

### Implementing Monitoring and Continuous Deployment for Serverless Applications on AWS

Learn about monitoring your serverless applications with Amazon CloudWatch and AWS X-Ray as well as creating custom operational dashboards and alarms.
