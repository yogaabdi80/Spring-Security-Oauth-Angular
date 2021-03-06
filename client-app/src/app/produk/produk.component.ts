import { DOCUMENT } from '@angular/common';
import { Component, Inject, OnInit, ViewEncapsulation } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
import { ActivatedRoute, Router } from '@angular/router';
import { CookieService } from 'ngx-cookie';
import { Produk } from '../model/produk';
import { AuthService } from '../service/auth.service';
import { ProdukService } from '../service/produk.service';

@Component({
  selector: 'app-produk',
  templateUrl: './produk.component.html',
  styleUrls: ['./produk.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ProdukComponent implements OnInit {

  public produk: Produk = new Produk();

  constructor(@Inject(DOCUMENT) private document: any, private produkService: ProdukService, private router: Router,
    private route: ActivatedRoute, private sanitizer: DomSanitizer, private authService: AuthService, private cookieService: CookieService) { }

  public imgProduk1: any;
  public imgProduk2: any;
  public imgProduk3: any;
  public imgProduk4: any;
  public imgProduk5: any;

  ngOnInit(): void {
    this.getProduk();
  }

  addCart() {
    if (this.authService.checkCredential()) {
      this.produkService.setLoadingScreen(true);
      this.produkService.addCart(this.produk.id.toString(), JSON.parse(this.cookieService.get("user")).cart, this.produk.jumlahItem.toString()).subscribe(data => {
        alert(data.message)
        this.produkService.getJumlahCart(JSON.parse(this.cookieService.get("user")).cart).subscribe(data => {
          this.produkService.setJumlahCart(data);
          setTimeout(() => {
            this.produkService.setLoadingScreen(false);
          }, 200);
        }, error => {
          console.log(error);
          setTimeout(() => {
            this.produkService.setLoadingScreen(false);
          }, 200);
        });
      });
    } else {
      alert("Harap Login Terlebih Dahulu");
    }
  }

  getProduk() {
    this.produkService.setLoadingScreen(true);
    this.produkService.getProduk(this.route.snapshot.params['id']).subscribe(
      result => {
        console.log(result);
        this.produk = result;
        this.produk.jumlahItem = 1;
        if (result.fotoProduk1) {
          this.produkService.getImage(result.fotoProduk1).subscribe(data => {
            this.imgProduk1 = this.sanitizer.bypassSecurityTrustUrl(URL.createObjectURL(data.body));
          })
        }
        if (result.fotoProduk2) {
          this.produkService.getImage(result.fotoProduk2).subscribe(data => {
            this.imgProduk2 = this.sanitizer.bypassSecurityTrustUrl(URL.createObjectURL(data.body));
          })
        }
        if (result.fotoProduk3) {
          this.produkService.getImage(result.fotoProduk3).subscribe(data => {
            this.imgProduk3 = this.sanitizer.bypassSecurityTrustUrl(URL.createObjectURL(data.body));
          })
        }
        if (result.fotoProduk4) {
          this.produkService.getImage(result.fotoProduk4).subscribe(data => {
            this.imgProduk4 = this.sanitizer.bypassSecurityTrustUrl(URL.createObjectURL(data.body));
          })
        }
        if (result.fotoProduk5) {
          this.produkService.getImage(result.fotoProduk5).subscribe(data => {
            this.imgProduk5 = this.sanitizer.bypassSecurityTrustUrl(URL.createObjectURL(data.body));
          })
        }
        setTimeout(() => {
          this.produkService.setLoadingScreen(false);
        }, 200);
      }, error => {
        console.log(error);
        setTimeout(() => {
          this.produkService.setLoadingScreen(false);
        }, 200);
      }
    )
  }

  incPrice() {
    this.produk.jumlahItem += 1;
  }

  decPrice() {
    if (this.produk.jumlahItem > 0) this.produk.jumlahItem -= 1;
  }

}
