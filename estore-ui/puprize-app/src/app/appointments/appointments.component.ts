import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { Appointment } from '../appointment';
import { LoginService } from '../login/login.service';
import { Profile } from '../profile';
import { AppointmentsService } from './appointments.service';

@Component({
  selector: 'app-appointments',
  templateUrl: './appointments.component.html',
  styleUrls: ['./appointments.component.css']
})
export class AppointmentsComponent implements OnInit {


  appointments: Appointment[] = [];
  currAppointment: Appointment | undefined;
  searchedAppointments: Appointment[] = [];
  profile: Profile | undefined;

  constructor(
    private appointmentsService: AppointmentsService,
    private route: ActivatedRoute,
    private loginService: LoginService
    ) { }

  ngOnInit(): void {
    this.getAppointments();
    this.getCurrentUser();
  }

  getAppointments(): void {
    this.appointmentsService.getAppointments()
    .subscribe(appointments => this.appointments = appointments);
  }

  getAppointment(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.appointmentsService.getAppointment(id)
    .subscribe(appointment => this.currAppointment = appointment);
  }

  find(date: string, time: string): void {
    this.appointmentsService.findAppointments(date, time)
    .subscribe(searchedAppointments => this.searchedAppointments = searchedAppointments);
  }

  getCurrentUser(): void {
    this.loginService.getCurrentUser().subscribe(profile => this.profile = profile);
 }

}
