package hr.fer.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import hr.fer.models.AcademicYear;
import hr.fer.models.Activity;
import hr.fer.models.IsMentor;
import hr.fer.models.ProfessorSubject;
import hr.fer.models.StudentSubject;
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
import hr.fer.view.ExcelReportView;
 

 
@Controller
public class ExportController {
	
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
	
    @RequestMapping(value="/professor/{yearId}/subjects/{courseId}/{subjectId}/students/{studentId}/export", method = RequestMethod.GET)
	public ModelAndView getExcel(@PathVariable("yearId") long yearId, @PathVariable("courseId") long courseId, 
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
		
		modelAndView.setView(new ExcelReportView());
		
		return modelAndView;
		
    }
}
