import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { CookieService } from 'ngx-cookie';
import { Observable } from 'rxjs';
import { Response } from '../login/login.component';
import { Token } from '../model/response';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient, private router: Router,private cookieService: CookieService) { }

  sudahLogin(): boolean {
    if (!this.cookieService.hasKey("token") && !this.cookieService.hasKey("user")) {
        alert("Harap Login Terlebih Dahulu")
        this.router.navigate(['']);
    }
    return this.cookieService.hasKey("token") && this.cookieService.hasKey("user");
}

  checkCredential(): boolean {
    return this.cookieService.hasKey("token") && this.cookieService.hasKey("user");
  }

  getToken(code): Observable<Response<Token>> {
    let url: string = "/auth/server/user/getToken";
    let formdata: FormData = new FormData();
    formdata.append("code", code)
    return this.http.post<Response<Token>>(url, formdata);
  }

  login(username:string,password:string):Observable<Response<any>>{
    let formdata: FormData = new FormData();
    formdata.append("username", username);
    formdata.append("password", password);
    return this.http.post<Response<any>>("/auth/server/rest-login", formdata);
  }


}
