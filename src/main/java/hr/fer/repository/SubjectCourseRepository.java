package hr.fer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import hr.fer.models.SubjectCourse;
import hr.fer.models.SubjectCourseId;

public interface SubjectCourseRepository extends JpaRepository<SubjectCourse, SubjectCourseId> {
	
	List<SubjectCourse> findBySubjectIdAndCourseId (long subjectId, long userId);

	void deleteBySubjectId(long subjectId);

	List<SubjectCourse> findBySubjectId(long subjectId);

}
