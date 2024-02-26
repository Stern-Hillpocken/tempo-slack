import { Component, EventEmitter, OnInit, Output } from "@angular/core";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";

@Component({
  selector: "app-delete-member",
  templateUrl: "./delete-member.component.html",
  styleUrls: ["./delete-member.component.scss"],
})
export class DeleteMemberComponent implements OnInit {
  @Output()
  deleteMemberEmitter: EventEmitter<string> = new EventEmitter();

  isPopupMemberDisplay: boolean = false;
  formDeleteMember!: FormGroup;

  constructor(private fb: FormBuilder) {}

  ngOnInit(): void {
    this.formDeleteMember = this.fb.group({
      name: ["", [Validators.required, Validators.minLength(1)]],
    });
  }

  openPopup(): void {
    this.isPopupMemberDisplay = true;
  }

  closePopup(): void {
    this.isPopupMemberDisplay = false;
  }

  deleteMember(): void {
    this.deleteMemberEmitter.emit(this.formDeleteMember.value.name);
    this.closePopup();
  }
}
