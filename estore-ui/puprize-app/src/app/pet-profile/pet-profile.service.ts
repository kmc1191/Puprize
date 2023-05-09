import { Injectable } from '@angular/core';
import { Login } from '../login';
import {Observable, of, pipe } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError, map, tap } from 'rxjs/operators';
import { Profile } from '../profile';
import { MessageService } from '../message.service';
import { Appointment } from '../appointment';
import { PetProfile } from '../petProfile';


@Injectable({
  providedIn: 'root'
})
export class PetProfileService {

  private petProfileUrl = 'http://localhost:8080/petProfiles'

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(
    private http: HttpClient,
    private messageService: MessageService,
  ) { }

  /** Log a PetProfileService message with the MessageService */
  private log(message: string){
    this.messageService.add(`PetProfileService: ${message}`);
}

/** POST a new pet profile to the server */
createPetProfile(petProfile: PetProfile): Observable<any> {
  return this.http.post<PetProfile>(this.petProfileUrl, petProfile, this.httpOptions).pipe(
    tap((petProfile: PetProfile) => this.log(`made pet profile w/name = ${petProfile.name}}`)),
    catchError(this.handleError<PetProfile>('makePetProfile'))
  );
}

/** GET Pet Profile by username. Will 404 if id not found */
 getPetProfile(username: string): Observable<PetProfile> {
  const url = `${this.petProfileUrl}/${username}`; 
  return this.http.get<PetProfile>(url).pipe(
    tap(_ => this.log(`fetched pet profile username=${username}`)),
    catchError(this.handleError<PetProfile>(`getPetProfile username=${username}`))
  );
}

/** DELETE a Pet Profile from the server by username */
deletePetProfile(username: string): Observable<PetProfile> {
  const url = `${this.petProfileUrl}/${username}`; 
  return this.http.delete<PetProfile>(url, this.httpOptions).pipe(
    tap(_ => this.log(`deleted pet profile username=${username}`)),
    catchError(this.handleError<PetProfile>('deletePetProfile'))
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
