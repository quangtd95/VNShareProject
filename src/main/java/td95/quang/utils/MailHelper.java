package td95.quang.utils;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import td95.quang.domain.User;

public class MailHelper {
	
	
	public static SimpleMailMessage createMail(String to, String subject, String text) {
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(to);
		mailMessage.setSubject(subject);
		mailMessage.setText(text);
		return mailMessage;
	}

	public static SimpleMailMessage contructResetTokenEmail(
			String contextPath,String token,User user){
		String url = contextPath + "/changepassword?id=" +user.getId()
		+"&token="+token;
		return createMail(user.getEmail(),"[VNSHARE-APP]RESET PASSWORD",url);
	}
}
