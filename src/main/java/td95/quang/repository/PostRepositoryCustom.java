package td95.quang.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import td95.quang.domain.Post;

public interface PostRepositoryCustom {
	List<Post> getHotPosts(); 
}
