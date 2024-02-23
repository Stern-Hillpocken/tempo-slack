import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { SigninForm } from '../core/models/signin-form.model';
import { UtilsService } from './utils.service.';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(
    private http: HttpClient,
    private utils: UtilsService
  ){}

  isUserExist(pseudoPassword: {pseudo: string, password: string}): Observable<boolean> {
    return this.http.post<boolean>(this.utils.getBaseUrl()+"users/exist", pseudoPassword);
  }

  add(signinForm: SigninForm): Observable<any> {
    return this.http.post<any>(this.utils.getBaseUrl()+"users", signinForm);
  }
}