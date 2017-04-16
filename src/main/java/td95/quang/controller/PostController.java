package td95.quang.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

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
		post.setCountViews(post.getCountViews() + 1);
		postService.save(post);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		model.addAttribute("own", false);
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			if (post.getUser().getEmail().equals(authentication.getName())) {
				model.addAttribute("own", true);
			}
			;
		}
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

	@GetMapping("/edit/{id}")
	public String getEdit(Principal principal, @PathVariable int id, Model model) {
		Post post = postService.findById(id);
		model.addAttribute("post", post);
		if (principal == null) {
			model.addAttribute("error", "Bạn không có quyền chỉnh sửa bài viết này");
			System.out.println(1);
		} else if (!post.getUser().getEmail().equals(principal.getName())) {
			model.addAttribute("error", "Bạn không có quyền chỉnh sửa bài viết này");
			System.out.println(2);

		} else {
			model.addAttribute("success", "success");
			System.out.println(3);
		}
		return "editpost";
	}

	@PostMapping("/edit")
	public @ResponseBody String postEdit(Principal principal, @RequestBody Post post) {
		int id = post.getId();
		Post postInDatabase = postService.findById(id);
		postInDatabase.setTitle(post.getTitle());
		postInDatabase.setImageCover(post.getImageCover());
		postInDatabase.setContent(post.getContent());
		postInDatabase.setUpdatedAt(Calendar.getInstance().getTime());
		postService.save(postInDatabase);
		return "ok";
	}
}
