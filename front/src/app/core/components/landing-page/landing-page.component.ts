import { Component } from '@angular/core';
import { UserService } from 'src/app/shared/user.service';
import { SigninForm } from '../../models/signin-form.model';
import { LocalStorageService } from 'src/app/shared/local-storage.service';
import { Router } from '@angular/router';

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
    private router: Router
  ){}

  onFormChoiceReceive(value: "login" | "signin"): void {
    this.formChoiceToDisplay = value;
  }

  onLoginReceive(pseudoPassword: {pseudo: string, password: string}): void {
    this.userService.isUserExist(pseudoPassword).subscribe(exist => {
      if(exist) {
        this.lss.setPseudoPassword(pseudoPassword);
        this.router.navigateByUrl("/home");
      } else {
        console.log("not found")
      }
    });
  }

  onSigninReceive(signinForm: SigninForm): void {
    this.userService.add(signinForm).subscribe(response => {
      console.log(response)
      if (response) {
        console.log("il y a une erreur")
      } else {
        this.lss.setPseudoPassword({pseudo: signinForm.pseudo, password: signinForm.password});
        this.router.navigateByUrl("/home");
      }
    });
  }

}
