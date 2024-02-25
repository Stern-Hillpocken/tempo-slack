import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { PopupFeedbackService } from '../shared/popup-feedback.service';
import { PopupFeedback } from './models/popup-feedback.model';
import { LocalStorageService } from '../shared/local-storage.service';

@Injectable({
  providedIn: 'root'
})
export class UserGuard {

  constructor(
    private router: Router,
    private lss: LocalStorageService,
    private pfs: PopupFeedbackService
  ){}

  canActivate(): boolean {
      if(this.lss.isPseudoPasswordInLocalStorage()){
        console.log("You are logged")
        return true;
      }else{
        this.pfs.setFeed(new PopupFeedback("Tu dois être connecté·e pour continuer 🔐", "error"));
        this.router.navigateByUrl("/");
        return false;
      }
  }
  
}
