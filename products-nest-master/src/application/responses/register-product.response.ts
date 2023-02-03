export class RegisterProductResponse {
  constructor(
    public id: number,
    public name: string,
    public description: string,
    public price: number,
    public currency: string,
    public stock: number,
    public stateId: number,
    public categoryId: number
  ) {}
}