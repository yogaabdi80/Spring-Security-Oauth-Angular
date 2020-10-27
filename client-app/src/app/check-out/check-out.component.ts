import { Component, OnInit } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
import { CartDTO } from '../model/produk';
import { ProdukService } from '../service/produk.service';

@Component({
  selector: 'app-check-out',
  templateUrl: './check-out.component.html',
  styleUrls: ['./check-out.component.scss']
})
export class CheckOutComponent implements OnInit {

  constructor(private produkService:ProdukService,private sanitizer: DomSanitizer) { }

  public sum:number=0;

  ngOnInit(): void {
    this.getData();
  }

  public cartList:Array<CartDTO> = new Array<CartDTO>();

  getData(){
    this.produkService.getCart().subscribe(data=>{
      this.cartList = data.cartDTO;
      data.cartDTO.forEach(element => {
        this.sum+=(element.hargaProduk*element.jumlahItem);
        if (element.fotoProduk1) {
          this.produkService.getImage(element.fotoProduk1).subscribe(data => {
            element.urlfotoProduk1 = this.sanitizer.bypassSecurityTrustUrl(URL.createObjectURL(data.body));
          });
        }
      });
    })
  }
}
