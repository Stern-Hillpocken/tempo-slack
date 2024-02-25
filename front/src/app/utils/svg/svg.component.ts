import { HttpClient } from '@angular/common/http';
import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-svg',
  templateUrl: './svg.component.html',
  styleUrls: ['./svg.component.scss']
})
export class SvgComponent {

  @Input()
  type!: string;

  dPath!: string;

  constructor(private http: HttpClient){
    // https://pictogrammers.com/library/mdi/
    this.http.get("assets/json/svg-paths.json").subscribe((obj:any) => {
      for (let i = 0; i < obj.length; i++) {
        if (obj[i].name === this.type) {
          this.dPath = obj[i].path;
          break;
        }
      }
    });
  }

}
