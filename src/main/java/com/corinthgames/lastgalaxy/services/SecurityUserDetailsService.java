package com.corinthgames.lastgalaxy.services;

import com.corinthgames.lastgalaxy.models.User;
import com.corinthgames.lastgalaxy.models.UserDetailsUser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by CorayThan on 10/31/2014.
 */
@Service(value="securityUserService")
public class SecurityUserDetailsService implements UserDetailsService {

	@Autowired
	private UserService userService;
	@Autowired
	private ObjectMapper jacksonMapper;

	/**
	 * Our usernames are actually e-mails
	 */
	@Transactional
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userService.getUser(email);
		if (user == null) {
			throw new UsernameNotFoundException("There was no user with the e-mail: " + email);
		}
		String userString;
		try {
			userString = jacksonMapper.writeValueAsString(user);
		} catch (JsonProcessingException e) {
			throw new RuntimeException("Failed to json process the user: " + user);
		}
		UserDetailsUser details = new UserDetailsUser(user, userString);
		return details;
	}

}