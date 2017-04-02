package td95.quang.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import td95.quang.domain.Tag;


public interface TagService {
	Page<Tag> findAll(Pageable pageable);
}
