package td95.quang.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import td95.quang.domain.PasswordResetToken;

@Repository
public interface ResetPasswordRepository extends JpaRepository<PasswordResetToken, Integer>{
	PasswordResetToken findByToken(String token);
}
