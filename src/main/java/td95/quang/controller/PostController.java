package td95.quang.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ibm.icu.util.Calendar;

import td95.quang.domain.Post;
import td95.quang.domain.User;
import td95.quang.service.PostService;
import td95.quang.service.UserService;

@Controller
public class PostController {

	@Autowired
	private PostService postService;

	@Autowired
	private UserService userService;

	@GetMapping("/post/{id}")
	public String viewPost(@PathVariable int id, Model model) {
		Post post = postService.findById(id);
		post.setCountViews(post.getCountViews()+1);
		postService.save(post);
		model.addAttribute("post", post);
		return "viewpost";
	}

	@GetMapping("/post")
	public String getWritePost(Model model, Principal principal) {
		Post post = new Post();
		String email = principal.getName();
		User user = userService.findOne(email);
		post.setUser(user);
		model.addAttribute("post", new Post());
		return "post";
	}

	@PostMapping("/post")
	public @ResponseBody String postWritePost(Principal principal, @RequestBody Post post) {
		User userCurrent = userService.findOne(principal.getName());
		post.setUser(userCurrent);
		post.setCreatedAt(Calendar.getInstance().getTime());
		post.setUpdatedAt(Calendar.getInstance().getTime());
		postService.save(post);
		return String.valueOf(post.getId());
	}
	
}
