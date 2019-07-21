import { Product } from './model.product';
export class Menu {
    constructor(
        public id?: number,
        public name?: string,
        public price?: number,
        public available?: boolean,
        public managerId?: number,
        public productsDTO?: Array<Product>
    ) {}
}
