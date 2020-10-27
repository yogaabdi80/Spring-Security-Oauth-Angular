import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { InformasiDiriRoutingModule } from './informasi-diri-routing.module';
import { InformasiDiriComponent } from './informasi-diri.component';
import { FormsModule } from '@angular/forms';


@NgModule({
  declarations: [InformasiDiriComponent],
  imports: [
    CommonModule,
    InformasiDiriRoutingModule,
    FormsModule
  ]
})
export class InformasiDiriModule { }
