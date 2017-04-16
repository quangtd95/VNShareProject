package td95.quang.controller;

import java.util.Iterator;
import java.util.Set;

import org.hibernate.integrator.spi.Integrator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import td95.quang.domain.Post;
import td95.quang.domain.User;
import td95.quang.service.UserService;

@Controller
public class UserController {
	@Autowired 
	private UserService userService;
	
	@GetMapping("/u/{id}")
	public String getUserProfile(@PathVariable int id,Model model) {
		User user = userService.findOne(id);
		model.addAttribute("user",user);
		return "user";
	}

	 
}
