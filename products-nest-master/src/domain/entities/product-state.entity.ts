import { AggregateRoot } from '@nestjs/cqrs';
import { Column, Entity, PrimaryColumn } from 'typeorm';
import { AuditTrail } from '../values/audit-trail.value';
import { ProductStateId } from '../values/product-state-id.value';
import { ProductStateName } from '../values/product-state-name.value';

@Entity('product_states')
export class ProductState extends AggregateRoot {
  @PrimaryColumn('tinyint', { name: 'id' })
  private id: ProductStateId;
  
  @Column((type) => ProductStateName, { prefix: false })
  private readonly name: ProductStateName;
  
  @Column((type) => AuditTrail, { prefix: false })
  private readonly auditTrail: AuditTrail;

  public constructor(name: ProductStateName, auditTrail: AuditTrail) {
    super();
    this.name = name;
    this.auditTrail = auditTrail;
  }

  public getId(): ProductStateId {
    return this.id;
  }

  public getName(): ProductStateName {
    return this.name;
  }

  public getAuditTrail(): AuditTrail {
    return this.auditTrail;
  }

  public changeId(id: ProductStateId) {
    this.id = id;
  }
}