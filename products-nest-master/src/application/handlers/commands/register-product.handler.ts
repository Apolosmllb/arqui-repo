import { CommandHandler, EventPublisher, ICommandHandler } from '@nestjs/cqrs';
import { InjectRepository } from '@nestjs/typeorm';
import { AppNotification } from 'src/application/app.notification';
import { RegisterProduct } from 'src/application/commands/register-product.command';
import { Product } from 'src/domain/entities/product.entity';
import { Currency } from 'src/domain/enums/currency.enum';
import { ProductFactory } from 'src/domain/factories/product.factory';
import { CategoryIdFK } from 'src/domain/values/category-id-fk.value';
import { ProductDescription } from 'src/domain/values/product-description.value';
import { ProductName } from 'src/domain/values/product-name.value';
import { ProductStateIdFK } from 'src/domain/values/product-state-id-fk.value';
import { ProductStock } from 'src/domain/values/product-stock.value';
import { Repository } from 'typeorm';
import { Result } from 'typescript-result';
import { ProductPrice } from '../../../domain/values/product-price.value';
import { ProductStateEnum } from 'src/domain/enums/product-state.enum';
import { AuditTrail } from 'src/domain/values/audit-trail.value';
import { ProductId } from 'src/domain/values/product-id.value';
import { DateTime } from 'src/domain/values/date-time.value';

@CommandHandler(RegisterProduct)
export class RegisterProductHandler
  implements ICommandHandler<RegisterProduct> {
  constructor(
    @InjectRepository(Product)
    private productRepository: Repository<Product>,
    private publisher: EventPublisher,
  ) {
  }

  async execute(command: RegisterProduct) {
    let productId: number = 0;
    const productNameResult: Result<AppNotification, ProductName> = ProductName.create(command.name);
    if (productNameResult.isFailure()) {
      return productId;
    }
    const productDesriptionResult: Result<AppNotification, ProductDescription> = ProductDescription.create(command.description);
    if (productDesriptionResult.isFailure()) {
      return productId;
    }
    const price: ProductPrice = ProductPrice.create(command.price, Currency.SOLES);
    const stock: ProductStock = ProductStock.of(command.stock);
    const stateId: ProductStateIdFK = ProductStateIdFK.of(ProductStateEnum.ACTIVE);
    const categoryId: CategoryIdFK = CategoryIdFK.of(command.categoryId);
    const auditTrail = AuditTrail.from(DateTime.utcNow().format(), 1, DateTime.utcNow().format(), 1);
    let productEntity: Product = ProductFactory.createFrom(
      productNameResult.value, 
      productDesriptionResult.value, 
      price, 
      stock, 
      stateId, 
      categoryId, 
      auditTrail);
    let product = await this.productRepository.save(productEntity);
    if (product == null) {
      return productId;
    }
    console.log(product);
    productId = Number(product.getId());
    console.log(productId);
    product.changeId(ProductId.of(productId));
    product = this.publisher.mergeObjectContext(product);
    product.register();
    product.commit();
    return productId;
  }
}