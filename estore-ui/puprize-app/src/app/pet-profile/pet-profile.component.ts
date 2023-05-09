
import { Component, Input, OnInit, Output, EventEmitter } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Router } from '@angular/router';

import { Product } from '../product';
import { MessageService } from '../message.service';
import { CartService } from '../cart/cart.service';
import { LoginComponent } from '../login/login.component';
import { LoginService } from '../login/login.service';
import { Login } from '../login';
import { Profile } from '../profile';
import { ProfileService } from '../profile/profile.service';
import { Appointment } from '../appointment';
import { PetProfile } from '../petProfile';
import { AppointmentsService } from '../appointments/appointments.service';
import { PetProfileService } from './pet-profile.service';

@Component({
  selector: 'app-pet-profile',
  templateUrl: './pet-profile.component.html',
  styleUrls: ['./pet-profile.component.css']
})
export class PetProfileComponent implements OnInit {

  login: Login | undefined;
  profile: Profile | undefined;
  petProfile: PetProfile | undefined;


  constructor(
    private loginService: LoginService,
    private profileService: ProfileService,
    private appointmentService: AppointmentsService,
    private router : Router,
    private petProfileService: PetProfileService,
  ) { }

  async ngOnInit(): Promise<void> {

      this.getCurrentUser();
      await this.delay(100);
      this.getPetProfile();

      
    }
  
    //gets the current user
    getCurrentUser(): void {
      this.loginService.getCurrentUser().subscribe(profile => this.profile = profile);
   }

   getPetProfile(): void {
    if(this.profile!=null){
      this.petProfileService.getPetProfile(this.profile.username).subscribe(petProfile => this.petProfile = petProfile)
    }
   }
  




  delay(ms: number) {
    return new Promise( resolve => setTimeout(resolve, ms) );
 }

}
