import { Component, EventEmitter, Output } from '@angular/core';

@Component({
  selector: 'app-navigation',
  templateUrl: './navigation.component.html',
  styleUrls: ['./navigation.component.scss']
})
export class NavigationComponent {

  @Output()
  homeEmitter: EventEmitter<void> = new EventEmitter();

  home(): void {
    this.homeEmitter.emit();
  }
}
