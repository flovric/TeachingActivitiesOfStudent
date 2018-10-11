package hr.fer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hr.fer.models.Subject;
import hr.fer.repository.SubjectRepository;

@Service
public class SubjectService {

	@Autowired
	SubjectRepository subjectRepository;
	
	
	public void saveSubject(Subject subject) {
		
		subjectRepository.save(subject);
	}
	
	public void deleteSubject(long id) {
		
		subjectRepository.delete(id);
	}
	
	public List<Subject> findAll() {
		return subjectRepository.findAll();
	}
	
	public Subject findSubjectById(long id) {
		return subjectRepository.findOne(id);
	}
	
	public Subject findSubjectByName(String name) {
		return subjectRepository.findByName(name);
	}
	
	/*  public List<Subject> findSubjectsByUser(User user) {
		return subjectRepository.findByUsers(user);
	} */
}
