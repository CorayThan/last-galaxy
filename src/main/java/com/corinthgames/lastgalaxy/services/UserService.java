package com.corinthgames.lastgalaxy.services;

import com.corinthgames.lastgalaxy.enums.Role;
import com.corinthgames.lastgalaxy.models.User;
import com.corinthgames.lastgalaxy.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	@Autowired
	private HouseService houseService;

	@Autowired
	private UserRepo userRepo;

	public User getUser(String email) {
		return userRepo.findOne(email);
	}

	public User createUser(User user) {
		user.setRole(Role.USER);
		user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
		userRepo.save(user);
		houseService.setupHouse(user);
		System.out.println("house: " + user.getHouse());
		User userToReturn = userRepo.save(user);
		System.out.println("house after user save: " + userToReturn.getHouse());
		return userToReturn;
	}

	public User updateUser(User user) {
		return userRepo.save(user);
	}

}