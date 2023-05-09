import { Injectable } from '@angular/core';
import { Appointment } from '../appointment';
import {Observable, of } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError, map, tap } from 'rxjs/operators';
import { MessageService } from '../message.service';

@Injectable({
  providedIn: 'root'
})
export class AppointmentsService {

  private appointmentsUrl = 'http://localhost:8080/appointments'

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(private http: HttpClient,
    private messageService: MessageService) {}

    /** Log a AppointmentsService message with the MessageService */
    private log(message: string){
    this.messageService.add(`AppointmentsService: ${message}`);}

  /**
  * GET Appointments from the server
  */
  getAppointments(): Observable<Appointment[]> {
    return this.http.get<Appointment[]>(this.appointmentsUrl)
    .pipe(tap(_ => this.log('got all Appointments')),
    catchError(this.handleError<Appointment[]>('getAppointments', [])));
  }

  /**
  * GET Appointment by id. Will 404 if id not found
  */
  getAppointment(id: number): Observable<Appointment> {
      const url = `${this.appointmentsUrl}/${id}`; 
      return this.http.get<Appointment>(url).pipe(
        tap(_ => this.log(`fetched Appointment id=${id}`)),
        catchError(this.handleError<Appointment>(`getAppointment id=${id}`))
      );
  }

  /**
  * GET Appointments by date and/or time
  */
  findAppointments(date: string, time: string): Observable<Appointment[]> {
    const url = `${this.appointmentsUrl}/?date=${date}&time=${time}`; 
    return this.http.get<Appointment[]>(url).pipe(
      tap(_ => this.log(`fetched Appointment date=${date} & time =${time}`)),
      catchError(this.handleError<Appointment[]>(`findAppointments`, []))
    );
  }

  /**
   * PUT Update an Appointment
   */
  updateAppointment(appointment: Appointment): Observable<any> {
    return this.http.put(this.appointmentsUrl, appointment, this.httpOptions).pipe(
      tap(_ => this.log(`updated appointment id=${appointment.id}`)),
      catchError(this.handleError<any>('updateAppointment'))
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
