import { Module } from '@nestjs/common';
import { CqrsModule } from '@nestjs/cqrs';
import { TypeOrmModule } from '@nestjs/typeorm';
import { ProductsController } from './api/products.controller';
import { AppController } from './app.controller';
import { AppService } from './app.service';
import { RegisterProductHandler } from './application/handlers/commands/register-product.handler';
import { ProductRegisteredHandler } from './application/handlers/events/product-registered.handler';
import { GetAllProductsHandler } from './application/handlers/queries/get-all-products.handler';
import { GetProductByIdHandler } from './application/handlers/queries/get-product-by-id.handler';
import { ProductsApplicationService } from './application/services/products-application.service';
import { RegisterProductValidator } from './application/validators/register-product.validator';
import { Product } from './domain/entities/product.entity';

export const CommandHandlers = [RegisterProductHandler];
export const EventHandlers = [ProductRegisteredHandler];
export const QueryHandlers = [GetAllProductsHandler, GetProductByIdHandler];

@Module({
  imports: [
    CqrsModule,
    TypeOrmModule.forRoot(),
    TypeOrmModule.forFeature([Product]),
  ],
  controllers: [
    AppController, 
    ProductsController
  ],
  providers: [
    AppService, 
    ProductsApplicationService,
    RegisterProductValidator,
    ...CommandHandlers,
    ...EventHandlers,
    ...QueryHandlers
  ],
})
export class AppModule {}