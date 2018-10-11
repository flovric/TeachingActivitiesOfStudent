package hr.fer.controller;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import hr.fer.models.AcademicYear;
import hr.fer.models.Activity;
import hr.fer.models.Comment;
import hr.fer.models.CommentTemp;
import hr.fer.models.Course;
import hr.fer.models.StudentSubject;
import hr.fer.models.Subject;
import hr.fer.models.SubjectCourseAcademicYear;
import hr.fer.models.SubjectTemp;
import hr.fer.models.User;
import hr.fer.service.AcademicYearService;
import hr.fer.service.ActivityService;
import hr.fer.service.CommentService;
import hr.fer.service.CourseService;
import hr.fer.service.StudentSubjectService;
import hr.fer.service.SubjectCourseAcademicYearService;
import hr.fer.service.SubjectCourseService;
import hr.fer.service.SubjectService;
import hr.fer.service.UserService;

@Controller
public class SubjectController {

	@Autowired
	UserService userService;
	
	@Autowired
	SubjectService subjectService;
	
	@Autowired
	StudentSubjectService studentSubjectService;
	
	@Autowired
	AcademicYearService academicYearService;
	
	@Autowired
	SubjectCourseService subjectCourseService;
	
	@Autowired
	CourseService courseService;
	
	@Autowired
	SubjectCourseAcademicYearService subjectCourseAcademicYearService;
	
	@Autowired
	ActivityService activityService;
	
	@Autowired
	CommentService commentService;
	
	@RequestMapping(value="/student", method = RequestMethod.GET)
	public ModelAndView userSubjects(){
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		
		List<AcademicYear> years = academicYearService.findAll();
		
		modelAndView.addObject("years", years);
		
		return modelAndView;
	}
	
	@RequestMapping(value="/student/{id}/subjects", method = RequestMethod.GET)
	public ModelAndView showSubjectByYear(@PathVariable("id") long id){
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		
		AcademicYear year = academicYearService.findAcademicYearById(id);
		
		//List<StudentSubject> studentSubjects = ;
		List<SubjectCourseAcademicYear> subjects1 = subjectCourseAcademicYearService.findByAcademicYear(id);
		List<StudentSubject> students1 = studentSubjectService.findByStudentId(user.getId());
		
		List<SubjectTemp> subjects = new ArrayList<SubjectTemp>();
		
		for(SubjectCourseAcademicYear sub : subjects1) {
			for(StudentSubject stud : students1) {
				if(sub.getSubjectId()==stud.getSubjectId() && sub.getCourseId()==stud.getCourseId() && 
						id==stud.getAcademicYearId()) {
					subjects.add(new SubjectTemp(sub.getSubjectId(), subjectService.findSubjectById(sub.getSubjectId()).getName(),
							subjectService.findSubjectById(sub.getSubjectId()).getEcts(), sub.getCourseId(), courseService.findCourseById(sub.getCourseId()).getCourse()));
	
				}
			}
		}
		
		modelAndView.addObject("year", year);
		modelAndView.addObject("subjects", subjects);
		modelAndView.setViewName("studentSubjects");
		
		return modelAndView;
	}
	
	@RequestMapping(value="/student/{yearId}/subjects/{courseId}/{subjectId}", method = RequestMethod.GET)
	public ModelAndView showActivities(@PathVariable("yearId") long yearId, @PathVariable("courseId") long courseId, 
			@PathVariable("subjectId") long subjectId){
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		
		AcademicYear year = academicYearService.findAcademicYearById(yearId);
		SubjectTemp subject = new SubjectTemp(subjectId, subjectService.findSubjectById(subjectId).getName(),
				subjectService.findSubjectById(subjectId).getEcts(), courseId, courseService.findCourseById(courseId).getCourse());
		Activity newActivity = new Activity();
		
		List<Activity> activities = activityService.findByYearIdAndCourseIdAndSubjectIdAndUserId(yearId, courseId, subjectId, user.getId());
		
		int suma = 0;
		for (Activity activity : activities) {
			suma += activity.getDuration();
		}
		
		modelAndView.addObject("newActivity", newActivity);
		modelAndView.addObject("year", year);
		modelAndView.addObject("subject", subject);
		modelAndView.addObject("activities", activities);
		modelAndView.addObject("suma", suma);
		modelAndView.setViewName("studentActivities");
		
		return modelAndView;
	}
	
	@RequestMapping(value="/student/{yearId}/subjects/{courseId}/{subjectId}", method = RequestMethod.POST)
	public ModelAndView addNewActivity(@PathVariable("yearId") long yearId, @PathVariable("courseId") long courseId, 
			@PathVariable("subjectId") long subjectId, Activity activityForm){
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		
		AcademicYear year = academicYearService.findAcademicYearById(yearId);
		SubjectTemp subject = new SubjectTemp(subjectId, subjectService.findSubjectById(subjectId).getName(),
				subjectService.findSubjectById(subjectId).getEcts(), courseId, courseService.findCourseById(courseId).getCourse());
		Activity newActivity = new Activity();
		Date date = new Date();
		
		List<Activity> activities = activityService.findByYearIdAndCourseIdAndSubjectIdAndUserId(yearId, courseId, subjectId, user.getId());
	
		
		Activity activity = new Activity();
		
		activity.setText(activityForm.getText());
		activity.setDuration(activityForm.getDuration());
		activity.setSubjectId(subjectId);
		activity.setUserId(user.getId());
		activity.setCourseId(courseId);
		activity.setYearId(yearId);
		activity.setCreationTime(date);
		
		activityService.saveActivity(activity);

		modelAndView.setViewName("redirect:/student/" + yearId+"/subjects/"+courseId+"/"+subjectId);
		return modelAndView;
	}
	
	@RequestMapping(value="/student/{yearId}/subjects/{courseId}/{subjectId}/activity/{activityId}", method = RequestMethod.GET)
	public ModelAndView showComments(@PathVariable("yearId") int yearId, @PathVariable("courseId") int courseId, 
			@PathVariable("subjectId") int subjectId, @PathVariable("activityId") int activityId){
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		
		AcademicYear year = academicYearService.findAcademicYearById(yearId);
		SubjectTemp subject = new SubjectTemp(subjectId, subjectService.findSubjectById(subjectId).getName(),
				subjectService.findSubjectById(subjectId).getEcts(), courseId, courseService.findCourseById(courseId).getCourse());
		Activity newActivity = new Activity();
		Comment newComment = new Comment();
		
		Activity activity = activityService.findById(activityId);
		List<Activity> activities = activityService.findByYearIdAndCourseIdAndSubjectIdAndUserId(yearId, courseId, subjectId, user.getId());
		List<Comment> commentsTemp = commentService.findByActivityId(activityId);
		
		List<CommentTemp> comments = new ArrayList<>();
		for (Comment comm : commentsTemp) {
			User u = userService.findById(comm.getAuthorId());
			comments.add(new CommentTemp(comm.getId(), new String(u.getName()+" "+u.getLastName()), comm.getCreationTime(),
					comm.getSubjectId(), comm.getActivityId(), comm.getText()));
		}
		
		int suma = 0;
		for (Activity act : activities) {
			suma += act.getDuration();
		}
		
		modelAndView.addObject("newActivity", newActivity);
		modelAndView.addObject("newComment", newComment);
		modelAndView.addObject("year", year);
		modelAndView.addObject("subject", subject);
		modelAndView.addObject("activity", activity);
		modelAndView.addObject("activities", activities);
		modelAndView.addObject("comments", comments);
		modelAndView.addObject("suma", suma);
		
		modelAndView.setViewName("studentComments");
		
		return modelAndView;
	}
	
	@RequestMapping(value="/student/{yearId}/subjects/{courseId}/{subjectId}/activity/{activityId}", 
			method = RequestMethod.POST)
	public ModelAndView addNewComment(@PathVariable("yearId") int yearId, 
			@PathVariable("courseId") int courseId, 
			@PathVariable("subjectId") int subjectId,
			@PathVariable("activityId") int activityId,
			@RequestParam("commentId") int commentId, 
			@RequestParam("delete") int delete, 
			@RequestParam("add") int add, Comment newComment){
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());

		Date date = new Date();
		
		if(delete==1) {
			if(user.getId() == commentService.findById(commentId).getAuthorId())
				commentService.deleteComment(commentId);
		}
		
		if(add==1) {
			Comment commentSave = new Comment();
			
			
			commentSave.setText(newComment.getText());
			commentSave.setAuthorId(user.getId());
			commentSave.setCreationTime(date);
			commentSave.setSubjectId(subjectId);
			commentSave.setActivityId(activityId);
			
			commentService.saveComment(commentSave);
			
		}
		
		modelAndView.setViewName("redirect:/student/" +yearId+ "/subjects/" +courseId+ "/" +subjectId+ "/activity/" +activityId);
		return modelAndView;
	}

}
