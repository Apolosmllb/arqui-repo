import { IEventHandler } from '@nestjs/cqrs';
import { EventsHandler } from '@nestjs/cqrs/dist/decorators/events-handler.decorator';
import { ProductRegistered } from 'src/domain/events/product-registered.event';

@EventsHandler(ProductRegistered)
export class ProductRegisteredHandler implements IEventHandler<ProductRegistered> {
  constructor() {}

  handle(event: ProductRegistered) {
    console.log('handle logic for ProductRegistered event');
    console.log(event);
  }
}