import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LandingPageComponent } from './components/landing-page/landing-page.component';
import { HeroComponent } from './components/landing-page/hero/hero.component';
import { LoginComponent } from './components/landing-page/login/login.component';
import { SigninComponent } from './components/landing-page/signin/signin.component';
import { LandingComponent } from './pages/landing/landing.component';
import { FormChoiceComponent } from './components/landing-page/form-choice/form-choice.component';
import { ReactiveFormsModule } from '@angular/forms';
import { PopupFeedbackComponent } from './components/popup-feedback/popup-feedback.component';



@NgModule({
  declarations: [
    LandingPageComponent,
    HeroComponent,
    LoginComponent,
    SigninComponent,
    LandingComponent,
    FormChoiceComponent,
    PopupFeedbackComponent
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule
  ], exports: [PopupFeedbackComponent]
})
export class CoreModule { }
