package td95.quang.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import td95.quang.domain.Tag;
import td95.quang.repository.TagRepository;
@Service
public class TagServiceImpl implements TagService{
	private static final int PAGE_SIZE = 20;
	@Autowired
	private TagRepository tagRepository;
	@Override
	public Page<Tag> findAll(Pageable pageable) {
		PageRequest request = new PageRequest((pageable.getPageNumber() == 0) ? 0 : pageable.getPageNumber() - 1,
				PAGE_SIZE);
		return tagRepository.findAll(request);
	}
	
}
