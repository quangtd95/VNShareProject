package td95.quang.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import td95.quang.entity.Contact;

@Service
public class ContactService {
	
	@Autowired
	private JavaMailSender mailSender;
	
	public void sendContact(Contact contact){
		new Thread(() -> {
			SimpleMailMessage mailMessage = new SimpleMailMessage();
			mailMessage.setTo("vnshare.tdq@gmail.com");
			mailMessage.setSubject("Y KIEN PHAN HOI");
			mailMessage.setText(contact.toString());
			mailSender.send(mailMessage);				
		}).start();

	}
}
