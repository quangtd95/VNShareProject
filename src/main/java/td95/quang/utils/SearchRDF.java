package td95.quang.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.util.FileManager;

import td95.quang.entity.Post;
import td95.quang.entity.Tag;
import td95.quang.entity.User;
import td95.quang.service.PostService;
import td95.quang.service.TagService;
import td95.quang.service.UserService;

@Component
public class SearchRDF {

	@Autowired
	private PostService postService;

	@Autowired
	private UserService userService;

	@Autowired
	private TagService tagService;

	public static String[] WHO = { "ai", "nguoi", "người", "user", "who" };
	public static String[] WHICHPOST = { "bài nào", "bai nao","bai viet nao","bài viết nào","bài viết","bai viet","bai gi","bài gì" };
	public static String[] WHICHTAG = { "tag nao", "tag nào","tag gi","tag gì", "thể loại nào", "the loai nao","chu de nao","chủ đề nào","the loai gi","chu de gi" };
	public static List<String> USER = new ArrayList<String>();
	public static List<String> POST = new ArrayList<String>();
	public static List<String> TAG = new ArrayList<String>();
	

	private static String path = System.getProperty("user.dir").toString() + "\\storage\\rdf\\db.rdf";

	public enum Action {
		USER, POST, TAG, DEFAULT
	}
	

	public List querySearching(String queryStr, Action action, String location) {
		Model model = init();
		Query query = QueryFactory.create(queryStr);
		QueryExecution qexec = QueryExecutionFactory.create(query, model);
		String uri = "http://vnshare.com/user/#";
		List result = new ArrayList();

		try {
			ResultSet results = qexec.execSelect();
			while (results.hasNext()) {
				QuerySolution soln = results.nextSolution();
				Resource r = null;
				if (action == Action.USER) {
					r = soln.getResource("user");
					Property emailUser = model.createProperty(uri + "email");
					String email = r.getProperty(emailUser).getString();
					User user = userService.findOne(email);
					System.out.println(user.getEmail() +" --- "+ email);
					result.add(user);
				} else if (action == Action.POST) {
					r = soln.getResource("post");
					Property idPost = model.createProperty(uri + "postId");
					int postId = r.getProperty(idPost).getInt();
					Post post = postService.findById(postId);
					System.out.println(post.getTitle() +" --- "+ postId);
					result.add(post);
				} else if (action == Action.TAG) {
					r = soln.getResource("tag");
					Property idTag = model.createProperty(uri + "tagId");
					int tagId = r.getProperty(idTag).getInt();
					Tag tag = tagService.findById(tagId);
					System.out.println(tag.getName() +" --- "+ tagId);
					result.add(tag);
				}


			}
		} catch (Exception e){
			e.printStackTrace();
		}finally {
			qexec.close();
		}
		System.out.println("result size " + result.size());		
		return result;
	}
	
	public List handleInputSearch(String inputStr) {
		Action action = null;
		String[] input = inputStr.split(" ");
		String query = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n"
				+ "prefix vns: <http://vnshare.com/user/#> \n" + "SELECT * Where { \n";
		if (checkAction(WHO, input)) {
			action = Action.USER;
		} else if (checkAction(WHICHPOST, inputStr) != null) {
			action = Action.POST;
		} else if (checkAction(WHICHTAG, inputStr) != null) {
			action = Action.TAG;
		} else {
			action = Action.DEFAULT;
		}
		String str = checkVocabulary(USER, inputStr);
		if (str != null) {
			query += " ?user vns:name \"" + str + "\" . \n";
			query +="?user vns:hasPost ?post .\n";
			query +="?post vns:hasTag ?tag .\n";
		}
		str = checkVocabulary(POST, input);
		if (str != null) {
			query +="?user vns:hasPost ?post .\n";
			query +="?post vns:hasTag ?tag .\n";
			query += " ?post vns:postTitle \"" + str + "\" . \n";
		}
		str = checkVocabulary(TAG, inputStr);
		if (str != null) {
			query +="?user vns:hasPost ?post .\n";
			query +="?post vns:hasTag ?tag .\n";
			query += " ?tag vns:tagTitle \"" + str + "\" . \n";
		}
		query += " }";
		System.out.println(query);
		return querySearching(query, action, str);
	}

	private static String checkVocabulary(List<String> vocabulary, String[] inputStr) {
		for (String input : inputStr) {
			for (String item : vocabulary) {
				if (input.toLowerCase().equals(item.toLowerCase())) {
					return item;
				}
			}
		}
		return null;
	}

	private static String checkVocabulary(List<String> vocabulary, String inputStr) {
		for (String item : vocabulary) {
			if (inputStr.toLowerCase().contains(item.toLowerCase())) {
				System.err.println("TU KHOA" + item);
				return item;
			}
		}
		return null;
	}

	private static boolean checkAction(String[] action, String[] inputStr) {
		for (int i = 0; i < inputStr.length; i++) {
			for (int j = 0; j < action.length; j++) {
				if (inputStr[i].toLowerCase().equals(action[j])) {
					return true;
				}
			}
		}
		return false;
	}

	private static String checkAction(String[] action, String inputStr) {
		for (String item : action) {
			if (inputStr.toLowerCase().contains(item)) {
				System.err.println("TU KHOAA:" + item);
				return item;
			}
		}
		return null;
	}

	// public static List<User> getPeopleMayKnow(String email) {
	// Model model = init();
	//
	// List<String> friends = getListFriendName(model, email);
	// List<User> peopleKnows = new ArrayList<User>();
	// List<String> mayKnow = new ArrayList<String>();
	//
	// ResultSet results;
	// for (int i = 0; i < friends.size(); i++) {
	// results = queryPeopleMayKnow(model, friends.get(i), email);
	// while (results.hasNext()) {
	// QuerySolution soln = results.nextSolution();
	// Resource r = (Resource) soln.getResource("r");
	// Literal name = soln.getLiteral("x");
	// String personEmail = name.toString();
	// if (!isFriend(friends, personEmail) && !isFriend(mayKnow, personEmail)) {
	// mayKnow.add(personEmail);
	// peopleKnows.add(getUserByResource(r));
	// }
	// }
	// }
	//
	// return peopleKnows;
	// }
	//
	// private static User getUserByResource(Resource r) {
	// int id = Integer.parseInt(r.toString().substring(25));
	// String email = r.getProperty(FOAF.accountName).getString();
	// String givenName = r.getProperty(FOAF.givenname).getString();
	// String familyName = r.getProperty(FOAF.family_name).getString();
	// Date born = null;
	// try {
	// String[] date = r.getProperty(FOAF.birthday).getString().split(" ");
	// born = new SimpleDateFormat("yyyy-MM-dd").parse(date[0]);
	// } catch (ParseException e) {
	// e.printStackTrace();
	// }
	// String address = r.getProperty(FOAF.based_near).getString();
	// String phone = r.getProperty(FOAF.phone).getString();
	// String avatar = r.getProperty(FOAF.img).getString();
	// User user = new User(id, email, givenName, familyName, born, address,
	// phone, avatar);
	// return user;
	// }
	//
	// private static boolean isFriend(List<String> friends, String person) {
	// for (String str : friends) {
	// if (str.equals(person)) {
	// return true;
	// }
	// }
	// return false;
	// }

	// private static ResultSet queryPeopleMayKnow(Model m, String email, String
	// user) {
	// String queryString = "PREFIX rdf:
	// <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
	// + "PREFIX foaf: <http://xmlns.com/foaf/0.1/> " + "SELECT * Where { " + "
	// ?person foaf:accountName \""
	// + email + "\" ." + " ?person foaf:knows ?r ." + " ?r foaf:accountName ?x
	// ." + " FILTER( ?x != \"" + user
	// + "\") " + "}";
	// Query query = QueryFactory.create(queryString);
	// QueryExecution qexec = QueryExecutionFactory.create(query, m);
	// ResultSet results = qexec.execSelect();
	// return results;
	// }

	// private static List<String> getListFriendName(Model model, String email)
	// {
	// List<String> friends = new ArrayList<String>();
	// String queryStr = "PREFIX rdf:
	// <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
	// + "PREFIX foaf: <http://xmlns.com/foaf/0.1/> " + "SELECT * Where { " + "
	// ?person foaf:accountName \""
	// + email + "\" ." + " ?person foaf:knows ?x ." + "}";
	// Query query = QueryFactory.create(queryStr);
	// QueryExecution qexec = QueryExecutionFactory.create(query, model);
	// try {
	// ResultSet results = qexec.execSelect();
	// while (results.hasNext()) {
	// QuerySolution soln = results.nextSolution();
	// Resource name = (Resource) soln.getResource("x");
	// String friend = name.getProperty(FOAF.accountName).getString();
	// friends.add(friend);
	// }
	// } finally {
	// qexec.close();
	// }
	//
	// return friends;
	// }

	private static Model init() {
		FileManager.get().addLocatorClassLoader(SearchRDF.class.getClassLoader());
		Model model = FileManager.get().loadModel(path);
		return model;
	}
}
