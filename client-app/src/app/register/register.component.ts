import { Component, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { UserRegister } from '../model/user';
import { UserService } from '../service/user.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss'],
  providers: [UserService]
})
export class RegisterComponent implements OnInit {

  @ViewChild('form') form: NgForm;
  public user: UserRegister = new UserRegister();
  public notifConfirm: boolean = false;
  public loading: boolean = false;

  constructor(private userService: UserService) { }


  ngOnInit(): void {
  }

  confirm() {
    this.loading = true;
    if (this.form.form.valid) this.userService.register(this.user).subscribe(data => {
      if (data.status === 200) this.notifConfirm = true;
      else alert(data.msg);
      this.loading = false;
    },error=>{
      console.log(error);
      this.loading = false;
    });
    else {
      alert("Ada Data Yang Belum Diisi!");
      this.loading = false;
    }
  }

  confirmPasswordValidation() {
    if (this.user.confirmPassword) {
      if (this.user.password !== this.user.confirmPassword) this.form.controls['confirmPassword'].setErrors({ confirmPasswordError: true, required: false });
      else this.form.controls['confirmPassword'].setErrors(null);
    } else this.form.controls['confirmPassword'].setErrors({ required: true });

  }

}
