import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SettingsPageComponent } from './pages/settings-page/settings-page.component';
import { SettingsFeaturesComponent } from './components/settings-features/settings-features.component';
import { UserInformationsComponent } from './components/settings-features/user-informations/user-informations.component';
import { NavigationComponent } from './components/settings-features/navigation/navigation.component';
import { LogoutComponent } from './components/settings-features/logout/logout.component';
import { SettingsRoutingModule } from './settings-routing.module';
import { ReactiveFormsModule } from '@angular/forms';
import { DeleteUserComponent } from './components/settings-features/delete-user/delete-user.component';
import { UtilsModule } from '../utils/utils.module';



@NgModule({
  declarations: [
    SettingsPageComponent,
    SettingsFeaturesComponent,
    UserInformationsComponent,
    NavigationComponent,
    LogoutComponent,
    DeleteUserComponent
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    SettingsRoutingModule,
    UtilsModule
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
