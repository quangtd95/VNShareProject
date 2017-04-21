package td95.quang.service;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import td95.quang.entity.Tag;


public interface TagService {
	Page<Tag> findAll(Pageable pageable);
	String searchTag(String query);
	Tag findByName(String name);
	void save(Tag tag);
	Tag findById(int id);
}
