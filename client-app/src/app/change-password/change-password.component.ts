import { Component, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { UserService } from '../service/user.service';

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.scss']
})
export class ChangePasswordComponent implements OnInit {

  constructor(public router: Router, private route: ActivatedRoute, private userService: UserService) { }
  @ViewChild('form') form: NgForm;
  public id: String;
  public currentPassword: String;
  public newPassword: String;
  public confirmPassword: String;
  public loading: boolean = false;

  ngOnInit(): void {
    this.id = this.route.snapshot.params['id'];
  }

  save() {
    if (this.form.valid) {
      if (this.id) {
        this.userService.changePassword(this.id.toString(), this.currentPassword.toString(), this.newPassword.toString()).subscribe(data => {
          alert(data.msg);
          if (data.status === 200) this.router.navigate(['informasi-diri', this.id]);
        });
      }
    }
  }

  confirmPasswordValidation() {
    if (this.confirmPassword) {
      if (this.newPassword !== this.confirmPassword) this.form.controls['confirmPassword'].setErrors({ confirmPasswordError: true, required: false });
      else this.form.controls['confirmPassword'].setErrors(null);
    } else this.form.controls['confirmPassword'].setErrors({ required: true });
  }

}
