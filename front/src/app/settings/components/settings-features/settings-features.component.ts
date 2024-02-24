import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { LocalStorageService } from 'src/app/shared/local-storage.service';

@Component({
  selector: 'app-settings-features',
  templateUrl: './settings-features.component.html',
  styleUrls: ['./settings-features.component.scss']
})
export class SettingsFeaturesComponent {

  constructor(
    private router: Router,
    private lss: LocalStorageService
  ){}

  onHomeReceive(): void {
    this.router.navigateByUrl("home");
  }

  onLogoutReceive(): void {
    this.lss.removePseudoPassword();
    this.router.navigateByUrl("/");
  }
}
