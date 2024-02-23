import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ErrorComponent } from './error/error.component';
import { ErrorsRoutingModule } from './errors-routing.module';



@NgModule({
  declarations: [
    ErrorComponent
  ],
  imports: [
    CommonModule,
    ErrorsRoutingModule
  ],
  exports: [ErrorComponent]
})
export class ErrorsModule { }
