package td95.quang.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	public void sendMail(SimpleMailMessage mailMessage){
		new Thread(()->javaMailSender.send(mailMessage)).start();
	}
	
}
