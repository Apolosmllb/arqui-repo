import { Controller, Post, Body, Res, Get, Param } from '@nestjs/common';
import { Result } from 'typescript-result';
import { QueryBus } from '@nestjs/cqrs';
import { ProductsApplicationService } from 'src/application/services/products-application.service';
import { RegisterProductRequest } from 'src/application/requests/register-product.request';
import { AppNotification } from 'src/application/app.notification';
import { RegisterProductResponse } from 'src/application/responses/register-product.response';
import { ApiController } from './api.controller';
import { GetAllProductsQuery } from 'src/application/queries/get-all-products.query';
import { GetProductByIdQuery } from 'src/application/queries/get-product-by-id.query';

@Controller('products')
export class ProductsController {
  constructor(
    private readonly productsApplicationService: ProductsApplicationService,
    private readonly queryBus: QueryBus
  ) {}

  @Post()
  async register(
    @Body() registerProductRequest: RegisterProductRequest,
    @Res({ passthrough: true }) response
  ): Promise<object> {
    try {
      const result: Result<AppNotification, RegisterProductResponse> = 
        await this.productsApplicationService.register(registerProductRequest);
      if (result.isSuccess()) {
          return ApiController.created(response, result.value);
      }
      return ApiController.error(response, result.error.getErrors());
    } catch (error) {
      return ApiController.serverError(response, error);
    }
  }

  @Get()
  async getAll(@Res({ passthrough: true }) response): Promise<object> {
    try {
      const products = await this.queryBus.execute(new GetAllProductsQuery());
      return ApiController.ok(response, products);
    } catch (error) {
      return ApiController.serverError(response, error);
    }
  }

  @Get('/:id')
  async getById(@Param('id') productId: number, @Res({ passthrough: true }) response): Promise<object> {
    try {
      const product = await this.queryBus.execute(new GetProductByIdQuery(productId));
      return ApiController.ok(response, product);
    } catch (error) {
      return ApiController.serverError(response, error);
    }
  }
}