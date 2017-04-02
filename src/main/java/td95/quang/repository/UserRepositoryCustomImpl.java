package td95.quang.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import td95.quang.domain.User;

@Repository
@Transactional
public class UserRepositoryCustomImpl implements UserRepositoryCustom{

	@PersistenceContext
	private EntityManager em;
	@Override
	public List<User> getHotAuthors() {
		Query query = em.createQuery("select u from User u order by u.reputation desc");
		query.setMaxResults(10);
		return query.getResultList();
	}

}
