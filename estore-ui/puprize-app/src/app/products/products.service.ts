import { Injectable } from '@angular/core';
import { Product } from '../product';
import {Observable, of } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError, map, tap } from 'rxjs/operators';
import { MessageService } from '../message.service';
import { CartService } from '../cart/cart.service';

@Injectable({
  providedIn: 'root'
})
export class ProductsService {

  private productUrl = 'http://localhost:8080/products'
  private cartUrl = 'http://localhost:8080/cart'

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor( 
    private http: HttpClient,
    private messageService: MessageService,
    private cartService: CartService) { }

  /** Log a ProductService message with the MessageService */
  private log(message: string){
      this.messageService.add(`ProductsService: ${message}`);
  }

  /**
   * GET Products from the server
   */
  getInventory(): Observable<Product[]> {
    return this.http.get<Product[]>(this.productUrl)
    .pipe(tap(_ => this.log('got all products')),
    catchError(this.handleError<Product[]>('getProducts', [])));
  }

  /**
   * GET Product by id. Will 404 if id not found
   */
  getProduct(id: number): Observable<Product> {
    const url = `${this.productUrl}/${id}`; 
    return this.http.get<Product>(url).pipe(
      tap(_ => this.log(`fetched product id=${id}`)),
      catchError(this.handleError<Product>(`getProduct id=${id}`))
    );
  }

  /**
   * UPDATE Product on the server
   */
  updateProduct(product: Product): Observable<any> {
    return this.http.put(this.productUrl, product, this.httpOptions).pipe(
      tap(_ => this.log(`updated product =${product.name}`)),
      catchError(this.handleError<any>('updateProduct'))
    );
  }

  /**
   * POST adds new Product to the server
   */
  addProduct(product: Product): Observable<Product> {
    return this.http.post<Product>(this.productUrl, product, this.httpOptions).pipe(
      tap((newProduct: Product) => this.log(`added product w/ id=${newProduct.id}`)),
      catchError(this.handleError<Product>('addProduct'))
    );
  }

  /** 
   * POST adds a product to a user's cart
   * Not currently in use. See addToCart in cart.service.ts for similar fucntionality
   */
  moveToCart(product: Product): Observable<Product>{
    return this.http.post<Product>(this.cartUrl, this.httpOptions).pipe(
      tap(_ => this.log(`moved product name=${product.name} to cart}`)),
      catchError(this.handleError<Product>('moveToCart'))
    );
  }

  /**
   * DELETE the product from the server
   */
  deleteProduct(id: number): Observable<Product> {
    const url = `${this.productUrl}/${id}`; 
    return this.http.delete<Product>(url, this.httpOptions).pipe(
      tap(_ => this.log(`deleted product id=${id}`)),
      catchError(this.handleError<Product>('deleteProduct'))
    );
  }

  /**
   * GET products whose name contain search keyword
   */
  findProducts(keyword: string): Observable<Product[]> {
    if (!keyword.trim()) {
      // if not search term, return empty product array.
      return of([]);
    }
    return this.http.get<Product[]>(`${this.productUrl}/?name=${keyword}`).pipe(
      tap(x => x.length ?
         this.log(`found products matching "${keyword}"`) :
         this.log(`no products matching "${keyword}"`)),
      catchError(this.handleError<Product[]>('searchProducts', []))
    );
  }


  /**
   * GET products after sorting in a specified order
   */
  prioritize(id: number): Observable<Product[]> { 
    const url = `${this.productUrl}/*?id=${id}`; 
    return this.http.get<Product[]>(`${this.productUrl}/*?id=${id}`).pipe(
      tap(x => x.length ?
         this.log(`prioritized product matching "${id}"`) :
         this.log(`no product matching "${id}"`)),
      catchError(this.handleError<Product[]>('prioritize', []))
    );
  } 

  /**
   * GET inventory after sorting in a specific order
   */
  changeDisplay(keyword: string): Observable<Product[]> {

    if (!keyword.trim()) {
      // if not search term, return the full inventory array.
      const url = `${this.productUrl}`; 
      return this.http.get<Product[]>(url).pipe(
        tap(_ => this.log(`invalid keyword: "${keyword}"`)),
        catchError(this.handleError<Product[]>('changeDisplay', []))
      );
    }
    else{
      const url = `${this.productUrl}/sort?method=${keyword}`; 
      return this.http.get<Product[]>(url).pipe(
        tap(_ => this.log(`sorted inventory by keyword: "${keyword}"`)),
        catchError(this.handleError<Product[]>('changeDisplay', []))
      );
    }
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
