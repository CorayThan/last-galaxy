package com.corinthgames.lastgalaxy.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.corinthgames.lastgalaxy.models.User;

public interface UserRepo extends JpaRepository<User, String> {

}
