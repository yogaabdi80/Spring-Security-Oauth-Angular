import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NotFoundComponent } from './not-found/not-found.component';
import { DefaultLayoutComponent } from './default-layout/default-layout.component';
import { MDBBootstrapModule } from 'angular-bootstrap-md';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HashLocationStrategy, LocationStrategy } from '@angular/common';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { ForgotPasswordComponent } from './forgot-password/forgot-password.component';
import { VerifikasiAkunComponent } from './verifikasi-akun/verifikasi-akun.component';
import { AddProdukComponent } from './add-produk/add-produk.component';
import { ResetPasswordComponent } from './reset-password/reset-password.component';
import { CKEditorModule } from '@ckeditor/ckeditor5-angular';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { MatBadgeModule } from '@angular/material/badge';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';

const ANGULAR_MATERIAL = [
  MatBadgeModule,
  MatIconModule,
  MatButtonModule
]


@NgModule({
  declarations: [
    AppComponent,
    NotFoundComponent,
    DefaultLayoutComponent,
    LoginComponent,
    RegisterComponent,
    ForgotPasswordComponent,
    VerifikasiAkunComponent,
    AddProdukComponent,
    ResetPasswordComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    MDBBootstrapModule.forRoot(),
    BrowserAnimationsModule,
    CKEditorModule,
    FormsModule,
    HttpClientModule,
    ANGULAR_MATERIAL
  ],
  providers: [{
    provide: LocationStrategy,
    useClass: HashLocationStrategy
  }],
  bootstrap: [AppComponent]
})
export class AppModule { }
