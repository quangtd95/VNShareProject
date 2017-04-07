package td95.quang.service;

import static org.mockito.Matchers.contains;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import td95.quang.domain.Contact;
import td95.quang.utils.MailHelper;

@Service
public class ContactService {
	private static final String ADMIN_ADDRESS = "vnshare.tdq@gmail.com";
	private static final String SUBJECT = "[VNSHARE-APP] Y KIEN PHAN HOI";
	
	@Autowired
	private JavaMailSender mailSender;
	
	public void sendContact(Contact contact) {
		new Thread(() -> {
			mailSender.send(MailHelper.createMail(ADMIN_ADDRESS, SUBJECT, contact.toString()));
		}).start();

	}
}
