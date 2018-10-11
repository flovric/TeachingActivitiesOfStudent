package hr.fer.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import hr.fer.models.Activity;
import hr.fer.models.Subject;
import hr.fer.models.User;
import hr.fer.service.ActivityService;
import hr.fer.service.SubjectService;
import hr.fer.service.UserService;

@Controller
public class ActivityController {
	
	@Autowired
	ActivityService activityService;
	
	@Autowired
	UserService userService;
	
	
	@RequestMapping(value="/student/subjects/{id}", method = RequestMethod.GET)
	public ModelAndView subject(@PathVariable("id") long id) {
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		
		Activity newActivity = new Activity();
		Subject subject = new Subject();
		subject.setId(id);
		
		//List<Activity> activities = activityService.findBySubjectIdAndUserId(id, user.getId());
		
		//modelAndView.addObject("activities", activities);
		modelAndView.addObject("newActivity", newActivity);
		modelAndView.addObject("subject", subject);
		modelAndView.setViewName("subject");
		
		return modelAndView;
	}
	
	@RequestMapping(value="/student/subjects/{id}", method = RequestMethod.POST)
	public ModelAndView subjectAddActivity(@PathVariable("id") long id, Activity activityForm, Subject subject) {
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		Date date = new Date();
		
		Activity activity = new Activity();
		
		activity.setText(activityForm.getText());
		activity.setDuration(activityForm.getDuration());
		activity.setSubjectId(subject.getId());
		activity.setUserId(user.getId());
		activity.setCreationTime(date);
		
		activityService.saveActivity(activity);

		modelAndView.setViewName("redirect:/student/subjects/" + id);
		return modelAndView;
	}
	
}
