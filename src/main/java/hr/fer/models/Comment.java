package hr.fer.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Represents a model of the user's content (that being a comment or a note).
 * 
 * @author Filip Lovric
 */

@Entity
@Table
public class Comment {

	/**
	 * Comment id.
	 */
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private int id;

	/**
	 * Autor's id.
	 */
	@Column
	private int authorId;

	/**
	 * Comment creation time.
	 */
	@Column
	private Date creationTime;

	/**
	 * Id of the comments subject.
	 */
	
	@Column
	private int subjectId;
	
	/**
	 * Id of the comments activity.
	 */
	@Column
	private int activityId;

	/**
	 * Comment text.
	 */
	@Column
	private String text;

	/**
	 * Instantiates the user comment.
	 *
	 * @param id content id
	 * @param autorId user's id
	 * @param creationTime comment creation time
	 * @param subjectId subject id
	 * @param text comment text
	 */
	public Comment(int id, int authorId, Date creationTime, int subjectId, int activityId,
			String text) {
		this.id = id;
		this.authorId = authorId;
		this.creationTime = creationTime;
		this.subjectId = subjectId;
		this.activityId = activityId;
		this.text = text;
	}
	
	public Comment() {
		
	}

	public int getActivityId() {
		return activityId;
	}

	public void setActivityId(int activityId) {
		this.activityId = activityId;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setAuthorId(int authorId) {
		this.authorId = authorId;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getId() {
		return id;
	}

	public int getAuthorId() {
		return authorId;
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public int getSubjectId() {
		return subjectId;
	}

	public String getText() {
		return text;
	}
	
	
}