import { Component, Input, OnInit, Output, EventEmitter } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { Product } from '../product';
import { ProductsService } from './products.service';
import { MessageService } from '../message.service';
import { CartService } from '../cart/cart.service';
import { LoginComponent } from '../login/login.component';
import { LoginService } from '../login/login.service';
import { Login } from '../login';
import { Profile } from '../profile';
import { NgbModal, ModalDismissReasons } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.css']
})

export class ProductsComponent implements OnInit {
  //products: Array<Product> = new Array<Product>();
  products: Product[] = [];
  mainProduct: Product | undefined;
  isOwner: boolean = false;
  login: Login | undefined;
  profile: Profile | undefined;
  closeResult = '';

  constructor(
    private productsService: ProductsService,
    private route: ActivatedRoute,
    private cartService: CartService,
    //private loginComponent: LoginComponent
    private loginService: LoginService,
    private modalService: NgbModal,
    ) { }

  ngOnInit(): void {
    this.getInventory();
    this.getCurrentUser();
  }

  getInventory(): void {
    this.productsService.getInventory()
    .subscribe(products => this.products = products);
  }

  getProduct(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.productsService.getProduct(id).subscribe(product => this.mainProduct = product);
  }

  async add(name: string, price: number, quantity: number): Promise<void> {
    name = name.trim();
    if (!name) { return; }
    this.productsService.addProduct({ name, price, quantity } as Product)
      .subscribe(product => {
        this.products.push(product);
      });

    await this.delay(100);
    this.getInventory();
    
  }

 async changeDisplay(sortType: string): Promise<void>{
    sortType = sortType.trim();
    if(!sortType){return;}
    this.productsService.changeDisplay(sortType).subscribe();
    await this.delay(100);
    this.getInventory();
  }

  async update(id: number, name: string, data: {price: number, quantity: number} ): Promise<void> {
    var price = data.price;
    var quantity = data.quantity;
  this.productsService.updateProduct({ id, name, price, quantity } as Product)
  .subscribe();
  await this.delay(100);
  this.getInventory();
  }

  delete(product: Product): void {
    this.products = this.products.filter(h => h !== product);
    this.productsService.deleteProduct(product.id).subscribe();
  }

  moveToCart(id: number, quantity: number): void {
    if( this.profile != null ) {
      this.cartService.addToCart(this.profile.username, id, quantity).subscribe();
    }
  }

  prioritize(id: number){
    this.productsService.prioritize(id)
    .subscribe(products => this.products = products);
  }

  /** 
  getLogin(): void{
    this.loginService.getLogin().subscribe(login => this.login = login);
    //this.getOwner(this.login);
  }
  */

  //getOwner(login: Login | undefined): void{
    //if(login != null){
    //this.isOwner = login.isOwner;
    //}
  //}

  /** 
  onLogin(owner: boolean) {
    this.isOwner = owner;
  }
  */

  getLogin(): void {
    this.loginService.getLogin().subscribe(login => this.login = login);
    if( this.login != null ) {
      this.isOwner = this.login.isOwner; 
    }
 }

 getCurrentUser(): void {
    this.loginService.getCurrentUser().subscribe(profile => this.profile = profile);
 }

 open(content: any) {
  this.modalService.open(content, { ariaLabelledBy: 'modal-basic-title' }).result.then(
    (result) => {
      this.closeResult = `Closed with: ${result}`;
    },
    (reason) => {
      this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
    },
  );
}

delay(ms: number) {
  return new Promise( resolve => setTimeout(resolve, ms) );
}

private getDismissReason(reason: any): string {
  if (reason === ModalDismissReasons.ESC) {
    return 'by pressing ESC';
  } else if (reason === ModalDismissReasons.BACKDROP_CLICK) {
    return 'by clicking on a backdrop';
  } else {
    return `with: ${reason}`;
  }
}

}
  