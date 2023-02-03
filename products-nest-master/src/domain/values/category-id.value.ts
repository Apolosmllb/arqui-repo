import { PrimaryColumn } from "typeorm";

export class CategoryId {
  @PrimaryColumn('int', { name: 'id' })
  protected readonly value: number;

  protected constructor(value: number) {
    this.value = Number(value);
  }

  public static of(value: number): CategoryId {
    return new CategoryId(value);
  }

  public getValue(): number {
    return Number(this.value);
  }
}