package td95.quang.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import td95.quang.domain.Post;

public interface PostRepository  extends JpaRepository<Post,Integer>{
	Page<Post> findAll(Pageable pageable);

	
}
