package td95.quang.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import td95.quang.domain.Contact;
import td95.quang.domain.Post;
import td95.quang.domain.Tag;
import td95.quang.domain.User;
import td95.quang.service.PageWrapper;
import td95.quang.service.TagService;
import td95.quang.service.UserService;

@Controller
public class MainController {
	
	@Autowired
	private TagService tagService;
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/tag")
	public String getTag(Pageable pageable,Model model){
		PageWrapper<Tag> page = new PageWrapper<Tag>(tagService.findAll(pageable), "/");
		model.addAttribute("page",page);
		return "tag";
	}
	
	@GetMapping("/author")
	public String getAuthor(Pageable pageable,Model model){
		PageWrapper<User> page = new PageWrapper<User>(userService.findAll(pageable), "/");
		model.addAttribute("page",page);
		return "author";
	}
	
	@GetMapping("/404")
	public String getError(){
		return "/error/404";
	}

	
}
