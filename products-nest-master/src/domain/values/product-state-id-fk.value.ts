import { Column } from "typeorm";

export class ProductStateIdFK {
  @Column('tinyint', { name: 'product_state_id' })
  protected readonly value: number;

  protected constructor(value: number) {
    this.value = Number(value);
  }

  public static of(value: number): ProductStateIdFK {
    return new ProductStateIdFK(value);
  }

  public getValue(): number {
    return Number(this.value);
  }
}