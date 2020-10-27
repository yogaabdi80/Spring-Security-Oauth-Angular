import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ProdukService } from '../service/produk.service';

@Component({
  selector: 'app-default-layout',
  templateUrl: './default-layout.component.html',
  styleUrls: ['./default-layout.component.scss'],
  providers: [ProdukService]
})
export class DefaultLayoutComponent implements OnInit {

  constructor(public router: Router, public http: HttpClient,public produkService:ProdukService) {
    produkService.jumlahCart$.subscribe(data=>{
      this.notif=data;
    });
   }

  public search: string = null;
  public notif: number=null;

  ngOnInit(): void {
    this.produkService.getJumlahCart().subscribe(data=>{
      this.notif=data;
    });
    this.search = localStorage.getItem("search");
  }


  kategoriFunction(kategori: string) {
    this.router.navigate(['home'], { queryParams: { kategori }, queryParamsHandling: 'merge' });
  }

  setSearch() {
    localStorage.setItem("search", this.search);
  }

  searchFunction() {
    if (this.search) this.router.navigate(['home'], { queryParams: { search: this.search }, queryParamsHandling: 'merge' });
  }

}
