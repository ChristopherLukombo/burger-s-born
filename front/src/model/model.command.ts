import { Menu } from 'src/model/model.menu';
import { Product } from './model.product';

export class Command {
    constructor(
        public id?: number,
        public orderStatus?: string,
        public date?: Date,
        public price?: number,
        public customerId?: boolean,
        public menusDTO?: Array<Menu>,
        public productsDTO?: Array<Product>,
    ) {}
}