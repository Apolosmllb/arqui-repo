import { IQueryHandler, QueryHandler } from '@nestjs/cqrs';
import { ProductDto } from 'src/application/dtos/product.dto';
import { GetProductByIdQuery } from 'src/application/queries/get-product-by-id.query';
import { getManager } from 'typeorm';

@QueryHandler(GetProductByIdQuery)
export class GetProductByIdHandler implements IQueryHandler<GetProductByIdQuery> {
  constructor() {}

  async execute(query: GetProductByIdQuery) {
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
    WHERE
      p.id = ?;`;
    const ormProducts = await manager.query(sql, [query.productId]);
    if (ormProducts.length <= 0) {
      return {};
    }
    const ormProduct = ormProducts[0];
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
  }
}