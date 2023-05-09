import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { LoginService } from '../login/login.service';
import { Appointment } from '../appointment';
import { AppointmentsService } from '../appointments/appointments.service';
import { Profile } from '../profile';
import { ProfileService } from '../profile/profile.service';


@Component({
  selector: 'app-appointment-detail',
  templateUrl: './appointment-details.component.html',
  styleUrls: ['./appointment-details.component.css']
})
export class AppointmentDetailsComponent implements OnInit {
  appointments: Appointment[] = [];
  currAppointment: Appointment | undefined;
  profile: Profile | undefined;
  date = String(this.route.snapshot.paramMap.get('date'));

  constructor(
    private route: ActivatedRoute,
    private appointmentsService: AppointmentsService,
    private loginService: LoginService,
    private profileService: ProfileService,
    private location: Location,
  ) {}

  ngOnInit(): void {
    this.findAppointments();
    this.getCurrentUser();
  }

  findAppointments(): void {
    const date= String(this.route.snapshot.paramMap.get('date'));
    this.appointmentsService.findAppointments(date, "")
      .subscribe(appointments => this.appointments = appointments);
  }

  async update(appointment: Appointment): Promise<void>{

    this.appointmentsService.updateAppointment(appointment)
    .subscribe(appointment => this.currAppointment = appointment);

    await this.delay(100);

    this.setAppointment(appointment);

    // Kaitlynn
    
    // End Kaitlynn
  }

  async setAppointment(appointment: Appointment): Promise<void> {
    this.getCurrentUser();
    await this.delay(100);
    if(this.profile != null ){
      this.getCurrentUser();
      this.profileService.setMyAppointment(this.profile.username, appointment.id).subscribe(profile => this.profile = profile);
    }
  }

  getCurrentUser(): void {
    this.loginService.getCurrentUser().subscribe(profile => this.profile = profile);
  }

  goBack(): void {
    this.location.back();
  }

  delay(ms: number) {
    return new Promise( resolve => setTimeout(resolve, ms) );
  }

}