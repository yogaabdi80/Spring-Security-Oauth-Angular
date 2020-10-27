import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { CartListComponent } from './cart-list.component';

const routes: Routes = [
  {
    path: '',
    component: CartListComponent,
    data: {
      title: 'Cart List'
    }
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CartListRoutingModule { }
