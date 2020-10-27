import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-verifikasi-akun',
  templateUrl: './verifikasi-akun.component.html',
  styleUrls: ['./verifikasi-akun.component.scss']
})
export class VerifikasiAkunComponent implements OnInit {

  constructor(public router:Router) { }

  ngOnInit(): void {
  }

}
