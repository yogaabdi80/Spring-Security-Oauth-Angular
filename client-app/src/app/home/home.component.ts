import { Component, OnInit } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
import { ActivatedRoute, Router } from '@angular/router';
import { ProdukHome } from '../model/produk';
import { ProdukService } from '../service/produk.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  constructor(public router: Router, private sanitizer: DomSanitizer, private produkService: ProdukService, public route: ActivatedRoute) { }
  public sortByFilter = {
    nama: false,
    harga: false,
    stok: false,
    sortType: null
  };

  public totalPage: number = 5;
  public lengthData: number = null;
  public page: number = 1;
  public sort: string[] = ["id"];
  public params: string[] = [];
  public pageSlide: number = 1;
  public pageNumber1: number = 1;
  public pageNumber2: number = 2;
  public pageNumber3: number = 3;
  public size: number = 15;
  public produkList: Array<ProdukHome> = new Array<ProdukHome>();

  sortFilterFunction(e: any) {
    if (this.sortByFilter.sortType === null) this.sortByFilter.sortType = 'asc';
    else if (!this.sortByFilter.nama && !this.sortByFilter.harga && !this.sortByFilter.stok) {
      this.sortByFilter.sortType = null;
    }
    if (e) {
      if (e.target.checked) {
        this.params.push(e.target.value + this.sortByFilter.sortType)
      } else {
        for (let index = 0; index < this.params.length; index++) {
          if (this.params[index].indexOf(e.target.value) !== -1) {
            this.params.splice(index, 1);
          }
        }
      }
    } else {
      for (let index = 0; index < this.params.length; index++) {
        this.params[index] = this.params[index].split(',')[0] + "," + this.sortByFilter.sortType;
      }
    }
    this.sort = this.params;
    if (this.params.length === 0) this.sort = ["id"];
    this.page = 1;
    this.pageSlide = 1;
    this.pageNumber1 = 1;
    this.pageNumber2 = 2;
    this.pageNumber3 = 3;
    this.getData();
  }

  previousTab() {
    if (this.pageSlide === 1 && this.page !== 1) {
      this.pageNumber1--;
      this.pageNumber2--;
      this.pageNumber3--;
    }
    if (this.page > 1) this.page--;
    if (this.pageSlide > 1) this.pageSlide--;
    this.getData();
  }

  clickPage(page: number, pageSlide: number) {
    this.page = page;
    this.pageSlide = pageSlide;
    this.getData();
  }

  nextTab() {
    if (this.pageSlide === 3 && this.page !== this.totalPage) {
      this.pageNumber1++;
      this.pageNumber2++;
      this.pageNumber3++;
    }
    if (this.page < this.totalPage) this.page++;
    if (this.pageSlide < 3) this.pageSlide++;
    this.getData();
  }

  ngOnInit(): void {
    this.getData();
  }

  pageFunction(index: string) {
    this.page = parseInt(index);
  }

  getData() {
    this.produkService.setLoadingScreen(true);
    this.route.queryParams.subscribe(params => {
      if (params['kategori'] && !params['search']) {
        this.produkService.getAllProduk(this.size.toString(), (this.page - 1).toString(), this.sort, params['kategori'], null).subscribe(result => {
          this.produkList = result.produk;
          this.totalPage = result.totalPage;
          this.lengthData = result.length;
          result.produk.forEach(element => {
            if (element.fotoProduk1) {
              this.produkService.getImage(element.fotoProduk1).subscribe(data => {
                element.image = this.sanitizer.bypassSecurityTrustUrl(URL.createObjectURL(data.body));
              });
            }
          });
          setTimeout(() => {
            this.produkService.setLoadingScreen(false);
          }, 200);
        });
      } else if (params['search'] && !params['kategori']) {
        this.produkService.getAllProduk(this.size.toString(), (this.page - 1).toString(), this.sort, null, params['search']).subscribe(result => {
          this.produkList = result.produk;
          this.totalPage = result.totalPage;
          this.lengthData = result.length;
          result.produk.forEach(element => {
            if (element.fotoProduk1) {
              this.produkService.getImage(element.fotoProduk1).subscribe(data => {
                element.image = this.sanitizer.bypassSecurityTrustUrl(URL.createObjectURL(data.body));
              });
            }
          });
          setTimeout(() => {
            this.produkService.setLoadingScreen(false);
          }, 200);
        });
      } else if (params['search'] && params['kategori']) {
        this.produkService.getAllProduk(this.size.toString(), (this.page - 1).toString(), this.sort, params['kategori'], params['search']).subscribe(result => {
          this.produkList = result.produk;
          this.totalPage = result.totalPage;
          this.lengthData = result.length;
          result.produk.forEach(element => {
            if (element.fotoProduk1) {
              this.produkService.getImage(element.fotoProduk1).subscribe(data => {
                element.image = this.sanitizer.bypassSecurityTrustUrl(URL.createObjectURL(data.body));
              });
            }
          });
          setTimeout(() => {
            this.produkService.setLoadingScreen(false);
          }, 200);
        });
      } else {
        this.produkService.getAllProduk(this.size.toString(), (this.page - 1).toString(), this.sort, null, null).subscribe(result => {
          this.produkList = result.produk;
          this.totalPage = result.totalPage;
          this.lengthData = result.length;
          result.produk.forEach(element => {
            if (element.fotoProduk1) {
              this.produkService.getImage(element.fotoProduk1).subscribe(data => {
                element.image = this.sanitizer.bypassSecurityTrustUrl(URL.createObjectURL(data.body));
              });
            }
          });
          setTimeout(() => {
            this.produkService.setLoadingScreen(false);
          }, 200);
        });
      };
    });
  };

}
