package td95.quang.service;

import java.util.Arrays;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import td95.quang.domain.PasswordResetToken;
import td95.quang.domain.User;
import td95.quang.repository.ResetPasswordRepository;

@Service
public class SecurityService {
	@Autowired
	private ResetPasswordRepository resetPasswordRepository;

	public String validatePasswordResetToken(int id, String token) {
		PasswordResetToken passwordResetToken = resetPasswordRepository.findByToken(token);
		if (passwordResetToken == null || passwordResetToken.getUser().getId() != id) {
			return "invalidateToken";
		}
		Calendar cal = Calendar.getInstance();
		if ((passwordResetToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
			return "expired";
		}
		User user = passwordResetToken.getUser();
		Authentication auth = new UsernamePasswordAuthenticationToken(user, null,
				Arrays.asList(new SimpleGrantedAuthority("CHANGE_PASSWORD_PRIVILEGE")));
		SecurityContextHolder.getContext().setAuthentication(auth);
		return null;
	}
}
