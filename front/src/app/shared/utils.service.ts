import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { SigninForm } from '../core/models/signin-form.model';

@Injectable({
  providedIn: 'root'
})
export class UtilsService {

  private readonly _baseUrl: string = "http://localhost:8080/";

  getBaseUrl(): string {
    return this._baseUrl;
  }
}
