import { Component, EventEmitter, OnInit, Output } from "@angular/core";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";

@Component({
  selector: "app-add-role",
  templateUrl: "./add-role.component.html",
  styleUrls: ["./add-role.component.scss"],
})
export class AddRoleComponent implements OnInit {
  @Output()
  addRoleEmitter: EventEmitter<string> = new EventEmitter();

  isPopupDisplay: boolean = false;
  formAddRole!: FormGroup;

  constructor(private fb: FormBuilder) {}

  ngOnInit(): void {
    this.formAddRole = this.fb.group({
      name: ["", [Validators.required, Validators.minLength(1)]],
    });
  }

  openPopup(): void {
    this.isPopupDisplay = true;
  }

  closePopup(): void {
    this.isPopupDisplay = false;
  }

  addRole(): void {
    this.addRoleEmitter.emit(this.formAddRole.value.name);
    this.closePopup();
  }
}
