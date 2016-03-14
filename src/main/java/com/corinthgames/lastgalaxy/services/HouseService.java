package com.corinthgames.lastgalaxy.services;

import com.corinthgames.lastgalaxy.models.House;
import com.corinthgames.lastgalaxy.models.User;
import com.corinthgames.lastgalaxy.repos.HouseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HouseService {

	@Autowired
	private HouseRepo houseRepo;
	@Autowired
	private HouseCreationService houseCreationService;

	/**
	 * Sets up a brand new house. Should only be called from in
	 * {@link com.corinthgames.lastgalaxy.services.UserService#createUser(com.corinthgames.lastgalaxy.models.User)}
	 *
	 * Also saves the house.
	 *
	 * @param user
	 * @return
	 */
	public House setupHouse(User user) {
		if (user.getHouse() != null) {
			throw new RuntimeException("You already have a house, and cannot create another one!");
		}
		House house = new House();
		user.setHouse(house);
		house.setUser(user);
		houseRepo.save(house);
		houseCreationService.createInitialCharacters(house);
		return house;
	}
}