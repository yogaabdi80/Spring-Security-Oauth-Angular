import { Component, OnInit, ViewChild } from '@angular/core';
import { NgForm, NgModel } from '@angular/forms';
import { DomSanitizer } from '@angular/platform-browser';
import { ActivatedRoute, Router } from '@angular/router';
import * as ClassicEditor from '@ckeditor/ckeditor5-build-classic';
import { Produk } from '../model/produk';
import { ProdukService } from '../service/produk.service';

@Component({
  selector: 'app-add-produk',
  templateUrl: './add-produk.component.html',
  styleUrls: ['./add-produk.component.scss'],
  providers: [ProdukService]
})
export class AddProdukComponent implements OnInit {

  @ViewChild('form') form: NgForm;
  public Editor = ClassicEditor;
  public produk: Produk = new Produk();
  public separator: string = "";
  public fileProduk: FileProduk = new FileProduk();
  public fotoProduk: any;

  constructor(private produkService: ProdukService, public router: Router, public route: ActivatedRoute, private sanitizer: DomSanitizer) { }

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.produkService.setLoadingScreen(true);
      if (params['action'] === 'edit') {
        this.produkService.getProduk(params['id']).subscribe(result => {
          this.produk = result;
          if (result.hargaProduk !== null) this.separator = result.hargaProduk.toLocaleString();
          if (result.fotoProduk1) {
            this.produkService.getImage(result.fotoProduk1).subscribe(data => {
              this.fileProduk.urlfoto1 = this.sanitizer.bypassSecurityTrustUrl(URL.createObjectURL(data.body));
            })
          }
          if (result.fotoProduk2) {
            this.produkService.getImage(result.fotoProduk2).subscribe(data => {
              this.fileProduk.urlfoto2 = this.sanitizer.bypassSecurityTrustUrl(URL.createObjectURL(data.body));
            })
          }
          if (result.fotoProduk3) {
            this.produkService.getImage(result.fotoProduk3).subscribe(data => {
              this.fileProduk.urlfoto3 = this.sanitizer.bypassSecurityTrustUrl(URL.createObjectURL(data.body));
            })
          }
          if (result.fotoProduk4) {
            this.produkService.getImage(result.fotoProduk4).subscribe(data => {
              this.fileProduk.urlfoto4 = this.sanitizer.bypassSecurityTrustUrl(URL.createObjectURL(data.body));
            })
          }
          if (result.fotoProduk5) {
            this.produkService.getImage(result.fotoProduk5).subscribe(data => {
              this.fileProduk.urlfoto5 = this.sanitizer.bypassSecurityTrustUrl(URL.createObjectURL(data.body));
            })
          }
        })
      };
      setTimeout(() => {
        this.produkService.setLoadingScreen(false);
      }, 200);
    });
    // this.validateFile();
  }

  public readerImage(e: any, pointer: any) {
    const fileReader = new FileReader();
    if (e.target.files.length > 0) {
      if (e.target.files[0].type.match(/image.*/)) {
        this.fileProduk[pointer] = e.target.files[0];
        fileReader.readAsDataURL(e.target.files[0]);
        fileReader.onload = () => {
          this.fileProduk['url' + pointer] = fileReader.result;
        };
        fileReader.onerror = (error) => {
          console.log('Error: ', error);
        };
      } else if (!e.target.files[0].type.match(/image.*/)) {
        alert("Harap Masukkan File Gambar!")
      }
    } else if (e.target.files.length === 0) {
      this.fileProduk[pointer] = null;
      this.fileProduk['url' + pointer] = null;
    }
    this.validateFile();
  }

  validateFile() {
    if (this.produk.fotoProduk1 || this.fileProduk.foto1 || this.fileProduk.urlfoto1) {
      this.form.controls['fotoProduk1'].setErrors({ fileError: false });
    } else if (!this.produk.fotoProduk1 && !this.fileProduk.foto1) {
      this.form.controls['fotoProduk1'].setErrors({ fileError: true });
    }
  }

  save() {
    this.produkService.setLoadingScreen(true);
    if (this.form.form.valid) {
      this.produkService.postProduk(this.produk, this.fileProduk.foto1, this.fileProduk.foto2,
        this.fileProduk.foto3, this.fileProduk.foto4, this.fileProduk.foto5).subscribe(data => {
          alert(data.message)
          setTimeout(() => {
            this.produkService.setLoadingScreen(false);
          }, 200);
        },error=>{
          console.log(error);
          setTimeout(() => {
            this.produkService.setLoadingScreen(false);
          }, 200);
        });
    } else {
      alert("Ada Data Yang Belum Diisi!");
      setTimeout(() => {
        this.produkService.setLoadingScreen(false);
      }, 200);
    }
  }

  public clearSeparator(): void {
    if (this.separator) this.separator = this.separator.split(",").join("");
  }

  public setSeparator(): void {
    if (this.separator) {
      this.produk.hargaProduk = parseInt(this.separator);
      this.separator = this.produk.hargaProduk.toLocaleString();
    } else {
      this.produk.hargaProduk = null;
    }
  }


}

export class FileProduk {
  public foto1: File = null;
  public foto2: File = null;
  public foto3: File = null;
  public foto4: File = null;
  public foto5: File = null;
  public urlfoto1: any = null;
  public urlfoto2: any = null;
  public urlfoto3: any = null;
  public urlfoto4: any = null;
  public urlfoto5: any = null;
}
