package td95.quang.config;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.hp.hpl.jena.datatypes.xsd.XSDDatatype;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;

import td95.quang.entity.Post;
import td95.quang.entity.Tag;
import td95.quang.entity.User;
import td95.quang.repository.UserRepository;
import td95.quang.utils.SearchRDF;

@Component
public class DataSeedingListener implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	private UserRepository userRepository;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent arg0) {

		createRDF((List<User>) userRepository.findAll());
	}

	public static Date parseDate(String date) {
		try {
			return new SimpleDateFormat("yyyy-MM-dd").parse(date);
		} catch (Exception e) {
			return null;
		}
	}

	public static void createRDF(List<User> users) {
		String path = System.getProperty("user.dir").toString() + "\\storage\\rdf\\db.rdf";
		Model model = ModelFactory.createDefaultModel();
		String uri = "http://vnshare.com/user/#";

		for (int i = 0; i < users.size(); i++) {
			User user = users.get(i);
			String uriUser = "http://vnshare.com/user/" + user.getId();

			Property countPosts = model.createProperty(uri + "countPosts");
			Property email = model.createProperty(uri + "email");
			Property reputation = model.createProperty(uri + "reputation");
			Property name = model.createProperty(uri + "name");
			Property hasPost = model.createProperty(uri + "hasPost");

			Resource rs_user = model.createResource(uriUser)
					.addProperty(countPosts, user.getCountPosts() + "", XSDDatatype.XSDdecimal)
					.addProperty(reputation, user.getReputation() + "", XSDDatatype.XSDdecimal)
					.addProperty(name, user.getName()).addProperty(email, user.getEmail());

			List<Post> posts = user.getPosts();
			if (!checkExist(SearchRDF.USER, user.getName())) {
				SearchRDF.USER.add(user.getName());
			}
			for (int j = 0; j < posts.size(); j++) {
				Post post = posts.get(j);
				String uriPost = uri + "post" + post.getId();
				Property idPost = model.createProperty(uri+"postId");
				Property titlePost = model.createProperty(uri + "postTitle");
				Property countViews = model.createProperty(uri + "countViews");
				Property points = model.createProperty(uri + "points");
				Resource rsPost = model.createResource(uriPost)
						.addProperty(idPost, post.getId()+"",XSDDatatype.XSDdecimal)
						.addProperty(countViews, post.getCountViews() + "", XSDDatatype.XSDdecimal)
						.addProperty(points, post.getPoints() + "", XSDDatatype.XSDdecimal)
						.addProperty(titlePost, post.getTitle());
				Property hasTag = model.createProperty(uri + "hasTag");
				
				if (!checkExist(SearchRDF.POST, post.getTitle())) {
					SearchRDF.POST.add(post.getTitle());
				}
				
				Set<Tag> tags = post.getTags();
				Iterator<Tag> it = tags.iterator();
				while (it.hasNext()) {
					Tag tag = it.next();
					String uriTag = uri + "tag" + tag.getId();
					Property idTag = model.createProperty(uri+"tagId");
					Property titleTag = model.createProperty(uri + "tagTitle");
					Resource rsTag = model.createResource(uriTag).addProperty(titleTag, tag.getName())
							.addProperty(idTag, tag.getId()+"",XSDDatatype.XSDdecimal)
							.addProperty(countPosts, tag.getCountPost() + "", XSDDatatype.XSDdecimal);
					rsPost.addProperty(hasTag, rsTag);
					if (!checkExist(SearchRDF.TAG, tag.getName())) {
						SearchRDF.TAG.add(tag.getName());
					}
				}
				rs_user.addProperty(hasPost, rsPost);
			}
		}

		model.setNsPrefix("vnshare", uri);
		FileOutputStream fos = null;
		try {
			File file = new File(path);
			fos = new FileOutputStream(file);
			if (!file.exists()) {
				file.createNewFile();
			}
			model.write(fos, "RDF/XML");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private static boolean checkExist(List<String> vocabulary, String str) {
		for (String item : vocabulary) {
			if (item.toLowerCase().equals(str.toLowerCase())) {
				return true;
			}
		}
		return false;
	}

}