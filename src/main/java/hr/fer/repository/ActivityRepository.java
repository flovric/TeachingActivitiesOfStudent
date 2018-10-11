package hr.fer.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import hr.fer.models.Activity;

public interface ActivityRepository extends CrudRepository<Activity, Long> {

	List<Activity> findByYearIdAndCourseIdAndSubjectIdAndUserId (long yearId, long courseId, 
			long subjectId, long userId);
}
