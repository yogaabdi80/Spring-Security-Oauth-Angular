import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Response } from '../login/login.component';
import { ApiResponse } from '../model/response';
import { UserInfo, UserRegister } from '../model/user';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) { }

  private readonly baseUrl = "/auth/server/user/"
  private readonly baseUrlInfo = "/resource/server/api/user/"

  public register(user: UserRegister): Observable<Response<any>> {
    return this.http.post<Response<any>>(this.baseUrl + "register", user);
  }

  public verifEmail(id: string): Observable<Response<any>> {
    let formdata: FormData = new FormData();
    formdata.append("id", id);
    return this.http.post<Response<any>>(this.baseUrl + "verify-email", formdata);
  }

  public getUserInfo(idUser: string): Observable<UserInfo> {
    return this.http.get<UserInfo>(this.baseUrlInfo + "info/" + idUser);
  }

  public saveUser(user: UserInfo, foto: File): Observable<ApiResponse> {
    let formdata: FormData = new FormData();
    formdata.append("foto", foto);
    formdata.append("userDto", new Blob([JSON.stringify(user)], {
      type: "application/json"
    }));
    return this.http.post<ApiResponse>(this.baseUrlInfo + "saveUser", formdata);
  }

  public getProfil(id: String): Observable<HttpResponse<Blob>> {
    return this.http.get(this.baseUrlInfo + "getProfil/" + id, {
      observe: 'response',
      responseType: 'blob'
    })
  }

  public getImage(filename: String): Observable<HttpResponse<Blob>> {
    return this.http.get(this.baseUrlInfo + "getGambarProfil/" + filename, {
      observe: 'response',
      responseType: 'blob'
    })
  }

  public forgotPassword(email: string): Observable<Response<any>> {
    let formdata: FormData = new FormData();
    formdata.append("email", email);
    return this.http.post<Response<any>>(this.baseUrl + "forgot-password", formdata);
  }

  public changePassword(id: string, password: string, newPassword: string): Observable<Response<any>> {
    let formdata: FormData = new FormData();
    formdata.append("id", id);
    formdata.append("password", password);
    formdata.append("newPassword", newPassword);
    return this.http.post<Response<any>>(this.baseUrl + "change-password", formdata);
  }
  public resetPassword(id: string, password: string): Observable<Response<any>> {
    let formdata: FormData = new FormData();
    formdata.append("id", id);
    formdata.append("password", password);
    return this.http.post<Response<any>>(this.baseUrl + "reset-password", formdata);
  }
  public checkValidityResetPassword(id: string): Observable<Response<any>> {
    let params = {
      "id": id
    }
    return this.http.get<Response<any>>(this.baseUrl + "check-validity-reset-password", { params });
  }
}
