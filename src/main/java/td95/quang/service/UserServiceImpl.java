package td95.quang.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import td95.quang.entity.User;
import td95.quang.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	private static final int PAGE_SIZE = 20;
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public Page<User> findAll(Pageable pageable) {
		PageRequest request = new PageRequest((pageable.getPageNumber() == 0) ? 0 : pageable.getPageNumber() - 1,
				PAGE_SIZE);
		return userRepository.findByOrderByReputationDesc(request);
	}

	@Override
	public User findOne(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public User findOne(int id) {
		return userRepository.findOne(id);
	}

	@Override
	public void save(User user) {
		userRepository.save(user);
	}

	@Override
	public List<User> getHotAuthors() {
		return userRepository.findTop10ByOrderByReputationDesc();
	}

}
