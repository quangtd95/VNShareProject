package td95.quang.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import td95.quang.entity.Vote;

public interface VoteRepository extends JpaRepository<Vote, Integer>{
	Vote findTopByUserIdAndPostId(int userId, int postId);
}
