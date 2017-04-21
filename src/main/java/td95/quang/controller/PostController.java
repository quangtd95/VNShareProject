package td95.quang.controller;



import java.security.Principal;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ibm.icu.util.Calendar;


import lombok.Data;
import td95.quang.entity.Post;
import td95.quang.entity.Tag;
import td95.quang.entity.User;
import td95.quang.entity.Vote;
import td95.quang.service.PostService;
import td95.quang.service.TagService;
import td95.quang.service.UserService;

@Controller
public class PostController {

	@Autowired
	private PostService postService;

	@Autowired
	private UserService userService;

	@Autowired
	private TagService tagService;

	@GetMapping("/post/{id}")
	public String viewPost(@PathVariable int id, Model model) {
		Post post = postService.findById(id);
		post.setCountViews(post.getCountViews() + 1);
		postService.save(post);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		model.addAttribute("own", false);
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			User user = userService.findOne(authentication.getName());
			if (post.getUser().getEmail().equals(user.getEmail())) {
				model.addAttribute("own", true);
			}
			Vote vote = postService.findVote(user.getId(), post.getId());
			if (vote == null) {
				vote = new Vote();
				vote.setPostId(id);
				vote.setUserId(user.getId());
				vote.setStatus(Vote.NONE);
				postService.save(vote);
			}
			model.addAttribute("vote", vote);
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
		Set<Tag> tags = new HashSet<Tag>();
		String[] s = post.getBufferTags().split(" ");
		for (int i = 0; i < s.length; i++) {
			Tag tag = tagService.findByName(s[i]);
			if (tag != null) {
				tag.setCountPost(tag.getCountPost() + 1);
				tagService.save(tag);
				tags.add(tag);
				
			}
		}
		User userCurrent = userService.findOne(principal.getName());
		post.setTags(tags);
		post.setUser(userCurrent);
		post.setCreatedAt(Calendar.getInstance().getTime());
		post.setUpdatedAt(Calendar.getInstance().getTime());
		System.out.println(post.getImageCover());
		userCurrent.setCountPosts(userCurrent.getCountPosts() + 1);
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

	@PostMapping("/delete")
	public @ResponseBody ResponseEntity<String> deletePost(Principal principal, @RequestBody int id) {

		User user = userService.findOne(principal.getName());
		Post post = postService.findById(id);
		if (post.getUser().getEmail().equals(user.getEmail())) {
			postService.delete(id);
			user.setCountPosts(user.getCountPosts() - 1);
			userService.save(user);
			Set<Tag> tags = post.getTags();
			Iterator<Tag> it = tags.iterator();
			while(it.hasNext()){
				Tag tag = it.next();
				tag.setCountPost(tag.getCountPost() - 1);
				tagService.save(tag);
			}
			return new ResponseEntity<String>("xóa thành công", HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("bạn không có quyền xóa", HttpStatus.FORBIDDEN);
		}

	}

	@PostMapping("/vote")
	public @ResponseBody ResponseEntity<Vote> postVote(Principal principal, @RequestBody Vote vote) {
		Post post = postService.findById(vote.getPostId());
		Vote voteInDatabase = postService.findVote(vote.getUserId(), vote.getPostId());
		if (vote.getStatus() == Vote.VOTE_UP) {
			voteUp(post);
		}
		if (vote.getStatus() == Vote.VOTE_DOWN) {
			voteDown(post);
		}
		if (vote.getStatus() == Vote.NONE) {
			if (voteInDatabase.getStatus() == Vote.VOTE_UP) {
				voteDown(post);
			}
			if (voteInDatabase.getStatus() == Vote.VOTE_DOWN) {
				voteUp(post);
			}
		}
		voteInDatabase.setStatus(vote.getStatus());
		postService.save(post);
		postService.save(voteInDatabase);
		return new ResponseEntity<Vote>(vote, HttpStatus.OK);
	}

	private void voteUp(Post post) {
		post.setPoints(post.getPoints() + 1);
		post.getUser().setReputation(post.getUser().getReputation() + 1);
	}

	private void voteDown(Post post) {
		post.setPoints(post.getPoints() - 1);
		post.getUser().setReputation(post.getUser().getReputation() - 1);
	}

}
