import { Component, EventEmitter, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { SigninForm } from 'src/app/core/models/signin-form.model';

@Component({
  selector: 'app-signin',
  templateUrl: './signin.component.html',
  styleUrls: ['./signin.component.scss']
})
export class SigninComponent {

  @Output()
  signinEmitter: EventEmitter<SigninForm> = new EventEmitter();

  formSignin!: FormGroup;

  constructor(
    private fb: FormBuilder
  ){}

  ngOnInit(): void {
    this.formSignin = this.fb.group({
      pseudo: ['Trapez', [Validators.required, Validators.minLength(3), Validators.maxLength(15)]],
      password: ['p@ssword', [Validators.required, Validators.minLength(6), Validators.maxLength(20)]],
      email: ['e@mail.fr'],
      avatar: ['base']
    });
  }

  signin(): void {
    this.signinEmitter.emit(this.formSignin.value);
  }

}
