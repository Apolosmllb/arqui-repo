import { PrimaryColumn } from "typeorm";

export class ProductStateId {
  @PrimaryColumn('tinyint', { name: 'id' })
  protected readonly value: number;

  protected constructor(value: number) {
    this.value = Number(value);
  }

  public static of(value: number): ProductStateId {
    return new ProductStateId(value);
  }

  public getValue(): number {
    return Number(this.value);
  }
}