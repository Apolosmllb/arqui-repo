import { AggregateRoot } from '@nestjs/cqrs';
import { Column, Entity, PrimaryColumn } from 'typeorm';
import { AuditTrail } from '../values/audit-trail.value';
import { CategoryId } from '../values/category-id.value';
import { CategoryName } from '../values/category-name.value';

@Entity('categories')
export class Category extends AggregateRoot {
  @PrimaryColumn('int', { name: 'id' })
  private id: CategoryId;
  
  @Column((type) => CategoryName, { prefix: false })
  private readonly name: CategoryName;
  
  @Column((type) => AuditTrail, { prefix: false })
  private readonly auditTrail: AuditTrail;

  public constructor(name: CategoryName, auditTrail: AuditTrail) {
    super();
    this.name = name;
    this.auditTrail = auditTrail;
  }

  public getId(): CategoryId {
    return this.id;
  }

  public getName(): CategoryName {
    return this.name;
  }

  public getAuditTrail(): AuditTrail {
    return this.auditTrail;
  }

  public changeId(id: CategoryId) {
    this.id = id;
  }
}