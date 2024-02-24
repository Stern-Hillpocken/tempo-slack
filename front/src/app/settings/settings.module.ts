import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SettingsPageComponent } from './pages/settings-page/settings-page.component';
import { SettingsFeaturesComponent } from './components/settings-features/settings-features.component';
import { UserInformationsComponent } from './components/settings-features/user-informations/user-informations.component';
import { NavigationComponent } from './components/settings-features/navigation/navigation.component';
import { LogoutComponent } from './components/settings-features/logout/logout.component';
import { SettingsRoutingModule } from './settings-routing.module';
import { ReactiveFormsModule } from '@angular/forms';



@NgModule({
  declarations: [
    SettingsPageComponent,
    SettingsFeaturesComponent,
    UserInformationsComponent,
    NavigationComponent,
    LogoutComponent
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    SettingsRoutingModule
  ],
  exports: [
    SettingsPageComponent,
    SettingsFeaturesComponent,
    UserInformationsComponent,
    NavigationComponent,
    LogoutComponent
  ]
})
export class SettingsModule { }