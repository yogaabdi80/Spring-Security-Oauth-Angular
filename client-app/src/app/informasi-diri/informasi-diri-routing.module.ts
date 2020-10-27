import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { InformasiDiriComponent } from './informasi-diri.component';

const routes: Routes = [
  {
    path: '',
    component: InformasiDiriComponent,
    data: {
      title: 'Informasi Diri'
    }
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class InformasiDiriRoutingModule { }
