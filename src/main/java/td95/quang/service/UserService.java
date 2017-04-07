package td95.quang.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import td95.quang.domain.User;

public interface UserService {
	Iterable<User> findAll();
	
	Page<User> findAll(Pageable pageable);

	User findOne(String email);

	User findOne(int id);

	void save(User user);
	
	List<User> getHotAuthors();
	
	void createPasswordResetTokenForUser(User user,String token);

}
