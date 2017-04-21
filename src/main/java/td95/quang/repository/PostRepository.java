package td95.quang.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import td95.quang.entity.Post;

public interface PostRepository  extends JpaRepository<Post,Integer>{
	Page<Post> findAll(Pageable pageable);
	List<Post> findTop10ByOrderByCountViewsDesc();

	
}
