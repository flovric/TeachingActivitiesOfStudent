package hr.fer.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import hr.fer.models.AcademicYear;
import hr.fer.models.Course;
import hr.fer.models.IsMentor;
import hr.fer.models.ProfessorSubject;
import hr.fer.models.Role;
import hr.fer.models.StudentSubject;
import hr.fer.models.Subject;
import hr.fer.models.SubjectCourse;
import hr.fer.models.SubjectCourseAcademicYear;
import hr.fer.models.SubjectTemp;
import hr.fer.models.User;
import hr.fer.service.AcademicYearService;
import hr.fer.service.CourseService;
import hr.fer.service.IsMentorService;
import hr.fer.service.ProfessorSubjectService;
import hr.fer.service.RoleService;
import hr.fer.service.StudentSubjectService;
import hr.fer.service.SubjectCourseAcademicYearService;
import hr.fer.service.SubjectCourseService;
import hr.fer.service.SubjectService;
import hr.fer.service.UserService;

@Controller
public class AdminController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	SubjectService subjectService;
	
	@Autowired
	AcademicYearService academicYearService;
	
	@Autowired
	SubjectCourseService subjectCourseService;
	
	@Autowired
	CourseService courseService;
	
	@Autowired
	SubjectCourseAcademicYearService subjectCourseAcademicYearService;
	
	@Autowired
	ProfessorSubjectService professorSubjectService;
	
	@Autowired
	StudentSubjectService studentSubjectService;
	
	@Autowired
	RoleService roleService;
	
	@Autowired
	IsMentorService isMentorService;
	
	//Save the uploaded file to this folder
    private static String UPLOADED_FOLDER = "C://ZavrsniRad/EvidencijaNastavnihAktivnosti/files/";

	@RequestMapping(value="/admin", method = RequestMethod.GET)
	public @ResponseBody ModelAndView adminHomeScreen() {
		
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		
		List<AcademicYear> years = academicYearService.findAll();
		AcademicYear year1 = new AcademicYear();
		
		List<SubjectCourseAcademicYear> subjectsByYear = subjectCourseAcademicYearService.findAll();
		List<Course> courses = courseService.findAll();
		List<Subject> subjects = subjectService.findAll();
		
	
		modelAndView.addObject("courses", courses);
		modelAndView.addObject("subjects", subjects);
		modelAndView.addObject("subjectsByYear", subjectsByYear);
		modelAndView.addObject("year1", year1);
		modelAndView.addObject("years", years);
		modelAndView.setViewName("adminHome");
		return modelAndView;
	}
	
	@RequestMapping(value = "/admin")
    @ResponseBody
    public List<SubjectCourseAcademicYear> getRequestedSubjects(@RequestParam long yearId) {
		List<SubjectCourseAcademicYear> subjectsByYear = subjectCourseAcademicYearService.findAll();
		return subjectsByYear;
}
	
	@RequestMapping(value="/admin/subjects", method = RequestMethod.POST)
	public @ResponseBody ModelAndView subjectOperations(@RequestParam("id") long id, 
			@RequestParam("delete") int delete, @RequestParam("edit") int edit, 
			@RequestParam("add") int add, Subject newSubject) {
		
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		
		if(delete==1) {
			subjectService.deleteSubject(id);
			subjectCourseService.deleteSubjectCourseBySubjectId(id);
		}
		
		if(add==1) {
			Subject subjectSave = new Subject();
			
			
			subjectSave.setEcts(newSubject.getEcts());
			subjectSave.setName(newSubject.getName());
			
			subjectService.saveSubject(subjectSave);
			
		}
		
		if(edit==1) {
			modelAndView.setViewName("redirect:/admin/subjects/" + id);
			return modelAndView;
		}
		
		
		

		modelAndView.setViewName("redirect:/admin/subjects");
		return modelAndView;
	}
	
	@RequestMapping(value="/admin/subjects", method = RequestMethod.GET)
	public ModelAndView adminHome(){
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		
		Subject newSubject = new Subject();
		List<Subject> subjects = subjectService.findAll();
		List<Course> courses = courseService.findAll();
		List<SubjectCourse> subjectCourses = subjectCourseService.findAll();
		
		modelAndView.addObject("user", user);
		modelAndView.addObject("subjects", subjects);
		modelAndView.addObject("courses", courses);
		modelAndView.addObject("newSubject", newSubject);
		modelAndView.setViewName("admin");
		
		return modelAndView;
	}
	
	
	@RequestMapping(value="/admin/subjects/{id}", method = RequestMethod.GET)
	public ModelAndView adminEditSubject(@PathVariable("id") long id){
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		
		Subject subject = subjectService.findSubjectById(id);
		Subject newSubject = new Subject();
		
		modelAndView.addObject("subject", subject);
		modelAndView.addObject("newSubject", newSubject);
		modelAndView.setViewName("admin2");
		
		return modelAndView;
	}
	
	@RequestMapping(value="/admin/subjects/{id}", method = RequestMethod.POST)
	public ModelAndView adminEditSubjectPost(@PathVariable("id") long id, Subject newSubject){
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		
		Subject subject = subjectService.findSubjectById(id);
		
		subject.setEcts(newSubject.getEcts());
		subject.setName(newSubject.getName());
		
		subjectService.saveSubject(subject);
		
		modelAndView.setViewName("redirect:/admin/subjects");
		return modelAndView;
	}
	
	@RequestMapping(value="/admin/years", method = RequestMethod.GET)
	public ModelAndView adminAcademicYear(){
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		
		AcademicYear newAcademicYear = new AcademicYear();
		List<AcademicYear> years = academicYearService.findAll();
		
		modelAndView.addObject("user", user);
		modelAndView.addObject("years", years);
		modelAndView.addObject("newAcademicYear", newAcademicYear);
		modelAndView.setViewName("admin3");
		
		return modelAndView;
	}
	@RequestMapping(value="/admin/years", method = RequestMethod.POST)
	public @ResponseBody ModelAndView academicYearOperations(@RequestParam("id") long id, 
			@RequestParam("delete") int delete, @RequestParam("edit") int edit, 
			@RequestParam("add") int add, @RequestParam("assign") int assign, AcademicYear newAcademicYear) {
		
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		
		if(delete==1) {
			academicYearService.deleteAcademicYear(id);
		}
		
		if(add==1) {
			AcademicYear academicYearSave = new AcademicYear();
			academicYearSave.setAcademicYear(newAcademicYear.getAcademicYear());
			academicYearSave.setSemester(newAcademicYear.getSemester());
			
			academicYearService.saveAcademicYear(academicYearSave);
		}
		
		if(edit==1) {
			modelAndView.setViewName("redirect:/admin/years/" + id);
			return modelAndView;
		}
		
		if(assign==1) {
			modelAndView.setViewName("redirect:/admin/years/" + id);
			return modelAndView;
		}
		

		modelAndView.setViewName("redirect:/admin/years");
		return modelAndView;
	}
	
	@RequestMapping(value="/admin/years/{id}", method = RequestMethod.GET)
	public ModelAndView adminEditAcademicYear(@PathVariable("id") long id){
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		
		AcademicYear academicYear = academicYearService.findAcademicYearById(id);
		AcademicYear newAcademicYear = new AcademicYear();
		
		String sem;
		
		if(academicYear.getSemester().equals("ZIMSKI"))
			sem="LJETNI";
		else 
			sem="ZIMSKI";
			
		
		modelAndView.addObject("academicYear", academicYear);
		modelAndView.addObject("sem", sem);
		modelAndView.addObject("newAcademicYear", newAcademicYear);
		
		modelAndView.setViewName("admin4");
		
		return modelAndView;
	}
	
	@RequestMapping(value="/admin/years/{id}", method = RequestMethod.POST)
	public ModelAndView adminEditAcademicYearPost(@PathVariable("id") long id, AcademicYear newAcademicYear){
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		
		AcademicYear academicYear = academicYearService.findAcademicYearById(id);
		academicYear.setAcademicYear(newAcademicYear.getAcademicYear());
		academicYear.setSemester(newAcademicYear.getSemester());
		
		academicYearService.saveAcademicYear(academicYear);
		
		modelAndView.setViewName("redirect:/admin/years");
		return modelAndView;
	}
	
	@RequestMapping(value="/admin/year/{id}", method = RequestMethod.GET)
	public ModelAndView academicYearAddSubjects(@PathVariable("id") long id){
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		
		AcademicYear academicYear = academicYearService.findAcademicYearById(id);
		
		List<SubjectCourseAcademicYear> subjectsByYear = subjectCourseAcademicYearService.findByAcademicYear(id);
		List<SubjectCourse> subjectCourses = subjectCourseService.findAll();
		List<SubjectTemp> availableSubjects = new ArrayList<>();
		List<SubjectTemp> assignedSubjects = new ArrayList<>();
		
		int assigned;
		
		for(SubjectCourse sub : subjectCourses) {
			
			assigned = 0;
			
			for(SubjectCourseAcademicYear subYear : subjectsByYear) {
			
				if(sub.getSubjectId()==subYear.getSubjectId() && sub.getCourseId() == subYear.getCourseId()) {
					
					assignedSubjects.add(new SubjectTemp(sub.getSubjectId(), 
							subjectService.findSubjectById(sub.getSubjectId()).getName(),
							subjectService.findSubjectById(sub.getSubjectId()).getEcts(), sub.getCourseId(), 
							courseService.findCourseById(sub.getCourseId()).getCourse()));
					
					assigned = 1;
					break;
				}
			}
			
			if(assigned == 0) {
				availableSubjects.add(new SubjectTemp(sub.getSubjectId(), 
						subjectService.findSubjectById(sub.getSubjectId()).getName(),
						subjectService.findSubjectById(sub.getSubjectId()).getEcts(), 
						sub.getCourseId(), 
						courseService.findCourseById(sub.getCourseId()).getCourse()));
			}
		}
		
		modelAndView.addObject("academicYear", academicYear);
		modelAndView.addObject("assignedSubjects", assignedSubjects);
		modelAndView.addObject("availableSubjects", availableSubjects);
		modelAndView.setViewName("yearSubjects");
		
		return modelAndView;
	}
	
	@RequestMapping(value="/admin/year/{id}", method = RequestMethod.POST)
	public ModelAndView academicYearAddSubjectsAndRemove(
			@PathVariable("id") long id, 
			@RequestParam("assign") int assign,
			@RequestParam("delete") int delete, 
			@RequestParam("subjectId") long subjectId,
			@RequestParam("courseId") long courseId){
		
		ModelAndView modelAndView = new ModelAndView();
	
		if(assign == 1) {
			subjectCourseAcademicYearService.addNewEntry(new SubjectCourseAcademicYear(subjectId, courseId, id));
		}
		
		else if (delete == 1) {
			subjectCourseAcademicYearService.removeEntry(new SubjectCourseAcademicYear(subjectId, courseId, id));
		}
	
		modelAndView.setViewName("redirect:/admin/year/" + id);
		
		return modelAndView;
	}
	
	@RequestMapping(value="/admin/subject/{id}", method = RequestMethod.GET)
	public ModelAndView assignSubjectCourse(@PathVariable("id") long id){
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		
		List<Course> courses = courseService.findAll();
		List<SubjectCourse> subjectCourses = subjectCourseService.findBySubjectId(id);
		List<Course> availableCourses = new ArrayList<>();
		List<Course> assignedCourses = new ArrayList<>();
		Subject subject = subjectService.findSubjectById(id);
		
		
		int assigned;
		
		for(Course course : courses) {
			
			assigned = 0;
			
			for(SubjectCourse subCourse : subjectCourses) {
			
				if(course.getId() == subCourse.getCourseId()) {
					
					assignedCourses.add(course);
					
					assigned = 1;
					break;
				}
			}
			
			if(assigned == 0) {
				availableCourses.add(course);
			}
		}
		
		
		modelAndView.addObject("assignedCourses", assignedCourses);
		modelAndView.addObject("subject", subject);
		modelAndView.addObject("availableCourses", availableCourses);
		modelAndView.setViewName("subjectCourses");
		
		return modelAndView;
	}
	
	@RequestMapping(value="/admin/subject/{id}", method = RequestMethod.POST)
	public ModelAndView assignSubjectCourseAndRemove(@PathVariable("id") long subjectId,
			@RequestParam("assign") int assign,
			@RequestParam("delete") int delete, 
			@RequestParam("courseId") long courseId){
		
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		
		if(assign == 1) {
			subjectCourseService.addNewEntry(new SubjectCourse(subjectId, courseId));
		}
		
		else if (delete == 1) {
			subjectCourseService.removeEntry(new SubjectCourse(subjectId, courseId));
		}
	
		modelAndView.setViewName("redirect:/admin/subject/" + subjectId);
		
		return modelAndView;
	}
	
	@RequestMapping(value="/admin/professors", method = RequestMethod.GET)
	public ModelAndView adminEditProfessors(){
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		
		List<AcademicYear> years = academicYearService.findAll();
		AcademicYear year1 = new AcademicYear();
		
		List<SubjectCourseAcademicYear> subjectsByYear = subjectCourseAcademicYearService.findAll();
		List<Course> courses = courseService.findAll();
		List<Subject> subjects = subjectService.findAll();
		
	
		modelAndView.addObject("courses", courses);
		modelAndView.addObject("subjects", subjects);
		modelAndView.addObject("subjectsByYear", subjectsByYear);
		modelAndView.addObject("year1", year1);
		modelAndView.addObject("years", years);
		modelAndView.setViewName("adminProfessors");
		return modelAndView;
	}
	
	@RequestMapping(value="/admin/professors/{yearId}", method = RequestMethod.GET)
	public ModelAndView adminEditProfessors2(@PathVariable("yearId") int yearId){
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		
		AcademicYear academicYear = academicYearService.findAcademicYearById(yearId);
		
		List<SubjectCourseAcademicYear> subjectsByYear = subjectCourseAcademicYearService.findByAcademicYear(yearId);
		List<SubjectCourse> subjectCourses = subjectCourseService.findAll();
		List<SubjectTemp> availableSubjects = new ArrayList<>();
		List<SubjectTemp> assignedSubjects = new ArrayList<>();
		List<AcademicYear> years = academicYearService.findAll();
		
		int assigned;
		
		for(SubjectCourse sub : subjectCourses) {
			
			assigned = 0;
			
			for(SubjectCourseAcademicYear subYear : subjectsByYear) {
			
				if(sub.getSubjectId()==subYear.getSubjectId() && sub.getCourseId() == subYear.getCourseId()) {
					
					assignedSubjects.add(new SubjectTemp(sub.getSubjectId(), 
							subjectService.findSubjectById(sub.getSubjectId()).getName(),
							subjectService.findSubjectById(sub.getSubjectId()).getEcts(), sub.getCourseId(), 
							courseService.findCourseById(sub.getCourseId()).getCourse()));
					
					assigned = 1;
					break;
				}
			}
			
			if(assigned == 0) {
				availableSubjects.add(new SubjectTemp(sub.getSubjectId(), 
						subjectService.findSubjectById(sub.getSubjectId()).getName(),
						subjectService.findSubjectById(sub.getSubjectId()).getEcts(), 
						sub.getCourseId(), 
						courseService.findCourseById(sub.getCourseId()).getCourse()));
			}
		}
		
		years.remove(academicYear);
		
		modelAndView.addObject("academicYear", academicYear);
		modelAndView.addObject("subjects", assignedSubjects);
		modelAndView.addObject("years", years);
		modelAndView.addObject("year", academicYear);
		
		
		modelAndView.setViewName("adminProfessors2");
		
		return modelAndView;
	}
	
	@RequestMapping(value="/admin/professors/{yearId}/{subjectId}/{courseId}", method = RequestMethod.GET)
	public ModelAndView adminEditProfessors3(@PathVariable("yearId") int yearId, 
			@PathVariable("subjectId") int subjectId,
			@PathVariable("courseId") int courseId ){
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		
		//int courseId = Integer.parseInt(courseIdS);
		AcademicYear academicYear = academicYearService.findAcademicYearById(yearId);
		SubjectTemp subTemp = new SubjectTemp();
		
		
		List<SubjectCourseAcademicYear> subjectsByYear = subjectCourseAcademicYearService.findByAcademicYear(yearId);
		List<SubjectCourse> subjectCourses = subjectCourseService.findAll();
		List<SubjectTemp> availableSubjects = new ArrayList<>();
		List<SubjectTemp> assignedSubjects = new ArrayList<>();
		List<AcademicYear> years = academicYearService.findAll();
		
		
		int assigned;
		
		for(SubjectCourse sub : subjectCourses) {
			
			assigned = 0;
			
			for(SubjectCourseAcademicYear subYear : subjectsByYear) {
			
				if(sub.getSubjectId()==subYear.getSubjectId() && sub.getCourseId() == subYear.getCourseId()) {
					
					assignedSubjects.add(new SubjectTemp(sub.getSubjectId(), 
							subjectService.findSubjectById(sub.getSubjectId()).getName(),
							subjectService.findSubjectById(sub.getSubjectId()).getEcts(), 
							sub.getCourseId(), 
							courseService.findCourseById(sub.getCourseId()).getCourse()));
					
					assigned = 1;
					if(sub.getSubjectId()==(long)subjectId && sub.getCourseId()==(long)courseId) {
						subTemp.setSubjectId((long)subjectId);
						subTemp.setCourseId((long)courseId);
						subTemp.setCourse(courseService.findCourseById(sub.getCourseId()).getCourse());
						subTemp.setEcts(subjectService.findSubjectById(sub.getSubjectId()).getEcts());
						subTemp.setName(subjectService.findSubjectById(sub.getSubjectId()).getName());
						
					} 
						
					break;
				}
			}
			
			if(assigned == 0) {
				availableSubjects.add(new SubjectTemp(sub.getSubjectId(), 
						subjectService.findSubjectById(sub.getSubjectId()).getName(),
						subjectService.findSubjectById(sub.getSubjectId()).getEcts(), 
						sub.getCourseId(), 
						courseService.findCourseById(sub.getCourseId()).getCourse()));
			}
		}
		
		List<User> availableProfessors = new ArrayList<>();
		List<User> assignedProfessors = new ArrayList<>();
		List<ProfessorSubject> profSubs = professorSubjectService.findAll();
		Set<Role> roles = new HashSet<>();
		roles.add(roleService.findByRole("PROFESSOR"));
		
		List<User> allProfessors = userService.findByRoles(roles);
		
		int assignedProf;		
		for (User prof : allProfessors) {
			assignedProf=0;
			for(ProfessorSubject profSub : profSubs) {
				
				if(profSub.getAcademicYearId() == yearId && 
						profSub.getCourseId() == courseId &&
						profSub.getSubjectId() == subjectId &&
						profSub.getProfessorId() == prof.getId()) {
					
					assignedProfessors.add(userService.findById((int)profSub.getProfessorId()));
					
					assignedProf = 1;
					break;
				
				}
			}
				
				if(assignedProf == 0)
					availableProfessors.add(userService.findById((int)prof.getId()));
		}
		
		years.remove(academicYear);
		assignedSubjects.removeIf(p -> p.getSubjectId()==(long)subjectId && p.getCourseId()==(long)courseId);
		
		modelAndView.addObject("academicYear", academicYear);
		modelAndView.addObject("subjects", assignedSubjects);
		modelAndView.addObject("subject", subTemp);
		modelAndView.addObject("years", years);
		modelAndView.addObject("year", academicYear);
		modelAndView.addObject("assignedProfessors", assignedProfessors);
		modelAndView.addObject("availableProfessors", availableProfessors);
		
		
		modelAndView.setViewName("adminProfessors3");
		
		return modelAndView;
	}
	
	@RequestMapping(value="/admin/professors/{yearId}/{subjectId}/{courseId}", method = RequestMethod.POST)
	public ModelAndView adminAssignProfessors(@PathVariable("yearId") int yearId, 
			@PathVariable("subjectId") int subjectId,
			@PathVariable("courseId") int courseId,
			@RequestParam("assign") int assign,
			@RequestParam("delete") int delete,
			@RequestParam("professorId") int professorId){
		
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		
		if(assign == 1) {
			professorSubjectService.addNewEntry(new ProfessorSubject(professorId, subjectId, courseId, yearId));
		}
		
		else if (delete == 1) {
			professorSubjectService.removeEntry(new ProfessorSubject(professorId, subjectId, courseId, yearId));
		}
	
		modelAndView.setViewName("redirect:/admin/professors/" +yearId+ "/" +subjectId+ "/" +courseId);
		
		return modelAndView;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	@RequestMapping(value="/admin/students", method = RequestMethod.GET)
	public ModelAndView adminEditStudents(){
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		
		List<AcademicYear> years = academicYearService.findAll();
		AcademicYear year1 = new AcademicYear();
		
		List<SubjectCourseAcademicYear> subjectsByYear = subjectCourseAcademicYearService.findAll();
		List<Course> courses = courseService.findAll();
		List<Subject> subjects = subjectService.findAll();
		
	
		modelAndView.addObject("courses", courses);
		modelAndView.addObject("subjects", subjects);
		modelAndView.addObject("subjectsByYear", subjectsByYear);
		modelAndView.addObject("year1", year1);
		modelAndView.addObject("years", years);
		modelAndView.setViewName("adminStudents");
		return modelAndView;
	}
	
	@RequestMapping(value="/admin/students/{yearId}", method = RequestMethod.GET)
	public ModelAndView adminEditStudents2(@PathVariable("yearId") int yearId){
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		
		AcademicYear academicYear = academicYearService.findAcademicYearById(yearId);
		
		List<SubjectCourseAcademicYear> subjectsByYear = subjectCourseAcademicYearService.findByAcademicYear(yearId);
		List<SubjectCourse> subjectCourses = subjectCourseService.findAll();
		List<SubjectTemp> availableSubjects = new ArrayList<>();
		List<SubjectTemp> assignedSubjects = new ArrayList<>();
		List<AcademicYear> years = academicYearService.findAll();
		
		int assigned;
		
		for(SubjectCourse sub : subjectCourses) {
			
			assigned = 0;
			
			for(SubjectCourseAcademicYear subYear : subjectsByYear) {
			
				if(sub.getSubjectId()==subYear.getSubjectId() && sub.getCourseId() == subYear.getCourseId()) {
					
					assignedSubjects.add(new SubjectTemp(sub.getSubjectId(), 
							subjectService.findSubjectById(sub.getSubjectId()).getName(),
							subjectService.findSubjectById(sub.getSubjectId()).getEcts(), sub.getCourseId(), 
							courseService.findCourseById(sub.getCourseId()).getCourse()));
					
					assigned = 1;
					break;
				}
			}
			
			if(assigned == 0) {
				availableSubjects.add(new SubjectTemp(sub.getSubjectId(), 
						subjectService.findSubjectById(sub.getSubjectId()).getName(),
						subjectService.findSubjectById(sub.getSubjectId()).getEcts(), 
						sub.getCourseId(), 
						courseService.findCourseById(sub.getCourseId()).getCourse()));
			}
		}
		
		years.remove(academicYear);
		
		modelAndView.addObject("academicYear", academicYear);
		modelAndView.addObject("subjects", assignedSubjects);
		modelAndView.addObject("years", years);
		modelAndView.addObject("year", academicYear);
		
		
		modelAndView.setViewName("adminStudents2");
		
		return modelAndView;
	}
	
	@RequestMapping(value="/admin/students/{yearId}/{subjectId}/{courseId}", method = RequestMethod.GET)
	public ModelAndView adminEditStudents3(@PathVariable("yearId") int yearId, 
			@PathVariable("subjectId") int subjectId,
			@PathVariable("courseId") int courseId ){
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		
		AcademicYear academicYear = academicYearService.findAcademicYearById(yearId);
		SubjectTemp subTemp = new SubjectTemp();
		
		
		List<SubjectCourseAcademicYear> subjectsByYear = subjectCourseAcademicYearService.findByAcademicYear(yearId);
		List<SubjectCourse> subjectCourses = subjectCourseService.findAll();
		List<SubjectTemp> availableSubjects = new ArrayList<>();
		List<SubjectTemp> assignedSubjects = new ArrayList<>();
		List<AcademicYear> years = academicYearService.findAll();
		
		
		int assigned;
		
		for(SubjectCourse sub : subjectCourses) {
			
			assigned = 0;
			
			for(SubjectCourseAcademicYear subYear : subjectsByYear) {
			
				if(sub.getSubjectId()==subYear.getSubjectId() && sub.getCourseId() == subYear.getCourseId()) {
					
					assignedSubjects.add(new SubjectTemp(sub.getSubjectId(), 
							subjectService.findSubjectById(sub.getSubjectId()).getName(),
							subjectService.findSubjectById(sub.getSubjectId()).getEcts(), 
							sub.getCourseId(), 
							courseService.findCourseById(sub.getCourseId()).getCourse()));
					
					assigned = 1;
					if(sub.getSubjectId()==(long)subjectId && sub.getCourseId()==(long)courseId) {
						subTemp.setSubjectId((long)subjectId);
						subTemp.setCourseId((long)courseId);
						subTemp.setCourse(courseService.findCourseById(sub.getCourseId()).getCourse());
						subTemp.setEcts(subjectService.findSubjectById(sub.getSubjectId()).getEcts());
						subTemp.setName(subjectService.findSubjectById(sub.getSubjectId()).getName());
						
					} 
						
					break;
				}
			}
			
			if(assigned == 0) {
				availableSubjects.add(new SubjectTemp(sub.getSubjectId(), 
						subjectService.findSubjectById(sub.getSubjectId()).getName(),
						subjectService.findSubjectById(sub.getSubjectId()).getEcts(), 
						sub.getCourseId(), 
						courseService.findCourseById(sub.getCourseId()).getCourse()));
			}
		}
		
		List<User> availableStudents = new ArrayList<>();
		List<User> assignedStudents = new ArrayList<>();
		List<StudentSubject> studSubs = studentSubjectService.findAll();
		Set<Role> roles = new HashSet<>();
		roles.add(roleService.findByRole("STUDENT"));
		
		List<User> allStudents = userService.findByRoles(roles);
		
		int assignedStud;		
		for (User stud : allStudents) {
			assignedStud=0;
			for(StudentSubject studSub : studSubs) {
				
				if(studSub.getAcademicYearId() == yearId && 
						studSub.getCourseId() == courseId &&
						studSub.getSubjectId() == subjectId &&
						studSub.getStudentId() == stud.getId()) {
					
					assignedStudents.add(userService.findById((int)studSub.getStudentId()));
					
					assignedStud = 1;
					break;
				
				}
			}
				
				if(assignedStud == 0)
					availableStudents.add(userService.findById((int)stud.getId()));
		}
		
		years.remove(academicYear);
		assignedSubjects.removeIf(p -> p.getSubjectId()==(long)subjectId && p.getCourseId()==(long)courseId);
		
		modelAndView.addObject("academicYear", academicYear);
		modelAndView.addObject("subjects", assignedSubjects);
		modelAndView.addObject("subject", subTemp);
		modelAndView.addObject("years", years);
		modelAndView.addObject("year", academicYear);
		modelAndView.addObject("assignedStudents", assignedStudents);
		modelAndView.addObject("availableStudents", availableStudents);
		
		
		modelAndView.setViewName("adminStudents3");
		
		return modelAndView;
	}
	
	@RequestMapping(value="/admin/students/{yearId}/{subjectId}/{courseId}", method = RequestMethod.POST)
	public ModelAndView adminAssignStudents(@PathVariable("yearId") int yearId, 
			@PathVariable("subjectId") int subjectId,
			@PathVariable("courseId") int courseId,
			@RequestParam("assign") int assign,
			@RequestParam("delete") int delete,
			@RequestParam("studentId") int studentId){
		
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		
		if(assign == 1) {
			studentSubjectService.addNewEntry(new StudentSubject(studentId, subjectId, courseId, yearId));
		}
		
		else if (delete == 1) {
			studentSubjectService.removeEntry(new StudentSubject(studentId, subjectId, courseId, yearId));
		}
	
		modelAndView.setViewName("redirect:/admin/students/" +yearId+ "/" +subjectId+ "/" +courseId);
		
		return modelAndView;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@RequestMapping(value="/admin/students/{yearId}/{subjectId}/{courseId}/upload", method = RequestMethod.POST)
	public ModelAndView adminAssignStudentsByFile(@PathVariable("yearId") int yearId, 
			@PathVariable("subjectId") int subjectId,
			@PathVariable("courseId") int courseId,
			@RequestParam("myFile") MultipartFile myFile) throws IOException{
		
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		
		AcademicYear academicYear = academicYearService.findAcademicYearById(yearId);
		SubjectTemp subTemp = new SubjectTemp();
		
		
		List<SubjectCourseAcademicYear> subjectsByYear = subjectCourseAcademicYearService.findByAcademicYear(yearId);
		List<SubjectCourse> subjectCourses = subjectCourseService.findAll();
		List<SubjectTemp> availableSubjects = new ArrayList<>();
		List<SubjectTemp> assignedSubjects = new ArrayList<>();
		List<AcademicYear> years = academicYearService.findAll();
		
		
		int assigned;
		
		for(SubjectCourse sub : subjectCourses) {
			
			assigned = 0;
			
			for(SubjectCourseAcademicYear subYear : subjectsByYear) {
			
				if(sub.getSubjectId()==subYear.getSubjectId() && sub.getCourseId() == subYear.getCourseId()) {
					
					assignedSubjects.add(new SubjectTemp(sub.getSubjectId(), 
							subjectService.findSubjectById(sub.getSubjectId()).getName(),
							subjectService.findSubjectById(sub.getSubjectId()).getEcts(), 
							sub.getCourseId(), 
							courseService.findCourseById(sub.getCourseId()).getCourse()));
					
					assigned = 1;
					if(sub.getSubjectId()==(long)subjectId && sub.getCourseId()==(long)courseId) {
						subTemp.setSubjectId((long)subjectId);
						subTemp.setCourseId((long)courseId);
						subTemp.setCourse(courseService.findCourseById(sub.getCourseId()).getCourse());
						subTemp.setEcts(subjectService.findSubjectById(sub.getSubjectId()).getEcts());
						subTemp.setName(subjectService.findSubjectById(sub.getSubjectId()).getName());
						
					} 
						
					break;
				}
			}
			
			if(assigned == 0) {
				availableSubjects.add(new SubjectTemp(sub.getSubjectId(), 
						subjectService.findSubjectById(sub.getSubjectId()).getName(),
						subjectService.findSubjectById(sub.getSubjectId()).getEcts(), 
						sub.getCourseId(), 
						courseService.findCourseById(sub.getCourseId()).getCourse()));
			}
		}
		
		List<User> availableStudents = new ArrayList<>();
		List<User> assignedStudents = new ArrayList<>();
		List<StudentSubject> studSubs = studentSubjectService.findAll();
		Set<Role> roles = new HashSet<>();
		roles.add(roleService.findByRole("STUDENT"));
		
		List<User> allStudents = userService.findByRoles(roles);
		
		int assignedStud;		
		for (User stud : allStudents) {
			assignedStud=0;
			for(StudentSubject studSub : studSubs) {
				
				if(studSub.getAcademicYearId() == yearId && 
						studSub.getCourseId() == courseId &&
						studSub.getSubjectId() == subjectId &&
						studSub.getStudentId() == stud.getId()) {
					
					assignedStudents.add(userService.findById((int)studSub.getStudentId()));
					
					assignedStud = 1;
					break;
				
				}
			}
				
				if(assignedStud == 0)
					availableStudents.add(userService.findById((int)stud.getId()));
		}
		
		try {

            // Get the file and save it somewhere
            byte[] bytes = myFile.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + "myFile");
            Files.write(path, bytes);

     
        } catch (IOException e) {
            e.printStackTrace();
        }
		
		FileReader input = new FileReader("files/myFile");
		BufferedReader bufRead = new BufferedReader(input);
		String myLine = null;
		
		int flagTxt = 0;

		while ( (myLine = bufRead.readLine()) != null) {
			flagTxt = 0;
			
			for(User studentT : assignedStudents) {
				if(myLine.equals(studentT.getJmbag()))
						flagTxt = 1;
						
			}
			if(flagTxt == 0) {
				User studentT = userService.findByJmbag(myLine);
				studentSubjectService.addNewEntry(new StudentSubject(studentT.getId(), subjectId, courseId, yearId));
			}
		}
		
		modelAndView.setViewName("redirect:/admin/students/" +yearId+ "/" +subjectId+ "/" +courseId);
		
		return modelAndView;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@RequestMapping(value="/admin/professors/mentor", method = RequestMethod.GET)
	public ModelAndView adminEditMentors(){
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		
		Set<Role> roles = new HashSet<>();
		roles.add(roleService.findByRole("PROFESSOR"));
		
		List<User> professors = userService.findByRoles(roles);
		
	
		modelAndView.addObject("professors", professors);
		
		
		modelAndView.setViewName("adminMentor");
		return modelAndView;
	}
	
	@RequestMapping(value="/admin/professors/mentor/{professorId}", method = RequestMethod.GET)
	public ModelAndView adminEditMentors2(@PathVariable("professorId") int professorId){
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		
		User prof = userService.findById(professorId);
		
		Set<Role> roles = new HashSet<>();
		roles.add(roleService.findByRole("PROFESSOR"));
		List<User> professors = userService.findByRoles(roles);
		
		Set<Role> roles2 = new HashSet<>();
		roles2.add(roleService.findByRole("STUDENT"));
		List<User> students = userService.findByRoles(roles2);
		
		List<IsMentor> mentors = isMentorService.findAll();
		List<User> availableStudents = new ArrayList<>();
		List<User> assignedStudents = new ArrayList<>();
		
		
		int assigned;
		
		for(User stud : students) {
			assigned=0;
			for (IsMentor mentor : mentors) {
				
				if(mentor.getProfessorId() == professorId && stud.getId() == mentor.getStudentId()) {
						
					assignedStudents.add(userService.findById(mentor.getStudentId()));
					
					assigned=1;
					break;
				}
			}
			
			if(assigned==0) {
				availableStudents.add(userService.findById(stud.getId()));
			}
		}
		
		professors.remove(prof);
		
		modelAndView.addObject("professors", professors);
		modelAndView.addObject("prof", prof);
		modelAndView.addObject("availableStudents", availableStudents);
		modelAndView.addObject("assignedStudents", assignedStudents);
		
		
		modelAndView.setViewName("adminMentor2");
		
		return modelAndView;
	}
	
	
	@RequestMapping(value="/admin/professors/mentor/{professorId}", method = RequestMethod.POST)
	public ModelAndView adminAssignProfessors(
			@PathVariable("professorId") int professorId,
			@RequestParam("assign") int assign,
			@RequestParam("delete") int delete,
			@RequestParam("studentId") int studentId){
		
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		
		if(assign == 1) {
			isMentorService.addNewEntry(new IsMentor(professorId, studentId));
		}
		
		else if (delete == 1) {
			isMentorService.removeEntry(new IsMentor(professorId, studentId));
		}
	
		modelAndView.setViewName("redirect:/admin/professors/mentor/" + professorId);
		
		return modelAndView;
	}
}
