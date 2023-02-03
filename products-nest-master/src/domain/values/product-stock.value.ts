import { Column } from "typeorm";

export class ProductStock {
  @Column('int', { name: 'stock' })
  protected readonly value: number;

  protected constructor(value: number) {
    this.value = Number(value);
  }

  public static of(value: number): ProductStock {
    return new ProductStock(value);
  }

  public getValue(): number {
    return Number(this.value);
  }
}