import { Component, EventEmitter, Input, Output } from '@angular/core';
import { UserPublic } from 'src/app/core/models/user-public.model';

@Component({
  selector: 'app-self-settings',
  templateUrl: './self-settings.component.html',
  styleUrls: ['./self-settings.component.scss']
})
export class SelfSettingsComponent {

  @Input()
  user!: UserPublic;

  @Output()
  settingsEmitter: EventEmitter<void> = new EventEmitter();

  settings(): void {
    this.settingsEmitter.emit();
  }

}
