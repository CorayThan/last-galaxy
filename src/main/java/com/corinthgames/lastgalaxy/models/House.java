package com.corinthgames.lastgalaxy.models;

import com.corinthgames.lastgalaxy.enums.Industry;
import com.corinthgames.lastgalaxy.enums.Specialization;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.nathanwestlake.dartgenerator.annotations.DartInclude;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@DartInclude
@Entity
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="@id")
public class House {

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;

	@Column(unique = true)
	private String name;

	@OneToOne(mappedBy="house")
	private User user;

	@OneToMany(mappedBy = "house")
	@OrderBy(value = "birthday")
	private List<Person> familyMembers;

	@ElementCollection(fetch = FetchType.EAGER)
	@Enumerated(value = EnumType.STRING)
	@OrderColumn
	private List<Specialization> specializations;

	@ElementCollection(fetch = FetchType.EAGER)
	@Enumerated(value = EnumType.STRING)
	@OrderColumn
	private List<Industry> industries;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Person> getFamilyMembers() {
		return familyMembers;
	}

	public void setFamilyMembers(List<Person> familyMembers) {
		this.familyMembers = familyMembers;
	}

	public List<Specialization> getSpecializations() {
		return specializations;
	}

	public void setSpecializations(List<Specialization> specializations) {
		this.specializations = specializations;
	}

	public List<Industry> getIndustries() {
		return industries;
	}

	public void setIndustries(List<Industry> industries) {
		this.industries = industries;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}