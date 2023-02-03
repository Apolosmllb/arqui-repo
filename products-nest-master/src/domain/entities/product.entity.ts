import { AggregateRoot } from '@nestjs/cqrs';
import { Column, Entity, PrimaryColumn } from 'typeorm';
import { ProductRegistered } from '../events/product-registered.event';
import { AuditTrail } from '../values/audit-trail.value';
import { CategoryIdFK } from '../values/category-id-fk.value';
import { ProductDescription } from '../values/product-description.value';
import { ProductId } from '../values/product-id.value';
import { ProductName } from '../values/product-name.value';
import { ProductPrice } from '../values/product-price.value';
import { ProductStateIdFK } from '../values/product-state-id-fk.value';
import { ProductStock } from '../values/product-stock.value';

@Entity('products')
export class Product extends AggregateRoot {
  @PrimaryColumn('bigint', { name: 'id' })
  private id: ProductId;
  
  @Column((type) => ProductName, { prefix: false })
  private readonly name: ProductName;
  
  @Column((type) => ProductDescription, { prefix: false })
  private readonly description: ProductDescription;
  
  @Column((type) => ProductPrice, { prefix: false })
  private price: ProductPrice;
  
  @Column((type) => ProductStock, { prefix: false })
  private stock: ProductStock;
  
  @Column((type) => ProductStateIdFK, { prefix: false })
  protected stateId: ProductStateIdFK;
  
  @Column((type) => CategoryIdFK, { prefix: false })
  private readonly categoryId: CategoryIdFK;
  
  @Column((type) => AuditTrail, { prefix: false })
  private readonly auditTrail: AuditTrail;

  public constructor(
    name: ProductName, 
    description: ProductDescription, 
    price: ProductPrice, 
    stock: ProductStock, 
    stateId: ProductStateIdFK, 
    categoryId: CategoryIdFK, 
    auditTrail: AuditTrail) {
    super();
    this.name = name;
    this.description = description;
    this.price = price;
    this.stock = stock;
    this.stateId = stateId;
    this.categoryId = categoryId;
    this.auditTrail = auditTrail;
  }

  public register() {
    const event = new ProductRegistered(
      this.id.getValue(), 
      this.name.getValue(), 
      this.description.getValue(), 
      this.price.getAmount(), 
      this.price.getCurrency(), 
      this.stock.getValue(), 
      this.categoryId.getValue());
    this.apply(event);
  }

  public getId(): ProductId {
    return this.id;
  }

  public getName(): ProductName {
    return this.name;
  }

  public getDescription(): ProductDescription {
    return this.description;
  }

  public getPrice(): ProductPrice {
    return this.price;
  }

  public getStock(): ProductStock {
    return this.stock;
  }

  public getStateId(): ProductStateIdFK {
    return this.stateId;
  }

  public getCategoryId(): CategoryIdFK {
    return this.categoryId;
  }

  public getAuditTrail(): AuditTrail {
    return this.auditTrail;
  }

  public changeId(id: ProductId) {
    this.id = id;
  }
}