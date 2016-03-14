package com.corinthgames.lastgalaxy.repos;

import com.corinthgames.lastgalaxy.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepo extends JpaRepository<Person, Long> {
}
