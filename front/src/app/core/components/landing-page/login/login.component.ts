import { Component, EventEmitter, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {

  @Output()
  loginEmitter: EventEmitter<{pseudo: string, password: string}> = new EventEmitter();

  formLogin!: FormGroup;

  constructor(
    private fb: FormBuilder
  ){}

  ngOnInit(): void {
    this.formLogin = this.fb.group({
      pseudo: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(15)]],
      password: ['', [Validators.required, Validators.minLength(6), Validators.maxLength(20)]]
    });
  }

  login(): void {
    this.loginEmitter.emit(this.formLogin.value);
  }

}
