package com.corinthgames.lastgalaxy.models;

import com.corinthgames.lastgalaxy.enums.Gender;
import com.corinthgames.lastgalaxy.enums.Stat;
import com.corinthgames.lastgalaxy.services.GameTime;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.nathanwestlake.dartgenerator.annotations.DartInclude;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by CorayThan on 10/24/2014.
 */
@DartInclude
@Entity
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="@id")
public class Person {

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;

	private String firstName;
	private String lastName;
	@Enumerated(value = EnumType.STRING)
	private Gender gender;
	private LocalDate birthday;
	private LocalDate deathday;

	@ElementCollection(fetch=FetchType.EAGER)
	@MapKeyEnumerated(EnumType.STRING)
	//stats range from 0 to 99
	private Map<Stat, Integer> stats;

	@ManyToOne
	private House house;

	@ManyToMany
	@JoinTable(name = "parents_children", joinColumns = {@JoinColumn(name = "parent_id", referencedColumnName = "id")},
			inverseJoinColumns = {@JoinColumn(name = "child_id", referencedColumnName = "id")},
			uniqueConstraints = @UniqueConstraint(name="uk_parent_child", columnNames = {"parent_id", "child_id"}))
	@Size(min=0, max=2)
	private List<Person> parents = new ArrayList<>();

	@ManyToMany(mappedBy = "parents")
	private List<Person> children = new ArrayList<>();

	@OneToOne
	private Person significantOther;

	public Person(House house, String lastName, Gender gender, LocalDate birthday, LocalDate deathday, Map<Stat, Integer> stats) {
		this.house = house;
		this.lastName = lastName;
		this.gender = gender;
		this.birthday = birthday;
		this.deathday = deathday;
	}

	//for hibernate only
	protected Person() {
	}

	/**
	 * Only used for creating twins
	 * Does not twin first name or id
	 * Make sure you set a new deathday
	 *
	 * @return
	 */
	public Person twin() {
		Person twin = new Person(house, lastName, gender, birthday, deathday, stats);
		return twin;
	}

	/**
	 * Adds full relationship, including for parents
	 *
	 * @param mom
	 * @param dad
	 */
	public void addParents(Person mom, Person dad) {
		this.getParents().add(mom);
		this.getParents().add(dad);
		mom.getChildren().add(this);
		dad.getChildren().add(this);
	}

	@Override
	public String toString() {
		return "CharacterModel{" +
				"id=" + id +
				", firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				", gender=" + gender +
				", birthday=" + birthday +
				", deathday=" + deathday +
				", stats=" + stats +
				", houseId=" + house.getId() +
				'}';
	}

	public int getAge() {
		return GameTime.getPeriod(birthday).getYears();
	}

	public boolean isAlive() {
		return GameTime.stillAlive(this);
	}

	public String getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public LocalDate getBirthday() {
		return birthday;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public House getHouse() {
		return house;
	}

	public void setHouse(House house) {
		this.house = house;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setBirthday(LocalDate birthday) {
		this.birthday = birthday;
	}

	public LocalDate getDeathday() {
		return deathday;
	}

	public void setDeathday(LocalDate deathday) {
		this.deathday = deathday;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public Map<Stat, Integer> getStats() {
		return stats;
	}

	public void setStats(Map<Stat, Integer> stats) {
		this.stats = stats;
	}

	public List<Person> getParents() {
		return parents;
	}

	public void setParents(List<Person> parents) {
		this.parents = parents;
	}

	public List<Person> getChildren() {
		return children;
	}

	public void setChildren(List<Person> children) {
		this.children = children;
	}

	public Person getSignificantOther() {
		return significantOther;
	}

	public void setSignificantOther(Person significantOther) {
		this.significantOther = significantOther;
	}
}