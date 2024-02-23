import { Component } from '@angular/core';

@Component({
  selector: 'app-landing-page',
  templateUrl: './landing-page.component.html',
  styleUrls: ['./landing-page.component.scss']
})
export class LandingPageComponent {

  formChoiceToDisplay: "login" | "signin" = "login";

  onFormChoiceReceive(value: "login" | "signin"): void {
    this.formChoiceToDisplay = value;
  }

}
