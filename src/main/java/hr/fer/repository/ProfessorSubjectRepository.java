package hr.fer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import hr.fer.models.ProfessorSubject;
import hr.fer.models.ProfessorSubjectId;


public interface ProfessorSubjectRepository extends JpaRepository<ProfessorSubject, ProfessorSubjectId> {
	
	List<ProfessorSubject> findByProfessorId (long ProfessorId);

}
