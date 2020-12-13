package com.firstproject.resourceserver.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.firstproject.resourceserver.model.dto.ApiResponse;
import com.firstproject.resourceserver.model.dto.CartDTO;
import com.firstproject.resourceserver.model.dto.CartDTOList;
import com.firstproject.resourceserver.model.dto.ProdukDTO;
import com.firstproject.resourceserver.model.dto.ProdukHome;
import com.firstproject.resourceserver.model.dto.ProdukHomeDTO;
import com.firstproject.resourceserver.model.dto.UploadFile;
import com.firstproject.resourceserver.model.entity.Cart;
import com.firstproject.resourceserver.model.entity.CartProduk;
import com.firstproject.resourceserver.model.entity.Produk;
import com.firstproject.resourceserver.repository.CartProdukRepo;
import com.firstproject.resourceserver.repository.CartRepo;
import com.firstproject.resourceserver.repository.KategoriProdukRepository;
import com.firstproject.resourceserver.repository.ProdukRepository;
import com.firstproject.resourceserver.service.StorageService;
import org.apache.commons.io.FilenameUtils;

@RestController
@RequestMapping("/api/produk")
@CrossOrigin(origins = "*")
public class ApiProduk {

	@Autowired
	private ProdukRepository produkRepo;

	@Autowired
	private KategoriProdukRepository kategoriRepo;

	@Autowired
	private CartRepo cartRepo;

	@Autowired
	private CartProdukRepo cartProdukRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private StorageService storage;

	@GetMapping("/getAll")
	private ProdukHomeDTO getAll(Pageable pageable,
			@RequestParam(name = "kategori", required = false, defaultValue = "") String kategori,
			@RequestParam(name = "search", required = false, defaultValue = "") String search) {
		Page<Produk> produkList;
		if (!kategori.isEmpty() && search.isEmpty()) {
			produkList = produkRepo.findByKategoriProduk(kategoriRepo.findById(kategori).get(), pageable);
		} else if (kategori.isEmpty() && !search.isEmpty()) {
			produkList = produkRepo.findByNamaProdukContainingIgnoreCase(search, pageable);
		} else if (!kategori.isEmpty() && !search.isEmpty()) {
			produkList = produkRepo.findByKategoriProdukAndNamaProdukContainingIgnoreCase(
					kategoriRepo.findById(kategori).get(), search, pageable);
		} else {
			produkList = produkRepo.findAll(pageable);
		}
		List<ProdukHome> produkDTOList = new ArrayList<ProdukHome>();
		for (Produk produk : produkList.toList()) {
			ProdukHome produkDTO = new ProdukHome();
			modelMapper.map(produk, produkDTO);
			produkDTOList.add(produkDTO);
		}
		ProdukHomeDTO produkHomeDTO = new ProdukHomeDTO();
		produkHomeDTO.setProduk(produkDTOList);
		produkHomeDTO.setTotalPage(produkList.getTotalPages());
		produkHomeDTO.setLength(produkList.getTotalElements());
		return produkHomeDTO;
	}

	@GetMapping("/getProduk/{id}")
	private ProdukDTO getProduk(@PathVariable String id) {
		Produk produk = produkRepo.findById(id).orElse(new Produk());
		ProdukDTO produkDTO = new ProdukDTO();
		modelMapper.map(produk, produkDTO);
		produkDTO.setKategori(produk.getKategoriProduk().getKategori());
		produkDTO.setKdKategori(produk.getKategoriProduk().getId());
		return produkDTO;
	}

	@Transactional
	@PostMapping("/addProduk")
	private ApiResponse<?> addProduk(@RequestPart(name = "produk") ProdukDTO produkDTO,
			@RequestParam(name = "foto1", required = false) MultipartFile foto1,
			@RequestParam(name = "foto2", required = false) MultipartFile foto2,
			@RequestParam(name = "foto3", required = false) MultipartFile foto3,
			@RequestParam(name = "foto4", required = false) MultipartFile foto4,
			@RequestParam(name = "foto5", required = false) MultipartFile foto5) {
		Produk produk = new Produk();
		modelMapper.map(produkDTO, produk);
		produk.setKategoriProduk(kategoriRepo.findById(produkDTO.getKdKategori()).get());
		produk = produkRepo.save(produk);
		List<UploadFile> uploadFiles = new ArrayList<UploadFile>();
		UploadFile uploadFile = new UploadFile();
		if (foto1 != null) {
			uploadFile = new UploadFile();
			uploadFile.setFile(foto1);
			uploadFile.setFilename(
					"Gambar_" + produk.getId() + "_1" + "." + FilenameUtils.getExtension(foto1.getOriginalFilename()));
			produk.setFotoProduk1(uploadFile.getFilename());
			uploadFiles.add(uploadFile);
		}
		if (foto2 != null) {
			uploadFile = new UploadFile();
			uploadFile.setFile(foto2);
			uploadFile.setFilename(
					"Gambar_" + produk.getId() + "_2" + "." + FilenameUtils.getExtension(foto2.getOriginalFilename()));
			produk.setFotoProduk2(uploadFile.getFilename());
			uploadFiles.add(uploadFile);
		}
		if (foto3 != null) {
			uploadFile = new UploadFile();
			uploadFile.setFile(foto3);
			uploadFile.setFilename(
					"Gambar_" + produk.getId() + "_3" + "." + FilenameUtils.getExtension(foto3.getOriginalFilename()));
			produk.setFotoProduk3(uploadFile.getFilename());
			uploadFiles.add(uploadFile);
		}
		if (foto4 != null) {
			uploadFile = new UploadFile();
			uploadFile.setFile(foto4);
			uploadFile.setFilename(
					"Gambar_" + produk.getId() + "_4" + "." + FilenameUtils.getExtension(foto4.getOriginalFilename()));
			produk.setFotoProduk4(uploadFile.getFilename());
			uploadFiles.add(uploadFile);
		}
		if (foto5 != null) {
			uploadFile = new UploadFile();
			uploadFile.setFile(foto5);
			uploadFile.setFilename(
					"Gambar_" + produk.getId() + "_5" + "." + FilenameUtils.getExtension(foto5.getOriginalFilename()));
			produk.setFotoProduk5(uploadFile.getFilename());
			uploadFiles.add(uploadFile);
		}
		storage.store(uploadFiles, "Produk");
		produkRepo.save(produk);
		return new ApiResponse<>("Berhasil Disimpan", 200,null);
	}

	@GetMapping("/cartNotif")
	private Integer findCartNotif(@RequestParam String idCart) {
		Cart cart = cartRepo.findById(Long.parseLong(idCart)).get();
		return cart.getCartList().size();
	}

	@GetMapping("/getCart")
	private CartDTOList getCart(@RequestParam String idCart) {
		List<CartDTO> cartListDTO = new ArrayList<CartDTO>();
		Cart cart = cartRepo.findById(Long.parseLong(idCart)).get();
		for (CartProduk cartIter : cart.getCartList()) {
			CartDTO cartDTO = new CartDTO();
			cartDTO.setId(cartIter.getId());
			cartDTO.setJumlahItem(cartIter.getJumlahItem());
			Produk produk = produkRepo.findById(cartIter.getIdProduk()).get();
			cartDTO.setHargaProduk(produk.getHargaProduk());
			cartDTO.setFotoProduk1(produk.getFotoProduk1());
			cartDTO.setIdProduk(produk.getId());
			cartDTO.setNamaProduk(produk.getNamaProduk());
			cartListDTO.add(cartDTO);
		}
		CartDTOList cartDTOList = new CartDTOList();
		cartDTOList.setId(cart.getId());
		cartDTOList.setIdUser(cart.getIdUser());
		cartDTOList.setCartDTO(cartListDTO);
		return cartDTOList;
	}

	@Transactional
	@PostMapping("/addCart")
	private ApiResponse<?> addCart(@RequestParam("idProduk") String idProduk, @RequestParam("idCart") String idCart,
			@RequestParam("jumlahItem") String jumlahItem) {
		CartProduk cartProduk = cartProdukRepo.findByIdCartAndIdProduk(Long.parseLong(idCart), idProduk)
				.orElse(new CartProduk());
		cartProduk.setIdProduk(idProduk);
		cartProduk.setJumlahItem(Integer.parseInt(jumlahItem));
		cartProduk.setIdCart(Long.parseLong(idCart));
		cartProdukRepo.save(cartProduk);
		return new ApiResponse<>("Produk Sudah Ditambahkan ke Cart!", 200,null);
	}

	@Transactional
	@PostMapping("/checkCartList")
	private ApiResponse<?> checkCartList(@RequestPart("cartDTOList") List<CartDTO> cartDTOList,
			@RequestParam("idCart") String idCart) {
		for (CartDTO cartDTO : cartDTOList) {
			CartProduk cart = new CartProduk();
			cart.setId(cartDTO.getId());
			cart.setIdCart(Long.parseLong(idCart));
			cart.setIdProduk(cartDTO.getIdProduk());
			cart.setJumlahItem(cartDTO.getJumlahItem());
			cartProdukRepo.save(cart);
		}
		return new ApiResponse<>("Berhasil Disimpan", 200,null);
	}

	@DeleteMapping("/deleteCart")
	private void deleteCart(@RequestParam String id, @RequestParam String idCart) {
		cartProdukRepo.delete(cartProdukRepo.findByIdAndIdCart(Long.parseLong(id),Long.parseLong(idCart)).get());
	}

	@GetMapping("/getGambarProduk/{filename}")
	private void getGambarProduk(@PathVariable String filename, HttpServletResponse response) {
		File file = storage.loadFile(filename, "Produk");
		InputStream inputStream = null;
		try {
			inputStream = new BufferedInputStream(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			FileCopyUtils.copy(inputStream, response.getOutputStream());
			response.flushBuffer();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
