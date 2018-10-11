package hr.fer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hr.fer.models.Activity;
import hr.fer.repository.ActivityRepository;

@Service
public class ActivityService {

	@Autowired
	ActivityRepository activityRepository;
	
	public void saveActivity(Activity activity) {
		activityRepository.save(activity);
	}
	
	public List<Activity> findByYearIdAndCourseIdAndSubjectIdAndUserId (long yearId, long courseId, 
			long subjectId, long userId) {
		return activityRepository.findByYearIdAndCourseIdAndSubjectIdAndUserId(yearId, courseId, subjectId, userId);
		
	}

	public Activity findById(int id) {
		return activityRepository.findOne((long) id);
	}
}
