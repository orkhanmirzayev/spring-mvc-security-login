package com.security.service;

import com.security.model.*;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.*;
import com.security.repository.PersonRepository;


@Service
public class PersonDetailsService implements UserDetailsService {

	@Autowired
	@Lazy
	private PersonRepository personRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Optional<Person> person = personRepository.findByUsername(username);
		final List<SimpleGrantedAuthority> grants= new ArrayList<>();
		person.get().getRoles().forEach(p->grants.add(new SimpleGrantedAuthority(p.getRole())));
		return person.map(p->new User(username,p.getPassword(), grants))
		.orElseThrow(()-> new UsernameNotFoundException("User not found"));
	}

}
