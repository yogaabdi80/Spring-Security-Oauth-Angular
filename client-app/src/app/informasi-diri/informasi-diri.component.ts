import { Component, OnInit } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
import { ActivatedRoute, Router } from '@angular/router';
import { UserInfo } from '../model/user';
import { ProdukService } from '../service/produk.service';
import { UserService } from '../service/user.service';

@Component({
  selector: 'app-informasi-diri',
  templateUrl: './informasi-diri.component.html',
  styleUrls: ['./informasi-diri.component.scss']
})
export class InformasiDiriComponent implements OnInit {

  public userDto: UserInfo = new UserInfo();
  public fotoProfilUrl: any;
  public fotoProfil: File;
  public id:any;

  constructor(private userService: UserService, private route: ActivatedRoute, private sanitizer: DomSanitizer, public router: Router,
    public produkService: ProdukService) { }

  ngOnInit(): void {
    this.getData();
  }

  public getData() {
    this.produkService.setLoadingScreen(true);
    this.id = this.route.snapshot.params['id'];
    this.userService.getUserInfo(this.id).subscribe(data => {
      this.userDto = data;
      if (data.fotoProfil) {
        this.userService.getImage(data.fotoProfil).subscribe(image => {
          this.fotoProfilUrl = this.sanitizer.bypassSecurityTrustUrl(URL.createObjectURL(image.body));
        }
        )
      }
      setTimeout(() => {
        this.produkService.setLoadingScreen(false);
      }, 200);
    })
  }

  public save() {
    this.produkService.setLoadingScreen(true);
    this.userService.saveUser(this.userDto, this.fotoProfil).subscribe(data => {
      alert(data.message);
      setTimeout(() => {
        this.produkService.setLoadingScreen(false);
      }, 200);
    }, error => {
      console.log(error);
      setTimeout(() => {
        this.produkService.setLoadingScreen(false);
      }, 200);
    }
    )
  }

  public readerImage(e: any) {
    const fileReader = new FileReader();
    if (e.target.files.length > 0) {
      if (e.target.files[0].type.match(/image.*/)) {
        this.fotoProfil = e.target.files[0];
        fileReader.readAsDataURL(e.target.files[0]);
        fileReader.onload = () => {
          this.fotoProfilUrl = fileReader.result;
        };
        fileReader.onerror = (error) => {
          console.log('Error: ', error);
        };
      } else if (!e.target.files[0].type.match(/image.*/)) {
        alert("Harap Masukkan File Gambar!")
      }
    } else if (e.target.files.length === 0) {
      this.fotoProfilUrl = null;
      this.fotoProfil = null;
    }
  }

}
