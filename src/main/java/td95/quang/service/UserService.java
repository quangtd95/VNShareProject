package td95.quang.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import td95.quang.entity.User;

public interface UserService {
	
	Page<User> findAll(Pageable pageable);

	User findOne(String email);

	User findOne(int id);

	void save(User user);
	
	List<User> getHotAuthors();

}
