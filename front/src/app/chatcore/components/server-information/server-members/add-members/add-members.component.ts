import { Component, EventEmitter, OnInit, Output } from "@angular/core";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";

@Component({
  selector: "app-add-members",
  templateUrl: "./add-members.component.html",
  styleUrls: ["./add-members.component.scss"],
})
export class AddMembersComponent implements OnInit {
  @Output()
  addMemberEmitter: EventEmitter<string> = new EventEmitter();

  isPopupMemberDisplay: boolean = false;
  formAddMember!: FormGroup;

  constructor(private fb: FormBuilder) {}

  ngOnInit(): void {
    this.formAddMember = this.fb.group({
      name: ["", [Validators.required, Validators.minLength(1)]],
    });
  }

  openPopup(): void {
    this.isPopupMemberDisplay = true;
  }

  closePopup(): void {
    this.isPopupMemberDisplay = false;
  }

  addMember(): void {
    this.addMemberEmitter.emit(this.formAddMember.value.name);
    this.closePopup();
  }
}
