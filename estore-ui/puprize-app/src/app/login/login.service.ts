import { Injectable } from '@angular/core';
import { Login } from '../login';
import {Observable, of, pipe } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError, map, tap } from 'rxjs/operators';
import { Profile } from '../profile';
import { MessageService } from '../message.service';
 
@Injectable({
 providedIn: 'root'
})
export class LoginService {
 
 private url = 'http://localhost:8080/login'
 private profileURL = 'http://localhost:8080/profiles'
 
 httpOptions = {
   headers: new HttpHeaders({ 'Content-Type': 'application/json' })
 };
 
 constructor(
   private http: HttpClient,
   private messageService: MessageService) { }
 
   /** Log a LoginService message with the MessageService */
 private log(message: string){
     this.messageService.add(`LoginService: ${message}`);
 }
 
 /** GET the login from the server */
 getLogin(): Observable<Login> {
   return this.http.get<Login>(this.url)
   .pipe(tap(_ => this.log('got login')),
   catchError(this.handleError<Login>('getLogin')));
 }

 /** PUT: a login is given to the server and a login attempt is made */
 tryLogin(username : String, password : String): Observable<any> {
  return this.http.put(`${this.url}?username=${username}&password=${password}`, {
    username,
    password
  }).pipe(tap(_ => this.log('login attempt: ' + username + " " + password)),
  catchError(this.handleError<any>('tryLogin')));

}

/** GET the user that is currently logged in */
getCurrentUser(): Observable<any> {
  return this.http.get(`${this.url}/*`)
  .pipe(tap(_ => this.log('got current user')),
   catchError(this.handleError<Login>('getCurrentUser')));
}

/** PUT: if the given username belongs to the current user, logs them out */
logout(username : String,): Observable<any> {
  //this.log('not finding username for user: ' + username) ;
  return this.http.put<any>(`${this.url}/?username=${username}`, username)
  .pipe(tap(_ => this.log('logged out current user: ' + username)),
   catchError(this.handleError<any>('logout')));
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
