package td95.quang.domain;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

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

	@Column(name= "avatar")
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

	@ManyToMany
	@JoinTable(
			name = "user_role", 
			joinColumns = @JoinColumn(name = "user_id"), 
			inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles;
	
	@Column(name="count_follows")
	private int countFollows;
	
	@Column(name="count_posts")
	private int countPosts;

}
