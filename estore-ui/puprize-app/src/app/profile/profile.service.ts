import { Injectable } from '@angular/core';
import { Login } from '../login';
import {Observable, of, pipe } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError, map, tap } from 'rxjs/operators';
import { Profile } from '../profile';
import { MessageService } from '../message.service';
import { ProfileComponent } from './profile.component';
import { Appointment } from '../appointment';


@Injectable({
  providedIn: 'root'
})
export class ProfileService {

  private profileUrl = 'http://localhost:8080/profiles'

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(
    private http: HttpClient,
    private messageService: MessageService,
  ) { }

  /** Log a ProductService message with the MessageService */
  private log(message: string){
    this.messageService.add(`ProfileService: ${message}`);
}

/** POST a new profile to the server */
  createProfile(profile: Profile): Observable<any> {
    return this.http.post<Profile>(this.profileUrl, profile, this.httpOptions).pipe(
      tap((profile: Profile) => this.log(`made profile w/username = ${profile.username}}`)),
      catchError(this.handleError<Profile>('makeProfile'))
    );
  }

  /** PUT an updated profile into the server */
  updateProfile(profile: Profile): Observable<any> {
    return this.http.put(this.profileUrl, profile, this.httpOptions).pipe(
      tap(_ => this.log(`updated profile =${profile.name}`)),
      catchError(this.handleError<any>('updateProduct'))
    );
  }

  /** GET a profile from the server by username */
  getMyAppointment(username: String): Observable<any> {
    const url = `${this.profileUrl}/app?username=${username}`;
    return this.http.get(url).pipe(
      tap(_ => this.log(`got appointment for =${username}`)),
      catchError(this.handleError<Appointment>('getAppointment'))
    );
  }

  /** SET an appointment to a specific profile by username */
  setMyAppointment(username: String, appointmentId: number): Observable<any> {
    const url = `${this.profileUrl}/*app?username=${username}&appointmentID=${appointmentId}`;
    return this.http.post(url, this.httpOptions).pipe(
      tap(_ => this.log(`set appointment ${appointmentId} for =${username}`)),
      catchError(this.handleError<Profile>('setAppointment'))
    );
  }

  /** POST: removes the appointment that is assigned to a profile by username */
  cancelMyAppointment(username: String): Observable<any> {
    const url = `${this.profileUrl}/-app?username=${username}`;
    return this.http.post(url, this.httpOptions).pipe(
      tap(_ => this.log(`cancelled appointment for =${username}`)),
      catchError(this.handleError<Appointment>('cancelAppointment'))
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





