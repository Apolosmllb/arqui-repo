import { Column, CreateDateColumn, UpdateDateColumn } from "typeorm";

export class AuditTrail {
  @Column({ name: 'created_at' })
  private createdAt: string;
  
  @Column('int', { name: 'created_by' })
  private createdBy: number;
  
  @Column({ name: 'updated_at' })
  private updatedAt: string;
  
  @Column('int', { name: 'updated_by' })
  private updatedBy: number;

  private constructor(createdAt: string, createdBy: number, updatedAt: string, updatedBy: number) {
    this.createdAt = createdAt;
    this.createdBy = createdBy;
    this.updatedAt = updatedAt;
    this.updatedBy = updatedBy;
  }

  public static from(createdAt: string, createdBy: number, updatedAt: string, updatedBy: number) {
    return new AuditTrail(createdAt, createdBy, updatedAt, updatedBy);
  }

  public getCreatedAt(): string {
    return this.createdAt;
  }

  public getCreatedBy(): number {
    return this.createdBy;
  }

  public getUpdatedAt(): string {
    return this.updatedAt;
  }

  public getUpdatedBy(): number {
    return this.updatedBy;
  }
}