import { Injectable } from '@angular/core';
import { Product } from '../product';
import { Checkout } from '../checkout';
import { CartDataTransfer } from '../cartDataTransfer';
import {Observable, of } from 'rxjs';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { catchError, map, tap } from 'rxjs/operators';
import { MessageService } from '../message.service';
import { Cart } from '../cart';

@Injectable({
  providedIn: 'root'
})
export class CartService {

  private cartUrl = 'http://localhost:8080/cart'

  //Allow: GET, POST, HEAD

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor( 
    private http: HttpClient,
    private messageService: MessageService) { }

    /** Log a CartService message with the MessageService */
  private log(message: string){
    this.messageService.add(`CartService: ${message}`);
  }

  /** GET products from the cart server */
  getCart(username: String): Observable<Cart> {
    const url = `${this.cartUrl}/?user=${username}`;
    return this.http.get<Cart>(url)
    .pipe(
      tap(_ => this.log('fetched cart for user ' + username)),
      catchError(this.handleError<Cart>('getCart'))
    );
  }

  /** GET products from the cart server */
  getItems(username: String): Observable<Product[]> {
    const url = `${this.cartUrl}/items?user=${username}`;
    return this.http.get<Product[]>(url)
    .pipe(
      tap(_ => this.log('fetched products from ' + username + '\'s cart')),
      catchError(this.handleError<Product[]>('getCart', []))
    );
  }

  /** POST: add a new product to the cart */
  addToCart(username: String, id: number, quantity: number): Observable<Product> {
    /** 
    const url = `${this.cartUrl}/${id}:${quantity}`;
    var data = {id, quantity } as CartDataTransfer

    return this.http.post<Product>(this.cartUrl, data, this.httpOptions).pipe(
      tap((newProduct: Product) => this.log(`added product to cart w/ id=${newProduct.id}, quantity=${newProduct.quantity}`)),
      catchError(this.handleError<Product>('addToCart'))
    );
      // tap((newProduct: Product) => this.log(`added product w/ id=${newProduct.id}`)),
      // catchError(this.handleError<Product>('addProduct'))
    */
    const url = `${this.cartUrl}/?user=${username}&id=${id}&quantity=${quantity}`
    return this.http.post<Product>(url, this.httpOptions).pipe(
      tap(_ => this.log(`moved quantity=${quantity} product(s) of id=${id} to username=${username}'s cart`)),
      catchError(this.handleError<Product>('moveToCart'))
    );
  }

  /** DELETE: delete the hero from the cart server */
  removeFromCart(username: String, id: number, quantity: number): Observable<Product> {
    const url = `${this.cartUrl}/?user=${username}&id=${id}&quantity=${quantity}`

    return this.http.delete<Product>(url, this.httpOptions).pipe(
      tap(_ => this.log(`removed product from cart with w/ id=${id}, quantity=${quantity}`)),
      catchError(this.handleError<Product>('removeFromCart'))
    );
  }

  /** DELETE: empty the server's shopping cart */
  emptyCart(username: String): Observable<Product[]> {
    const url = `${this.cartUrl}/*?user=${username}`;
    return this.http.delete<Product[]>(url, this.httpOptions).pipe(
      tap(_ => this.log(`emptied shopping cart for user ${username}`)),
      catchError(this.handleError<Product[]>('emptyCart'))
    );
  }

  /** GET the total price of all products in the cart and remove them
   * from the inventory
   */
  checkout(username: String): Observable<Checkout> {
    const url = `${this.cartUrl}/*?user=${username}`;
    return this.http.get<Checkout>(url)
    .pipe(
      tap(_ => this.log('checkout out all cart products for user ' + username)),
      catchError(this.handleError<Checkout>('checkout'))
    );
  }

  /** GET the total price of all products in the cart and remove them
   * from the inventory
   */
   getTotalCost(username: String): Observable<number> {
    const url = `${this.cartUrl}/**?user=${username}`;
    return this.http.get<number>(url)
    .pipe(
      tap(_ => this.log('received cost of all products in cart for user ' + username)),
      catchError(this.handleError<number>('getTotalCost'))
    );
  }

  /**
   * Handle Http operation that failed.
   * Let the app continue.
   *
   * @param operation - name of the operation that failed
   * @param result - optional value to return as the observable result
   */
  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {

      // TODO: send the error to remote logging infrastructure
      console.error(error); // log to console instead

      // TODO: better job of transforming error for user consumption
      this.log(`${operation} failed: ${error.message}`);

      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
}


}
