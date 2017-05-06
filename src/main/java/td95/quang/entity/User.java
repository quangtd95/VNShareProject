package td95.quang.entity;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;
import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
@Entity
@Table(name = "user")
public class User {
	@Id
	@GeneratedValue
	@Column(name = "id", nullable = false)
	private int id;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "email", nullable = false, unique = true)
	private String email;

	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "avatar")
	private String avatar;
	

	@Column(name = "reputation")
	private int reputation;

	@Column(name = "remember_token")
	private String rememberToken;

	@Column(name = "created_at")
	@Type(type = "timestamp")
	private Date createdAt;

	@Column(name = "updated_at")
	@Type(type = "timestamp")
	private Date updatedAt;
	
	@Column(name = "count_followers")
	@Type(type="int")
	private int countFollowers;

	@Column(name = "count_posts")
	@Type(type="int")
	private int countPosts;

	@Column(name = "count_tags")
	@Type(type="int")
	private int countTags;

	@Column(name = "count_followings")
	@Type(type="int")
	private int countFollowings;
	
	@ManyToMany
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles;

	@ManyToMany
	@JoinTable(name = "user_tag", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "tag_id"))
	private Set<Tag> tags;
	
	@OneToMany(mappedBy = "user",fetch=FetchType.EAGER)
	private List<Post> posts;
	
	@Transient
	private MultipartFile file;
	
	@Transient
	private String newPassword;
}
