package td95.quang.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "Tag")
public class Tag {
	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;

	@Column(name = "name")
	private String name;

	@Column(name = "count_post")
	private int countPost;

	@Column(name = "count_follow")
	private int countFollow;

	@Column(name = "image")
	private String image;

	@ManyToMany(mappedBy = "tags")
	private Set<User> users;
	
	@ManyToMany
	@JoinTable(
			name="post_tag",
			joinColumns= @JoinColumn(name="tag_id"),
			inverseJoinColumns = @JoinColumn(name="post_id")
			)
	private Set<Post> posts;
}