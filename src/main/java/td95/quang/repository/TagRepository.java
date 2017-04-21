package td95.quang.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import td95.quang.entity.Tag;

public interface TagRepository extends JpaRepository<Tag, Integer>{
	Page<Tag> findAll(Pageable pageable);
	List<Tag> findByNameContaining(String query);
	Tag findByName(String name);
	Tag findById(int id);
	
}
