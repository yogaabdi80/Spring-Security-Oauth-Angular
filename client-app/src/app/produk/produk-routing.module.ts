import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ProdukComponent } from './produk.component';

const routes: Routes = [
  {
    path: '',
    component: ProdukComponent,
    data: {
      title: 'Produk'
    }
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ProdukRoutingModule { }
