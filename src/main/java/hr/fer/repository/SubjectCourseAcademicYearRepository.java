package hr.fer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import hr.fer.models.SubjectCourseAcademicYear;
import hr.fer.models.SubjectCourseAcademicYearId;

public interface SubjectCourseAcademicYearRepository extends JpaRepository<SubjectCourseAcademicYear, SubjectCourseAcademicYearId> {

	List<SubjectCourseAcademicYear> findByAcademicYearId(long academicYearId);

	
}
