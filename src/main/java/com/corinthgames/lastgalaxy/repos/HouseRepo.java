package com.corinthgames.lastgalaxy.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.corinthgames.lastgalaxy.models.House;

public interface HouseRepo extends JpaRepository<House, String> {

}
