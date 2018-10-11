package hr.fer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hr.fer.models.SubjectCourseAcademicYear;
import hr.fer.repository.SubjectCourseAcademicYearRepository;

@Service
public class SubjectCourseAcademicYearService {

	@Autowired
	SubjectCourseAcademicYearRepository subjectCourseAcademicYearRepository;
	
	public List<SubjectCourseAcademicYear> findAll() {
		return subjectCourseAcademicYearRepository.findAll();
	}
	
	public List<SubjectCourseAcademicYear> findByAcademicYear(long academicYearId) {
		return subjectCourseAcademicYearRepository.findByAcademicYearId(academicYearId);
	}
	
	public void addNewEntry (SubjectCourseAcademicYear scay) {
		subjectCourseAcademicYearRepository.save(scay);
	}

	public void removeEntry(SubjectCourseAcademicYear subjectCourseAcademicYear) {
		subjectCourseAcademicYearRepository.delete(subjectCourseAcademicYear);
		
	}
}
