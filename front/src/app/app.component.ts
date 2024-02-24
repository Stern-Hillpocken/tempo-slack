import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'Octotchat';

  ngOnInit(): void {
    if (localStorage.getItem("data-theme")) document.querySelector("body")?.setAttribute("data-theme", localStorage.getItem("data-theme") as string);
    else document.querySelector("body")?.setAttribute("data-theme", "navy");
  }
}
