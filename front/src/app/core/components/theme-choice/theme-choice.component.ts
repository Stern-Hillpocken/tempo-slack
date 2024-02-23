import { Component } from '@angular/core';

@Component({
  selector: 'app-theme-choice',
  templateUrl: './theme-choice.component.html',
  styleUrls: ['./theme-choice.component.scss']
})
export class ThemeChoiceComponent {

  changeTheme(value: "navy" | "retro"): void {
    document.querySelector("body")?.setAttribute("data-theme", value);
    localStorage.setItem("data-theme", value);
  }
}
