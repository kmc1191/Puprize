import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AppointmentsComponent } from './appointments/appointments.component';
import { LoginComponent } from './login/login.component';
import { ProfileComponent } from './profile/profile.component';
import { FaqComponent } from './faq/faq.component';
import { ProductsComponent } from './products/products.component';
import { ServicesComponent } from './services/services.component';
import { CartComponent } from './cart/cart.component';
import { HomeComponent } from './home/home.component';
import { MessageComponent } from './message/message.component';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { ProductSearchComponent } from './product-search/product-search.component';
import { AppointmentDetailsComponent } from './appointment-details/appointment-details.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { PetProfileComponent } from './pet-profile/pet-profile.component';

@NgModule({
  declarations: [
    AppComponent,
    AppointmentsComponent,
    LoginComponent,
    ProfileComponent,
    FaqComponent,
    ProductsComponent,
    ServicesComponent,
    CartComponent,
    HomeComponent,
    MessageComponent,
    ProductSearchComponent,
    AppointmentDetailsComponent,
    PetProfileComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    NgbModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
