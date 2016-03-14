package com.corinthgames.lastgalaxy.services;

import com.corinthgames.lastgalaxy.enums.Gender;
import com.corinthgames.lastgalaxy.enums.Stat;
import com.corinthgames.lastgalaxy.models.House;
import com.corinthgames.lastgalaxy.models.Person;
import com.corinthgames.lastgalaxy.repos.PersonRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.EnumMap;
import java.util.Map;
import java.util.Random;

@Service
@Transactional
public class PersonService {

	@Autowired
	private PersonRepo personRepo;
	@Autowired
	private RandomService randomService;

	/**
	 * Sets their deathday, everything else should already be set up
	 *
	 * @param character
	 * @return
	 */
	public Person createPerson(Person character) {
		character.setDeathday(deathday(character.getBirthday()));
		personRepo.save(character);
		return character;
	}

	public Person createPerson(House house, Gender gender, LocalDate birthday, int qualityModifier) {
		Map<Stat, Integer> stats = new EnumMap<Stat, Integer>(Stat.class);
		stats.put(Stat.LEADERSHIP, randomService.generateCharacterStat(qualityModifier));
		stats.put(Stat.INTELLIGENCE, randomService.generateCharacterStat(qualityModifier));
		stats.put(Stat.WISDOM, randomService.generateCharacterStat(qualityModifier));
		stats.put(Stat.CHARISMA, randomService.generateCharacterStat(qualityModifier));
		stats.put(Stat.ATHLETICISM, randomService.generateCharacterStat(qualityModifier));
		Person character = new Person(house, house.getName(), gender, birthday, deathday(birthday), stats);
		personRepo.save(character);
		return character;
	}

	private LocalDate deathday(LocalDate birthday) {
		//add some exceptions for times when people are more likely to die
		Random random = randomService.getRandom();
		int ageAtDeath;
		if (random.nextInt(150) == 0) {
			ageAtDeath = 0;
		} else if (random.nextInt(60) == 0) {
			ageAtDeath = randomService.randomIntOnCurve(8, 3, -4);
		} else if (random.nextInt(65) == 0) {
			ageAtDeath = randomService.randomIntOnCurve(16, 3, 2) + 6;
		} else {
			ageAtDeath = randomService.randomIntOnCurve(131, 5, 17);
		}
		return birthday.plusYears(ageAtDeath);
	}

}