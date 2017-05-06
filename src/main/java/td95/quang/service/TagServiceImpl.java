package td95.quang.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import td95.quang.entity.Tag;
import td95.quang.repository.TagRepository;

@Service
public class TagServiceImpl implements TagService {
	private static final int PAGE_SIZE = 20;

	@Autowired
	private TagRepository tagRepository;

	@Override
	public Page<Tag> findAll(Pageable pageable) {
		PageRequest request = new PageRequest((pageable.getPageNumber() == 0) ? 0 : pageable.getPageNumber() - 1,
				PAGE_SIZE);
		return tagRepository.findAll(request);
	}

	@Override
	public String searchTag(String query) {
		if (!query.isEmpty() && !query.equals(" ")) {
			try {
				Query q = new ObjectMapper().readValue(query, Query.class);
				List<Tag> tags = tagRepository.findByNameContaining(q.query);
				ObjectMapper mapper = new ObjectMapper();
				return mapper.writeValueAsString(tags);
			} catch(Exception e){
				e.printStackTrace();
			}
					
		}
		return "";
	}
	
	@Override
	public Tag findByNameInIgnoreCase(String name) {
		return tagRepository.findByNameInIgnoreCase(name);
	}
	
	@Override
	public void save(Tag tag) {
		tagRepository.save(tag);
	}
	
	@Override
	public Tag findById(int id) {
		return tagRepository.findById(id);
	}

}

class Query {
	public String query;
}
