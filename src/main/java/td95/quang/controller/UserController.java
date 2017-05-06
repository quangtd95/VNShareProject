package td95.quang.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.Iterator;
import java.util.Set;

import org.hibernate.integrator.spi.Integrator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ibm.icu.util.Calendar;

import td95.quang.entity.Post;
import td95.quang.entity.User;
import td95.quang.service.UserService;

@Controller
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@GetMapping("/profile")
	public String getProfile(Principal principal) {
		if (principal != null) {
			String email = principal.getName();
			User user = userService.findOne(email);
			return "redirect:/u/" + user.getId();
		}
		else {
			return "redirect:/login";
		}
	}

	@GetMapping("/u/{id}")
	public String getUserProfile(@PathVariable int id, Model model) {
		User user = userService.findOne(id);
		model.addAttribute("user", user);
		System.out.println(user.getPosts().size()+"cos tong cong post");
		for (int i = 0 ; i < user.getPosts().size(); i++){
			System.out.println(user.getPosts().get(i).getId());
		}
		return "user";
	}
	
	@GetMapping("/setting")
	public String getSetting(){
		return "redirect:/setting/profile";
	}
	
	@GetMapping("/setting/profile")
	public String getSettingProfile(Principal principal,Model model){
		User user = userService.findOne(principal.getName());
		model.addAttribute("user",user);
		return "updateprofile";
	}
	@PostMapping("/setting/profile")
	public String postSettingProfile(User user,RedirectAttributes redirectAttributes,Principal principal){
		String UPLOADED_FOLDER = "E://DOCUMENTS//TUHOCLACHINH//JAVA//servletProject//VNShareProject//src//main/resources//static//images//";
		User userInDB = userService.findOne(principal.getName());
		try {
			MultipartFile file = user.getFile();
			if (!file.isEmpty()) {
				byte[] bytes = file.getBytes();
				Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
				Files.write(path, bytes);
				userInDB.setAvatar("/images/" + file.getOriginalFilename());
			}
			userInDB.setUpdatedAt(Calendar.getInstance().getTime());
			if (!user.getName().equals(userInDB.getName())){
				userInDB.setName(user.getName());
			}
			userService.save(userInDB);
			
			redirectAttributes.addFlashAttribute("success", "cập nhật thành công");
			return "redirect:/setting/profile";
		} catch (org.springframework.dao.DataIntegrityViolationException | IOException e) {
			redirectAttributes.addFlashAttribute("error", "cập nhật không thành công");
			return "redirect:/setting/profile";
		}
	}

	@GetMapping("/setting/password")
	public String getSettingPassword(Principal principal,Model model){
		model.addAttribute("user",userService.findOne(principal.getName()));
		return "updatepassword";
	}
	
	@PostMapping("/setting/password")
	public String postSettingPassword(Principal principal,Model model,User user,RedirectAttributes attributes){
		User userCurrent = userService.findOne(principal.getName());
		
		if (BCrypt.checkpw(user.getPassword(), userCurrent.getPassword())){
			userCurrent.setPassword(passwordEncoder.encode(user.getNewPassword()));
			userService.save(userCurrent);
			attributes.addFlashAttribute("success","Đổi mật khẩu thành công");
		} else {
			attributes.addFlashAttribute("error","Mật khẩu hiện tại không khớp");
			
		}
		return "redirect:/setting/password";
		
	}
}
