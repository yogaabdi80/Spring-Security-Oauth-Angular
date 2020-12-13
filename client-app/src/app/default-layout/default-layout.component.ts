import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from '../service/auth.service';
import { ProdukService } from '../service/produk.service';
import jwt_decode from "jwt-decode";
import { Response } from '../login/login.component';
import { CookieService } from 'ngx-cookie';
import { UserService } from '../service/user.service';
import { DomSanitizer } from '@angular/platform-browser';

@Component({
  selector: 'app-default-layout',
  templateUrl: './default-layout.component.html',
  styleUrls: ['./default-layout.component.scss'],
  providers: [ProdukService,AuthService,UserService]
})
export class DefaultLayoutComponent implements OnInit {

  constructor(public router: Router, public http: HttpClient,public produkService:ProdukService, private activedRoute : ActivatedRoute,
    public authService : AuthService, private cookieService: CookieService, private userService : UserService, private sanitizer: DomSanitizer) {
    this.activedRoute.queryParams.subscribe(params=>{
      this.code = params['code'];
    })
    produkService.jumlahCart$.subscribe(data=>{
      this.notif=data;
    });
    produkService.loading$.subscribe(data=>{
      this.loading=data;
    })
   }

  public search: string = null;
  public notif: number=null;
  public code:string = null;
  public userInfo:any;
  public fotoProfil:any;
  public loading:boolean=true;

  ngOnInit(): void {
    if(this.authService.checkCredential()){
      this.userInfo = JSON.parse(this.cookieService.get("user"));
      this.produkService.getJumlahCart(this.userInfo.cart).subscribe(data=>{
        this.notif=data;
      });
      this.userService.getProfil(this.userInfo.idUser).subscribe(image=>{
        this.fotoProfil = this.sanitizer.bypassSecurityTrustUrl(URL.createObjectURL(image.body));
      })
      this.search = sessionStorage.getItem("search");
    } else{
      if(this.code){
        this.authService.getToken(this.code).subscribe(data=>{
          if(data.status===200) {
            this.userInfo = jwt_decode(data.objek.access_token);
            let date : Date = new Date();
            date.setDate(date.getDate()+1);
            this.cookieService.put("token",data.objek.access_token,{expires: date});
            this.cookieService.put("user",JSON.stringify(this.userInfo),{expires: date});
            this.produkService.getJumlahCart(this.userInfo.cart).subscribe(data=>{
              this.notif=data;
            });
            this.userService.getProfil(this.userInfo.idUser).subscribe(image=>{
              this.fotoProfil = this.sanitizer.bypassSecurityTrustUrl(URL.createObjectURL(image.body));
            })
          }
        });
      }
    }
  }


  logout(){
    this.http.delete<Response<any>>("/auth/server/user/revoke-token/"+this.cookieService.get("token")).subscribe(data=>{
      alert(data.msg);
      this.cookieService.removeAll();
      if(data.status===200) this.router.navigate(['home']);
    })
  }

  profil(){
    this.router.navigate(['informasi-diri',this.userInfo.idUser]);
  }

  kategoriFunction(kategori: string) {
    this.router.navigate(['home'], { queryParams: { kategori }, queryParamsHandling: 'merge' });
  }

  setSearch() {
    sessionStorage.setItem("search", this.search);
  }

  searchFunction() {
    if (this.search) this.router.navigate(['home'], { queryParams: { search: this.search }, queryParamsHandling: 'merge' });
  }

}
