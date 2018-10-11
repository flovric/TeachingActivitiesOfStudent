package hr.fer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hr.fer.models.IsMentor;
import hr.fer.repository.IsMentorRepository;

@Service
public class IsMentorService {
	
	@Autowired
	IsMentorRepository isMentorRepository;
	
	public List<IsMentor> findByProfessorId (int professorId) {
		return isMentorRepository.findByProfessorId(professorId);
	}
	
	public List<IsMentor> findByStudentId(int studentId) {
		return isMentorRepository.findByStudentId(studentId);
	}

	public List<IsMentor> findAll() {
		
		return isMentorRepository.findAll();
	}

	public void addNewEntry(IsMentor isMentor) {
		isMentorRepository.save(isMentor);
		
	}

	public void removeEntry(IsMentor isMentor) {
		isMentorRepository.delete(isMentor);
		
	}

}
