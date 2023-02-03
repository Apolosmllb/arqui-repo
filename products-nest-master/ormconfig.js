module.exports = {
  type: 'mysql',
  url: process.env.PRODUCTS_NEST_MYSQL,
  migrationsRun: true,
  logging: true,
  timezone: '+0',
  bigNumberStrings: false,
  entities: [process.env.ENVIRONMENT == 'prod' ? '**/domain/entities/*.js' : 'dist/**/domain/entities/*.js'],
  migrations: [process.env.ENVIRONMENT == 'prod' ? 'infrastructure/persistence/migrations/*.js' : 'dist/infrastructure/persistence/migrations/*.js'],
  cli: {
    migrationsDir: process.env.ENVIRONMENT == 'prod' ? 'infrastructure/persistence/migrations' : 'src/infrastructure/persistence/migrations',
  },
};