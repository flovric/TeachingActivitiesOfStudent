package hr.fer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hr.fer.models.StudentSubject;
import hr.fer.repository.StudentSubjectRepository;

@Service
public class StudentSubjectService {
	
	@Autowired
	StudentSubjectRepository studentSubjectRepository;
	
	public List<StudentSubject> findByStudentId (long studentId) {
		return studentSubjectRepository.findByStudentId(studentId);
	}
	
	public List<StudentSubject> findBySubjectIdAndCourseIdAndAcademicYearId (long subjectId, long courseId, long academicYearId ) {
		return studentSubjectRepository.findBySubjectIdAndCourseIdAndAcademicYearId(subjectId, courseId, academicYearId);
	}

	public void addNewEntry(StudentSubject studentSubject) {
		studentSubjectRepository.save(studentSubject);
		
	}

	public void removeEntry(StudentSubject studentSubject) {
		studentSubjectRepository.delete(studentSubject);
		
	}

	public List<StudentSubject> findAll() {
		
		return studentSubjectRepository.findAll();
	}

}
