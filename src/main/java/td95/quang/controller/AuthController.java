package td95.quang.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ibm.icu.util.Calendar;

import td95.quang.entity.Contact;
import td95.quang.entity.Post;
import td95.quang.entity.User;
import td95.quang.service.PageWrapper;
import td95.quang.service.PostService;
import td95.quang.service.UserService;

@Controller
public class AuthController {

	@Autowired
	private UserService userService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	private static String UPLOADED_FOLDER = "E://DOCUMENTS//TUHOCLACHINH//JAVA//servletProject//VNShareProject//src//main/resources//static//images//";

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
			MultipartFile file = user.getFile();
			if (file.isEmpty()) {
				redirect.addFlashAttribute("error", "Please select a file to upload");
				return "redirect:/register";
			}
			// Get the file and save it somewhere
			byte[] bytes = file.getBytes();
			Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
			Files.write(path, bytes);

			user.setAvatar("/images/" + file.getOriginalFilename());
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			user.setCreatedAt(Calendar.getInstance().getTime());
			user.setUpdatedAt(Calendar.getInstance().getTime());
			userService.save(user);
			redirect.addFlashAttribute("success", "Đăng ký thành công");
			return "redirect:/login";
		} catch (org.springframework.dao.DataIntegrityViolationException e) {
			e.printStackTrace();
			redirect.addFlashAttribute("error", "Email đã được sử dụng");
			return "redirect:/register";
		} catch (IOException e) {
			e.printStackTrace();
			redirect.addFlashAttribute("error", "Lỗi Upload avatar");
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

}
