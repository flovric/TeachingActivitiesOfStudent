package hr.fer.models;


public class SubjectTemp {

	
	private long subjectId;
	
	private String name;
	
	private int ects;
	
	private long courseId;
	
	private String course;
	

	public SubjectTemp(long subjectId, String name, int ects, long courseId, String course) {
		super();
		this.subjectId = subjectId;
		this.name = name;
		this.ects = ects;
		this.courseId= courseId;
		this.course = course;
	}
	
	public SubjectTemp() {
	}

	public long getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(long subjectId) {
		this.subjectId = subjectId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getEcts() {
		return ects;
	}

	public void setEcts(int ects) {
		this.ects = ects;
	}

	public long getCourseId() {
		return courseId;
	}

	public void setCourseId(long courseId) {
		this.courseId = courseId;
	}

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}
	
}
