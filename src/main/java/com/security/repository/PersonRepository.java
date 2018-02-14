package com.security.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.security.model.Person;

public interface PersonRepository extends JpaRepository<Person, Long>{

	Optional<Person> findByUsername(String username);
}
