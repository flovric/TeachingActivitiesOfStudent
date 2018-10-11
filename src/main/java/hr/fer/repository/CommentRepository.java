package hr.fer.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import hr.fer.models.Comment;

public interface CommentRepository extends CrudRepository<Comment, Integer> {

	List<Comment> findByActivityId (int activityId);
}
