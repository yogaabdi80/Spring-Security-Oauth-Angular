package com.firstproject.authserver.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.firstproject.authserver.model.dto.Response;
import com.firstproject.authserver.model.dto.TokenResp;
import com.firstproject.authserver.model.dto.UserDto;
import com.firstproject.authserver.model.entity.Authorities;
import com.firstproject.authserver.model.entity.Cart;
import com.firstproject.authserver.model.entity.User;
import com.firstproject.authserver.model.entity.UserDetail;
import com.firstproject.authserver.model.entity.UserNotif;
import com.firstproject.authserver.proxy.AuthProxy;
import com.firstproject.authserver.repository.AuthoritiesRepository;
import com.firstproject.authserver.repository.CartRepo;
import com.firstproject.authserver.repository.UserDetailRepo;
import com.firstproject.authserver.repository.UserRepository;
import com.firstproject.authserver.repository.UserNotifRepository;

@Service
@Transactional
public class UserService {

	@Autowired
	private AuthProxy proxy;

	@Resource(name = "tokenServices")
	private ConsumerTokenServices tokenServices;

	@Resource(name = "tokenStore")
	TokenStore tokenStore;

	private static final String basicAuthCode = "Basic Y2xpZW50YXV0aGNvZGU6YWJjZA==";
	private static final String grantType = "authorization_code";
	private static final String redirectUrl = "http://localhost";

	@Autowired
	private UserRepository repo;

	@Autowired
	private CartRepo cartRepo;

	@Autowired
	private AuthoritiesRepository authoritiesRepo;

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private UserNotifRepository notifRepo;
	
	@Autowired
	private UserDetailRepo detailRepo;

	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	private static final String idRoleUser = "r002";
	private SimpleMailMessage message;
	private static final String messageVerif = "Account activation link: " + redirectUrl
			+ "/#/notif-page?action=verifikasi&id=";
	private static final String messageResetPass = "Reset Password link: " + redirectUrl
			+ "/#/notif-page?action=reset_password&id=";
	private static final String messageEmailAdd = "\r\n\r\nExpired in 10 minutes.";
	private static final String verifNotifUser = "register";
	private static final String forgotPasswordNotifUser = "forgot password";
	private static final int successStatus = 200;
	private static final int warningStatus = 99;
	private static final int errorStatus = -1;

	public Response<?> revoke(String token) {
		if (!token.isEmpty()) {
			tokenServices.revokeToken(token);
		} else {
			return new Response<Object>("Logout Gagal!", 99, null);
		}
		return new Response<Object>("Logout Berhasil!", 200, null);
	}

	public Response<TokenResp> getToken(String code) {
		TokenResp authenticationResponse = null;
		try {
			authenticationResponse = proxy.getToken(basicAuthCode, grantType, code, redirectUrl);
		} catch (Exception e) {
			e.printStackTrace();
			return new Response<TokenResp>("Token Gagal Diterima!", 99, authenticationResponse);
		}
		return new Response<TokenResp>("Token Berhasil Diterima!", 200, authenticationResponse);
	}

	public List<String> getClientTokens() {
		List<String> tokenValues = new ArrayList<String>();
		Collection<OAuth2AccessToken> tokens = tokenStore.findTokensByClientId("clientauthcode");
		if (tokens != null) {
			for (OAuth2AccessToken token : tokens) {
				tokenValues.add(token.getValue());
			}
		}
		return tokenValues;
	}

	public Response<Object> register(UserDto userDto) {
		User user = new User(userDto);
		try {
			if (repo.findByEmail(user.getEmail()) != null) {
				return new Response<Object>("Email Sudah Terdaftar!", warningStatus, null);
			} else if (repo.findByUsername(user.getUsername()) != null) {
				return new Response<Object>("Username Sudah Terdaftar!", warningStatus, null);
			}
			user = repo.save(user);
		} catch (Exception e) {
			e.printStackTrace();
			return new Response<Object>("Data Gagal Disimpan!", errorStatus, null);
		}
		List<Authorities> authorities = new ArrayList<Authorities>();
		authorities.add(authoritiesRepo.findById(idRoleUser).get());
		user.setAuthorities(authorities);
		user = repo.save(user);
		UserNotif userNotif = notifRepo.findByIdUserAndAction(user.getId(), verifNotifUser);
		if (userNotif == null) {
			userNotif = new UserNotif();
			userNotif.setIdUser(user.getId());
			userNotif.setUsername(user.getUsername());
			userNotif.setEmail(user.getEmail());
			userNotif.setAction(verifNotifUser);
		}
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MINUTE, 10);
		userNotif.setExpiredDate(new Date(calendar.getTimeInMillis()));
		notifRepo.save(userNotif);
		message = new SimpleMailMessage();
		message.setSubject("Account Verification");
		message.setText(messageVerif + user.getId() + messageEmailAdd);
		message.setTo(user.getEmail());
		message.setFrom("Verification<noreply@gmail.com>");
		mailSender.send(message);
		return new Response<Object>("Registrasi Berhasil!", successStatus, null);
	}

	public Response<Object> verifyEmail(Long id) {
		try {
			UserNotif userNotif = notifRepo.findByIdUserAndAction(id, verifNotifUser);
			if (userNotif != null) {
				User user = repo.findById(id).get();
				if (userNotif.getExpiredDate().after(new Date(Calendar.getInstance().getTimeInMillis()))) {
					repo.setEnabled(id, true);
					Cart cart = new Cart();
					cart.setUserCart(user);
					cartRepo.save(cart);
					UserDetail detail = new UserDetail();
					detail.setUserDetail(user);
					detailRepo.save(detail);
				} else {
					notifRepo.delete(userNotif);
					repo.delete(user);
					return new Response<Object>("Waktu sudah habis, harap registrasi ulang!", warningStatus, null);
				}
			} else {
				return new Response<Object>("User tidak ditemukan!", warningStatus, null);
			}
			return new Response<Object>("Verifikasi berhasil!", successStatus, null);
		} catch (Exception e) {
			e.printStackTrace();
			return new Response<Object>("User tidak ditemukan!", errorStatus, null);
		}
	}

	public Response<Object> forgotPassword(String email) {
		try {
			User user = repo.findByEmail(email);
			if (user == null) {
				return new Response<Object>("Email Tidak Terdaftar!", warningStatus, null);
			} else {
				UserNotif userNotif = notifRepo.findByIdUserAndAction(user.getId(), forgotPasswordNotifUser);
				if (userNotif == null) {
					userNotif = new UserNotif();
					userNotif.setIdUser(user.getId());
					userNotif.setUsername(user.getUsername());
					userNotif.setEmail(user.getEmail());
					userNotif.setAction(forgotPasswordNotifUser);
				}
				Calendar calendar = Calendar.getInstance();
				calendar.add(Calendar.MINUTE, 10);
				userNotif.setExpiredDate(new Date(calendar.getTimeInMillis()));
				notifRepo.save(userNotif);
				message = new SimpleMailMessage();
				message.setSubject("Reset Password");
				message.setText(messageResetPass + user.getId() + messageEmailAdd);
				message.setTo(user.getEmail());
				message.setFrom("Reset Password<noreply@gmail.com>");
				mailSender.send(message);
				return new Response<Object>("Pesan Telah Terkirim ke Email!", successStatus, null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new Response<Object>("Gagal Diproses!", errorStatus, null);
		}

	}

	public Response<Object> checkValidityResetPassword(Long id) {
		try {
			UserNotif userNotif = notifRepo.findByIdUserAndAction(id, forgotPasswordNotifUser);
			if (userNotif != null) {
				if (!userNotif.getExpiredDate().before(new Date(Calendar.getInstance().getTimeInMillis()))) {
					return new Response<Object>("", successStatus, null);
				} else {
					notifRepo.delete(userNotif);
					return new Response<Object>("Waktu sudah habis, harap kirim ulang!", warningStatus, null);
				}
			} else {
				return new Response<Object>("User Tidak Ditemukan!", warningStatus, null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new Response<Object>("Password Gagal Dirubah!", errorStatus, null);
		}
	}

	public Response<Object> resetPassword(Long id, String password) {
		try {
			repo.setPassword(id, encoder.encode(password));
			UserNotif userNotif = notifRepo.findByIdUserAndAction(id, forgotPasswordNotifUser);
			notifRepo.delete(userNotif);
			return new Response<Object>("Password sudah dirubah, silahkan login!", successStatus, null);
		} catch (Exception e) {
			e.printStackTrace();
			return new Response<Object>("Password Gagal Dirubah!", errorStatus, null);
		}
	}

	public Response<Object> changePassword(Long id, String password, String newPassword) {
		try {
			User user = repo.findById(id).get();
			if (user == null) {
				return new Response<Object>("User Tidak Terdaftar!", warningStatus, null);
			}
			if (encoder.matches(password, user.getPassword())) {
				repo.setPassword(id, encoder.encode(newPassword));
				return new Response<Object>("Password Sudah Dirubah!", successStatus, null);
			} else {
				return new Response<Object>("Password Tidak Sesuai!", warningStatus, null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new Response<Object>("Password Gagal Dirubah!", errorStatus, null);
		}
	}
}
