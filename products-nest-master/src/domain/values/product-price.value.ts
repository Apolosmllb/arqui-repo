import { Column } from 'typeorm';

export class ProductPrice {
  @Column('decimal', { name: 'price' })
  private readonly amount: number;
  @Column('varchar', { name: 'currency' })
  private readonly currency: string;

  private constructor(
    amount: number,
    currency: string
  ) {
    this.amount = Number(amount);
    this.currency = currency;
  }

  public static create(amount: number, currency: string): ProductPrice {
    return new ProductPrice(
      amount,
      currency,
    );
  }

  public add(other: ProductPrice): ProductPrice {
    return this.newMoney(this.amount + other.getAmount());
  }

  public subtract(other: ProductPrice): ProductPrice {
    return this.newMoney(this.amount - other.getAmount());
  }

  private newMoney(amount: number): ProductPrice {
    return new ProductPrice(amount, this.currency);
  }

  public getAmount(): number {
    return this.amount;
  }

  public getCurrency(): string {
    return this.currency;
  }
}