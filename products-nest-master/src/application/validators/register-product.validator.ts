import { Injectable } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { Product } from 'src/domain/entities/product.entity';
import { Repository } from 'typeorm';
import { AppNotification } from '../app.notification';
import { RegisterProductRequest } from '../requests/register-product.request';

@Injectable()
export class RegisterProductValidator {
  constructor(@InjectRepository(Product) private productRepository: Repository<Product>) {}

  public async validate(registerProductRequest: RegisterProductRequest): Promise<AppNotification> {
    let notification: AppNotification = new AppNotification();
    const name: string = registerProductRequest.name.trim();
    if (name.length <= 0) {
      notification.addError('Product name is required', null);
    }
    const description: string = registerProductRequest.description.trim();
    if (description.length <= 0) {
      notification.addError('Product description is required', null);
    }
    if (notification.hasErrors()) {
      return notification;
    }
    const product: Product = await this.productRepository.createQueryBuilder().where("name = :name", { name }).getOne();
    if (product != null) {
      notification.addError('Product name is taken', null);
    }
    return notification;
  }
}