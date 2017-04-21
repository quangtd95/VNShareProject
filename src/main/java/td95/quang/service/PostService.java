package td95.quang.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import td95.quang.entity.Post;
import td95.quang.entity.Vote;

public interface PostService {
	Page<Post> findAll(Pageable pageable);
	List<Post> getHotPosts();
	Post findById(int id);
	void save(Post post);
	void delete(int id);
	Vote findVote(int userId,int postId);
	void save(Vote vote);
	
}
