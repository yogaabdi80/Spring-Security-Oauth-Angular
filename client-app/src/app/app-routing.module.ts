import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AddProdukComponent } from './add-produk/add-produk.component';
import { DefaultLayoutComponent } from './default-layout/default-layout.component';
import { ForgotPasswordComponent } from './forgot-password/forgot-password.component';
import { LoginComponent } from './login/login.component';
import { NotFoundComponent } from './not-found/not-found.component';
import { RegisterComponent } from './register/register.component';
import { AuthGuardService } from './service/auth-guard.service';
import { NotifPageComponent } from './notif-page/notif-page.component';
import { ChangePasswordComponent } from './change-password/change-password.component';

const routes: Routes = [
  { path: '', redirectTo: 'home', pathMatch: 'full' },
  {
    path: '', component: DefaultLayoutComponent, children: [
      {
        path: 'home',
        loadChildren: () => import("../app/home/home.module").then(m => m.HomeModule)
      },{
        path: 'produk/:id',
        loadChildren: () => import("../app/produk/produk.module").then(m => m.ProdukModule)
      },{
        path: 'check-out',
        loadChildren: () => import("../app/check-out/check-out.module").then(m => m.CheckOutModule), canActivate : [AuthGuardService]
      },{
        path: 'cart-list',
        loadChildren: () => import("../app/cart-list/cart-list.module").then(m => m.CartListModule), canActivate : [AuthGuardService]
      },{
        path: 'informasi-diri/:id',
        loadChildren: () => import("../app/informasi-diri/informasi-diri.module").then(m => m.InformasiDiriModule), canActivate : [AuthGuardService]
      }
    ]
  },
  {path:'login',component: LoginComponent},
  {path:'register',component: RegisterComponent },
  {path:'add-produk',component: AddProdukComponent , canActivate : [AuthGuardService]},
  {path:'forgot-password',component: ForgotPasswordComponent },
  {path:'change-password/:id',component: ChangePasswordComponent , canActivate : [AuthGuardService]},
  {path:'notif-page',component: NotifPageComponent },
  {path:'**',component: NotFoundComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
