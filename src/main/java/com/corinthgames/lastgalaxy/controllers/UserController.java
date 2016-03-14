package com.corinthgames.lastgalaxy.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.corinthgames.lastgalaxy.annotations.LoggedInUser;
import com.corinthgames.lastgalaxy.config.SpringConfig;
import com.corinthgames.lastgalaxy.models.User;
import com.corinthgames.lastgalaxy.services.UserService;

@RestController
@Transactional
public class UserController {

	private static final String SECURE_MAPPING = SpringConfig.SECURE_NAMESPACE + "/user";
	private static final String PUBLIC_MAPPING = SpringConfig.PUBLIC_NAMESPACE + "/user";

	@Autowired
	private UserService userService;

	@RequestMapping(value=PUBLIC_MAPPING, method=RequestMethod.POST)
	private User createUser(@RequestBody User user) {
		return userService.createUser(user);
	}

	@RequestMapping(value=SECURE_MAPPING)
	private User getLoggedInUser(@LoggedInUser User user) {
		return user;
	}
}