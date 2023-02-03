import { AuditTrailDto } from "./audit-trail.dto";

export class CategoryDto {
  public id: number;
  public name: string;
  public auditTrailDto: AuditTrailDto;
}