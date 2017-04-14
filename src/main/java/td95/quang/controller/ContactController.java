package td95.quang.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import td95.quang.domain.Contact;
import td95.quang.service.ContactService;

@Controller
public class ContactController {

	@Autowired
	private ContactService contactService;

	/*
	 * @RequestMapping(value = "/contact", method = RequestMethod.POST)
	 * public @ResponseBody String postContact(@RequestBody Contact contact) {
	 * contactService.sendContact(contact); return contact.toString(); }
	 */

	@PostMapping("/contact")
	public @ResponseBody String postContact(@RequestBody Contact contact) {
		contactService.sendContact(contact);
		return "OK";
	}

}
