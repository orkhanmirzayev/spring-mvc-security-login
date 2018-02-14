package com.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.security.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

	Role findByRole(String role);
}
