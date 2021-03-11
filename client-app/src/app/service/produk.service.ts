import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { CookieService } from 'ngx-cookie';
import { Observable, Subject } from 'rxjs';
import { CartDTO, CartDTOList, Produk, ProdukHomeDTO } from '../model/produk';
import { ApiResponse } from '../model/response';

@Injectable({
  providedIn: 'root'
})
export class ProdukService {

  constructor(private http: HttpClient) { }

  private baseUrl = "/resource/server/api/produk/";

  public jumlahCart = new Subject<number>();
  public jumlahCart$ = this.jumlahCart.asObservable();
  public loading = new Subject<boolean>();
  public loading$ = this.loading.asObservable();

  setLoadingScreen(bool:boolean){
    // console.log(this.loading);
    this.loading.next(bool);
  }

  setJumlahCart(number:number) {
    this.jumlahCart.next(number);
  }

  getJumlahCart(idCart:number): Observable<number> {
    let params={
      "idCart":idCart.toString()
    }
    return this.http.get<number>(this.baseUrl + "cartNotif",{ params: params });
  }

  getImage(filename: String): Observable<HttpResponse<Blob>> {
    return this.http.get(this.baseUrl + "getGambarProduk/" + filename, {
      observe: 'response',
      responseType: 'blob'
    })
  }

  getAllProduk(size: string, page: string, sort: string[], kategori: string, search: string): Observable<ProdukHomeDTO> {
    let params = {
      "size": size,
      "page": page,
      "sort": sort
    }
    if (kategori) params["kategori"] = kategori;
    if (search) params["search"] = search;
    return this.http.get<ProdukHomeDTO>(this.baseUrl + "getAll", { params: params });
  }

  getProduk(id: String): Observable<Produk> {
    return this.http.get<Produk>(this.baseUrl + "getProduk/" + id);
  }

  postProduk(produk: Produk, foto1: File, foto2: File, foto3: File, foto4: File, foto5: File): Observable<ApiResponse> {
    let formdata: FormData = new FormData();
    formdata.append("foto1", foto1);
    formdata.append("foto2", foto2);
    formdata.append("foto3", foto3);
    formdata.append("foto4", foto4);
    formdata.append("foto5", foto5);
    formdata.append("produk", new Blob([JSON.stringify(produk)], {
      type: "application/json"
    }));
    return this.http.post<ApiResponse>(this.baseUrl + "addProduk", formdata);
  }

  addCart(idProduk:string,idCart:number,jumlahItem:string): Observable<ApiResponse> {
    let formdata: FormData = new FormData();
    formdata.append("idProduk", idProduk);
    formdata.append("idCart", idCart.toString());
    formdata.append("jumlahItem", jumlahItem);
    return this.http.post<ApiResponse>(this.baseUrl + "addCart", formdata);
  }

  checkCart(cartDTOList: Array<CartDTO>,idCart:number): Observable<ApiResponse> {
    let formdata: FormData = new FormData();
    formdata.append("idCart",idCart.toString());
    formdata.append("cartDTOList", new Blob([JSON.stringify(cartDTOList)], {
      type: "application/json"
    }));
    return this.http.post<ApiResponse>(this.baseUrl + "checkCartList", formdata);
  }

  getCart(idCart:number): Observable<CartDTOList> {
    let params={
      "idCart": idCart.toString()
    }
    return this.http.get<CartDTOList>(this.baseUrl + "getCart", { params: params });
  }

  deleteCart(id:number,idCart:number){
    let params={
      "id":id.toString(),
      "idCart": idCart.toString()
    }
    return this.http.delete(this.baseUrl + "deleteCart", { params: params });
  }

}
