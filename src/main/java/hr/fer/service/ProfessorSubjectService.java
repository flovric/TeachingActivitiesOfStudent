package hr.fer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hr.fer.models.ProfessorSubject;
import hr.fer.repository.ProfessorSubjectRepository;



@Service
public class ProfessorSubjectService {
	
	@Autowired
	ProfessorSubjectRepository professorSubjectRepository;
	
	public List<ProfessorSubject> findByProfessorId (long professorId) {
		return professorSubjectRepository.findByProfessorId(professorId);
	}

	public void addNewEntry(ProfessorSubject professorSubject) {
		professorSubjectRepository.save(professorSubject);
		
	}

	public void removeEntry(ProfessorSubject professorSubject) {
		professorSubjectRepository.delete(professorSubject);
	}

	public List<ProfessorSubject> findAll() {
		return professorSubjectRepository.findAll();
	}

}
