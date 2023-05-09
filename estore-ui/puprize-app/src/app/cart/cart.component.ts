import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { Product } from '../product' ;
import { CartService } from './cart.service';
import { MessageService } from '../message.service';
import { LoginService } from '../login/login.service';
import { Profile } from '../profile';
import { waitForAsync } from '@angular/core/testing';
import { timer } from 'rxjs';
import { Cart } from '../cart';
import { Checkout } from '../checkout';
import { NgbModal, ModalDismissReasons } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})

export class CartComponent implements OnInit {
  cart: Product[] | undefined ;
  profile: Profile | undefined;
  checked: Checkout | undefined ;
  delivery: boolean = false ;
  DELIVERY_FEE: number = 10 ;
  totalCost: number | undefined ;
  closeResult = '';

  constructor(
    
    private cartService: CartService, 
    private loginService: LoginService,
    private router : Router,
    private modalService: NgbModal,
    ) { 
    }

  ngOnInit(): void {
    this.getCurrentUser();
    timer(100).subscribe(x => 
      { 
        this.getItems();
        this.getTotalCost();
     });
  }

  getItems(): void {
    if( this.profile != null ) {
      this.cartService.getItems(this.profile.username)
      .subscribe(cart => this.cart = cart) ;
    }
    //else {
    //   this.cart = undefined ;
    //}
  }

  async emptyCart(): Promise<void> {
    if( this.profile != null ) {
      this.cartService.emptyCart(this.profile.username).subscribe(cart => this.cart = cart) ;
      await this.delay(200);
      this.getItems() ;
    }
  }

  /** 
  add(id: number, quantity: number): void {
    this.cartService.addToCart(id, quantity).subscribe(); 
  }
  */

  async remove(id: number, quantity: number): Promise<void> {
    if( this.profile != null ) {
      this.cartService.removeFromCart(this.profile.username, id, quantity).subscribe();
      await this.delay(200);
      this.getItems() ;
    }
  }

  getTotalCost(): void {
    if( this.profile != null ) {
      this.cartService.getTotalCost(this.profile.username).subscribe(totalCost => this.totalCost = totalCost);
    }
  }

  async checkout(): Promise<void> {
    if( this.profile != null ) {
      this.cartService.checkout( this.profile.username ).subscribe(checked => this.checked = checked) ;
      
    }
    await this.delay(300);
    this.getItems() ;
  }

  getCurrentUser(): void {
    this.loginService.getCurrentUser().subscribe(profile => this.profile = profile);
   }

   //Navigates user to login page if not logged in
  redirectUser(): void {
    this.router.navigate(['/products'])
  }

  redirectToLogin(): void {
    this.router.navigate(['/login'])
  }

  delay(ms: number) {
    return new Promise( resolve => setTimeout(resolve, ms) );
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
