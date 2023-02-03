import { Injectable } from '@nestjs/common';
import { CommandBus } from '@nestjs/cqrs';
import { Result } from 'typescript-result';
import { AppNotification } from '../app.notification';
import { RegisterProduct } from '../commands/register-product.command';
import { RegisterProductRequest } from '../requests/register-product.request';
import { RegisterProductResponse } from '../responses/register-product.response';
import { ProductStateEnum } from 'src/domain/enums/product-state.enum';
import { RegisterProductValidator } from '../validators/register-product.validator';

@Injectable()
export class ProductsApplicationService {
  constructor(
    private commandBus: CommandBus,
    private registerProductValidator: RegisterProductValidator,
  ) {}

  async register(registerProductRequest: RegisterProductRequest): Promise<Result<AppNotification, RegisterProductResponse>> {
    const notification: AppNotification = await this.registerProductValidator.validate(registerProductRequest);
    if (notification.hasErrors()) {
      return Result.error(notification);
    }
    const registerProduct: RegisterProduct = new RegisterProduct(
      registerProductRequest.name,
      registerProductRequest.description,
      registerProductRequest.price,
      registerProductRequest.currency,
      registerProductRequest.stock,
      registerProductRequest.categoryId
    );
    const productId: number = await this.commandBus.execute(registerProduct);
    const registerProductResponse: RegisterProductResponse = new RegisterProductResponse(
      productId, 
      registerProduct.name, 
      registerProduct.description, 
      registerProduct.price, 
      registerProduct.currency, 
      registerProduct.stock, 
      ProductStateEnum.ACTIVE,
      registerProductRequest.categoryId
    );
    return Result.ok(registerProductResponse);
  }
}