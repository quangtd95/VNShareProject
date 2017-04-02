package td95.quang.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import td95.quang.domain.Post;

public interface PostService {
	Page<Post> findAll(Pageable pageable);
	List<Post> getHotPosts();
}
