import { Component, OnInit } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
import { Router } from '@angular/router';
import { CookieService } from 'ngx-cookie';
import { CartDTO } from '../model/produk';
import { ProdukService } from '../service/produk.service';

@Component({
  selector: 'app-cart-list',
  templateUrl: './cart-list.component.html',
  styleUrls: ['./cart-list.component.scss']
})
export class CartListComponent implements OnInit {

  constructor(public router: Router, private produkService: ProdukService, private sanitizer: DomSanitizer, private cookieService: CookieService) { }

  ngOnInit(): void {
    this.idCart = JSON.parse(this.cookieService.get("user")).cart;
    this.getData();
  }

  public cartList: Array<CartDTO> = new Array<CartDTO>();
  public idCart: number;

  checkout() {
    this.produkService.checkCart(this.cartList, this.idCart).subscribe(data => {
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
    this.produkService.setLoadingScreen(true);
    this.produkService.getCart(this.idCart).subscribe(data => {
      this.cartList = data.cartDTO;
      console.log(data)
      data.cartDTO.forEach(element => {
        if (element.fotoProduk1) {
          this.produkService.getImage(element.fotoProduk1).subscribe(data => {
            element.urlfotoProduk1 = this.sanitizer.bypassSecurityTrustUrl(URL.createObjectURL(data.body));
          });
        }
      });
      setTimeout(() => {
        this.produkService.setLoadingScreen(false);
      }, 200);
    })
  }

  delete(id: number) {
    this.produkService.setLoadingScreen(true);
    this.produkService.deleteCart(id, this.idCart).subscribe(() => {
      this.getData();
      this.produkService.getJumlahCart(this.idCart).subscribe(data => {
        this.produkService.setJumlahCart(data);
      });
    });
  }

}
