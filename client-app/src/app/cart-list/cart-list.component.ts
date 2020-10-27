import { Component, OnInit } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
import { Router } from '@angular/router';
import { CartDTO } from '../model/produk';
import { ProdukService } from '../service/produk.service';

@Component({
  selector: 'app-cart-list',
  templateUrl: './cart-list.component.html',
  styleUrls: ['./cart-list.component.scss']
})
export class CartListComponent implements OnInit {

  constructor(public router: Router, private produkService: ProdukService, private sanitizer: DomSanitizer) { }

  ngOnInit(): void {
    this.getData();
  }

  public cartList: Array<CartDTO> = new Array<CartDTO>();
  public idUser: number = 1;

  checkout() {
    this.produkService.checkCart(this.cartList, 41).subscribe(data => {
      if (data.status === 200) this.router.navigate(['check-out']);
    })
  }

  incPrice(cart: CartDTO) {
    cart.jumlahItem += 1;
  }

  decPrice(cart: CartDTO) {
    if (cart.jumlahItem > 1) cart.jumlahItem -= 1;
  }

  getData() {
    this.produkService.getCart().subscribe(data => {
      this.cartList = data.cartDTO;
      console.log(data)
      data.cartDTO.forEach(element => {
        if (element.fotoProduk1) {
          this.produkService.getImage(element.fotoProduk1).subscribe(data => {
            element.urlfotoProduk1 = this.sanitizer.bypassSecurityTrustUrl(URL.createObjectURL(data.body));
          });
        }
      });
    })
  }

  delete(id: number) {
    this.produkService.deleteCart(id).subscribe(() => {
      this.getData();
      this.produkService.getJumlahCart().subscribe(data=>{
        this.produkService.setJumlahCart(data);
      });
    });
  }

}
