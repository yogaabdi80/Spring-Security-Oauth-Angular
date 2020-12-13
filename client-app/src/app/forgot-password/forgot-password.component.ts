import { Component, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from '../service/user.service';

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.scss']
})
export class ForgotPasswordComponent implements OnInit {

  public email:string;
  public loading:boolean = false;
  @ViewChild('form') form: NgForm;

  constructor(private userService:UserService, public router: Router) { }

  ngOnInit(): void {
  }

  submit(){
    this.loading=true;
    if(this.email){
      this.userService.forgotPassword(this.email).subscribe(data=>{
        this.loading=false;
        if(data.status===200) {
          alert(data.msg);
          this.router.navigate(['login']);
        } else{
          alert(data.msg);
        }
      },error=>{
        console.log(error);
        this.loading=false;
      })
    } else{
      alert("Harap masukkan email!");
      this.loading=false;
    }

  }

}
