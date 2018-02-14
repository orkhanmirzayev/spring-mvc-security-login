package com.security.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.security.model.Person;
import com.security.model.Role;
import com.security.repository.PersonRepository;
import com.security.repository.RoleRepository;

@Service
public class PersonServiceImpl implements PersonService {

	
	@Autowired
	private PersonRepository personRepository;
	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Override
	public Person findById(Long id) {
		return personRepository.findOne(id);
	}

	@Override
	public List<Person> findAll() {
		return personRepository.findAll();
	}

	@Override
	public Optional<Person> findByUsername(String username) {
		return personRepository.findByUsername(username);
	}

	@Override
	public Person createPerson(Person p) {
		p.setPassword(encoder.encode(p.getPassword()));
		Role role = roleRepository.findByRole("ROLE_USER");
		List<Role> roles = new ArrayList<>();
		roles.add(role);
		p.setRoles(roles);
		return personRepository.save(p);
	}
	
	
}
