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
import hr.fer.models.IsMentor;
import hr.fer.models.ProfessorSubject;
import hr.fer.models.StudentSubject;
import hr.fer.models.Subject;
import hr.fer.models.SubjectCourseAcademicYear;
import hr.fer.models.SubjectTemp;
import hr.fer.models.User;
import hr.fer.service.AcademicYearService;
import hr.fer.service.ActivityService;
import hr.fer.service.CommentService;
import hr.fer.service.CourseService;
import hr.fer.service.IsMentorService;
import hr.fer.service.ProfessorSubjectService;
import hr.fer.service.StudentSubjectService;
import hr.fer.service.SubjectCourseAcademicYearService;
import hr.fer.service.SubjectCourseService;
import hr.fer.service.SubjectService;
import hr.fer.service.UserService;

@Controller
public class ProfessorController {

	@Autowired
	UserService userService;
	
	@Autowired
	SubjectService subjectService;
	
	@Autowired
	StudentSubjectService studentSubjectService;
	
	@Autowired
	ProfessorSubjectService professorSubjectService;
	
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
	IsMentorService isMentorService;
	
	@Autowired
	CommentService commentService;
	
	@RequestMapping(value="/professor", method = RequestMethod.GET)
	public ModelAndView professorHome(){
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		
		List<AcademicYear> years = academicYearService.findAll();
		
		modelAndView.addObject("years", years);
		modelAndView.setViewName("professor");
		
		return modelAndView;
	}
	
	@RequestMapping(value="/professor/{id}/subjects", method = RequestMethod.GET)
	public ModelAndView subjectBySelectedYear(@PathVariable("id") long id){
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		
		AcademicYear year = academicYearService.findAcademicYearById(id);
		
		
		List<SubjectCourseAcademicYear> subjects1 = subjectCourseAcademicYearService.findByAcademicYear(id);
		List<ProfessorSubject> professors1 = professorSubjectService.findByProfessorId(user.getId());
		
		List<SubjectTemp> subjects = new ArrayList<SubjectTemp>();
		
		for(SubjectCourseAcademicYear sub : subjects1) {
			for(ProfessorSubject prof : professors1) {
				if(sub.getSubjectId()==prof.getSubjectId() && sub.getCourseId()==prof.getCourseId() && 
						id==prof.getAcademicYearId()) {
					subjects.add(new SubjectTemp(sub.getSubjectId(), subjectService.findSubjectById(sub.getSubjectId()).getName(),
							subjectService.findSubjectById(sub.getSubjectId()).getEcts(), sub.getCourseId(), courseService.findCourseById(sub.getCourseId()).getCourse()));
	
				}
			}
		}
		
		modelAndView.addObject("year", year);
		modelAndView.addObject("subjects", subjects);
		modelAndView.setViewName("professorSubjects");
		
		return modelAndView;
	}
	
	
	@RequestMapping(value="/professor/{yearId}/subjects/{courseId}/{subjectId}/students", method = RequestMethod.GET)
	public ModelAndView showStudentsActivities(@PathVariable("yearId") int yearId, @PathVariable("courseId") int courseId, 
			@PathVariable("subjectId") int subjectId){
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		
		AcademicYear year = academicYearService.findAcademicYearById(yearId);
		SubjectTemp subject = new SubjectTemp(subjectId, subjectService.findSubjectById(subjectId).getName(),
				subjectService.findSubjectById(subjectId).getEcts(), courseId, courseService.findCourseById(courseId).getCourse());
		
		List<SubjectCourseAcademicYear> subjects1 = subjectCourseAcademicYearService.findByAcademicYear(yearId);
		List<ProfessorSubject> professors1 = professorSubjectService.findByProfessorId(user.getId());
		
		List<SubjectTemp> subjects = new ArrayList<SubjectTemp>();
		
		List<IsMentor> mentors = isMentorService.findByProfessorId(user.getId());
		List<IsMentor> mentors2 = new ArrayList<>();
		
		List<StudentSubject> studentsBySubject = studentSubjectService.findBySubjectIdAndCourseIdAndAcademicYearId(subjectId, courseId, yearId);
		List<User> students = new ArrayList<>();
		
		for (IsMentor mentor : mentors) {
			for (StudentSubject stud : studentsBySubject) {
				if (mentor.getStudentId() == stud.getStudentId()) {
					students.add(userService.findById(mentor.getStudentId()));
				}
			}
		}
		
		for(SubjectCourseAcademicYear sub : subjects1) {
			for(ProfessorSubject prof : professors1) {
				if(sub.getSubjectId()==prof.getSubjectId() && sub.getCourseId()==prof.getCourseId() && 
						yearId==prof.getAcademicYearId()) {
					subjects.add(new SubjectTemp(sub.getSubjectId(), subjectService.findSubjectById(sub.getSubjectId()).getName(),
							subjectService.findSubjectById(sub.getSubjectId()).getEcts(), sub.getCourseId(), courseService.findCourseById(sub.getCourseId()).getCourse()));
	
				}
			}
		}
		
		modelAndView.addObject("students", students);
		modelAndView.addObject("year", year);
		modelAndView.addObject("subject", subject);
		modelAndView.addObject("subjects", subjects);
		
		modelAndView.setViewName("professorSubjectStudents");
		
		return modelAndView;
	}
	
	@RequestMapping(value="/professor/{yearId}/subjects/{courseId}/{subjectId}/students/{studentId}", method = RequestMethod.GET)
	public ModelAndView showStudentsForSubject(@PathVariable("yearId") long yearId, @PathVariable("courseId") long courseId, 
			@PathVariable("subjectId") long subjectId, @PathVariable("studentId") int studentId){
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		
		AcademicYear year = academicYearService.findAcademicYearById(yearId);
		SubjectTemp subject = new SubjectTemp(subjectId, subjectService.findSubjectById(subjectId).getName(),
				subjectService.findSubjectById(subjectId).getEcts(), courseId, courseService.findCourseById(courseId).getCourse());
		
		User student = userService.findById(studentId);
		
		List<SubjectCourseAcademicYear> subjects1 = subjectCourseAcademicYearService.findByAcademicYear(yearId);
		List<ProfessorSubject> professors1 = professorSubjectService.findByProfessorId(user.getId());
		
		List<SubjectTemp> subjects = new ArrayList<SubjectTemp>();
		
		List<IsMentor> mentors = isMentorService.findByProfessorId(user.getId());
		List<IsMentor> mentors2 = new ArrayList<>();
		
		List<StudentSubject> studentsBySubject = studentSubjectService.findBySubjectIdAndCourseIdAndAcademicYearId(subjectId, courseId, yearId);
		List<User> students = new ArrayList<>();
		
		List<Activity> activities = activityService.findByYearIdAndCourseIdAndSubjectIdAndUserId(yearId, courseId, subjectId, studentId);
		
		int suma = 0;
		for (Activity activity : activities) {
			suma += activity.getDuration();
		}
		
		for (IsMentor mentor : mentors) {
			for (StudentSubject stud : studentsBySubject) {
				if (mentor.getStudentId() == stud.getStudentId()) {
					students.add(userService.findById(mentor.getStudentId()));
				}
			}
		}
		
		for(SubjectCourseAcademicYear sub : subjects1) {
			for(ProfessorSubject prof : professors1) {
				if(sub.getSubjectId()==prof.getSubjectId() && sub.getCourseId()==prof.getCourseId() && 
						yearId==prof.getAcademicYearId()) {
					subjects.add(new SubjectTemp(sub.getSubjectId(), subjectService.findSubjectById(sub.getSubjectId()).getName(),
							subjectService.findSubjectById(sub.getSubjectId()).getEcts(), sub.getCourseId(), courseService.findCourseById(sub.getCourseId()).getCourse()));
	
				}
			}
		}
		
		modelAndView.addObject("students", students);
		modelAndView.addObject("student", student);
		modelAndView.addObject("year", year);
		modelAndView.addObject("subject", subject);
		modelAndView.addObject("subjects", subjects);
		modelAndView.addObject("activities", activities);
		modelAndView.addObject("suma", suma);
		
		modelAndView.setViewName("professorActivities");
		
		return modelAndView;
	}
	
	@RequestMapping(value="/professor/{yearId}/subjects/{courseId}/{subjectId}/students/{studentId}/activity/{activityId}", 
			method = RequestMethod.GET)
	public ModelAndView showCommentsProf(@PathVariable("yearId") int yearId, @PathVariable("courseId") int courseId, 
			@PathVariable("subjectId") int subjectId, @PathVariable("studentId") int studentId, 
			@PathVariable("activityId") int activityId){
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		
		AcademicYear year = academicYearService.findAcademicYearById(yearId);
		SubjectTemp subject = new SubjectTemp(subjectId, subjectService.findSubjectById(subjectId).getName(),
				subjectService.findSubjectById(subjectId).getEcts(), courseId, 
				courseService.findCourseById(courseId).getCourse());
		
		User student = userService.findById(studentId);
		Comment newComment = new Comment();
		Activity activity = activityService.findById(activityId);
		
		List<SubjectCourseAcademicYear> subjects1 = subjectCourseAcademicYearService.findByAcademicYear(yearId);
		List<ProfessorSubject> professors1 = professorSubjectService.findByProfessorId(user.getId());
		
		List<SubjectTemp> subjects = new ArrayList<SubjectTemp>();
		
		List<IsMentor> mentors = isMentorService.findByProfessorId(user.getId());
		List<IsMentor> mentors2 = new ArrayList<>();
		
		List<StudentSubject> studentsBySubject = 
				studentSubjectService.findBySubjectIdAndCourseIdAndAcademicYearId(subjectId, courseId, yearId);
		List<User> students = new ArrayList<>();
		
		List<Activity> activities = 
				activityService.findByYearIdAndCourseIdAndSubjectIdAndUserId(yearId, courseId, subjectId, studentId);
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
		
		for (IsMentor mentor : mentors) {
			for (StudentSubject stud : studentsBySubject) {
				if (mentor.getStudentId() == stud.getStudentId()) {
					students.add(userService.findById(mentor.getStudentId()));
				}
			}
		}
		
		for(SubjectCourseAcademicYear sub : subjects1) {
			for(ProfessorSubject prof : professors1) {
				if(sub.getSubjectId()==prof.getSubjectId() && sub.getCourseId()==prof.getCourseId() && 
						yearId==prof.getAcademicYearId()) {
					subjects.add(new SubjectTemp(sub.getSubjectId(), subjectService.findSubjectById(sub.getSubjectId()).getName(),
							subjectService.findSubjectById(sub.getSubjectId()).getEcts(), sub.getCourseId(), courseService.findCourseById(sub.getCourseId()).getCourse()));
	
				}
			}
		}
		
		modelAndView.addObject("students", students);
		modelAndView.addObject("student", student);
		modelAndView.addObject("year", year);
		modelAndView.addObject("subject", subject);
		modelAndView.addObject("subjects", subjects);
		modelAndView.addObject("activity", activity);
		modelAndView.addObject("activities", activities);
		modelAndView.addObject("newComment", newComment);
		modelAndView.addObject("comments", comments);
		modelAndView.addObject("suma", suma);
		
		modelAndView.setViewName("professorComments");
		
		return modelAndView;
	}
	
	@RequestMapping(value="/professor/{yearId}/subjects/{courseId}/{subjectId}/students/{studentId}/activity/{activityId}", 
			method = RequestMethod.POST)
	public ModelAndView addNewCommentProf(@PathVariable("yearId") int yearId, 
			@PathVariable("courseId") int courseId, 
			@PathVariable("subjectId") int subjectId,
			@PathVariable("studentId") int studentId,
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
		
		modelAndView.setViewName("redirect:/professor/" +yearId+ "/subjects/" +courseId+ "/" +subjectId+ "/students/" +studentId+ "/activity/" +activityId);
		return modelAndView;
	}

}
