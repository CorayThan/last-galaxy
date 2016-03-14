package com.corinthgames.lastgalaxy.services;

import com.corinthgames.lastgalaxy.enums.Gender;
import com.corinthgames.lastgalaxy.models.House;
import com.corinthgames.lastgalaxy.models.Person;
import com.corinthgames.lastgalaxy.repos.PersonRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class HouseCreationService {

	final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private RandomService randomService;
	@Autowired
	private PersonService personService;
	@Autowired
	private PersonRepo personRepo;

	/**
	 * Creates initial characters for house. Player must set names and house leader later.
	 * Only after those are chosen will character relationships be generated.
	 * <p/>
	 * This should only be called from {@link HouseService#setupHouse(com.corinthgames.lastgalaxy.models.User)}
	 *
	 * Saves all characters
	 *
	 * @param house to add characters to
	 */
	public void createInitialCharacters(House house) {
			List<Person> family = new ArrayList<>();
			house.setFamilyMembers(family);

			int members = randomService.getRandom().nextInt(4) + 4;
			int qualityModifier = 5;
			if (members == 6) {
				qualityModifier = 7;
			} else if (members == 5) {
				qualityModifier = 9;
			} else if (members == 4) {
				qualityModifier = 11;
			}
			Random random = randomService.getRandom();
			int age = random.nextInt(32) + random.nextInt(19) + 18;

			//make husband then wife, followed by others (direct family)
			Person husband = personService.createPerson(house, Gender.MALE, birthday(age), qualityModifier);
			boolean husbandIsAlive = false;
			if (GameTime.stillAlive(husband)) {
				family.add(husband);
				husbandIsAlive = true;
			}
			addWifeAndChildren(husbandIsAlive, husband, age, members, family, house, qualityModifier);

			for (Person child : husband.getChildren()) {
				if (child.getGender() == Gender.MALE) {
					int childAge = child.getAge();
					if (childAge > 17) {
						int ageMinus18 = childAge - 18;
						if (ageMinus18 > 12) {
							ageMinus18 = 12;
						}
						//10% chance for 18 year olds to have a wife, increasing to 75% for 30+
						if ((0.055 * ageMinus18 + 0.1) > random.nextDouble()) {
							addWifeAndChildren(child.isAlive(), child, childAge, members, family, house, qualityModifier);
						}
					}
				}
			}
		logger.info("created family members with " + family.size() + " people and these people: " + family);
	}

	private void addWifeAndChildren(boolean husbandIsAlive, Person husband, int husbandAge, int members, List<Person> family, House house, int qualityModifier) {
		if (family.size() < members) {
			int wifeMin = husbandAge - 20;
			int wifeMax = husbandAge + 20;
			if (wifeMin < 18) {
				wifeMin = 18;
			} else if (wifeMax > 69) {
				wifeMax = 69;
			}
			int wifeAge = randomService.randomIntBetween(wifeMin, wifeMax, 4, husbandAge - 2);
			Person wife = personService.createPerson(house, Gender.FEMALE, birthday(wifeAge), qualityModifier);
			boolean wifeIsAlive = false;
			if (GameTime.stillAlive(wife)) {
				family.add(wife);
				wifeIsAlive = true;
			}
			if (husbandIsAlive && wifeIsAlive) {
				husband.setSignificantOther(wife);
				wife.setSignificantOther(husband);
			}

			addChildren(husband, wife, wifeAge, members, family, house, qualityModifier);
		}
	}

	private void addChildren(Person father, Person mother, int momAge, int members, List<Person> family, House house, int qualityModifier) {

		Random random = randomService.getRandom();
		boolean addChildren = true;
		int children = 0;
		int previousChildAge = 0;
		while (addChildren) {
			//add children while the family has room, and the wife's age allows it given a 98% change to have one child by age 48
			//and that chance reducing by
			addChildren = members > family.size() && random.nextDouble() < (momAge - 18) / (30 / (0.96 * (children == 0 ? 1 : Math.pow(0.75, children))));
			if (addChildren) {
				int targetAge = momAge - 24;
				int maxAge = targetAge + 6;

				if (children > 0) {
					if (maxAge > previousChildAge - 1) {
						//ensure women have enough time to get pregnant again
						maxAge = previousChildAge - 1;
						if (maxAge < 0) {
							break;
						}
					}
					targetAge -= children * 2;
				}
				if (targetAge < 0) {
					targetAge = 0;
				}
				int childAgeMod;
				if (targetAge > maxAge) {
					childAgeMod = maxAge / 2;
				} else {
					childAgeMod = targetAge - maxAge / 2;
					if (childAgeMod > maxAge / 2) {
						childAgeMod--;
					} else if (childAgeMod < -(maxAge / 2)) {
						childAgeMod++;
					}
				}
				int childAge = randomService.randomIntOnCurve(maxAge, 2, childAgeMod);
				Person child = personService.createPerson(house, random.nextBoolean() ? Gender.MALE : Gender.FEMALE, birthday(childAge), qualityModifier);
				if (GameTime.stillAlive(child)) {
					family.add(child);
					children++;
					child.addParents(mother, father);
				}
				previousChildAge = childAge;
				if (members < family.size() && random.nextInt(30) == 0) {
					//make twins separate method
					Person twin;
					if (random.nextInt(3) == 0) {
						twin = personService.createPerson(child.twin());
					} else {
						twin = personService.createPerson(house, random.nextBoolean() ? Gender.MALE : Gender.FEMALE, child.getBirthday(), qualityModifier);
					}
					if (GameTime.stillAlive(twin)) {
						family.add(twin);
						children++;
						twin.addParents(mother, father);
					}

				}
			}
		}

	}

	private LocalDate birthday(int age) {
		return GameTime.getGameDate(-age, -randomService.getRandom().nextInt(366));
	}

}