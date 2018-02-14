package com.security.service;

import java.util.List;
import java.util.Optional;

import com.security.model.Person;

public interface PersonService {

	Person findById(Long id);
	List<Person> findAll();
	Optional<Person> findByUsername(String username);
	Person createPerson(Person p);
}
