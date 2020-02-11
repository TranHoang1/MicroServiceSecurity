package com.example.demo.model.hardcodeUser;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		final List<UserDetailsApp> lists = Arrays.asList(
				new UserDetailsApp(1, "hoang", bCryptPasswordEncoder.encode("1234"), "ADMIN"),
				new UserDetailsApp(2, "Duc", bCryptPasswordEncoder.encode("456"), "USER"));
		for (UserDetailsApp userDetailsApp : lists) {
			if (username.equals(userDetailsApp.getUserName())) {
				List<GrantedAuthority> authorities = AuthorityUtils
						.commaSeparatedStringToAuthorityList("ROLE_" + userDetailsApp.getRole());
				return new User(userDetailsApp.getUserName(), userDetailsApp.getPassWord(), authorities);
			}
		}
		throw new UsernameNotFoundException("Username: " + username + " not found");
	}

	private static class UserDetailsApp {
		private Integer id;
		private String userName;

		public UserDetailsApp(Integer id, String userName, String passWord, String role) {
			super();
			this.id = id;
			this.userName = userName;
			this.passWord = passWord;
			this.role = role;
		}

		private String passWord;
		private String role;

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public String getUserName() {
			return userName;
		}

		public String getPassWord() {
			return passWord;
		}

		public String getRole() {
			return role;
		}

	}

}
