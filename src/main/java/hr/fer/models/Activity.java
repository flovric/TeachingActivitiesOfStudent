package hr.fer.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Activity {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column
    private long id;
	
	@Column
	private long yearId;
	
	@Column
	private long subjectId;
	
	@Column
	private long courseId;
	
	@Column
	private long userId;
	
	@Column
	private String text;
	
	/**
	 * Duration of activity in hours.
	 */
	
	@Column
	private int duration;
	
	@Column
	private Date creationTime;

	public Activity() {
	}

	public Activity(int id, long yearId, long subjectId, long courseId, long userId, String text, int duration, Date creationTime) {
		super();
		this.id = id;
		this.subjectId = subjectId;
		this.userId = userId;
		this.text = text;
		this.duration = duration;
		this.creationTime = creationTime;
		this.yearId = yearId;
		this.courseId = courseId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(long subjectId) {
		this.subjectId = subjectId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	public long getYearId() {
		return yearId;
	}

	public void setYearId(long yearId) {
		this.yearId = yearId;
	}

	public long getCourseId() {
		return courseId;
	}

	public void setCourseId(long courseId) {
		this.courseId = courseId;
	}
	

}
