import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { InformasiDiriRoutingModule } from './informasi-diri-routing.module';
import { InformasiDiriComponent } from './informasi-diri.component';
import { FormsModule } from '@angular/forms';
import { UserService } from '../service/user.service';
import { ProdukService } from '../service/produk.service';


@NgModule({
  declarations: [InformasiDiriComponent],
  providers:[UserService,ProdukService],
  imports: [
    CommonModule,
    InformasiDiriRoutingModule,
    FormsModule
  ]
})
export class InformasiDiriModule { }
