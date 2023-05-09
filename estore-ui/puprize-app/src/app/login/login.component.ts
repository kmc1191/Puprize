import { Component, Input, OnInit, Output, EventEmitter } from '@angular/core';
import { Router } from '@angular/router';

import { Login } from '../login';
import { LoginService } from './login.service';
import { MessageService } from '../message.service';
import { NgForm } from '@angular/forms';
import { ProductsComponent } from '../products/products.component'; 
import { CartService } from '../cart/cart.service';

import { Profile } from '../profile';
import { Product } from '../product';
import { ProfileService } from '../profile/profile.service';
import { ProductsService } from '../products/products.service';

 
@Component({
 selector: 'app-login',
 templateUrl: './login.component.html',
 styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
    
    login: Login | undefined;
    profile: Profile | undefined;
    loginFail: boolean = false;
    createUser: boolean = false;
    ignore: boolean = false;

    tempName: String = "";

    //@Output() ownership = new EventEmitter<boolean>(); 

 constructor(
   private loginService : LoginService, 
   private cartService : CartService,
   private profileService : ProfileService,
   //private productsComponent : ProductsComponent,
   private productsService : ProductsService,
   private router : Router
   ) { }

 
 ngOnInit(): void {
    this.getLogin();
    this.getCurrentUser();
 }

 getLogin(): void {
    this.loginService.getLogin().subscribe(login => this.login = login);
    if( this.login != null ) {
      //this.productsComponent.onLogin(this.login?.isOwner);
    }
 }


 async loginUser(data: { username: String; password: String; }): Promise<void> {

   //const { username, password } = this.form;

   //this.loginService.tryLogin(this.form.username, this.form.password).subscribe(login => this.login = login);
   this.loginService.tryLogin(data.username, data.password).subscribe(profile => this.profile = profile);
   await this.delay(200) ;
   this.loginService.getCurrentUser().subscribe(profile => this.profile = profile);
   //this.getLogin();
   //if(this.profile != null){
   //   this.worked=true;
   //   this.router.navigate(['/home'])
   //}  
   //this.checkData(this.profile) ;
   //this.ownership.emit(this.login.isOwner);
}

async logoutUser(): Promise<void> {
   if(this.profile != null ) {
      //this.loginService.logout(this.profile.username).subscribe(profile => this.checkData(profile));
      this.loginService.logout(this.profile.username).subscribe(profile => this.checkData(profile));
      
      //Gives loginservice time to logout user so we DONT have to 
      //refresh to see it go back to the normal log in page
      await this.delay(100);
      
      this.loginService.getCurrentUser().subscribe(profile => this.profile = profile);
   }
}

checkData(profile: Profile | undefined) {
   if(profile != null){
      this.router.navigate(['/home'])
   }  
   else{
      this.loginFail = true;
   }
}

getCurrentUser(): void {
   this.loginService.getCurrentUser().subscribe(profile => this.profile = profile);
}

setCreate(): void{
   this.createUser = true;
}


   async createProfileHelper(data: { name: string; username: string; password: string; email: string; phone: string; }): Promise<void>{

   //No idea how to pass it in as a profile, doign exactly what product component ts does and it throws a large error
   const name = data.name ;
   const username = data.username ;
   const password = data.password ;
   const email = data.email ;
   const phone = data.phone ;
   const isOwner = false ;
   this.profileService.createProfile({ name, username, password, email, phone, isOwner } as Profile).subscribe(profile => this.profile = profile);
   
   await this.delay(600);

   this.loginService.tryLogin(data.username, data.password).subscribe(profile => this.profile = profile);

   await this.delay(300);

   this.loginService.getCurrentUser().subscribe(profile => this.profile = profile);

}

delay(ms: number) {
   return new Promise( resolve => setTimeout(resolve, ms) );
}


}

