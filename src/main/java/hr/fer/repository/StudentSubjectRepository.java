package hr.fer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import hr.fer.models.StudentSubject;
import hr.fer.models.StudentSubjectId;

public interface StudentSubjectRepository extends JpaRepository<StudentSubject, StudentSubjectId> {
	
	List<StudentSubject> findByStudentId (long studentId);

	List<StudentSubject> findBySubjectIdAndCourseIdAndAcademicYearId(long subjectId, long courseId,
			long academicYearId);

}
