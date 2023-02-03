import { Column } from 'typeorm';
import { Result } from 'typescript-result';
import { AppNotification } from '../../application/app.notification';

export class ProductDescription {
  @Column('varchar', { name: 'description' })
  private readonly value: string;
  private static MAX_LENGTH: number = 4000;

  private constructor(value: string) {
    this.value = value;
  }

  public getValue(): string {
    return this.value;
  }

  public static create(description: string): Result<AppNotification, ProductDescription> {
    let notification: AppNotification = new AppNotification();
    description = (description ?? "").trim();
    if (description === "") {
      notification.addError('description is required', null);
    }
    if (description.length > this.MAX_LENGTH) {
      notification.addError('The maximum length of an description is ' + this.MAX_LENGTH + ' characters including spaces', null);
    }
    if (notification.hasErrors()) {
      return Result.error(notification);
    }
    return Result.ok(new ProductDescription(description));
  }
}