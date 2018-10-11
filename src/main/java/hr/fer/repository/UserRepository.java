package hr.fer.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hr.fer.models.Role;
import hr.fer.models.User;


@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Integer> {
	 User findByEmail(String email);
	 
	 User findById (int id);

	List<User> findByRoles(Set<Role> roles);

	User findByJmbag(String jmbag);
}