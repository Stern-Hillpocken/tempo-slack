import { Component } from '@angular/core';
import { UserService } from 'src/app/shared/user.service';
import { SigninForm } from '../../models/signin-form.model';
import { LocalStorageService } from 'src/app/shared/local-storage.service';
import { Router } from '@angular/router';
import { PopupFeedbackService } from 'src/app/shared/popup-feedback.service';
import { PopupFeedback } from '../../models/popup-feedback.model';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-landing-page',
  templateUrl: './landing-page.component.html',
  styleUrls: ['./landing-page.component.scss']
})
export class LandingPageComponent {

  formChoiceToDisplay: "login" | "signin" = "login";

  constructor(
    private userService: UserService,
    private lss: LocalStorageService,
    private router: Router,
    private pfbs: PopupFeedbackService,
    private http: HttpClient
  ){}

  ngOnInit(): void {
    this.lss.removePseudoPassword();
  }

  onFormChoiceReceive(value: "login" | "signin"): void {
    this.formChoiceToDisplay = value;
  }

  onLoginReceive(pseudoPassword: {pseudo: string, password: string}): void {
    this.userService.isUserExist(pseudoPassword).subscribe(exist => {
      if(exist) {
        this.pfbs.setFeed(new PopupFeedback("Bon retour parmi nous 👋", "valid"));
        this.lss.setPseudoPassword(pseudoPassword);
        this.router.navigateByUrl("/home");
      } else {
        this.pfbs.setFeed(new PopupFeedback("Problème lors de la connexion.", "error"));
      }
    });
  }

  onSigninReceive(signinForm: SigninForm): void {
    this.userService.add(signinForm).subscribe(response => {
      console.log(response)
      if (response) {
        this.pfbs.setFeed(new PopupFeedback("Problème lors de l'enregistrement.", "error"));
      } else {
        this.pfbs.setFeed(new PopupFeedback("Bienvenue parmi les 🐙 !!", "valid"));
        this.lss.setPseudoPassword({pseudo: signinForm.pseudo, password: signinForm.password});
        this.router.navigateByUrl("/home");
      }
    });
  }

  generateServers(): void {
    this.http.get("http://localhost:8080/init").subscribe(resp => this.pfbs.setFeed(new PopupFeedback("Servers générés !!","valid")));
  }

}
