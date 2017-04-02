package td95.quang.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import td95.quang.domain.User;
import td95.quang.repository.UserRepository;
import td95.quang.repository.UserRepositoryCustom;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserRepositoryCustom userRepositoryCustom;

	@Override
	public Iterable<User> findAll() {
		return userRepository.findAll();
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
		return userRepositoryCustom.getHotAuthors();
	}

	@Override
	public Page<User> findAll(Pageable pageable) {
		return userRepository.findAll(pageable);
	}

}
