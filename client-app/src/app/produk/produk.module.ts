import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ProdukRoutingModule } from './produk-routing.module';
import { ProdukComponent } from './produk.component';
import { MDBBootstrapModule } from 'angular-bootstrap-md';
import { CKEditorModule } from '@ckeditor/ckeditor5-angular';
import { FormsModule } from '@angular/forms';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { ProdukService } from '../service/produk.service';
import { RouterModule } from '@angular/router';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { AuthService } from '../service/auth.service';
import { CookieModule } from 'ngx-cookie';
import { TokenInterceptor } from '../service/TokenInterceptor';


@NgModule({
  declarations: [ProdukComponent],
  imports: [
    CommonModule,
    ProdukRoutingModule,
    FormsModule,
    MDBBootstrapModule.forRoot(),
    CKEditorModule,
    MatIconModule,
    MatInputModule,
    MatButtonModule,
    RouterModule,
    HttpClientModule,
    CookieModule.forRoot()
  ], providers: [
    ProdukService,
    AuthService, {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptor,
      multi: true
    }
  ]
})
export class ProdukModule { }
