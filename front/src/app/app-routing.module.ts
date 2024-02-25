import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { LandingComponent } from "./core/pages/landing/landing.component";
import { UserGuard } from "./core/user.guard";

const routes: Routes = [
  { path: "", component: LandingComponent },
  { path: "home", loadChildren: () => import("./chatcore/chatcore.module").then((m) => m.ChatcoreModule), canActivate: [UserGuard] },
  { path: "settings", loadChildren: () => import("./settings/settings.module").then((m) => m.SettingsModule), canActivate: [UserGuard] },
  { path: "**", loadChildren: () => import("./errors/errors.module").then((m) => m.ErrorsModule) },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [],
})
export class AppRoutingModule {}
