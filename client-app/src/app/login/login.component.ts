import { HttpClient, HttpHeaders } from '@angular/common/http';
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

  constructor(private authService: AuthService, public router: Router, private http: HttpClient) { }
  public username: string = "";
  public password: string = "";
  @ViewChild('form') form: NgForm;
  private readonly client_id = "clientauthcode";
  private readonly response_type = "code";
  private readonly redirect_uri = "http://client-app";
  public readonly auth_uri = "http://localhost:8081/"
  public readonly auth_uri_google = "auth/server/oauth2/authorization/google"
  public readonly auth_uri_facebook = "auth/server/oauth2/authorization/facebook"
  public readonly auth_uri_client = "auth/server/oauth2/authorization/clientauthcode"

  ngOnInit(): void {
  }

  login() {
    this.authService.login(this.username, this.password).subscribe(data => {
      if (data.status == 200) {
        // console.log(data)
        // add_header 'Cache-Control' 'public';
        //     add_header 'Access-Control-Allow-Origin' '*';
        //     add_header 'Access-Control-Allow-Credentials' 'true';
        // let headers: HttpHeaders = new HttpHeaders({ 'Access-Control-Allow-Origin': '*', 'Access-Control-Allow-Credentials': 'true', 'Cache-Control': 'public' });
        // this.http.get(this.auth_uri_client, { headers }).subscribe();
        window.location.href=this.auth_uri+this.auth_uri_client
      } else {
        alert(data.msg)
      }
    });
  }

  google() {
    this.http.get(this.auth_uri_google).subscribe();
  }

  facebook() {
    this.http.get(this.auth_uri_facebook).subscribe();
  }

}

export class Response<T> {
  public status: number;
  public msg: String;
  public objek: T;
}