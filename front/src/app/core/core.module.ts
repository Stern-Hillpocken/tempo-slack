import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LandingPageComponent } from './components/landing-page/landing-page.component';
import { HeroComponent } from './components/landing-page/hero/hero.component';
import { LoginComponent } from './components/landing-page/login/login.component';
import { SigninComponent } from './components/landing-page/signin/signin.component';
import { LandingComponent } from './pages/landing/landing.component';



@NgModule({
  declarations: [
    LandingPageComponent,
    HeroComponent,
    LoginComponent,
    SigninComponent,
    LandingComponent
  ],
  imports: [
    CommonModule
  ]
})
export class CoreModule { }
