## Installation

### NVM - Windows

1. Remove existing Node Versions

2. Install NVM on windows

   - Download the nvm-setup.zip file from https://github.com/coreybutler/nvm-windows/releases
   - Unzip the nvm-setup.zip file and then execute the nvm-setup.exe file

   ```bash
   $ nvm --version
   ```

### NVM - Mac

1. Remove existing Node Versions

2. Install NVM on macOS

   ```bash
   $ brew update
   $ brew install nvm
   $ nvm --version
   ```

### Node

1. Install Node.js with NVM

   ```bash
   $ nvm ls-remote
   $ nvm install 16.14.2
   $ nvm ls
   $ nvm alias default 16.14.2
   $ nvm use 16.14.2
   ```

2. Uninstall Node.js with NVM

   ```bash
   $ nvm list
   $ nvm uninstall 16.14.2
   ```

### NPM - Latest version

```bash
$ npm install -g npm@latest
```

### GIT - Mac

```bash
$ brew install git
$ brew upgrade git
```

## Nest Installation

```bash
$ npm install -g @nestjs/cli ts-node typescript
$ nest --version
```

## Creating the app

```bash
$ cd documents/workspace-upc
$ nest new product-nest
$ cd product-nest
```

## Dependencies

```bash
$ npm install --save typeorm
$ npm install --save @nestjs/typeorm
$ npm install --save mysql
$ npm install --save @nestjs/cqrs
$ npm install --save typescript-result
$ npm install --save moment-timezone
$ npm install --save node-sql-reader
```
## Dev-Dependencies

```bash
$ npm install --save-dev npm-run-all
```

nestjs/typeorm 8.0.3 and typeorm 0.3.6 are not compatible, version 8.0.3 of nest is compatible with version 0.2.34

## Package.json

Add the typerom command in the "scripts" section

```
"scripts": {
"typeorm": "ts-node -r tsconfig-paths/register ./node_modules/typeorm/cli.js"
}
```

## Migrations

```bash
$ npm run typeorm migration:create -- -n CreateInitialSchema
$ npm run typeorm migration:create -- -n InsertMasterData
```

## Environment variables

```
PRODUCTS_NEST_MYSQL=mysql://{user}:{password}@{host}:{port}/{database}
PRODUCTS_NEST_MYSQL=mysql://root:root@localhost:3306/products-typeorm
Note: Password must be URL encoded, %25 is the url encoding of %.
```

## Fix issue with MySQL 8

Client does not support authentication protocol requested by server; consider upgrading MySQL client.
To fix it, run the following command changing the values with your credentials:

```
ALTER USER '{user}'@'{host}' IDENTIFIED WITH mysql_native_password BY '{password}';
FLUSH PRIVILEGES;
```

### Example:

```
ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY 'root';
FLUSH PRIVILEGES;
```
## Generate Dist folder
```bash
$ npm run build 
```

## Running the app

```bash
# development
$ npm run start

# watch mode
$ npm run start:dev

# production mode
$ npm run start:prod
```