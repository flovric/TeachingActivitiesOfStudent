package hr.fer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hr.fer.models.Role;
import hr.fer.repository.RoleRepository;

@Service
public class RoleService {

	@Autowired
	RoleRepository roleRepository;

	public Role findByRole(String role) {
		return roleRepository.findByRole(role);
	}
}
