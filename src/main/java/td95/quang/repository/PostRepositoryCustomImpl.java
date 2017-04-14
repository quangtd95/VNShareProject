package td95.quang.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import td95.quang.domain.Post;

@Repository
@Transactional
public class PostRepositoryCustomImpl implements PostRepositoryCustom{

	@PersistenceContext
	private EntityManager em;
	@Override
	public List<Post> getHotPosts() {
		Query query = em.createQuery("select p from Post p order by p.countViews desc");
		query.setMaxResults(10);
		return query.getResultList();
	}
}
