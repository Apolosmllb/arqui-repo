import { IQueryHandler, QueryHandler } from '@nestjs/cqrs';
import { ProductDto } from 'src/application/dtos/product.dto';
import { GetAllProductsQuery } from 'src/application/queries/get-all-products.query';
import { getManager } from 'typeorm';

@QueryHandler(GetAllProductsQuery)
export class GetAllProductsHandler implements IQueryHandler<GetAllProductsQuery> {
  constructor() {}

  async execute(query: GetAllProductsQuery) {
    const manager = getManager();
    const sql = `
    SELECT
      p.id,
      p.name,
      p.description,
      p.price,
      p.stock,
      p.product_state_id,
      p.category_id,
      p.created_at,
      p.created_by,
      p.updated_at,
      p.updated_by
    FROM 
      products p
    ORDER BY
      p.created_at DESC;`;
    const ormProducts = await manager.query(sql);
    if (ormProducts.length <= 0) {
      return [];
    }
    const products: ProductDto[] = ormProducts.map(function (ormProduct) {
      let productDto = new ProductDto();
      productDto.id = Number(ormProduct.id);
      productDto.name = ormProduct.number;
      productDto.description = ormProduct.description;
      productDto.price = Number(ormProduct.price);
      productDto.currency = ormProduct.currency;
      productDto.stock = ormProduct.stock;
      productDto.stateId = ormProduct.product_state_id;
      productDto.categoryId = ormProduct.category_id;
      productDto.createdAt = ormProduct.created_at;
      productDto.createdBy = ormProduct.created_by;
      productDto.updatedAt = ormProduct.updated_at;
      productDto.updatedBy = ormProduct.updated_by;
      return productDto;
    });
    return products;
  }
}