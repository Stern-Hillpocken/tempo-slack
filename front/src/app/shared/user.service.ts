import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { SigninForm } from '../core/models/signin-form.model';
import { UtilsService } from './utils.service';
import { UserPublic } from '../core/models/user-public.model';
import { PseudoPassword } from '../core/models/pseudo-password.model';
import { User } from '../core/models/user.model';
import { LocalStorageService } from './local-storage.service';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(
    private http: HttpClient,
    private utils: UtilsService,
    private lss: LocalStorageService
  ){}

  isUserExist(pseudoPassword: {pseudo: string, password: string}): Observable<boolean> {
    return this.http.post<boolean>(this.utils.getBaseUrl()+"users/exist", pseudoPassword);
  }

  add(signinForm: SigninForm): Observable<any> {
    return this.http.post<any>(this.utils.getBaseUrl()+"users", signinForm);
  }

  getPublic(pseudo: string): Observable<UserPublic> {
    return this.http.get<UserPublic>(this.utils.getBaseUrl()+"users/"+pseudo);
  }

  getPrivate(pseudoPassword: PseudoPassword): Observable<User> {
    return this.http.post<User>(this.utils.getBaseUrl()+"users/me", pseudoPassword);
  }

  update(obj: any): Observable<any> {
    let dto = {...obj, user: this.lss.getPseudoPassword()};
    return this.http.put(this.utils.getBaseUrl()+"users/"+Object.keys(obj)[0], dto);
  }
}
