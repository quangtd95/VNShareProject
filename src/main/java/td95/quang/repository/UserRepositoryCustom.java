package td95.quang.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import td95.quang.domain.User;

public interface UserRepositoryCustom {
	List<User> getHotAuthors();
}
