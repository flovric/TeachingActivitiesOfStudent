package hr.fer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hr.fer.models.Course;
import hr.fer.repository.CourseRepository;

@Service
public class CourseService {

	@Autowired
	CourseRepository courseRepository;
	
	public Course findCourseById(long id) {
		return courseRepository.findOne(id);
	}
	
	public Course findCourseByCourse(String course) {
		return courseRepository.findByCourse(course);
	}

	public List<Course> findAll() {
		return courseRepository.findAll();
		
	}

}
