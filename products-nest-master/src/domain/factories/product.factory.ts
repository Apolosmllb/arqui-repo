import { ProductName } from "../values/product-name.value";
import { ProductDescription } from '../values/product-description.value';
import { ProductPrice } from '../values/product-price.value';
import { ProductStock } from "../values/product-stock.value";
import { CategoryIdFK } from "../values/category-id-fk.value";
import { AuditTrail } from "../values/audit-trail.value";
import { Product } from "../entities/product.entity";
import { ProductStateIdFK } from "../values/product-state-id-fk.value";
import { ProductId } from "../values/product-id.value";

export class ProductFactory {
  public static createFrom(
    name: ProductName,
    description: ProductDescription,
    price: ProductPrice,
    stock: ProductStock,
    stateId: ProductStateIdFK,
    categoryId: CategoryIdFK,
    auditTrail: AuditTrail): Product {
    return new Product(
      name, 
      description, 
      price, 
      stock,
      stateId,
      categoryId,
      auditTrail);
  }

  public static withId(
    productId: ProductId,
    name: ProductName,
    description: ProductDescription,
    price: ProductPrice,
    stock: ProductStock,
    stateId: ProductStateIdFK,
    categoryId: CategoryIdFK,
    auditTrail: AuditTrail): Product {
    let product: Product = new Product(
      name, 
      description, 
      price, 
      stock,
      stateId,
      categoryId,
      auditTrail);
      product.changeId(productId);
    return product;
  }
}