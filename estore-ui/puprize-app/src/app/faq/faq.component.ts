import { Component, OnInit } from '@angular/core';
import { LoginService } from '../login/login.service';
import { Profile } from '../profile';
import { Login } from '../login';
import { NgbModal, ModalDismissReasons } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-faq',
  templateUrl: './faq.component.html',
  styleUrls: ['./faq.component.css']
})
export class FaqComponent implements OnInit {
  faqStrings: String[] = [];
  profile: Profile | undefined;
  isOwner: boolean = false;
  login: Login | undefined;
  closeResult ='';

  constructor(
    private loginService: LoginService,
    private modalService: NgbModal
  ) { }

  getCurrentUser(): void {
    this.loginService.getCurrentUser().subscribe(profile => this.profile = profile);
 }

 getLogin(): void {
  this.loginService.getLogin().subscribe(login => this.login = login);
  if( this.login != null ) {
    //this.productsComponent.onLogin(this.login?.isOwner);
  }
}

loginUser(data: { username: String; password: String; }): void {
}

open(content: any) {
  this.modalService.open(content, { ariaLabelledBy: 'modal-basic-title' }).result.then(
    (result) => {
      this.closeResult = `Closed with: ${result}`;
    },
    (reason) => {
      this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
    },
  );
}
private getDismissReason(reason: any): string {
  if (reason === ModalDismissReasons.ESC) {
    return 'by pressing ESC';
  } else if (reason === ModalDismissReasons.BACKDROP_CLICK) {
    return 'by clicking on a backdrop';
  } else {
    return `with: ${reason}`;
  }
}

updateString(index: number, updatedString: string){
  this.faqStrings[index] = updatedString;
}

  ngOnInit(): void {
    this.getCurrentUser();
    this.faqStrings[0] = "Q: What do I need to bring to my appointment?";
    this.faqStrings[1] = "A: Your dog and a leash is all you will need!";
    this.faqStrings[2] = "Q: How much does it cost to get a haircut done?";
    this.faqStrings[3] = "A: Our services page details the standard prices for all the services we offer at Puprize!";
    this.faqStrings[4] = "Q: Can I bring my dog even though they aren’t friendly with strangers?";
    this.faqStrings[5] = "A: Yes! Our team at Puprize! is committed to providing great service for ALL dogs and are equipped with the right tools and compassion to help your dog have a great grooming experience.";
    this.faqStrings[6] = "Q: Are the products on the website the same as the products you use in the store?";
    this.faqStrings[7] = "A: Yes! We sell the products we know work and that you’ll love using. All products used in our salon are available for purchase on our website.";
    this.faqStrings[8] = "Q: I really like one of your products but it’s not on the website, how can I buy more?";
    this.faqStrings[9] = "A: If a product is out of stock, you can ask our groomers at our location if they have any available for purchase in store! Otherwise, we are out of stock for the time being.";
  }

}
