package td95.quang.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import td95.quang.entity.Post;
import td95.quang.entity.User;

public interface UserRepository extends CrudRepository<User, Integer> {
	User findByEmail(String email);

	Page<User> findByOrderByReputationDesc(Pageable pageable);
	
	List<User> findTop10ByOrderByReputationDesc();
}
