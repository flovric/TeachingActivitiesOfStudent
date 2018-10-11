package hr.fer.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import hr.fer.models.Role;
import hr.fer.models.User;
import hr.fer.repository.RoleRepository;
import hr.fer.repository.UserRepository;


@Service("userService")
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	@Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	public User findUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public void saveUser(User user, String role) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(1);
        Role userRole = new Role();
        
        userRole = roleRepository.findByRole(role);
        if(!role.equals("STUDENT")) {
        	user.setJmbag("nepoznat");
        }
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
		userRepository.save(user);
	}

	@Override
	public User findById(int id) {
		
		return userRepository.findById(id);
	}

	@Override
	public List<User> findByRoles(Set<Role> roles) {
		return userRepository.findByRoles(roles);
	}

	@Override
	public User findByJmbag(String jmbag) {
		return userRepository.findByJmbag(jmbag);
	}

	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}

}