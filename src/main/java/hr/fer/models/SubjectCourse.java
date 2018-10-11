package hr.fer.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity @IdClass(SubjectCourseId.class)
@Table
public class SubjectCourse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column
	long subjectId;
	
	@Id
	@Column
	long courseId;

	public long getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(long subjectId) {
		this.subjectId = subjectId;
	}

	public long getCourseId() {
		return courseId;
	}

	public void setCourseId(long courseId) {
		this.courseId = courseId;
	}

	public SubjectCourse(long subjectId, long courseId) {
		super();
		this.subjectId = subjectId;
		this.courseId = courseId;
	}

	public SubjectCourse() {
		super();
	}
	
	
}

	
	

