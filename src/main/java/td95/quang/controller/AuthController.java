package td95.quang.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import td95.quang.domain.Contact;
import td95.quang.domain.Post;
import td95.quang.domain.User;
import td95.quang.service.PageWrapper;
import td95.quang.service.PostService;
import td95.quang.service.UserService;

@Controller
public class AuthController {

	@Autowired
	private UserService userService;

	@Autowired
	private PostService postService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@GetMapping("/login")
	public String login() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			return "redirect:/";
		}
		return "login";
	}

	@GetMapping("/register")
	public String getRegister(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			return "redirect:/";
		}
		model.addAttribute("user", new User());
		return "register";
	}

	@PostMapping("/register")
	public String postRegister(User user, BindingResult result, RedirectAttributes redirect) {
		if (result.hasErrors()) {
			List<ObjectError> errors = result.getAllErrors();
			for (ObjectError error : errors) {
				System.out.println(error.toString());
			}
			return "register";
		}
		try {
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			userService.save(user);
			redirect.addFlashAttribute("success", "Đăng ký thành công");
			return "redirect:/login";
		} catch (org.springframework.dao.DataIntegrityViolationException e) {
			e.printStackTrace();
			redirect.addFlashAttribute("error", "Email đã được sử dụng");
			return "redirect:/register";
		}

	}

	@GetMapping("/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response, Model model,
			RedirectAttributes redirectAttributes) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		redirectAttributes.addFlashAttribute("logout", "Bạn đã đăng xuất");
		return "redirect:/login";
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
