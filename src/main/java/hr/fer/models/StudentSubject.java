package hr.fer.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity @IdClass(StudentSubjectId.class)
@Table
public class StudentSubject implements Serializable {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column
	private long studentId;
	
	@Id
	@Column
	private long subjectId;
	
	@Id
	@Column
	private long courseId;
	
	@Id
	@Column
	private long academicYearId;

	public StudentSubject(long studentId, long subjectId, long courseId, long academicYearId) {
		super();
		this.studentId = studentId;
		this.subjectId = subjectId;
		this.courseId = courseId;
		this.academicYearId = academicYearId;
	}
	public StudentSubject() {
		
	}

	public long getStudentId() {
		return studentId;
	}

	public void setStudentId(long studentId) {
		this.studentId = studentId;
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
