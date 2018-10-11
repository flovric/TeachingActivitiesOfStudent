package hr.fer.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity @IdClass(SubjectCourseAcademicYearId.class)
@Table
public class SubjectCourseAcademicYear implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column
	private long subjectId;
	
	@Id
	@Column
	private long courseId;
	
	@Id
	@Column
	private long academicYearId;

	public SubjectCourseAcademicYear(long subjectId, long courseId, long academicYearId) {
		super();
		this.subjectId = subjectId;
		this.courseId = courseId;
		this.academicYearId = academicYearId;
	}
	
	
	
	public SubjectCourseAcademicYear() {
		super();
	}



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

	public long getAcademicYearId() {
		return academicYearId;
	}

	public void setAcademicYearId(long academicYearId) {
		this.academicYearId = academicYearId;
	}

	
}
