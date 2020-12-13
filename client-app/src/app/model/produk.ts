export class Produk {
    public id:String=null;
    public kategori: String=null;
    public kdKategori: String=null;
    public namaProduk: String=null;
    public hargaProduk: number=null;
    public stokProduk: number=null;
    public deskripsiProduk:String=null;
	public fotoProduk1:String=null;
	public fotoProduk2:String=null;
	public fotoProduk3:String=null;
	public fotoProduk4:String=null;
    public fotoProduk5:String=null;
    public jumlahItem:number=null;
}

export class ProdukHome{
    public id:String=null;
    public namaProduk: String=null;
    public hargaProduk: number=null;
    public stokProduk: number=null;
    public fotoProduk1:String=null;
    public image:any=null;
}

export class ProdukHomeDTO{
    public produk:Array<ProdukHome>;
    public totalPage:number;
    public length:number;
}

export class CartDTO{
    public id:number;
    public jumlahItem:number;
    public idUser:number;
    public idProduk:String;
    public namaProduk:String;
    public hargaProduk:number;
    public fotoProduk1:String;
    public urlfotoProduk1:any;
}

export class CartDTOList{
    public id:number;
    public idUser:number;
    public cartDTO:Array<CartDTO>;
}