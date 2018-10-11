package hr.fer.service;

import java.util.List;
import java.util.Set;

import hr.fer.models.Role;
import hr.fer.models.User;

public interface UserService {
	
	public User findUserByEmail(String email);
	
	public void saveUser(User user, String role);

	public User findById(int id);

	public List<User> findByRoles(Set<Role> roles);

	public User findByJmbag(String jmbag);

	public List<User> findAll();

	
}