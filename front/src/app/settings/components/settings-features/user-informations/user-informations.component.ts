import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { User } from 'src/app/core/models/user.model';

@Component({
  selector: 'app-user-informations',
  templateUrl: './user-informations.component.html',
  styleUrls: ['./user-informations.component.scss']
})
export class UserInformationsComponent {

  @Input()
  user!: User;

  @Output()
  updateEmitter: EventEmitter<any> = new EventEmitter();

  attributeEditing: "avatar" | "pseudo" | "password" | "email" | "" = "";

  formAvatar!: FormGroup;
  formPseudo!: FormGroup;
  formPassword!: FormGroup;
  formEmail!: FormGroup;

  constructor(private fb: FormBuilder){}

  fillForm(): void {
    this.formAvatar = this.fb.group({
      avatar: [this.user.avatar, [Validators.required]]
    });
    this.formPseudo = this.fb.group({
      pseudo: [this.user.pseudo, [Validators.required, Validators.minLength(3), Validators.maxLength(15)]]
    });
    this.formPassword = this.fb.group({
      password: ["", [Validators.required, Validators.minLength(6), Validators.maxLength(20), Validators.pattern('.*\\W+.*')]]
    });
    this.formEmail = this.fb.group({
      email: [this.user.email, [Validators.email]]
    });
  }

  edit(attribute: "avatar" | "pseudo" | "password" | "email" | ""): void {
    this.fillForm();
    this.attributeEditing = attribute;
  }

  cancel(): void {
    this.attributeEditing = "";
  }

  update(attribute: "avatar" | "pseudo" | "password" | "email" | ""): void {
    if (attribute === "avatar") this.updateEmitter.emit(this.formAvatar.value);
    if (attribute === "pseudo") this.updateEmitter.emit(this.formPseudo.value);
    if (attribute === "password") this.updateEmitter.emit(this.formPassword.value);
    if (attribute === "email") this.updateEmitter.emit(this.formEmail.value);
    this.attributeEditing = "";
  }
}
