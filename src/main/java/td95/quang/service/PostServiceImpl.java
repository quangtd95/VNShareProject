package td95.quang.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import td95.quang.entity.Post;
import td95.quang.entity.Vote;
import td95.quang.repository.PostRepository;
import td95.quang.repository.VoteRepository;

@Service
public class PostServiceImpl implements PostService {

	private static final int PAGE_SIZE = 20;
	
	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private VoteRepository voteRepository;
	

	@Override
	public Page<Post> findAll(Pageable pageable) {
		PageRequest request = new PageRequest((pageable.getPageNumber() == 0) ? 0 : pageable.getPageNumber() - 1,
				PAGE_SIZE,new Sort(Sort.Direction.DESC, "id"));
		return postRepository.findAll(request);
		
	}

	@Override
	public List<Post> getHotPosts() {
		//return postRepositoryCustom.getHotPosts();
		return postRepository.findTop10ByOrderByCountViewsDesc();
	}

	@Override
	public Post findById(int id) {
		return postRepository.findOne(id);
	}

	@Override
	public void save(Post post) {
		postRepository.save(post);
		
	}

	@Override
	public Vote findVote(int userId, int postId) {
		return voteRepository.findTopByUserIdAndPostId(userId, postId);
	}

	@Override
	public void save(Vote vote) {
		voteRepository.save(vote);
	}

	@Override
	public void delete(int id) {
		postRepository.delete(id);
	}




}
