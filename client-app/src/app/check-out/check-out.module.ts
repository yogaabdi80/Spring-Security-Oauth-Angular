import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CheckOutRoutingModule } from './check-out-routing.module';
import { CheckOutComponent } from './check-out.component';
import { ProdukService } from '../service/produk.service';
import { CookieModule } from 'ngx-cookie';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { TokenInterceptor } from '../service/TokenInterceptor';


@NgModule({
  declarations: [CheckOutComponent],
  imports: [
    CommonModule,
    CheckOutRoutingModule,
    CookieModule.forRoot()
  ], providers: [ProdukService, {
    provide: HTTP_INTERCEPTORS,
    useClass: TokenInterceptor,
    multi: true
  }]
})
export class CheckOutModule { }
