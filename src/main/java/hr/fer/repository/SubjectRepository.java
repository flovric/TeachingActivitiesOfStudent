package hr.fer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import hr.fer.models.Subject;

public interface SubjectRepository extends JpaRepository<Subject, Long> {

	Subject findByName(String name);

	//List<Subject> findByUsers(User users);

}
