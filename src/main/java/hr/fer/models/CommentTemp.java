package hr.fer.models;

import java.util.Date;


/**
 * Represents a model of the user's content (that being a comment or a note).
 * 
 * @author Filip Lovric
 */


public class CommentTemp {

	/**
	 * Comment id.
	 */
	

	private int id;

	/**
	 * Autor's id.
	 */

	private String author;

	/**
	 * Comment creation time.
	 */

	private Date creationTime;

	/**
	 * Id of the comments subject.
	 */
	

	private int subjectId;
	
	/**
	 * Id of the comments activity.
	 */

	private int activityId;

	/**
	 * Comment text.
	 */

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
	public CommentTemp(int id, String author, Date creationTime, int subjectId, int activityId,
			String text) {
		this.id = id;
		this.author = author;
		this.creationTime = creationTime;
		this.subjectId = subjectId;
		this.activityId = activityId;
		this.text = text;
	}
	
	public CommentTemp() {
		
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

	public void setAuthor(String author) {
		this.author = author;
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

	public String getAuthor() {
		return author;
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