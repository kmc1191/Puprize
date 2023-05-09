import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-services',
  templateUrl: './services.component.html',
  styleUrls: ['./services.component.css']
})
export class ServicesComponent implements OnInit {

  images = ["assets/images/DogBathing.jpg", "assets/images/DogHaircut.jpg", "assets/images/DogEarClean.jpg", "assets/images/DogNailTrim.jpg"];

  constructor() { }

  ngOnInit(): void {
  }
  
}

