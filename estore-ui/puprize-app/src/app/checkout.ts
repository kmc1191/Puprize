import { Product } from "./product";

export interface Checkout {
    cost: number ;
    invalidItems: Product[] ;
}