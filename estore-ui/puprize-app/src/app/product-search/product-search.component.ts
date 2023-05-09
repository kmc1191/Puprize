import { Component, OnInit } from '@angular/core';

import { Observable, Subject } from 'rxjs';

import {
   debounceTime, distinctUntilChanged, switchMap
 } from 'rxjs/operators';

import { Product } from '../product';
import { ProductsService } from '../products/products.service';
import { ProductsComponent } from '../products/products.component';
import { LoginService } from '../login/login.service';
import { Profile } from '../profile';
import { Login } from '../login';

@Component({
  selector: 'app-product-search',
  templateUrl: './product-search.component.html',
  styleUrls: ['./product-search.component.css']
})
export class ProductSearchComponent implements OnInit {

  products$!: Observable<Product[]>;
  private searchTerms = new Subject<string>();
  isOwner: boolean = false;
  profile: Profile | undefined;
  login: Login | undefined;

  constructor(
    private productService: ProductsService,
    private productComponent: ProductsComponent,
    private loginService: LoginService
    ) {}

  // Push a search term into the observable stream.
  search(term: string): void {
    this.searchTerms.next(term);
  }

  prioritize(product: Product){
    this.productComponent.prioritize(product.id);
  }

  ngOnInit(): void {
    this.products$ = this.searchTerms.pipe(
      // wait 300ms after each keystroke before considering the term
      debounceTime(300),

      // ignore new term if same as previous term
      distinctUntilChanged(),

      // switch to new search observable each time the term changes
      switchMap((term: string) => this.productService.findProducts(term)),
    );
    this.getCurrentUser();
  }

  getLogin(): void {
    this.loginService.getLogin().subscribe(login => this.login = login);
    if( this.login != null ) {
      this.isOwner = this.login.isOwner; 
    }
  }


  getCurrentUser(): void {
    this.loginService.getCurrentUser().subscribe(profile => this.profile = profile);
  }

}
