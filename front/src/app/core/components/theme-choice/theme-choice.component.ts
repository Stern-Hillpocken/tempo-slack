import { Component } from '@angular/core';

@Component({
  selector: 'app-theme-choice',
  templateUrl: './theme-choice.component.html',
  styleUrls: ['./theme-choice.component.scss']
})
export class ThemeChoiceComponent {

  theme: "navy" | "retro" = "navy";

  changeTheme(value: "navy" | "retro"): void {
    this.theme = value;
    document.querySelector("body")?.setAttribute("data-theme", value);
    localStorage.setItem("data-theme", value);
  }
}
