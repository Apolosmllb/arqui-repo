import { PrimaryColumn } from "typeorm";

export class ProductId {
  @PrimaryColumn('bigint', { name: 'id' })
  protected readonly value: number;

  protected constructor(value: number) {
    this.value = Number(value);
  }

  public static of(value: number): ProductId {
    return new ProductId(value);
  }

  public getValue(): number {
    return Number(this.value);
  }
}