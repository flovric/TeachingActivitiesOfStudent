package hr.fer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hr.fer.models.SubjectCourse;
import hr.fer.repository.SubjectCourseRepository;

@Service
public class SubjectCourseService {

	@Autowired
	SubjectCourseRepository subjectCourseRepository;
	
	public List<SubjectCourse> findBySubjectIdAndCourseId (long subjectId, long userId) {
		return subjectCourseRepository.findBySubjectIdAndCourseId(subjectId, userId);
	}
	
	public void saveSubjectCourse (SubjectCourse subjectCourse) {
		subjectCourseRepository.save(subjectCourse);
	}

	public void deleteSubjectCourseBySubjectId(long subjectId) {
		subjectCourseRepository.deleteBySubjectId(subjectId);
		
	}

	public List<SubjectCourse> findAll() {
		
		return subjectCourseRepository.findAll();
	}

	public List<SubjectCourse> findBySubjectId(long subjectId) {
		return subjectCourseRepository.findBySubjectId(subjectId);
	}

	public void addNewEntry(SubjectCourse subjectCourse) {
		subjectCourseRepository.save(subjectCourse);
		
	}

	public void removeEntry(SubjectCourse subjectCourse) {
		subjectCourseRepository.delete(subjectCourse);
		
	}
}
