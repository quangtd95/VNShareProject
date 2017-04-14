package td95.quang.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import td95.quang.domain.Post;
import td95.quang.repository.PostRepository;
import td95.quang.repository.PostRepositoryCustom;

@Service
public class PostServiceImpl implements PostService {

	private static final int PAGE_SIZE = 20;
	
	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private PostRepositoryCustom postRepositoryCustom;

	@Override
	public Page<Post> findAll(Pageable pageable) {
		PageRequest request = new PageRequest((pageable.getPageNumber() == 0) ? 0 : pageable.getPageNumber() - 1,
				PAGE_SIZE,new Sort(Sort.Direction.DESC, "id"));
		return postRepository.findAll(request);
		
	}

	@Override
	public List<Post> getHotPosts() {
		return postRepositoryCustom.getHotPosts();
	}

	@Override
	public Post findById(int id) {
		return postRepository.findOne(id);
	}

	@Override
	public void save(Post post) {
		postRepository.save(post);
		
	}



}
