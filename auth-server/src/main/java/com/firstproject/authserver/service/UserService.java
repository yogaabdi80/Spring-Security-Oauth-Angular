package com.firstproject.authserver.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.firstproject.authserver.model.dto.Response;
import com.firstproject.authserver.model.dto.UserDto;
import com.firstproject.authserver.model.entity.Authorities;
import com.firstproject.authserver.model.entity.User;
import com.firstproject.authserver.repository.AuthoritiesRepository;
import com.firstproject.authserver.repository.UserRepository;

@Service
@Transactional
public class EmailSenderService {
	
	@Autowired
	private UserRepository repo;

	@Autowired
	private AuthoritiesRepository authoritiesRepo;
	
	@Autowired
	private JavaMailSender mailSender;
	
	private SimpleMailMessage message;
	
	public Response<Object> register(UserDto userDto) {
		User user = new User(userDto);
		if(repo.findByEmail(user.getEmail())!=null) {
			return new Response<Object>("Email Sudah Terdaftar!", 99, null);
		} else if(repo.findByUsername(user.getUsername())!=null) {
			return new Response<Object>("Username Sudah Terdaftar!", 99, null);
		}
		try {
			user = repo.save(user);			
		} catch (Exception e) {
			e.printStackTrace();
			return new Response<Object>("Email Sudah Terdaftar!", 99, null);
		}
		List<Authorities> authorities = new ArrayList<Authorities>();
		authorities.add(authoritiesRepo.findById("r002").get());
		user.setAuthorities(authorities);
		user = repo.save(user);
		message = new SimpleMailMessage();
		message.setSubject("Account Verification");
		message.setText("Account activation link: http://localhost:8081/auth/server/user/verify/email?id="+user.getId());
		message.setTo(user.getEmail());
		message.setFrom("Verification<noreply@gmail.com>");
		mailSender.send(message);
		return new Response<Object>("Registrasi Berhasil!", 200, null);
	}
	
	public Response<Object> verifyEmail(Long id) {
		try {
			repo.setEnabled(id, true);	
			return new Response<Object>("Registrasi Berhasil!", 200, null);
		} catch (Exception e) {
			e.printStackTrace();
			return new Response<Object>("Email Sudah Terdaftar!", 99, null);
		}
	}
	
	public Response<Object> forgotPassword(String email) {
		User user = repo.findByEmail(email);
		if(user==null) {
			return new Response<Object>("Email Tidak Terdaftar!", 99, null);
		}
		else {
			message = new SimpleMailMessage();
			message.setSubject("Reset Password");
			message.setText("Reset Password link: http://localhost:8081/auth/server/user/change-password?id="+user.getId());
			message.setTo(user.getEmail());
			message.setFrom("Reset Password<noreply@gmail.com>");
			mailSender.send(message);
			return new Response<Object>("Pesan Telah Terkirim ke Email!", 200, null);
		}
	}
	
	public Response<Object> resetPassword(Long id, String password) {
		User user = repo.findById(id).get();
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		user.setPassword(encoder.encode(password));
		repo.save(user);
		return new Response<Object>("Password Sudah Dirubah!", 200, null);
	}
}
