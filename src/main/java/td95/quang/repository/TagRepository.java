package td95.quang.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import td95.quang.domain.Tag;

public interface TagRepository extends CrudRepository<Tag, Integer>{
	Page<Tag> findAll(Pageable pageable);
	
}
