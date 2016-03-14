package com.corinthgames.lastgalaxy.controllers;

import com.corinthgames.lastgalaxy.config.SpringConfig;
import com.corinthgames.lastgalaxy.models.House;
import com.corinthgames.lastgalaxy.services.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value=SpringConfig.SECURE_NAMESPACE + "/house")
@Transactional
public class HouseController {

	@Autowired
	private HouseService houseService;

	@RequestMapping(method= RequestMethod.GET)
	private House getHouse() {
		System.out.println("getting a new house");
		House house = new House();
		house.setName("house name");
		return house;
	}
}
