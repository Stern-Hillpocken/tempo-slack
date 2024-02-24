import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { PopupFeedback } from 'src/app/core/models/popup-feedback.model';
import { User } from 'src/app/core/models/user.model';
import { LocalStorageService } from 'src/app/shared/local-storage.service';
import { PopupFeedbackService } from 'src/app/shared/popup-feedback.service';
import { UserService } from 'src/app/shared/user.service';

@Component({
  selector: 'app-settings-features',
  templateUrl: './settings-features.component.html',
  styleUrls: ['./settings-features.component.scss']
})
export class SettingsFeaturesComponent {

  user: User = {id:0, pseudo:"", password:"", email:"", avatar:"", lastUpdate:new Date(), serverList:[], accountIsActive: true};

  constructor(
    private router: Router,
    private lss: LocalStorageService,
    private userService: UserService,
    private pfs: PopupFeedbackService
  ){}

  ngOnInit(): void {
    this.updateDisplay();
  }

  updateDisplay(): void {
    this.userService.getPrivate(this.lss.getPseudoPassword()).subscribe(u => this.user = u);
  }

  onHomeReceive(): void {
    this.router.navigateByUrl("home");
  }

  onLogoutReceive(): void {
    this.lss.removePseudoPassword();
    this.pfs.setFeed(new PopupFeedback("Vous êtes bien déco 🚪", "valid"));
    this.router.navigateByUrl("/");
  }

  onUpdateReceive(event: any): void {
    this.userService.update(event).subscribe(resp => {
      this.pfs.setFeed(new PopupFeedback("Mise à jour effectuée !", "valid"));
      if (Object.keys(event)[0] === "pseudo") localStorage.setItem("pseudo", event.pseudo);
      if (Object.keys(event)[0] === "password") localStorage.setItem("password", event.password);
      this.updateDisplay();
    });
  }
}
