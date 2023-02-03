import { AuditTrailDto } from "./audit-trail.dto";
import { CategoryDto } from "./category.dto";

export class ProductDto {
  public id: number;
  public name: string;
  public description: string;
  public price: number;
  public currency: string;
  public stock: number;
  public stateId: number;
  public categoryId: number;
  public createdAt: string;
  public createdBy: number;
  public updatedAt: string;
  public updatedBy: number;
}