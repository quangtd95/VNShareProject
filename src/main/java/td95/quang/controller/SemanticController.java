package td95.quang.controller;


import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;

import td95.quang.entity.User;
import td95.quang.service.UserService;

@Controller
public class SemanticController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/semantic")
	public String getSemantic(Model model){
		return "semantic";
	}
	
	@PostMapping("/semantic")
	public @ResponseBody String postSemantic(@RequestBody String p_query,Model model,Principal principal){
		String _query ="prefix uni: <vnshare.net:3000/demo/university.owl#>\n"
				+"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n"
				+"SELECT ?student\n"
				+"WHERE {\n"
				  +"?student uni:studies uni:M204\n"
				+"}\n"
				+"LIMIT 25\n";
					        String query="prefix owl: <vnshare.net:3000/demo/university.owl#> \n"
					        		+ _query;
						QueryExecution qe = QueryExecutionFactory.sparqlService("http://localhost:3030/ds/query",
								query);
						ResultSet results = qe.execSelect();
						ResultSetFormatter.out(System.out, results);	  
						qe.close();
						List<String> list = results.getResultVars();
						model.addAttribute("result", list.get(0));
						ResponseEntity<User> entity = new ResponseEntity<User>(userService.findOne(principal.getName()), HttpStatus.OK);
		return  list.get(0);
	}
	
}
