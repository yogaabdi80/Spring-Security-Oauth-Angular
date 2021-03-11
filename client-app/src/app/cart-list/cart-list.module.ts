import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CartListRoutingModule } from './cart-list-routing.module';
import { CartListComponent } from './cart-list.component';
import { FormsModule } from '@angular/forms';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
import { ProdukService } from '../service/produk.service';
import { CookieModule } from 'ngx-cookie';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { TokenInterceptor } from '../service/TokenInterceptor';


@NgModule({
  declarations: [CartListComponent],
  imports: [
    CommonModule,
    FormsModule,
    CartListRoutingModule,
    MatIconModule,
    MatInputModule,
    CookieModule.forRoot()
  ], providers:[ProdukService, {
    provide: HTTP_INTERCEPTORS,
    useClass: TokenInterceptor,
    multi: true
  }]
})
export class CartListModule { }
