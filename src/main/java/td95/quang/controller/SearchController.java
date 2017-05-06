package td95.quang.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import td95.quang.entity.Post;
import td95.quang.entity.Tag;
import td95.quang.entity.User;
import td95.quang.utils.SearchRDF;
import td95.quang.utils.SearchRDF.Action;

@Controller
public class SearchController {
		
	@Autowired
	private SearchRDF searchRDF;

	@GetMapping("/search")
	public String postSearch(@RequestParam String query,Model model) {
		SearchRDF.Action action;
		List list = searchRDF.handleInputSearch(query);
		List<User> users;
		List<Post> posts;
		List<Tag> tags;
		if (list.size() == 0){
			model.addAttribute("result", new ArrayList());
			model.addAttribute("type","DEFAULT");
			return "search";
		} else {
			Object object =list.get(0);
			if (object == null){
				System.err.println("error");
			} else {
				if (object instanceof User){
					action = Action.USER;
					users = new ArrayList<>();
					for (int i = 0 ; i < list.size(); i++){
						users.add((User) list.get(i));
					}
					System.out.println("result co kich thuoc "+users.size());
					model.addAttribute("result", users);
					model.addAttribute("type","USER");
					
				} else if (object instanceof Post){
					action = Action.POST;
					posts = new ArrayList<>();
					for (int i = 0 ; i < list.size(); i++){
						posts.add((Post) list.get(i));
					}
					model.addAttribute("result", posts);
					model.addAttribute("type","POST");
					System.out.println("result co kich thuoc "+posts.size());
					
				} else if (object instanceof Tag){
					action = Action.TAG;
					tags = new ArrayList<>();
					for (int i = 0 ; i < list.size(); i++){
						tags.add((Tag) list.get(i));
					}
					
					model.addAttribute("result", tags);
					model.addAttribute("type","TAG");
					System.out.println("result co kich thuoc "+tags.size());
				}
			}
			
			return "search";	
		}
		
	}
	
	
	
}
