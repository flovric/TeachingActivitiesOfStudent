package hr.fer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import hr.fer.models.Course;


public interface CourseRepository extends JpaRepository<Course, Long> {

	
	Course findById(long id);
	

	Course findByCourse(String course);
}
