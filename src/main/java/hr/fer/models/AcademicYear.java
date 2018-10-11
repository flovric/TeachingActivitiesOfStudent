package hr.fer.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class AcademicYear {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private long id;
	
	@Column
	private long academicYear;
	
	@Column
	private String semester;

	
	
	public AcademicYear(long id, long academicYear, String semester) {
		super();
		this.id = id;
		this.academicYear = academicYear;
		this.semester = semester;
	}

	public AcademicYear() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(long academicYear) {
		this.academicYear = academicYear;
	}

	public String getSemester() {
		return semester;
	}

	public void setSemester(String semester) {
		this.semester = semester;
	}
	
	
}
