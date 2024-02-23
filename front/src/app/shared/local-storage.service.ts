import { Injectable } from '@angular/core';
import { PseudoPassword } from '../core/models/pseudo-password.model';

@Injectable({
  providedIn: 'root'
})
export class LocalStorageService {

  isPseudoPasswordInLocalStorage(): boolean {
    if (localStorage.getItem("pseudo") === null || localStorage.getItem("pseudo") === "") return false;
    if (localStorage.getItem("password") === null || localStorage.getItem("password") === "") return false;
    return true;
  }

  getPseudoPassword(): PseudoPassword {
    let pp: PseudoPassword = {pseudo: "", password: ""};
    if (this.isPseudoPasswordInLocalStorage()) pp = {pseudo: localStorage.getItem("pseudo") as string, password: localStorage.getItem("password") as string};
    return pp;
  }

  setPseudoPassword(pp: PseudoPassword): void {
    localStorage.setItem("pseudo", pp.pseudo);
    localStorage.setItem("password", pp.password);
  }
}
