import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CheckOutRoutingModule } from './check-out-routing.module';
import { CheckOutComponent } from './check-out.component';
import { ProdukService } from '../service/produk.service';


@NgModule({
  declarations: [CheckOutComponent],
  imports: [
    CommonModule,
    CheckOutRoutingModule
  ], providers: [ProdukService]
})
export class CheckOutModule { }
