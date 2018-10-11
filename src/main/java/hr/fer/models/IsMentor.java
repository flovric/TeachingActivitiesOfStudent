package hr.fer.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity @IdClass(IsMentorId.class)
@Table
public class IsMentor implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column
	int professorId;
	
	@Id
	@Column
	int studentId;

	public IsMentor(int professorId, int studentId) {
		super();
		this.professorId = professorId;
		this.studentId = studentId;
	}

	public IsMentor() {
		super();
	}

	public int getProfessorId() {
		return professorId;
	}

	public void setProfessorId(int professorId) {
		this.professorId = professorId;
	}

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}
	
	
}
