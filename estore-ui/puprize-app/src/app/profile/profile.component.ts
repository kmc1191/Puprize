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
import { ProfileService } from './profile.service';
import { Appointment } from '../appointment';
import { PetProfile } from '../petProfile';
import { AppointmentsService } from '../appointments/appointments.service';
import { PetProfileService } from '../pet-profile/pet-profile.service';
import { IfStmt } from '@angular/compiler';


@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})


export class ProfileComponent implements OnInit {

  login: Login | undefined;
  profile: Profile | undefined;
  appointment: Appointment | undefined;
  petProfile: PetProfile | undefined;
  apptbool: boolean = false;
  createdPet: boolean = false;

  temp_nails: boolean = false;
  temp_ears: boolean = false;
  temp_bath: boolean = false;
  temp_cut: boolean = false;

  isCheckedNails: boolean = false;
  isCheckedBath: boolean = false;
  isCheckedEar: boolean = false;
  isCheckedCut: boolean = false;

  constructor(

    private loginService: LoginService,
    private profileService: ProfileService,
    private appointmentService: AppointmentsService,
    private petProfileService: PetProfileService,
    private router : Router

  ) { }

  async ngOnInit(): Promise<void> {

    this.getCurrentUser();
    await this.delay(100);
    this.getAppointment();
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

 
async update(profile: Profile): Promise<void>{

  this.profileService.updateProfile(profile)
    .subscribe(profile => this.profile = profile);

   // await this.delay(400);

   // this.getCurrentUs
   
}

async updatePet(petProfile: PetProfile): Promise<void>{

  

}


 setCreate(): void {
  this.createdPet = true;
}

getAppointment(): void {
  if( this.profile != null) {
    this.profileService.getMyAppointment(this.profile.username).subscribe(appointment => this.appointment = appointment) ;
    this.profile.appointment = this.appointment ;
  }
}

async setMyAppointment(id: number): Promise<void> {
  this.getCurrentUser() ;
  await this.delay(200) ;
  if( this.profile != null) {
    this.profileService.setMyAppointment(this.profile.username, id).subscribe(appointment => this.appointment = appointment) ;
  }
}


  async cancelMyAppointment(): Promise<void> {
  if( this.profile != null) {
    this.profileService.cancelMyAppointment(this.profile.username).subscribe(appointment => this.appointment = appointment) ;
  }

  await this.delay(100);

  this.getCurrentUser() ;
  this.getAppointment();
}

async deletePet(): Promise<void> {

if(this.profile !=null){

  this.petProfileService.deletePetProfile(this.profile.username).subscribe(petProfile => this.petProfile = petProfile);

  await this.delay(400);

  this.getPetProfile();

}

}

async createPet(data: { name: string; age: number; breed: string; temperament: string; extraInfo: string; nails: string; earClean: string; bath: string; style: String; cut: string;}): Promise<void>{

  if(this.profile!=null){

  const name = data.name ;
  const age = data.age ;
  const breed = data.breed ;
  const temperament = data.temperament ;
  const extraInfo = data.extraInfo ;
  const style = data.style
  const username = this.profile.username;


  if(data.earClean == "true"){
    this.temp_ears = true;
  }
  if(data.nails == "true"){
    this.temp_ears = true;
  }
  if(data.bath == "true"){
    this.temp_bath = true;
  }
  if(data.cut == "true"){
    this.temp_cut = true;
  }

  //const earClean = this.temp_ears;
  //const nails = this.temp_nails
  const nails = this.isCheckedNails ;
  const bath = this.isCheckedBath ;
  const cut = this.isCheckedCut ;
  const earClean = this.isCheckedEar ;
  //const bath = this.temp_bath;
  //const cut = this.temp_cut;

  this.petProfileService.createPetProfile({name, age, breed, temperament, extraInfo, nails, 
    earClean, bath, style, cut, username} as PetProfile).subscribe(petProfile => this.petProfile = petProfile);

  await this.delay(400);

  this.getPetProfile();

  await this.delay(400);

  this.isCheckedNails = false;
  this.isCheckedBath = false;
  this.isCheckedCut = false;
  this.isCheckedEar = false;

  //IF DATA.NAILS /EAR /... == "TRUE" SET IT TO TRUE ELSE LEAVE IT
  //WANT IT AS A BOOLEAN TO PASS IN TO PROFILE SERVICE


  //CREATE PET PROFILE SERVICE, HAVE CALL TO CREATE PET PROFILE SERVICE
  //USE DELAY (SEE LOGIN FOR CREATE PROFILE)
  //UPDATE
  //SET TO TRUE SO THE PET PROFILE IS DISPLAYED AND THIS IS GONE
  //DONE

  }



}


  //Navigates user to login page if not logged in
  redirectUser(): void {
    this.router.navigate(['/login'])
  }


  delay(ms: number) {
    return new Promise( resolve => setTimeout(resolve, ms) );
 }


}
