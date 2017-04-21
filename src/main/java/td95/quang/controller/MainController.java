package td95.quang.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import td95.quang.entity.Contact;
import td95.quang.entity.Post;
import td95.quang.entity.Tag;
import td95.quang.entity.User;
import td95.quang.repository.TagRepository;
import td95.quang.service.PageWrapper;
import td95.quang.service.PostService;
import td95.quang.service.TagService;
import td95.quang.service.UserService;

@Controller
public class MainController {
	
	@Autowired
	private TagService tagService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PostService postService;
	
	@GetMapping("/tag")
	public String getTag(Pageable pageable,Model model){
		PageWrapper<Tag> page = new PageWrapper<Tag>(tagService.findAll(pageable), "/tag");
		model.addAttribute("page",page);
		return "tag";
	}
	
	@GetMapping("/tag/{id}")
	public String getTagDetail(@PathVariable int id,Model model){
		Tag tag = tagService.findById(id);
		model.addAttribute("tag",tag);
		return "tagDetail";
	}
	
	
	@GetMapping("/author")
	public String getAuthor(Pageable pageable,Model model){
		PageWrapper<User> page = new PageWrapper<User>(userService.findAll(pageable), "/author");
		model.addAttribute("page",page);
		return "author";
	}
	
	@GetMapping("/404")
	public String getError(){
		return "/error/404";
	}
	
	@GetMapping("/")
	public String getHome(Pageable pageable, Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			User user = userService.findOne(auth.getName());
			model.addAttribute("user", user);
		}
		PageWrapper<Post> page = new PageWrapper<Post>(postService.findAll(pageable), "/");
		model.addAttribute("page",page);
		
		model.addAttribute("hotAuthors", userService.getHotAuthors());
		model.addAttribute("hotPosts",postService.getHotPosts());
		model.addAttribute("contact",new Contact());
		return "home";
	}

	
}
