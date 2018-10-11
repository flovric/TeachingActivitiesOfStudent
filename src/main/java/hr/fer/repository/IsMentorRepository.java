package hr.fer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import hr.fer.models.IsMentor;
import hr.fer.models.IsMentorId;


public interface IsMentorRepository extends JpaRepository<IsMentor, IsMentorId> {
	
	List<IsMentor> findByProfessorId (int professorId);

	List<IsMentor> findByStudentId(int studentId);

}
