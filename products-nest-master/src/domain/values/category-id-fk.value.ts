import { PrimaryGeneratedColumn } from "typeorm";

export class CategoryIdFK {
  @PrimaryGeneratedColumn('increment', { name: 'category_id' })
  protected readonly value: number;

  protected constructor(value: number) {
    this.value = Number(value);
  }

  public static of(value: number): CategoryIdFK {
    return new CategoryIdFK(value);
  }

  public getValue(): number {
    return Number(this.value);
  }
}