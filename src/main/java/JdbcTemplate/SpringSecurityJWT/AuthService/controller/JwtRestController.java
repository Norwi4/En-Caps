package JdbcTemplate.SpringSecurityJWT.AuthService.controller;


import JdbcTemplate.SpringSecurityJWT.AuthService.exception.DisabledUserException;
import JdbcTemplate.SpringSecurityJWT.AuthService.exception.InvalidUserCredentialsException;
import JdbcTemplate.SpringSecurityJWT.AuthService.service.UserAuthService;
import JdbcTemplate.SpringSecurityJWT.AuthService.util.JwtUtil;
import JdbcTemplate.SpringSecurityJWT.AuthService.vo.JwtRequest;
import JdbcTemplate.SpringSecurityJWT.AuthService.vo.JwtResponse;
import JdbcTemplate.SpringSecurityJWT.AuthService.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class JwtRestController {

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private UserAuthService userAuthService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@PostMapping("/signin")
	public ResponseEntity<JwtResponse> generateJwtToken(@RequestBody JwtRequest jwtRequest) {
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(), jwtRequest.getUserpwd()));
		} catch (DisabledException e) {
			throw new DisabledUserException("User Inactive");
		} catch (BadCredentialsException e) {
			throw new InvalidUserCredentialsException("Invalid Credentials");
		}
		UserDetails userDetails = userAuthService.loadUserByUsername(jwtRequest.getUsername());
		String username = userDetails.getUsername();
		String userpwd = userDetails.getPassword();
		Set<String> roles = userDetails.getAuthorities().stream().map(r -> r.getAuthority())
				.collect(Collectors.toSet());
		UserVo user = new UserVo();
		user.setUsername(username);
		user.setUserpwd(userpwd);
		user.setRoles(roles);
		String token = jwtUtil.generateToken(user);
		return new ResponseEntity<JwtResponse>(new JwtResponse(token), HttpStatus.OK);
	}

	/**
	 * Создание пользоователей и присваивание им прав доступа
	 * Может только админ (пока что)
	 * @param userVo
	 * @return
	 */
	//@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/signup")
	public ResponseEntity<String> signup(@RequestBody UserVo userVo) {

		//UserVo u = userAuthService.getUserByUsername(userVo.getUsername());
		Boolean u = userAuthService.existUser(userVo.getUsername());

		if (!u) {
			userAuthService.saveUser(userVo);
			return new ResponseEntity<String>("User successfully registered", HttpStatus.OK);

		} else {
			return new ResponseEntity<String>("User already exists", HttpStatus.CONFLICT);
		}
	}

}
