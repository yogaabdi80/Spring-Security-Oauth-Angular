import { Component, OnInit } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
import { CookieService } from 'ngx-cookie';
import { CartDTO } from '../model/produk';
import { ProdukService } from '../service/produk.service';

@Component({
  selector: 'app-check-out',
  templateUrl: './check-out.component.html',
  styleUrls: ['./check-out.component.scss']
})
export class CheckOutComponent implements OnInit {

  constructor(private produkService:ProdukService,private sanitizer: DomSanitizer, private cookieService: CookieService) { }

  public sum:number=0;
  public idCart: number;

  ngOnInit(): void {
    this.idCart = JSON.parse(this.cookieService.get("user")).cart;
    this.getData();
  }

  public cartList:Array<CartDTO> = new Array<CartDTO>();

  getData(){
    this.produkService.setLoadingScreen(true);
    this.produkService.getCart(this.idCart).subscribe(data=>{
      this.cartList = data.cartDTO;
      data.cartDTO.forEach(element => {
        this.sum+=(element.hargaProduk*element.jumlahItem);
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
}
