package hr.fer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hr.fer.models.Comment;
import hr.fer.repository.CommentRepository;

@Service
public class CommentService {

	@Autowired
	CommentRepository commentRepository;
	
	public void saveComment(Comment comment) {
		commentRepository.save(comment);
	}
	
	public List<Comment> findByActivityId (int activityId) {
		return commentRepository.findByActivityId(activityId);
	}

	public void deleteComment(int id) {
		commentRepository.delete(id);
		
	}

	public Comment findById(int commentId) {
		
		return commentRepository.findOne(commentId);
	}
}
