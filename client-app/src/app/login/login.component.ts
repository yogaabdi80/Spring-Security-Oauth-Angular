import { HttpClient } from '@angular/common/http';
import { Component, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../service/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  constructor(private authService : AuthService,public router:Router,private http:HttpClient) { }
  public username: string = "";
  public password: string = "";
  @ViewChild('form') form: NgForm;
  private readonly client_id = "clientauthcode";
  private readonly response_type = "code";
  private readonly redirect_uri = "http://localhost:4200";
  private readonly auth_uri = "http://localhost:8081"
  public readonly auth_uri_google = `${this.auth_uri}/auth/server/oauth2/authorization/google`
  public readonly auth_uri_facebook = `${this.auth_uri}/auth/server/oauth2/authorization/facebook`

  ngOnInit(): void {
  }

  login() {
    this.authService.login(this.username,this.password).subscribe(data => {
      if (data.status == 200) {
        window.location.href=`${this.auth_uri}/auth/server/oauth/authorize?client_id=${this.client_id}&response_type=${this.response_type}&redirect_uri=${this.redirect_uri}`
      } else{
        alert(data.msg)
      }
    });
  }

  google(){
    this.http.get("auth/server/oauth2/authorization/google").subscribe();
  }

  facebook(){
    this.http.get("auth/server/oauth2/authorization/facebook").subscribe();
  }

}

export class Response<T> {
  public status: number;
  public msg: String;
  public objek: T;
}