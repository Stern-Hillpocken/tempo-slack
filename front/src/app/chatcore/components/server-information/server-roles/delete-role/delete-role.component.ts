import { Component, EventEmitter, OnInit, Output } from "@angular/core";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";

@Component({
  selector: "app-delete-role",
  templateUrl: "./delete-role.component.html",
  styleUrls: ["./delete-role.component.scss"],
})
export class DeleteRoleComponent implements OnInit {
  @Output()
  deleteRoleEmitter: EventEmitter<string> = new EventEmitter();

  isPopupDisplay: boolean = false;
  formDeleteRole!: FormGroup;

  constructor(private fb: FormBuilder) {}

  ngOnInit(): void {
    this.formDeleteRole = this.fb.group({
      name: ["", [Validators.required, Validators.minLength(1)]],
    });
  }

  openPopup(): void {
    this.isPopupDisplay = true;
  }

  closePopup(): void {
    this.isPopupDisplay = false;
  }

  deleteRole(): void {
    this.deleteRoleEmitter.emit(this.formDeleteRole.value.name);
    this.closePopup();
  }
}
