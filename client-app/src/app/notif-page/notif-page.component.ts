import { Component, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { UserService } from '../service/user.service';

@Component({
  selector: 'app-notif-page',
  templateUrl: './notif-page.component.html',
  styleUrls: ['./notif-page.component.scss']
})
export class NotifPageComponent implements OnInit {

  constructor(public router: Router, private route: ActivatedRoute, private userService: UserService) { }
  @ViewChild('form') form: NgForm;
  public id: String;
  public message: String;
  public action: String;
  public verifikasiBool: boolean = false;
  public resetPasswordBool: boolean = false;
  public messageFailedBool: boolean = false;
  public currentPassword: String = null;
  public newPassword: String = null;
  public passwordConfirm: String = null;
  public loading: boolean = false;

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.id = params['id'];
      if (params['action'] === 'verifikasi') {
        if (this.id) {
          this.userService.verifEmail(this.id.toString()).subscribe(data => {
            this.message = data.msg;
            if (data.status === 200) {
              this.verifikasiBool = true;
            } else {
              this.messageFailedBool = true;
            }
          })
        }
      }
      else if (params['action'] === 'reset_password') {
        if (this.id) {
          this.userService.checkValidityResetPassword(this.id.toString()).subscribe(data => {
            console.log(data)
            if (data.status === 200) this.resetPasswordBool = true;
            else {
              this.message = data.msg;
              this.messageFailedBool = true;
            }
          })
        }
      }
    })
  }

  save() {
    if (this.form.valid) {
      this.userService.resetPassword(this.id.toString(), this.newPassword.toString()).subscribe(data => {
        alert(data.msg);
        if (data.status === 200){
          this.router.navigate(['login']);
        }
      })
    }
  }

  confirmPasswordValidation() {
    if (this.passwordConfirm) {
      if (this.newPassword !== this.passwordConfirm) this.form.controls['passwordConfirm'].setErrors({ confirmPasswordError: true, required: false });
      else this.form.controls['passwordConfirm'].setErrors(null);
    } else this.form.controls['passwordConfirm'].setErrors({ required: true });
  }

}
